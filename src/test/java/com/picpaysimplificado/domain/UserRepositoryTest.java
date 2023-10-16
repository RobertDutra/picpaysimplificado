package com.picpaysimplificado.domain;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.picpaysimplificado.common.UserConstants.INVALID_USER;
import static com.picpaysimplificado.common.UserConstants.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    public void createUser_WithInvalidData_ThrowsException() {
        assertThatThrownBy(() -> userRepository.save(INVALID_USER)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createUser_WithExistingCpf_ThrowsException(){
        User user = testEntityManager.persistFlushFind(USER);
        testEntityManager.detach(user);
        user.setId(null);
        assertThatThrownBy(() -> userRepository.save(user)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void getUser_ByExistingId_ReturnsPlanet(){
        User user = testEntityManager.persistFlushFind(USER);

        User userFound = userRepository.findUserById(user.getId());

        assertThat(userFound).isNotNull();
        assertThat(userFound).isEqualTo(user);

    }

    @Test
    public void getUser_ByUnexistingId_ReturnsEmpty() {
        User user = userRepository.findUserById(1L);

        assertThat(user).isNull();
    }

    @Test
    public void listUsers_ReturnsUsers() {
        User user = testEntityManager.persistFlushFind(USER);

        List<User> userList = userRepository.findAll();

        assertThat(userList).isNotEmpty();
        assertThat(userList).hasSize(1);
        assertThat(userList.get(0)).isEqualTo(user);
    }

    @Test
    public void removeUser_WithExistingId_RemoveUserFromDatabase() {
        User user = testEntityManager.persistFlushFind(USER);

        userRepository.deleteById(user.getId());

        User removeUser = testEntityManager.find(User.class, user.getId());
        assertThat(removeUser).isNull();
    }

}
