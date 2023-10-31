package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.picpaysimplificado.common.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    UserService userService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AuthorizeTransactionService authorizeTransactionService;

    @Mock
    NotificationService notificationService;

    @Autowired
    @InjectMocks
    TransactionService transactionService;


    @Test
    void createTransaction_WithTransactionAuthorized_ReturnsTransation() throws EntityNotFoundException {
        when(userService.findUserById(1L)).thenReturn(USER);
        when(userService.findUserById(2L)).thenReturn(LOJISTA);
        when(authorizeTransactionService.authorizeTransaction(any(), any())).thenReturn(true);

        Transaction transaction = transactionService.create(TRANSACTION_DTO);

        assertEquals(TRANSACTION_DTO.amount(), transaction.getAmount());
        assertEquals(TRANSACTION_DTO.payer(), transaction.getPayer().getId());
        assertEquals(TRANSACTION_DTO.payee(), transaction.getPayee().getId());

        verify(transactionRepository, times(1)).save(any());

        //USER.setSaldo(new BigDecimal(90));
        verify(userService, times(1)).save(USER);

        //LOJISTA.setSaldo(new BigDecimal(110));
        verify(userService, times(1)).save(LOJISTA);

        verify(notificationService, times(1)).sendNotification(USER, "Transação realizada com sucesso!");
        verify(notificationService, times(1)).sendNotification(LOJISTA, "Transação realizada com sucesso!");

    }

    @Test
    void createTransaction_WithTransactionNotAuthorized_ReturnsThrowsExaception() throws EntityNotFoundException {
        when(userService.findUserById(1L)).thenReturn(USER);
        when(userService.findUserById(2L)).thenReturn(LOJISTA);
        when(authorizeTransactionService.authorizeTransaction(any(), any())).thenReturn(false);

        assertThatThrownBy(() -> transactionService.create(TRANSACTION_DTO)).isInstanceOf(EntityNotFoundException.class);

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void getTransaction_ByExistingId_ReturnsTransation() throws EntityNotFoundException {
        when(transactionRepository.findTransactionById(1L)).thenReturn(Optional.of(TRANSACTION));

        Transaction transaction = transactionService.findById(1L);

        assertNotNull(transaction);
        assertEquals(TRANSACTION.getAmount(),transaction.getAmount());
        assertEquals(TRANSACTION.getPayer(), transaction.getPayer());
        assertEquals(TRANSACTION.getPayee(), transaction.getPayee());
        assertEquals(TRANSACTION.getTime(), transaction.getTime());

        verify(transactionRepository, times(1)).findTransactionById(any());

    }
    @Test
    void getTransaction_ByUnexistingId_ThrowsException() {
        when(transactionRepository.findTransactionById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.findById(1L)).isInstanceOf(EntityNotFoundException.class);

        verify(transactionRepository, times(1)).findTransactionById(1L);

    }

    @Test
    void getTransactions_GetAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(TRANSACTION_LIST);

        List<Transaction> transactionList = transactionService.transactions();

        assertFalse(transactionList.isEmpty());
        assertEquals(3, transactionList.size());
        assertEquals(TRANSACTION_LIST.get(0).getPayer(), transactionList.get(0).getPayer());
        assertEquals(TRANSACTION_LIST.get(1).getPayer(), transactionList.get(1).getPayer());
        assertEquals(TRANSACTION_LIST.get(2).getPayer(), transactionList.get(2).getPayer());

        verify(transactionRepository, times(1)).findAll();
    }

}