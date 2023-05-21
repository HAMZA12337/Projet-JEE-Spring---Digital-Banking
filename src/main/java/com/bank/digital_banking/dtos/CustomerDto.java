package com.bank.digital_banking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id ;
    private String name;
    private String email;




}
