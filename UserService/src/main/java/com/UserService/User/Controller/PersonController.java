package com.UserService.User.Controller;

import com.UserService.User.Dto.UserDto;
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
    @PostMapping("/verifyAccount")
    public boolean chabgeStatus(@RequestBody VerifyForm verifyForm)throws Exception{
        return userInterface.verifyEmail(verifyForm);
    }
    @GetMapping("/alluser")
    public AnyEntityResponse<UserDto> chabgeStatus()throws Exception{
        return userInterface.getAllUsers(10,1);
    }
}
