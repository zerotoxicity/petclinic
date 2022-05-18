package com.learning.spring.petclinic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHomepage(){
        return "homepage";
    }
}
