package app.service;

import app.model.dto.JobAddBinding;
import app.model.entities.Job;

import java.util.List;

public interface JobService {

    Job getCompetition(Long id);

    List<Job> getHomeData();

    boolean addJob(JobAddBinding jobAddBinding);

    boolean enroll(Long id);

    boolean remove(Long id);
}
