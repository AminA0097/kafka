package com.UserService.User.Forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPassWordForm {
    private String email;
    private String userName;
    private String password;
    private String confirmPassword;
}
