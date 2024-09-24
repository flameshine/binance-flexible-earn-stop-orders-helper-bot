package com.flameshine.crypto.helper.api.entity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    @Column(name = "api_key", unique = true, nullable = false)
    private String apiKey;

    @Column(name = "secret_key", unique = true, nullable = false)
    private String secretKey;

    @OneToMany(mappedBy = "account")
    private Set<Order> orders;

    /*
     * Implementing a real encryption mechanism here is a bit redundant, as API keys are IP-restricted.
     * Therefore, simple obfuscation using Base64 is applied.
     */

    @PrePersist
    @PreUpdate
    private void encodeKeys() {
        this.apiKey = Base64.getEncoder().encodeToString(apiKey.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @PostLoad
    private void decodeKeys() {
        this.apiKey = new String(Base64.getDecoder().decode(apiKey), StandardCharsets.UTF_8);
        this.secretKey = new String(Base64.getDecoder().decode(secretKey), StandardCharsets.UTF_8);
    }

    public static Optional<Account> findByTelegramUserIdOptional(Long telegramUserId) {
        return find("telegramUserId", telegramUserId).firstResultOptional();
    }
}