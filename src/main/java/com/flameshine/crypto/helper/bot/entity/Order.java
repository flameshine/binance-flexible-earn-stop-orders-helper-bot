package com.flameshine.crypto.helper.bot.entity;

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

    @Column(name = "target", nullable = false)
    private BigDecimal target;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "key_id", nullable = false)
    private Key key;

    public static Optional<Order> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    public static List<Order> findAllByKeys(List<Key> keys) {
        return find("key in ?1", keys).list();
    }

    public enum Type {

        BUY,
        SELL;

        public static Type fromValue(String value) {

            for (var item : values()) {
                if (item.name().substring(0, 1).equalsIgnoreCase(value)) {
                    return item;
                }
            }

            throw new IllegalArgumentException("Invalid order type: " + value);
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}