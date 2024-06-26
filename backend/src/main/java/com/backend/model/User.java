package com.backend.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Entity
@Table(name = "USER_CRUD")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_CRUD_SEQ")
    @SequenceGenerator(name = "USER_CRUD_SEQ", sequenceName = "USER_CRUD_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

}
