package com.UserService.User.Service;
import com.UserService.User.Configs.DigitCode;
import com.UserService.User.Forms.*;
import com.UserService.User.Mapper.EmailTemp;
import com.UserService.User.Res.AnyEntityResponse;
import com.UserService.User.Validation.PassWordConfig;
import com.UserService.User.Dto.*;
import com.UserService.User.Entities.PersonEntity;
import com.UserService.User.Entities.UserEntity;
import com.UserService.User.Repo.PersonRepo;
import com.UserService.User.Repo.UserRepo;
import com.UserService.User.Validation.PassWordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.UserService.User.Validation.EmailValidator;



@Service

public class UserServiceImpl implements UserInterface {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PersonRepo personRepo;
    private final UserRepo userRepo;
    private final PersonMapper personMapper;
    private final PassWordConfig passWordConfig;
    private final EmailValidator emailValidator;
    private final PassWordValidator passWordValidator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DigitCode digitCode;

    public UserServiceImpl(
            PersonRepo personRepo,
            PersonMapper personMapper,
            UserRepo userRepo,
            PassWordConfig passWordConfig,
            EmailValidator emailValidator,
            PassWordValidator passWordValidator,
            KafkaTemplate<String, String> kafkaTemplate,
            DigitCode digitCode) {
        this.personRepo = personRepo;
        this.personMapper = personMapper;
        this.userRepo = userRepo;
        this.passWordConfig = passWordConfig;
        this.emailValidator = emailValidator;
        this.passWordValidator = passWordValidator;
        this.kafkaTemplate = kafkaTemplate;
        this.digitCode = digitCode;
    }

    @Override
    @Transactional
    public boolean register(RegisterForm registerForm) throws Exception{
//        Check For Existing
        PersonEntity person = personRepo.findUser(registerForm.getEmail());
        UserEntity user = userRepo.findUserByUserName(registerForm.getUserName());
        if(person != null) {
            throw new Exception("This person already exists");
        }
        if(user != null){
            throw new Exception("This username already exists");
        }
//        Validate Inputs
        if(!emailValidator.isValid(registerForm.getEmail(),null)){
            throw new Exception("Invalid email");
        }
        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }
        if(!passWordValidator.isValid(registerForm.getPassword(),null)){
            throw new Exception("Invalid password");
        }
//        Save PersonInfo
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(registerForm.getUserName());
        personEntity.setEmail(registerForm.getEmail());
        PersonEntity PersonRes = personRepo.save(personEntity);
//        Save User
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(registerForm.getUserName());
        userEntity.setEnabled(false);
        userEntity.setPersonEntity(PersonRes);
        userEntity.setPassWord(passWordConfig.PassWordConfig().encode(registerForm.getPassword()));
        userRepo.save(userEntity);
//        Send Confirm Code
        sendEmail(registerForm.getEmail(),"OTP");
        return true;
    }
    @Override
    public boolean sendEmail(String email,String reason) throws Exception {
        String subject = EmailTemp.getSubject(reason);
        String body = EmailTemp.getBody(reason);
        if(reason.equals("OTP")){
            String content = digitCode.getCode(email);
            body = body.replace("OTPCODE",content);
        }
        KafkaMsg kafkaMsg = new KafkaMsg();
        kafkaMsg.setSubject(subject);
        kafkaMsg.setBody(body);
        String msg = kafkaMsg.toString();
        try {
            kafkaTemplate.send("incomingTopic",email,msg);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyEmail(VerifyForm verifyForm) throws Exception {
        if(digitCode.verify(verifyForm.getEmail(),verifyForm.getCode())){
            UserEntity user = userRepo.findUserByUserName(verifyForm.getUserName());
            user.setEnabled(true);
            userRepo.save(user);
        };
        throw new Exception("Failed to verify email");
    }
    @Override
    public boolean changeUserStatus(String userName) {
        UserEntity user = userRepo.findUserByUserName(userName);
        user.setEnabled(!user.getEnabled());
        userRepo.save(user);
        log.info("User changed successfully");
        return true;
    }

    @Override
    public AnyEntityResponse<UserDto> getAllUsers(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber -1 , pageSize);
        Page<UserEntity> all = userRepo.findAll(pageable);
        return personMapper.toEntityResponse(all);
    }

    @Override
    public boolean login(loginForm loginForm) throws Exception {
        return false;
    }

    @Override
    public boolean forgotPassWord(ForgotPassWordForm forgotPassWordForm) throws Exception {
        return false;
    }
}
