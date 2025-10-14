package com.baontin.jobportal.repository;

import com.baontin.jobportal.entity.IRecruiterJobs;
import com.baontin.jobportal.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer> {

    /*
    Retrieves list of jobs for a given recruiter id.

    Combines/joins job post activity, job location, company

    Also includes a count of job-seekers/candidates who have applied for a given job

    -----
    when run this query,
    replace :recruiter in query with the value of
            the method parameter named recruiter (@Param("recruiter"))
    * */
    @Query(value =
            "SELECT " +
                "COUNT(s.user_id) as totalCandidates, j.job_post_id,j.job_title," +
                "l.id as locationId, l.city,l.state,l.country," +
                "c.id as companyId, c.name " +
            "FROM job_post_activity j " +
            "INNER JOIN job_location l " +
                "on j.job_location_id = l.id " +
            "INNER JOIN job_company c  " +
                "on j.job_company_id = c.id " +
            "LEFT JOIN job_seeker_apply s " +
                "on s.job = j.job_post_id " +
            "WHERE j.posted_by_id = :recruiter " +
            "GROUP BY j.job_post_id" ,nativeQuery = true)

    List<IRecruiterJobs> getRecruiterJobs(@Param("recruiter") int recruiter);
}
