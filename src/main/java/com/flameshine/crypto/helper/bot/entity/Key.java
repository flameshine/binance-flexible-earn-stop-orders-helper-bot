package com.flameshine.crypto.helper.bot.entity;

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
@Table(name = "api_key") // "key" is a reserved keyword in SQL
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Key extends PanacheEntity {

    @Column(name = "telegram_user_id", nullable = false)
    private Long telegramUserId;

    @Column(name = "label", unique = true, nullable = false)
    private String label;

    // TODO: encrypt/secure the field below

    @Column(name = "value", unique = true, nullable = false)
    private String value;

    @OneToMany(mappedBy = "key")
    private Set<Order> orders;

    public static Optional<Key> findByLabelOptional(String label) {
        return find("label", label).firstResultOptional();
    }

    public static List<Key> findAllByTelegramUserId(Long telegramUserId) {
        return find("telegramUserId", telegramUserId).list();
    }
}