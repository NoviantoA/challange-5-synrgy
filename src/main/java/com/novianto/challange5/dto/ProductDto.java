package com.novianto.challange5.dto;

import com.novianto.challange5.entity.Merchant;
import lombok.Data;

@Data
public class ProductDto {
    private String productName;
    private Double price;
    private Merchant merchant;
}
