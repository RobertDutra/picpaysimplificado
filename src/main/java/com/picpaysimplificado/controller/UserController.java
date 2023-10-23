package com.picpaysimplificado.controller;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Serviço para cadastrar usuário.")
    public User save(@RequestBody @Valid UserDTO user) {
        return service.create(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Serviço para buscar usuário por id.")
    public User findByid(@PathVariable Long id) throws EntityNotFoundException {
        return service.findUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Serviço para atualizar usuário por id.")

    public UserDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO user) throws EntityNotFoundException {
        return service.update(id, user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Serviço para listar usuários.")
    public List<User> findAll() {
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Serviço para deletar usuário por id.")
    void delete(@PathVariable Long id) throws EntityNotFoundException {
        service.delete(id);
    }
}
