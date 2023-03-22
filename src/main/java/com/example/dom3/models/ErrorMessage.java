package com.example.dom3.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    private Machine machine;

}
