package com.UserService.User.Service;
import com.UserService.User.Dto.KafkaMsg;
import com.UserService.User.Dto.PersonDto;
import com.UserService.User.Dto.PersonResponse;
import com.UserService.User.Dto.RegisterForm;

public interface PersonService {
    PersonResponse<PersonDto> getAll(Integer pageSize, Integer pageNumber);
    void register(RegisterForm registerForm)throws Exception;
    void sendToEmail(KafkaMsg kafkaMsg)throws Exception;
}
