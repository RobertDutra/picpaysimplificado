package com.picpaysimplificado.domain;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.repository.UserRepository;
import com.picpaysimplificado.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.picpaysimplificado.common.UserConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void validateTransaction_doNotThrowAnyException(){
        assertThatCode(() -> userService.validateTransaction(USER, new BigDecimal(10))).doesNotThrowAnyException();
    }

    @Test
    public void validateTransaction_withValidLojista_ThrowException(){
        assertThatThrownBy( () -> userService.validateTransaction(LOJISTA, new BigDecimal(10))).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void validateTransaction_withValidAmount_ThrowException(){
        assertThatThrownBy( () -> userService.validateTransaction(USER, new BigDecimal(1000))).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void createUser_WithValidData_ReturnsUser() {
        when(userRepository.save(any())).thenReturn(USER);

        User user = userService.create(USER_DTO);

        assertThat(user).isEqualTo(USER);
    }

    @Test
    public void createUser_WithInvalid_ThrowsException(){
        when(userRepository.save(INVALID_USER)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> userService.create(INVALID_USER_DTO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getUser_ByExistingId_ReturnsUser() throws EntityNotFoundException {
        when(userRepository.findUserById(1L)).thenReturn(USER);

        User user = userService.findUserById(1L);

        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(USER);
    }

    @Test
    public void getUser_UnexistingId_ThrowsException(){
        when(userRepository.findUserById(1L)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> userService.findUserById(1L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getAllUsers_ReturnsAllUsers(){
        when(userRepository.findAll()).thenReturn(List.of(USER));

        List<User> users = userService.findAll();

        assertThat(users).isNotNull();
        assertThat(users).hasSize(1);
        assertThat(users.get(0)).isEqualTo(USER);
    }

    @Test
    @DisplayName("")
    public void updateUser_WithValidData_ReturnsUser() throws EntityNotFoundException {
        when(userRepository.findUserById(any())).thenReturn(USER);
        when(userRepository.save(any())).thenReturn(USER);

        UserDTO userDTO = userService.update(1L, USER_DTO);
        assertEquals(userDTO.nome(), USER_DTO.nome());
    }

    @Test
    public void removeUser_WithExistingId_doesNotThrowAnyException(){
        when(userRepository.findUserById(1L)).thenReturn(USER);

        assertThatCode(() -> userService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removeUser_WithUnexistingId_ThrowException(){
        assertThatThrownBy(() -> userService.delete(1L)).isInstanceOf(EntityNotFoundException.class);
    }
}
