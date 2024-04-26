package app.service.impl;

import app.model.dto.CompetitionReviewBinding;
import app.model.entities.Competition;
import app.model.entities.Review;
import app.model.entities.enums.ReviewType;
import app.repo.CompetitionRepository;
import app.repo.ReviewRepository;
import app.repo.UserRepository;
import app.service.LoggedUser;
import app.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CompetitionRepository competitionRepository;
    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    public ReviewServiceImpl(ReviewRepository reviewRepository, LoggedUser loggedUser,
                             CompetitionRepository competitionRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.loggedUser = loggedUser;
        this.competitionRepository = competitionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public boolean reviewCompetition(Long id, CompetitionReviewBinding binding, ReviewType reviewType) {
        if(!loggedUser.isLogged()) return false;

        if(reviewType == ReviewType.COMPETITION) {
            Competition competition = competitionRepository.findById(id).orElse(null);

            if (competition == null) return false;

            Review review = new Review();

            review.setTitle(binding.getTitle());
            review.setDescription(binding.getDescription());
            review.setRating(Integer.parseInt(binding.getRating()));
            review.setReviewType(reviewType);

            competition.getReviews().add(review);
            competitionRepository.save(competition);

            review.setReviewedBy(userRepository.findByUsername(loggedUser.getUsername()));

            reviewRepository.save(review);
        }

        return true;
    }
}
