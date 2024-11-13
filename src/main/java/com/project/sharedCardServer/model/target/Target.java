package com.project.sharedCardServer.model.target;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.category.Category;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.shop.Shop;
import com.project.sharedCardServer.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Column(name = "id_group", insertable = false, updatable = false)
    @JsonProperty("id_group")
    private UUID idGroup;
    @NotNull
    @Column(name = "id_category", insertable = false, updatable = false)
    @JsonProperty("id_category")
    private Integer idCategory;
    @Column(name = "id_shop", insertable = false, updatable = false)
    @JsonProperty("id_shop")
    private Integer idShop;
    @NotNull
    @Column(name = "price_first")
    @JsonProperty("price_first")
    private int priceFirst;
    @Column(name = "price_last")
    @JsonProperty("price_last")
    private Integer priceLast;
    @NotNull
    @Column(name = "id_currency_first", insertable = false, updatable = false)
    @JsonProperty("id_currency_first")
    private Integer idCurrencyFirst;
    @Column(name = "id_currency_last", insertable = false, updatable = false)
    @JsonProperty("id_currency_last")
    private Integer idCurrencyLast;
    @NotNull
    @Column(name = "id_creator", insertable = false, updatable = false)
    @JsonProperty("id_creator")
    private UUID idCreator;
    @Column(name = "id_buyer", insertable = false, updatable = false)
    @JsonProperty("id_buyer")
    private UUID idBuyer;
    @JsonProperty("date_first")
    public Long dateFirst() {
        return dateFirst.getTime();
    }
    @JsonProperty("date_first")
    public void dateFirst(long _dateFirst){
        dateFirst = new Date(_dateFirst);
    }
    @JsonProperty("date_last")
    public Long dateLast() {
        if(dateLast == null){
            return null;
        }
        return dateLast.getTime();
    }
    @JsonProperty("date_last")
    public void dateLast(long _dateLast){
        dateLast = new Date(_dateLast);
    }
    @JsonProperty("status")
    public int status() {
        if (status) return 2;
        else return 0;
    }
    @JsonProperty("status")
    public void status(Integer status) {
        this.status =status.equals(2);

    }


    @JsonIgnore
    @JoinColumn(name = "id_buyer",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User buyer;
    @JsonIgnore
    @JoinColumn(name = "id_creator",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User creator;
    @JsonIgnore
    @JoinColumn(name = "id_currency_last",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Currency currencyLast;
    @NotNull
    @DateTimeFormat
    @Column(name = "date_first")
    @JsonIgnore
    private Date dateFirst;
    @DateTimeFormat
    @Column(name = "date_last")
    @JsonIgnore
    private Date dateLast;
    @JsonIgnore
    @JoinColumn(name = "id_currency_first",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Currency currencyFirst;
    @JsonIgnore
    @JoinColumn(name = "id_shop",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Shop shop;
    @JsonIgnore
    @JoinColumn(name = "id_category",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Category category;
    @NotNull
    @JsonIgnore
    private boolean status;
}
