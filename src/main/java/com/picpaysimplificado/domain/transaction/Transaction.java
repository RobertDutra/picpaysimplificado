package com.picpaysimplificado.domain.transaction;

import com.picpaysimplificado.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @JoinColumn(name = "payer_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User payer;

    @JoinColumn(name = "payee_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User payee;

    private LocalDateTime time;

    public Transaction(BigDecimal amount, User payer, User payee, LocalDateTime time) {
        this.amount = amount;
        this.payer = payer;
        this.payee = payee;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
