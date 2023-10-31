package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.picpaysimplificado.common.UserConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        assertEquals(USER_DTO.nome(), userArgumentCaptor.getValue().getNome());
        assertEquals(USER_DTO.cpf(), userArgumentCaptor.getValue().getCpf());
        assertEquals(USER_DTO.email(), userArgumentCaptor.getValue().getEmail());
        assertEquals(USER_DTO.senha(), userArgumentCaptor.getValue().getSenha());
        assertEquals(USER_DTO.saldo(), userArgumentCaptor.getValue().getSaldo());
        assertEquals(USER_DTO.userType(), userArgumentCaptor.getValue().getUserType());
    }

    @Test
    public void createUser_WithInvalid_ThrowsException(){
        when(userRepository.save(any())).thenThrow(ConstraintViolationException.class);

        assertThatThrownBy(() -> userService.create(INVALID_USER_DTO)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createUser_WithExistingCpf_ReturnsBadRequest() throws Exception{
        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> userService.create(USER_DTO)).isInstanceOf(DataIntegrityViolationException.class );

    }

    @Test
    public void getUser_ByExistingId_ReturnsUser() throws EntityNotFoundException {
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(USER));

        User user = userService.findUserById(1L);

        assertNotNull(user);
        assertEquals(USER, user );

        verify(userRepository, times(1)).findUserById(any());
    }

    @Test
    public void getUser_UnexistingId_ThrowsException(){
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUserById(1L)).isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).findUserById(1L);
    }

    @Test
    public void getAllUsers_ReturnsAllUsers(){
        when(userRepository.findAll()).thenReturn(USER_LIST);

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(3, users.size());
        assertEquals(RICARDO.getNome(), users.get(0).getNome());
        assertEquals(LUCAS.getNome(), users.get(1).getNome());
        assertEquals(ASTOR.getNome(), users.get(2).getNome());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void updateUser_WithValidData_ReturnsUser() throws EntityNotFoundException {
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(USER));
        when(userRepository.save(any())).thenReturn(USER);

        UserDTO userDTO = userService.update(1L, USER_DTO);
        assertEquals(USER.getNome(), userDTO.nome());
        assertEquals(USER.getCpf(), userDTO.cpf());
        assertEquals(USER.getEmail(), userDTO.email());
        assertEquals(USER.getSenha(), userDTO.senha());
        assertEquals(USER.getSaldo(), userDTO.saldo());
        assertEquals(USER.getUserType(), userDTO.userType());

        verify(userRepository, times(1)).findUserById(1L);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void updateUser_WithInvalidData_ThrowException(){
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(1L, USER_DTO)).isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).findUserById(1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void removeUser_WithExistingId_doesNotThrowAnyException(){
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(USER));

        assertThatCode(() -> userService.delete(1L)).doesNotThrowAnyException();

        verify(userRepository, times(1)).findUserById(1L);
    }

    @Test
    public void removeUser_WithUnexistingId_ThrowException(){
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.delete(1L)).isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).findUserById(1L);
        verify(userRepository, never()).save(any());
    }
}
