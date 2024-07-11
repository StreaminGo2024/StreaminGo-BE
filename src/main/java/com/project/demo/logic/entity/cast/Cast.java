package com.project.demo.logic.entity.cast;

import com.project.demo.logic.entity.actor.Actor;
import jakarta.persistence.*;

@Table(name = "cast")
@Entity
public class Cast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actor_id", referencedColumnName = "id", nullable = false)
    private Actor actor;

   /** @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    private Movie movie;*/
}
