package com.picpaysimplificado.interfaces;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;

import java.util.List;

public interface TransactionInterface {

    Transaction create(TransactionDTO transaction) throws EntityNotFoundException;

    Transaction findById(Long id) throws EntityNotFoundException;

    List<Transaction> transactions();

}
