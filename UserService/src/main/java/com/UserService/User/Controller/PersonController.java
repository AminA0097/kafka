package com.UserService.User.Controller;

import com.UserService.User.Dto.UserDto;
import com.UserService.User.Dto.PersonResponse;
import com.UserService.User.Dto.RegisterForm;
import com.UserService.User.Service.PersonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/person")
public class PersonController {
    private final PersonService personService;
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping("/getall")
    public PersonResponse<UserDto> getAll() {
        return personService.getAll(20,1);
    }
    @PostMapping("/register")
    public String register(@RequestBody RegisterForm registerForm)throws Exception{
        personService.register(registerForm);
        return String.format("Successfully registered: %s", registerForm.getName());
    }

}
