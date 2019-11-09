package com.demo.cloud.serviceconsumer.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private Long id;

    private String userName;

    private String password;

    private String name;
}
