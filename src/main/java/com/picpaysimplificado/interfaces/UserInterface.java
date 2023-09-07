package com.picpaysimplificado.interfaces;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface UserInterface {

    User create(UserDTO user);

    User findUserById(Long id) throws EntityNotFoundException;

    List<User> findAll();

    UserDTO update(Long id, UserDTO userDto) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;

    void validateTransaction(User payer , BigDecimal amount) throws EntityNotFoundException;

    void save(User user);
}
