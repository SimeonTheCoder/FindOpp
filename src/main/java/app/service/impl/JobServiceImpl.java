package app.service.impl;

import app.model.dto.JobAddBinding;
import app.model.entities.Job;
import app.model.entities.User;
import app.repo.JobRepository;
import app.repo.UserRepository;
import app.service.JobService;
import app.service.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final LoggedUser loggedUser;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobServiceImpl(LoggedUser loggedUser, JobRepository jobRepository, UserRepository userRepository) {
        this.loggedUser = loggedUser;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Job getCompetition(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public List<Job> getHomeData() {
        return jobRepository.findAll();
    }

    @Override
    public boolean addJob(JobAddBinding binding) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(JobAddBinding.class, Job.class).addMappings(
                mapper -> {
                    mapper.map(
                            j -> {
                                try {
                                    return binding.getFilename().getBytes();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            },
                            Job::setImage
                    );
                }
        );

        Job job = modelMapper.map(binding, Job.class);

        job.setAddedBy(userRepository.findByUsername(loggedUser.getUsername()));
        jobRepository.save(job);

        return true;
    }

    @Override
    public boolean enroll(Long id) {
        Job job = jobRepository.findById(id).orElse(null);

        if (job == null) return false;
        if (loggedUser == null) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        job.getEmployees().add(user);

        jobRepository.save(job);

        loggedUser.login(user);

        return true;
    }

    @Override
    public boolean remove(Long id) {
        if (loggedUser == null) return false;
        if (!loggedUser.isHost() && !loggedUser.isAdmin()) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        if (jobRepository.findById(id).orElse(null).getAddedBy().getId() != user.getId() && !loggedUser.isAdmin()) return false;

        jobRepository.deleteById(id);

        return true;
    }
}
