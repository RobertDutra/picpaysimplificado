package com.picpaysimplificado.common;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dto.UserDTO;

import java.math.BigDecimal;

public class UserConstants {

    public static final UserDTO USER_DTO = new UserDTO("Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User USER = new User("Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User USER_ID = new User(1L, "Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User LOJISTA = new User("Ricardo", "634734", "ricardo@gmail.com", "21542", new BigDecimal(100), UserType.LOJISTA);
    public static final User INVALID_USER = new User(1L, "", "", "", "", new BigDecimal(0), UserType.COMUM);
    public static final UserDTO INVALID_USER_DTO = new UserDTO("", "", "", "", new BigDecimal(0), UserType.COMUM);
}
