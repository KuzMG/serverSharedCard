package com.project.sharedCardServer.model.check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.metrics.Metric;
import com.project.sharedCardServer.model.product.Product;
import com.project.sharedCardServer.model.shop.Shop;
import com.project.sharedCardServer.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "check_")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Column(name = "id_product", insertable = false, updatable = false)
    @JsonProperty("id_product")
    private Integer idProduct;
    @Column(name = "id_shop", insertable = false, updatable = false)
    @JsonProperty("id_shop")
    private Integer idShop;
    @Column(name = "id_currency", insertable = false, updatable = false)
    @JsonProperty("id_currency")
    private Integer idCurrency;
    @NotNull
    @Column(name = "id_metric", insertable = false, updatable = false)
    @JsonProperty("id_metric")
    private Integer idMetric;
    @NotNull
    @Column(name = "id_group", insertable = false, updatable = false)
    @JsonProperty("id_group")
    private UUID idGroup;
    @NotNull
    @Column(name = "id_creator", insertable = false, updatable = false)
    @JsonProperty("id_creator")
    private UUID id_creator;
    @Column(name = "id_buyer", insertable = false, updatable = false)
    @JsonProperty("id_buyer")
    private UUID id_buyer;
    @NotNull
    private Integer count;

    private Integer price;
    private String description;
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


    @NotNull
    @JsonIgnore
    private boolean status;
    @JsonIgnore
    @JoinColumn(name = "id_buyer", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User buyer;
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
    @JoinColumn(name = "id_creator", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User creator;
    @JsonIgnore
    @JoinColumn(name = "id_metric", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Metric metric;
    @JsonIgnore
    @JoinColumn(name = "id_currency", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Currency currency;
    @JsonIgnore
    @JoinColumn(name = "id_shop", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Shop shop;
    @JsonIgnore
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
}
