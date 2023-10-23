package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.interfaces.UserInterface;
import com.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;


@org.springframework.stereotype.Service
public class UserService implements UserInterface {

    @Autowired
    UserRepository repository;

    @Override
    public void validateTransaction(User payer, BigDecimal amount) throws EntityNotFoundException {

        if (payer.getUserType() == UserType.LOJISTA) {
            throw  new EntityNotFoundException("Lojista " + payer.getNome() + " não esta autorizado a fazer transação");
        }

        if (payer.getSaldo().compareTo(amount) < 0) {
            throw  new EntityNotFoundException("Saldo da conta com id " + payer.getId() + " insufiente!");
        }
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = new User(userDTO);
        return this.repository.save(user);
    }

    @Override
    public User findUserById(Long id) throws EntityNotFoundException {

        return this.repository.findUserById(id).orElseThrow(() -> new EntityNotFoundException ("Usuário com id " + id + " não encontrado!"));


    }

    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }

    @Override
    public UserDTO update(Long id , UserDTO userDto) throws EntityNotFoundException {
        User userById = findUserById(id);
        User user = new User(userDto);
        BeanUtils.copyProperties(user, userById, "id");
        save(userById);
        return userDto;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        findUserById(id);
        this.repository.deleteById(id);
    }

    @Override
    public void save(User user){
        this.repository.save(user);
    }


}
