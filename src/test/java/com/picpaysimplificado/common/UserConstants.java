package com.picpaysimplificado.common;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.dto.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserConstants {

    public static final UserDTO USER_DTO = new UserDTO("Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User USER = new User(1L,"Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User RICARDO = new User(1L, "Ricardo", "634734", "ricardo@gmail.com", "21542", new BigDecimal(100), UserType.LOJISTA);
    public static final User LUCAS = new User(2L, "Lucas", "5123214", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User ASTOR = new User(3L, "Astor", "32632623", "aston@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User USER_CPF = new User("teo", USER.getCpf(), "teo@gmail.com", "124", new BigDecimal(10), UserType.COMUM);
    public static final User USER_EMAIL = new User("teo", "1251242521", USER.getEmail(), "124", new BigDecimal(10), UserType.COMUM);
    public static final User LOJISTA = new User(2L,"Ricardo", "634734", "ricardo@gmail.com", "21542", new BigDecimal(100), UserType.LOJISTA);
    public static final UserDTO INVALID_USER_DTO = new UserDTO("", "", "", "", new BigDecimal(0), UserType.COMUM);
    public static final User PAYER = new User("Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User PAYEE = new User("Ricardo", "634734", "ricardo@gmail.com", "21542", new BigDecimal(100), UserType.LOJISTA);


    public static final List<User> USER_LIST = new ArrayList<>() {
        {
            add(RICARDO);
            add(LUCAS);
            add(ASTOR);
        }
    };
    public static final Transaction TRANSACTION = new Transaction(new BigDecimal(10), PAYER, PAYEE, LocalDateTime.now());
    public static final TransactionDTO TRANSACTION_DTO = new TransactionDTO(new BigDecimal(10), USER.getId(), LOJISTA.getId());

    public static final TransactionDTO TRANSACTION_WITH_INVALID_DATA= new TransactionDTO();
    public static final List<Transaction> TRANSACTION_LIST = new ArrayList<>() {
        {
            add(new Transaction(new BigDecimal(10), USER, LOJISTA, LocalDateTime.now()));
            add(new Transaction(new BigDecimal(10), RICARDO, LUCAS, LocalDateTime.now()));
            add(new Transaction(new BigDecimal(10), LUCAS, ASTOR, LocalDateTime.now()));
        }
    };

}
