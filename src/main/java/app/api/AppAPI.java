package app.api;

import app.model.dto.CompetitionResponseDTO;
import app.model.dto.JobResponseDTO;
import app.model.entities.Competition;
import app.model.entities.Job;
import app.model.entities.News;
import app.repo.CompetitionRepository;
import app.repo.JobRepository;
import app.repo.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class AppAPI {
    private final CompetitionRepository competitionRepository;
    private ModelMapper modelMapper;
    private final JobRepository jobRepository;
    private final NewsRepository newsRepository;

    public AppAPI(CompetitionRepository competitionRepository,
                  JobRepository jobRepository,
                  NewsRepository newsRepository) {
        this.competitionRepository = competitionRepository;
        this.modelMapper = new ModelMapper();
        this.jobRepository = jobRepository;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/competition")
    public ResponseEntity<CompetitionResponseDTO> findById(@RequestParam(value = "id") Long id) {
        Competition competition = competitionRepository.findById(id).orElse(null);

        if(competition == null) {
            return ResponseEntity.notFound().build();
        }

        modelMapper.typeMap(Competition.class, CompetitionResponseDTO.class).addMappings(
                mapper -> {
                    mapper.map(
                            src -> src.getAddedBy().getUsername(),
                            CompetitionResponseDTO::setAddedBy
                    );
                }
        );

        CompetitionResponseDTO competitionResponseDTO = modelMapper.map(competition, CompetitionResponseDTO.class);

        competitionResponseDTO.add(linkTo(methodOn(AppAPI.class).findById(id)).withSelfRel());

        return new ResponseEntity<>(competitionResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/job")
    public ResponseEntity<JobResponseDTO> findJobById(@RequestParam(value = "id") Long id) {
        Job job = jobRepository.findById(id).orElse(null);

        if(job == null) {
            return ResponseEntity.notFound().build();
        }

        modelMapper.typeMap(Competition.class, CompetitionResponseDTO.class).addMappings(
                mapper -> {
                    mapper.map(
                            src -> src.getAddedBy().getUsername(),
                            CompetitionResponseDTO::setAddedBy
                    );
                }
        );

        JobResponseDTO jobResponseDTO = modelMapper.map(job, JobResponseDTO.class);

        jobResponseDTO.add(linkTo(methodOn(AppAPI.class).findById(id)).withSelfRel());

        return new ResponseEntity<>(jobResponseDTO, HttpStatus.OK);
    }

    @GetMapping("competitions/all")
    public ResponseEntity<List<CompetitionResponseDTO>> getAll() {
        List<Competition> competitions = competitionRepository.findAll();
        List<CompetitionResponseDTO> responses = new ArrayList<>();

        competitions.forEach((e) -> {
            responses.add(new CompetitionResponseDTO(e));
        });

        if (competitions.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(responses);
        }
    }

    @GetMapping("jobs/all")
    public ResponseEntity<List<JobResponseDTO>> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        List<JobResponseDTO> responses = new ArrayList<>();

        jobs.forEach((e) -> {
            responses.add(new JobResponseDTO(e));
        });

        if (jobs.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(responses);
        }
    }

    @GetMapping("competition/image")
    public ResponseEntity<byte[]> findImageById(@RequestParam(value = "id") Long id) {
        Competition competition = competitionRepository.findById(id).orElse(null);

        if (competition == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(competition.getImage());
        }
    }

    @GetMapping("job/image")
    public ResponseEntity<byte[]> findJobImageById(@RequestParam(value = "id") Long id) {
        Job job = jobRepository.findById(id).orElse(null);

        if (job == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(job.getImage());
        }
    }

    @GetMapping("news/image")
    public ResponseEntity<byte[]> findNewsImageById(@RequestParam(value = "id") Long id) {
        News news = newsRepository.findById(id).orElse(null);

        if (news == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(news.getMainImage());
        }
    }
}
