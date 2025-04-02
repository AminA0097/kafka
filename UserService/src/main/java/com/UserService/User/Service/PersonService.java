package com.UserService.User.Service;

import com.UserService.User.Dto.PersonDto;
import com.UserService.User.Dto.PersonForm;
import com.UserService.User.Dto.PersonResponse;
import com.UserService.User.Dto.RegisterForm;
import org.apache.kafka.common.protocol.types.Field;

public interface PersonService {
    PersonResponse<PersonDto> getAll(Integer pageSize, Integer pageNumber);
    void register(RegisterForm registerForm)throws Exception;
    void sendmsg(String msg);
}
