package com.baontin.jobportal.repository;

import com.baontin.jobportal.entity.JobPostActivity;
import com.baontin.jobportal.entity.JobSeekerProfile;
import com.baontin.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
}
