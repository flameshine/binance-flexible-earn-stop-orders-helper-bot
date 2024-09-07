package com.flameshine.crypto.binance.helper.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stop_order") // "order" is a reserved keyword in SQL
@Data
public class StopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "target", unique = true, nullable = false)
    private BigDecimal target;

    // TODO: replace the field below with some Binance-provided enum if possible

    @Column(name = "base", unique = true, nullable = false)
    private String base;

    @Column(name = "quote", unique = true, nullable = false)
    private String quote;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}