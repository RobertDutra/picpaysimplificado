package com.picpaysimplificado.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpaysimplificado.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.picpaysimplificado.common.UserConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TransactionService transactionService;

    TransactionControllerTest() {
    }

    @Test
    void createTransaction_WithValidData_ReturnsTransation() throws Exception {
        when(transactionService.create(TRANSACTION_DTO)).thenReturn(TRANSACTION);

        mockMvc.perform(post("/transaction").content(objectMapper.writeValueAsString(TRANSACTION_DTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(TRANSACTION.getAmount()))
                .andExpect(jsonPath("$.payer.nome").value(TRANSACTION.getPayer().getNome()))
                .andExpect(jsonPath("$.payee.nome").value(TRANSACTION.getPayee().getNome()));

    }

    @Test
    void createTransaction_WithInvalidData_ReturnsThrowsException() throws Exception {
        mockMvc.perform(post("/transaction").content(objectMapper.writeValueAsString(TRANSACTION_WITH_INVALID_DATA))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    void getAllTransactions_ReturnsAllTransactions() throws Exception{
        when(transactionService.transactions()).thenReturn(TRANSACTION_LIST);

        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].payer.nome").value("Lucas"))
                .andExpect(jsonPath("$[1].payer.nome").value("Ricardo"))
                .andExpect(jsonPath("$[2].payer.nome").value("Lucas"));
    }
}