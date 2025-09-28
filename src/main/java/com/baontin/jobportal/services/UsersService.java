package com.baontin.jobportal.services;

import com.baontin.jobportal.entity.JobSeekerProfile;
import com.baontin.jobportal.entity.RecruiterProfile;
import com.baontin.jobportal.entity.Users;
import com.baontin.jobportal.repository.JobSeekerProfileRepository;
import com.baontin.jobportal.repository.RecruiterProfileRepository;
import com.baontin.jobportal.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public UsersService(UsersRepository usersRepository,
                        RecruiterProfileRepository recruiterProfileRepository,
                        JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.usersRepository = usersRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }

    public Users addNew(Users users) {

        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        Users savedUser = usersRepository.save(users);

        // set type for create profile
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
