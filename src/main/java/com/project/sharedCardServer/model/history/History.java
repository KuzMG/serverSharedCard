package com.project.sharedCardServer.model.history;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.basket.Basket;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.person.Person;
import com.project.sharedCardServer.model.product.Product;
import com.project.sharedCardServer.model.shop.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "history")
public class History {
    @Id
    @NotNull
    @Column(name = "id_basket", insertable = false, updatable = false)
    @JsonProperty("id_basket")
    private UUID idBasket;
    @Column(name = "id_currency", insertable = false, updatable = false)
    @JsonProperty("id_currency")
    private Integer idCurrency;
    @NotNull
    @Column(name = "id_person", insertable = false, updatable = false)
    @JsonProperty("id_person")
    private UUID idPerson;
    @NotNull
    @Column(name = "id_shop", insertable = false, updatable = false)
    @JsonProperty("id_shop")
    private Integer idShop;
    @NotNull
    private Double price;

    @JsonProperty("purchase_date")
    public Long dateFirst() {
        return purchaseDate.getTime();
    }

    @JsonProperty("purchase_date")
    public void dateFirst(long _dateFirst) {
        purchaseDate = new Date(_dateFirst);
    }

    @NotNull
    @DateTimeFormat
    @Column(name = "purchase_date")
    @JsonIgnore
    private Date purchaseDate;

    @JsonIgnore
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;
    @JsonIgnore
    @JoinColumn(name = "id_currency", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Currency currency;
    @JsonIgnore
    @JoinColumn(name = "id_basket", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Basket basket;
    @JsonIgnore
    @JoinColumn(name = "id_shop", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Shop shop;
}
