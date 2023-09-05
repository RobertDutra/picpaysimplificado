package com.picpaysimplificado.dto;

import com.picpaysimplificado.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String nome, String cpf, String email, String senha, BigDecimal saldo, UserType userType) {
}
