package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import com.picpaysimplificado.interfaces.TransactionInterface;
import com.picpaysimplificado.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService implements TransactionInterface {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AuthorizeTransactionService authorizeTransactionService;

    @Override
    public Transaction create(TransactionDTO transaction) throws EntityNotFoundException {
        User payer = userService.findUserById(transaction.payer());
        User payee = userService.findUserById(transaction.payee());

        userService.validateTransaction(payer, transaction.amount());

        boolean isAuthorized = authorizeTransactionService.authorizeTransaction(payer, transaction.amount());
        if (!isAuthorized) {
            throw new EntityNotFoundException("Transação não autorizada!");
        }

        payer.setSaldo(payer.getSaldo().subtract(transaction.amount()));
        payee.setSaldo(payee.getSaldo().add(transaction.amount()));

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.amount());
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setTime(LocalDateTime.now());

        this.transactionRepository.save(newTransaction);
        this.userService.save(payer);
        this.userService.save(payee);

        this.notificationService.sendNotification(payer, "Transação realizada com sucesso!");
        this.notificationService.sendNotification(payee, "Transação realizada com sucesso!");

        return newTransaction;
    }

    @Override
    public Transaction findById(Long id) throws EntityNotFoundException {
        return this.transactionRepository.findTransactionById(id).orElseThrow(() -> new EntityNotFoundException("Transação com id " + id + " não encontrada!"));
    }

    @Override
    public List<Transaction> transactions() {
        return this.transactionRepository.findAll();
    }

}
