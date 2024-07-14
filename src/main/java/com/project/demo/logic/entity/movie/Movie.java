package com.project.demo.logic.entity.movie;

import com.project.demo.logic.entity.casting.Casting;
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
    private String imageCover;

    @Column(nullable = false)
    private String video;

    @Column(nullable = false)
    private Integer realesedYear;

    @Column(nullable = false)
    private Integer duration;
    /**Relacion pelicula con casting
   @OneToOne()
    @JoinColumn(name = "casting_id", referencedColumnName = "id")
    private Casting casting;*/

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public Integer getRealesedYear() {
        return realesedYear;
    }

    public void setRealesedYear(Integer realesedYear) {
        this.realesedYear = realesedYear;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**public Casting getCast() {
        return casting;
    }

    public void setCast(Casting casting) {
        this.casting = casting;
    }*/
}
