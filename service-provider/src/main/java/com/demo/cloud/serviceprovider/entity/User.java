package com.demo.cloud.serviceprovider.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_user")
public class User {
    @Id
//    @GeneratedValue
    private Long id;

    private String userName;

    private String password;

    private String name;
}
