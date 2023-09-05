package com.picpaysimplificado.controller;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody UserDTO user) {
        return service.save(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Optional<User> findByid(@PathVariable Long id) throws EntityNotFoundException {
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO user) throws EntityNotFoundException {
        return service.update(id, user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) throws EntityNotFoundException {
        service.delete(id);
    }
}