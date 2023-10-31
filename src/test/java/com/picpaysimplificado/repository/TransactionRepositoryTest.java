package com.picpaysimplificado.repository;

import com.picpaysimplificado.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.picpaysimplificado.common.UserConstants.TRANSACTION;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository repository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void findTransactionById_ByExistingId_ReturnsTransacation() {
        Transaction transaction = testEntityManager.persistFlushFind(TRANSACTION);

        Optional<Transaction> transactionOptional = this.repository.findTransactionById(1L);

        assertNotNull(transactionOptional);
        assertEquals(transaction.getAmount(), transactionOptional.get().getAmount());
        assertEquals(transaction.getPayer(), transactionOptional.get().getPayer());
        assertEquals(transaction.getPayee(), transactionOptional.get().getPayee());
        assertEquals(transaction.getTime(), transactionOptional.get().getTime());
    }

    @Test
    void findTransactionById_ByUnexistingId_ReturnsEmpyt() {
        Optional<Transaction> transactionOptional = this.repository.findTransactionById(1L);

        assertTrue(!transactionOptional.isPresent());
    }
}