package com.tudog.graphqldemo01.controller;

import com.tudog.graphqldemo01.model.common.WebResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenController {
    @GetMapping("/getCsrf")

    WebResult getCsrf() {
        return new WebResult("200", "Csrf token is responded in response's header");
    }
}