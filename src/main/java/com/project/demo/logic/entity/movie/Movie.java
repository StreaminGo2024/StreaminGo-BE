package com.project.demo.logic.entity.movie;

import com.project.demo.logic.entity.genre.Genre;
import jakarta.persistence.*;

@Table(name = "movie")
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String video;
}
