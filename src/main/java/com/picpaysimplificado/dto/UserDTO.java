package com.picpaysimplificado.dto;

import com.picpaysimplificado.domain.user.UserType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UserDTO(@NotBlank(message = "Nome deve ser preenchido!") String nome,@NotBlank(message = "Cpf deve ser preenchido!") String cpf,@NotBlank(message = "Email deve ser preenchido!") String email,@NotBlank(message = "Senha deve ser preenchido!") String senha, BigDecimal saldo, UserType userType) {
}
