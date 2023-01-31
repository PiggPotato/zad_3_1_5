package com.example.demosecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Смысловой нагрузки этот класс не несёт
 * Нужен, чтобы при запуске приложения сразу кидало на страницу авторизации
 */

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String auth(){
        return "redirect:/auth/login";
    }
}
