package com.flameshine.crypto.binance.helper.entity;

import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "stop_order") // "order" is a reserved keyword in SQL
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StopOrder extends PanacheEntity {

    // TODO: replace the field below with some Binance-provided enum if possible

    @Column(name = "base", unique = true, nullable = false)
    private String base;

    @Column(name = "quote", unique = true, nullable = false)
    private String quote;

    @Column(name = "target", unique = true, nullable = false)
    private BigDecimal target;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}