package com.UserService.User.Forms;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
    private String name;
}
