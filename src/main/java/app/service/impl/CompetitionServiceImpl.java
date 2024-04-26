package app.service.impl;

import app.model.dto.CategoryAddBinding;
import app.model.dto.CompetitionAddBinding;
import app.model.entities.Competition;
import app.model.entities.CompetitionCategory;
import app.model.entities.User;
import app.repo.CompetitionCategoryRepository;
import app.repo.CompetitionRepository;
import app.repo.ReviewRepository;
import app.repo.UserRepository;
import app.service.CompetitionService;
import app.service.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionCategoryRepository competitionCategoryRepository;
    private final UserRepository userRepository;
    private final LoggedUser loggedUser;
    private final ReviewRepository reviewRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, CompetitionCategoryRepository competitionCategoryRepository, UserRepository userRepository, LoggedUser loggedUser,
                                  ReviewRepository reviewRepository) {
        this.competitionRepository = competitionRepository;
        this.competitionCategoryRepository = competitionCategoryRepository;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public int addCompetition(CompetitionAddBinding binding) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CompetitionAddBinding.class, Competition.class).addMappings(
                mapper -> {
                    mapper.map(
                            CompetitionAddBinding::getName,
                            Competition::setTitle
                    );
                    mapper.map(
                            b -> null,
                            Competition::setCompetitionCategory
                    );
                    mapper.map(
                            CompetitionAddBinding::getType,
                            Competition::setCompetitionType
                    );
                    mapper.map(
                            b -> {
                                try {
                                    return b.getFilename().getBytes();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            },
                            Competition::setImage
                    );
                }
        );

        Competition competition = modelMapper.map(binding, Competition.class);

        competition.setParticipants(new ArrayList<>());
        competition.setReviews(new ArrayList<>());

        competition.setAddedBy(userRepository.findByUsername(loggedUser.getUsername()));

        competition.setCompetitionCategory(competitionCategoryRepository.findByName(binding.getCategory()));

        competitionRepository.save(competition);

        return competition.getCompetitionType().ordinal();
    }

    @Override
    public Competition getCompetition(Long id) {
        return this.competitionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Competition> getHomeData() {
        return competitionRepository.findAll();
    }

    @Override
    public boolean addCategory(CategoryAddBinding binding) {
        CompetitionCategory competitionCategory = new CompetitionCategory();
        competitionCategory.setName(binding.getName());

        competitionCategory.setCompetitions(new ArrayList<>());

        competitionCategoryRepository.save(competitionCategory);

        return true;
    }

    @Override
    public boolean enroll(Long id) {
        Competition competition = competitionRepository.findById(id).orElse(null);

        if (competition == null) return false;
        if (loggedUser == null) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        competition.getParticipants().add(user);

        competitionRepository.save(competition);

        loggedUser.login(user);

        return true;
    }

    @Override
    public boolean remove(Long id) {
        if (loggedUser == null) return false;
        if (!loggedUser.isHost() && !loggedUser.isAdmin()) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        if (competitionRepository.findById(id).orElse(null).getAddedBy().getId() != user.getId() && !loggedUser.isAdmin()) return false;

        competitionRepository.deleteById(id);

        return true;
    }
}
