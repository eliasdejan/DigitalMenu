package com.eliasdejan.digial_menu.model;

import javax.persistence.*;

@Entity
@Table(name = "menu_item_types")
public class MenuItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;
}
