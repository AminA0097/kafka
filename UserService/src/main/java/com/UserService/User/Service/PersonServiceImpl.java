package com.UserService.User.Service;
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

public class PersonServiceImpl implements PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final PersonRepo personRepo;
    private final UserRepo userRepo;
    private final PersonMapper personMapper;
    private final PassWordConfig passWordConfig;
    private final EmailValidator emailValidator;
    private final PassWordValidator passWordValidator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public PersonServiceImpl(
            PersonRepo personRepo,
            PersonMapper personMapper,
            UserRepo userRepo,
            PassWordConfig passWordConfig,
            EmailValidator emailValidator,
            PassWordValidator passWordValidator,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.personRepo = personRepo;
        this.personMapper = personMapper;
        this.userRepo = userRepo;
        this.passWordConfig = passWordConfig;
        this.emailValidator = emailValidator;
        this.passWordValidator = passWordValidator;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public PersonResponse<UserDto> getAll(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber -1 , pageSize);
        Page<UserEntity> all = userRepo.findAll(pageable);
        return personMapper.toEntityResponse(all);
    }
    @Override
    @Transactional
    public void register(RegisterForm registerForm) throws Exception{
        PersonEntity person = personRepo.findUser(registerForm.getEmail());
        UserEntity user = userRepo.findUserByUserName(registerForm.getUserName());
        UserEntity userEntity = new UserEntity();
        PersonEntity personEntity = new PersonEntity();
        KafkaMsg kafkaMsg = new KafkaMsg();
        if(person != null) {
            throw new Exception("This person already exists");
        }
        if(user != null){
            throw new Exception("This username already exists");
        }
        if(!emailValidator.isValid(registerForm.getEmail(),null)){
            throw new Exception("Invalid email");
        }
        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }
        if(!passWordValidator.isValid(registerForm.getPassword(),null)){
            throw new Exception("Invalid password");
        }
            personEntity.setName(registerForm.getUserName());
            personEntity.setEmail(registerForm.getEmail());
            PersonEntity personEntity1 = personRepo.save(personEntity);
            userEntity.setUserName(registerForm.getUserName());
            userEntity.setEnabled(true);
            userEntity.setPersonEntity(personEntity1);
            userEntity.setPassWord(passWordConfig.PassWordConfig().encode(registerForm.getPassword()));
            userRepo.save(userEntity);
            kafkaMsg.setEmail(registerForm.getEmail());
            kafkaMsg.setStatus(0);
            kafkaMsg.setRead(0);
            kafkaMsg.setTopic("Unverified");
            sendToEmail(kafkaMsg);
    }

    @Override
    public void sendToEmail(KafkaMsg kafkaMsg) throws Exception {
        String msg = String.format("%s,%d,%d",
                kafkaMsg.getEmail(),
                kafkaMsg.getStatus(),
                kafkaMsg.getRead());
        kafkaTemplate.send(kafkaMsg.getTopic(),kafkaMsg.getEmail(),msg);

    }

    @Override
    public void makeEnable(String email) {
        String userName = "Amin";
        UserEntity user = userRepo.findUserByUserName(userName);
        user.setEnabled(true);
        userRepo.save(user);
        log.info(userName + " is enabled");
    }
}
