package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    public static final String REDIRECT = "redirect:/";

    @GetMapping(value = "/report")
    public ModelAndView home() {
        return new ModelAndView(REDIRECT);
    }

    @GetMapping(value = "/swagger")
    public ModelAndView swagger() {
        return new ModelAndView(REDIRECT);
    }
}
