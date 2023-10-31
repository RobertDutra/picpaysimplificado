package com.picpaysimplificado.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDTO(@NotNull(message = "Valor não informado!") BigDecimal amount, @NotNull(message = "Id do pagador não informado!") Long payer, @NotNull(message = "Id do beneficiário não informado!") Long payee) {

    public TransactionDTO (){
        this(null, null,null);
    }
}
