package com.example.test;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Entity
@Table(name = "userdata")
@Data
public class User {

    public User(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iD;
    private String name;

    public User(){};
}
