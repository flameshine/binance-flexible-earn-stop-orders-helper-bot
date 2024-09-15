package com.flameshine.crypto.helper.bot.entity;

import java.math.BigDecimal;
import java.util.List;

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
public class Order extends PanacheEntity {

    // TODO: replace fields below with some Binance-provided enum if possible

    @Column(name = "base", nullable = false)
    private String base;

    @Column(name = "quote", nullable = false)
    private String quote;

    @Column(name = "target", nullable = false)
    private BigDecimal target;

    @ManyToOne
    @JoinColumn(name = "key_id", nullable = false)
    private Key key;

    public static List<Order> findAllByKeys(List<Key> keys) {
        return find("key in ?1", keys).list();
    }
}