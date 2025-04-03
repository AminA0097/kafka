package com.UserService.User.Service;
import com.UserService.User.Dto.KafkaMsg;
import com.UserService.User.Dto.UserDto;
import com.UserService.User.Dto.PersonResponse;
import com.UserService.User.Dto.RegisterForm;

public interface PersonService {
    PersonResponse<UserDto> getAll(Integer pageSize, Integer pageNumber);
    void register(RegisterForm registerForm)throws Exception;
    void sendToEmail(KafkaMsg kafkaMsg)throws Exception;
    void makeEnable(String email);
}
