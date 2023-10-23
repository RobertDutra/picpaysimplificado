package com.picpaysimplificado.repository;

import com.picpaysimplificado.domain.user.User;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static com.picpaysimplificado.common.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        USER.setId(null);
    }

    public UserRepositoryTest() {
    }

    @Test
    public void createUser_WithValidData_ReturnsUser(){
        User user = userRepository.save(USER);

        User userEntity = testEntityManager.find(User.class, user.getId());

        assertThat(userEntity).isNotNull();
        assertThat(user.getNome()).isEqualTo(userEntity.getNome());
        assertThat(user.getCpf()).isEqualTo(userEntity.getCpf());
        assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(user.getSenha()).isEqualTo(userEntity.getSenha());
        assertThat(user.getSaldo()).isEqualTo(userEntity.getSaldo());
        assertThat(user.getUserType()).isEqualTo(userEntity.getUserType());
    }

    @Test
    public void createUser_WithExistingCpf_ThrowsException(){
        User user = testEntityManager.persistFlushFind(USER);
        assertThatThrownBy(() -> userRepository.save(USER_CPF)).isInstanceOf(DataIntegrityViolationException.class);
    }
    @Test
    public void createUser_WithExistingEmail_ThrowsException(){
        User user = testEntityManager.persistFlushFind(USER);
        assertThatThrownBy(() -> userRepository.save(USER_EMAIL)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void getUser_ByExistingId_ReturnsUser(){
        User user = testEntityManager.persistFlushFind(USER);

        Optional<User> userFound = userRepository.findUserById(user.getId());

        assertNotNull(userFound);
        assertEquals(user.getNome(), userFound.get().getNome());
        assertEquals(user.getCpf(), userFound.get().getCpf());
        assertEquals(user.getEmail(), userFound.get().getEmail());
        assertEquals(user.getSenha(), userFound.get().getSenha());
        assertEquals(user.getSaldo(), userFound.get().getSaldo());
        assertEquals(user.getUserType(), userFound.get().getUserType());
    }

    @Test
    public void getUser_ByUnexistingId_ReturnsEmpty() {
        Optional<User> user = userRepository.findUserById(1L);

        assertFalse(user.isPresent());
    }

    @Test
    public void listUsers_ReturnsUsers() {
        User user = testEntityManager.persistFlushFind(USER);

        List<User> userList = userRepository.findAll();


        assertNotNull(userList);
        assertFalse(userList.isEmpty());
        assertEquals(user, userList.get(0));
        assertEquals(userList.size(), 1);
    }

    @Test
    public void removeUser_WithExistingId_RemoveUserFromDatabase() {
        User user = testEntityManager.persistFlushFind(USER);

        userRepository.deleteById(user.getId());

        User removeUser = testEntityManager.find(User.class, user.getId());
        assertNull(removeUser);
    }

}
