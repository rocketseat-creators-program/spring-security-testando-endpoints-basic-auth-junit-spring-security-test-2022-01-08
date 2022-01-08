package com.rocketseat.expertsclub.testbasicauthjunitspringsecurity.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "students")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String stack;

    @Column(nullable = false)
    private Integer yearsExperience;


    public Student(Object o, String kamila, String java, int i) {
    }
}

