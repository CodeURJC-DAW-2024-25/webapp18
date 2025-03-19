package es.codeurjc.dto;
import java.time.LocalDate;

public class ReviewDTO {
    private Long reviewId;
    private Long userId;
    private Long apartmentId;
    private int score;
    private LocalDate date;
    private String comment;




public Long getReviewId() {
    return reviewId;
}

public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
}


public Long getUserId() {
    return userId;
}

public void setUserId(Long userId) {
    this.userId = userId;
}


public Long getApartmentId() {
    return apartmentId;
}

public void setApartmentId(Long apartmentId) {
    this.apartmentId = apartmentId;
}


public int getScore() {
    return score;
}

public void setScore(int score) {
    this.score = score;
}


public LocalDate getDate() {
    return date;
}

public void setDate(LocalDate date) {
    this.date = date;
}

public String getComment() {
    return comment;
}

public void setComment(String comment) {
    this.comment = comment;
}

}

