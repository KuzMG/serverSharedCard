package com.project.sharedCardServer.model.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.person.Person;
import com.project.sharedCardServer.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Column(name = "id_product", insertable = false, updatable = false)
    @JsonProperty("id_product")
    private Integer idProduct;
    @Column(name = "id_currency", insertable = false, updatable = false)
    @JsonProperty("id_currency")
    private Integer idCurrency;
    @NotNull
    @Column(name = "id_group", insertable = false, updatable = false)
    @JsonProperty("id_group")
    private UUID idGroup;
    @NotNull
    @Column(name = "id_person", insertable = false, updatable = false)
    @JsonProperty("id_person")
    private UUID idPerson;
    @NotNull
    @Column(name = "is_bought", insertable = false, updatable = false)
    @JsonProperty("is_bought")
    private boolean isBought;
    @NotNull
    private Double count;
    @NotNull
    private Double price;
    @NotNull
    private String description;

    @JsonProperty("creation_date")
    public Long creationDate() {
        return creationDate.getTime();
    }

    @JsonProperty("creation_date")
    public void creationDate(long _creationDate) {
        creationDate = new Date(_creationDate);
    }


    @JsonProperty("purchase_date")
    public Long purchaseDate() {
        if(purchaseDate==null){
            return null;
        }else{
            return purchaseDate.getTime();
        }
    }

    @JsonProperty("purchase_date")
    public void purchaseDate(Long _purchaseDate) {
            if(_purchaseDate != null){
                purchaseDate = new Date(_purchaseDate);
            }
    }

    @NotNull
    @DateTimeFormat
    @Column(name = "creation_date")
    @JsonIgnore
    private Date creationDate;

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
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
}
