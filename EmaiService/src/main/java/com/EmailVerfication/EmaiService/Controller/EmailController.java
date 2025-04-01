package com.EmailVerfication.EmaiService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/email")
public class EmailController {
    @GetMapping("/confirm")
    public String confirm() {
        return "confirm";
    }
}
