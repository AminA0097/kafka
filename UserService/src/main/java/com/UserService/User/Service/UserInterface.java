package com.UserService.User.Service;

import com.UserService.User.Forms.*;
import com.UserService.User.Dto.UserDto;
import com.UserService.User.Res.UserResponse;

public interface UserInterface {
    boolean login(loginForm loginForm)throws Exception;
    boolean register(RegisterForm registerForm)throws Exception;
    boolean forgotPassWord(ForgotPassWordForm forgotPassWordForm)throws Exception;
    boolean changeUserStatus(String userName)throws Exception;
    UserResponse<UserDto> getAllUsers(Integer pageSize, Integer pageNumb)throws Exception;
    boolean sendEmail(String email,String reason)throws Exception;
    boolean verifyEmail(VerifyForm verifyForm)throws Exception;
}
