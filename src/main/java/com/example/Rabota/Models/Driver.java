package com.example.Rabota.Models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="vy_id")
    private Vy vy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vy getVy() {
        return vy;
    }

    public void setVy(Vy vy) {
        this.vy = vy;
    }
    public Driver(String name, Vy vy) {
        this.name = name;
        this.vy = vy;
    }
    public Driver() {
    }
}
