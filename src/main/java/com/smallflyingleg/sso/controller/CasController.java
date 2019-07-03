package com.smallflyingleg.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController
@Controller
@RequestMapping("/cas")
public class CasController {

    @GetMapping
    public String cas(){
        return "1232132";
    }


    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
