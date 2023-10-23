package com.picpaysimplificado.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.picpaysimplificado.common.UserConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    @Test
    public void createUser_WithValidData_ReturnsCreated() throws Exception {
        when(service.create(USER_DTO)).thenReturn(USER);

        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(USER_DTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(USER.getNome()))
                .andExpect(jsonPath("$.cpf").value(USER.getCpf()))
                .andExpect(jsonPath("$.email").value(USER.getEmail()))
                .andExpect(jsonPath("$.senha").value(USER.getSenha()))
                .andExpect(jsonPath("$.saldo").value(USER.getSaldo()))
                .andExpect(jsonPath("$.userType").value(USER.getUserType().name()));


    }

    @Test
    public void createUser_WithInvalidData_ReturnsBadRequest() throws Exception{
        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(INVALID_USER_DTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUser_WithExistingCpf_ReturnsBadRequest() throws Exception{
        when(service.create(USER_DTO)).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(USER_DTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getUser_ByExistingId_ReturnsUser() throws Exception{
        when(service.findUserById(1L)).thenReturn(USER);

        mockMvc.perform(get("/user/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(USER.getNome()))
                .andExpect(jsonPath("$.cpf").value(USER.getCpf()))
                .andExpect(jsonPath("$.email").value(USER.getEmail()))
                .andExpect(jsonPath("$.senha").value(USER.getSenha()))
                .andExpect(jsonPath("$.saldo").value(USER.getSaldo()))
                .andExpect(jsonPath("$.userType").value(USER.getUserType().name()));
    }

    @Test
    public void getUser_ByUnexistingId_ReturnsNotFound() throws Exception{
        when(service.findUserById(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listUsers_ReturnsUsers() throws Exception {
        when(service.findAll()).thenReturn(USER_LIST);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].nome").value("Ricardo"))
                .andExpect(jsonPath("$[1].nome").value("Lucas"))
                .andExpect(jsonPath("$[2].nome").value("Astor"));

    }

    @Test
    public void removeUser_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeUser_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException("")).when(service).delete(1L);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNotFound());
    }

}
