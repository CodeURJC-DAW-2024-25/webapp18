package es.codeurjc.model;


import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int score;

    private String comment;

    private LocalDate date;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private UserE user;

    public Review() {

    }

    public Review(int score, String comment, LocalDate date, Apartment apartment, UserE user) {
        this.score = score;
        this.comment = comment;
        this.date = date;
        this.apartment = apartment;
        this.user = user;
    }

    public Long getIdReview() {
        return id;
    }

    public void setIdReview(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Apartment getHotel() {
        return apartment;
    }

    public void setHotel(Apartment apartment) {
        this.apartment = apartment;
    }

    public UserE getUser() {
        return user;
    }

    public void setUser(UserE user) {
        this.user = user;
    }

}
