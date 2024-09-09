package com.flameshine.crypto.binance.helper.entity;

import java.util.List;
import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Account extends PanacheEntity {

    @Column(name = "telegram_user_id", nullable = false)
    private Long telegramUserId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    // TODO: encrypt/secure the field below

    @Column(name = "binance_api_key", unique = true, nullable = false)
    private String binanceApiKey;

    @OneToMany(mappedBy = "account")
    private Set<StopOrder> orders;

    public static Account findByName(String name) {
        return find("name", name).firstResult();
    }

    public static List<Account> findAllByTelegramUserId(Long telegramUserId) {
        return find("telegramUserId", telegramUserId).list();
    }
}