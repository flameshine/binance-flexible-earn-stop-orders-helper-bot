package com.flameshine.crypto.helper.api.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.flameshine.crypto.helper.api.enums.OrderType;

@Entity
@Table(name = "stop_order") // "order" is a reserved keyword in SQL
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Order extends PanacheEntity {

    @Column(name = "base", nullable = false)
    private String base;

    @Column(name = "quote", nullable = false)
    private String quote;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public static Optional<Order> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    public static List<Order> findAllByAccount(Account account) {
        return find("account", account).list();
    }
}