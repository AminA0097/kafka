package com.UserService.User.Controller;

import com.UserService.User.Dto.PersonDto;
import com.UserService.User.Dto.PersonForm;
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
    public PersonResponse<PersonDto> getAll() {
        return personService.getAll(5,1);
    }
    @PostMapping("/register")
    public String register(@RequestBody RegisterForm registerForm)throws Exception{
        personService.register(registerForm);
        return String.format("Successfully registered: %s", registerForm.getName());
    }
    @GetMapping("/kafka")
    public String kafka() {
        personService.sendmsg("Amin");
        return "Successfully sendmsg";
    }

}
