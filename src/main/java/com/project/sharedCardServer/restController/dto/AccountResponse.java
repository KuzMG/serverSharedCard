package com.project.sharedCardServer.restController.dto;

import com.project.sharedCardServer.model.basket.Basket;
import com.project.sharedCardServer.model.history.History;
import com.project.sharedCardServer.model.purchase.Purchase;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.group_persons.GroupPersons;
import com.project.sharedCardServer.model.target.Target;
import com.project.sharedCardServer.model.person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private List<Person> persons = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<GroupPersons> groupPersons = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();
    private List<Basket> baskets = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
}
