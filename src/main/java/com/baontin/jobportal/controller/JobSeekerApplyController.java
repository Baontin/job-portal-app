package com.baontin.jobportal.controller;

import com.baontin.jobportal.entity.JobPostActivity;
import com.baontin.jobportal.services.JobPostActivityService;
import com.baontin.jobportal.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JobSeekerApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;

    public JobSeekerApplyController(JobPostActivityService jobPostActivityService,
                                    UsersService usersService) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
    }

    @GetMapping("/job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model) {

        JobPostActivity jobDetails = jobPostActivityService.getOne(id);

        model.addAttribute("userProfile", usersService.getCurrentUserProfile());
        model.addAttribute("jobDetails", jobDetails);
        return "job-details";
    }

}
