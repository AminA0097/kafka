package com.UserService.User.Controller;

import com.UserService.User.Dto.UserDto;
import com.UserService.User.Forms.ForgotPassWordForm;
import com.UserService.User.Forms.VerifyForm;
import com.UserService.User.Forms.RegisterForm;
import com.UserService.User.Res.AnyEntityResponse;
import com.UserService.User.Service.UserInterface;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/person")
public class PersonController {
    private final UserInterface userInterface;
    public PersonController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }
    @PostMapping("/register")
    public String register(@RequestBody RegisterForm registerForm)throws Exception{
        userInterface.register(registerForm);
        return String.format("Successfully registered: %s", registerForm.getName());
    }
    @PostMapping("/resetpassword")
    public String resetPassWord(@RequestBody ForgotPassWordForm forgotPassWordForm)throws Exception{
        userInterface.forgotPassWord(forgotPassWordForm);
        return String.format("Successfully registered: %s", forgotPassWordForm.getUserName());
    }
    @PostMapping("/verify")
    public String verify(@RequestBody VerifyForm verifyForm )throws Exception{
        userInterface.verifyEmail(verifyForm);
        return String.format("Successfully registered: %s", verifyForm.getUserName());
    }
    @PostMapping("/changestatus")
    public String verify(@RequestParam String userName)throws Exception{
        userInterface.changeUserStatus(userName);
        return String.format("Successfully registered: %s", userName);
    }
    @GetMapping("/alluser")
    public AnyEntityResponse<UserDto> chabgeStatus()throws Exception{
        return userInterface.getAllUsers(10,1);
    }
}
