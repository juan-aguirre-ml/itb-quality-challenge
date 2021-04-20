package com.itbqualitychallenge.bookings.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int dni;
    private String name;
    private String lastName;
    private String birthDate;
    private String mail;
}
