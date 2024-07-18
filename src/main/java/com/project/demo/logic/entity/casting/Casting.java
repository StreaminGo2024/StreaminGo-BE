package com.project.demo.logic.entity.casting;

import com.project.demo.logic.entity.actor.Actor;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Table(name = "casting")
@Entity
public class Casting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "casting")

    private List<Actor> actor;
// AGREGAR PELICULA ONE TO ONE
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Actor> getActors() {
        return actor;
    }

    public void setActors(List<Actor> actors) {
        this.actor = actors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
