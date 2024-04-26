package app.service.impl;

import app.model.entities.CompetitionCategory;
import app.repo.CompetitionCategoryRepository;
import app.service.CompetitionCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionCategoryServiceImpl implements CompetitionCategoryService {
    private final CompetitionCategoryRepository competitionCategoryRepository;

    public CompetitionCategoryServiceImpl(CompetitionCategoryRepository competitionCategoryRepository) {
        this.competitionCategoryRepository = competitionCategoryRepository;
    }

    @Override
    public List<CompetitionCategory> getAll() {
        return this.competitionCategoryRepository.findAll();
    }
}
