package app.service;

import app.model.dto.CompetitionReviewBinding;
import app.model.entities.Review;
import app.model.entities.enums.ReviewType;

import java.util.List;

public interface ReviewService{

    List<Review> getAll();

    Review getReview(Long id);

    boolean reviewCompetition(Long id, CompetitionReviewBinding competitionreviewBinding, ReviewType reviewType);
}
