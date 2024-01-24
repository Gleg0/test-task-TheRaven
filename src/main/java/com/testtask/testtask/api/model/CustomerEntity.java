package com.testtask.testtask.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Instant created;

    @Column()
    private Instant updated;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column()
    private String phone;

    @Column(nullable = false)
    private Boolean isActive;
}
