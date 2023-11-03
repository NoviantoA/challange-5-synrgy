package com.novianto.challange5.dto;

import com.novianto.challange5.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private Date orderTime;
    private String destinationAddress;
    private boolean completed;
    private User user;
}
