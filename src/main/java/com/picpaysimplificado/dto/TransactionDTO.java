package com.picpaysimplificado.dto;


import java.math.BigDecimal;

public record TransactionDTO(BigDecimal amount, Long payer, Long payee) {
}
