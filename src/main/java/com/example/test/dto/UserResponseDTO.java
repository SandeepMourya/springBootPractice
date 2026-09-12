package com.example.test.dto;

import com.example.test.model.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    private String id;
    private String name;
    private String email;
    private AddressDTO address;
    private UserRole userRole;
}
