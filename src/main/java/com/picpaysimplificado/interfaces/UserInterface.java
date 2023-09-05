package com.picpaysimplificado.interfaces;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserInterface {

    User save(UserDTO user);

    Optional<User> findById(Long id) throws EntityNotFoundException;

    List<User> findAll();

    UserDTO update(Long id, UserDTO userDto) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;
}
