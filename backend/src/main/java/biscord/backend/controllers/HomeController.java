package biscord.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HomeController {

    @GetMapping("/serverTime")
    public String getServerTime() {
        return "현재 서버 시각: " + LocalDateTime.now();
    }
}
