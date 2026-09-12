package com.example.test.dto;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String name;
    private String email;
    private AddressDTO address;

}
