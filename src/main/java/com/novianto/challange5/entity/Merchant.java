package com.novianto.challange5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novianto.challange5.util.AbstractDate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "merchants")
@Where(clause = "deleted_date is null")
public class Merchant extends AbstractDate implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String merchantName;

    private String merchantLocation;

    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
