package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.interfaces.UserInterface;
import com.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class UserService implements UserInterface {

    @Autowired
    UserRepository repository;

    @Override
    public User save(UserDTO userDTO) {
        User user = new User(userDTO);
        return repository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) throws EntityNotFoundException {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user;
        }
        else {
            throw  new EntityNotFoundException ("Usuário com id " + id + " não encontrado!");
        }
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public UserDTO update(Long id , UserDTO userDto) throws EntityNotFoundException {
        Optional<User> userOptional = findById(id);
        User user = new User(userDto);
        BeanUtils.copyProperties(user, userOptional.get(), "id");
        repository.save(userOptional.get());
        return userDto;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        findById(id);
        repository.deleteById(id);
    }
}
