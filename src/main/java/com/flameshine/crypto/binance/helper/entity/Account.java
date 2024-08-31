package com.flameshine.crypto.binance.helper.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "telegram_user_id", unique = true, nullable = false)
    private Long telegramUserId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    // TODO: encrypt/secure the field below

    @Column(name = "binance_api_key", unique = true, nullable = false)
    private String binanceApiKey;

    @OneToMany(mappedBy = "account")
    private Set<StopOrder> orders;
}