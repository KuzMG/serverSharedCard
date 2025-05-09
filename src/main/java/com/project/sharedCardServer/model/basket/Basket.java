package com.project.sharedCardServer.model.basket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.person.Person;
import com.project.sharedCardServer.model.product.Product;
import com.project.sharedCardServer.model.purchase.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Column(name = "id_purchase", insertable = false, updatable = false)
    @JsonProperty("id_purchase")
    private UUID idPurchase;

    @NotNull
    private Double count;

}
