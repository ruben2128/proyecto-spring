package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.Role;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.UserRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {

        Date creationDate = new Date();
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con este email");
        }else if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Ya existe un usuario con este nombre de usuario");
        }else if(user.getEmail() == null){
            throw new IllegalArgumentException("Email obligatorio");
        }else if(user.getUsername() == null){
            throw new IllegalArgumentException("Username obligatorio");
        }
        user.setRole(Role.USER);
        user.setCreationDate(creationDate);
        return userRepository.save(user);
    }

}
