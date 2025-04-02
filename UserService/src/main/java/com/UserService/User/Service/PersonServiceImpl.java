package com.UserService.User.Service;

import com.UserService.User.Validation.PassWordConfig;
import com.UserService.User.Dto.*;
import com.UserService.User.Entities.PersonEntity;
import com.UserService.User.Entities.UserEntity;
import com.UserService.User.Repo.PersonRepo;
import com.UserService.User.Repo.UserRepo;
import com.UserService.User.Validation.PassWordValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.UserService.User.Validation.EmailValidator;



@Service

public class PersonServiceImpl implements PersonService {
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
    public PersonResponse<PersonDto> getAll(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber -1 , pageSize);
        Page<PersonEntity> all = personRepo.findAll(pageable);
        return personMapper.toEntityResponse(all);
    }
    @Override
    @Transactional
    public void register(RegisterForm registerForm) throws Exception{
        PersonEntity person = personRepo.findUser(registerForm.getEmail());
        UserEntity user = userRepo.findUserByUserName(registerForm.getUserName());
        UserEntity userEntity = new UserEntity();
        PersonEntity personEntity = new PersonEntity();
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
    }
    @Override
    public void sendmsg(String msg){
        kafkaTemplate.send("test-group", msg);
    }

}
