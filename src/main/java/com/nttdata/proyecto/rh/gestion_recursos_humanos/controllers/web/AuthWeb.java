package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserRegisterDto;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/auth")
public class AuthWeb {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder wBuilder;

    private final String apiUrl = "http://localhost:8080/api/v1/auth";

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegisterDto());
        return "registerUser"; // Nombre del archivo HTML sin extensión
    }

    @PostMapping("/register")
    public String postMethodName(@ModelAttribute("user") UserRegisterDto user, Model model) {

        try {
            ResponseEntity<ResponseDto> response = restTemplate.postForEntity(apiUrl +
                    "/register", user,
                    ResponseDto.class);

            System.out.println("username " + user.getUsername());
            if (response.getBody().getStatus() == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("error", response.getBody().getMessage());
            }
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo registrar el usuario");
        }
        return "registerUser";

    }

}
