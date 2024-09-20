package com.flameshine.crypto.helper.api.entity;

import java.util.List;
import java.util.Optional;
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

    // TODO: encrypt/secure the field below

    @Column(name = "api_key", unique = true, nullable = false)
    private String apiKey;

    @Column(name = "secret_key", unique = true, nullable = false)
    private String secretKey;

    @OneToMany(mappedBy = "account")
    private Set<Order> orders;

    public static Optional<Account> findByTelegramUserIdOptional(Long telegramUserId) {
        return find("telegramUserId", telegramUserId).firstResultOptional();
    }

    public static List<Account> findAllByTelegramUserId(Long telegramUserId) {
        return find("telegramUserId", telegramUserId).list();
    }
}