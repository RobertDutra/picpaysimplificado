package com.picpaysimplificado.common;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dto.UserDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserConstants {

    public static final UserDTO USER_DTO = new UserDTO("Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);
    public static final User USER = new User("Lucas", "2187621", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM);

    public static final User USER_CPF = new User("teo", USER.getCpf(), "teo@gmail.com", "124", new BigDecimal(10), UserType.COMUM);

    public static final User USER_EMAIL = new User("teo", "1251242521", USER.getEmail(), "124", new BigDecimal(10), UserType.COMUM);

    public static final User LOJISTA = new User("Ricardo", "634734", "ricardo@gmail.com", "21542", new BigDecimal(100), UserType.LOJISTA);
    public static final User INVALID_USER = new User(1L, "", "", "", "", new BigDecimal(0), UserType.COMUM);
    public static final UserDTO INVALID_USER_DTO = new UserDTO("", "", "", "", new BigDecimal(0), UserType.COMUM);
    public static final List<User> USER_LIST = new ArrayList<>() {
        {
            add(new User(1L, "Ricardo", "634734", "ricardo@gmail.com", "21542", new BigDecimal(100), UserType.LOJISTA));
            add(new User(2L, "Lucas", "5123214", "lucas@gmail.com", "21542", new BigDecimal(100), UserType.COMUM));
            add(new User(3L, "Astor", "32632623", "aston@gmail.com", "21542", new BigDecimal(100), UserType.COMUM));
        }
    };

}
