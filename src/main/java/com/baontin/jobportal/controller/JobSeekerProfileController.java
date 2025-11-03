package com.baontin.jobportal.controller;

import com.baontin.jobportal.entity.JobSeekerProfile;
import com.baontin.jobportal.entity.Skills;
import com.baontin.jobportal.entity.Users;
import com.baontin.jobportal.repository.UsersRepository;
import com.baontin.jobportal.services.JobSeekerProfileService;
import com.baontin.jobportal.util.FileDownloadUtil;
import com.baontin.jobportal.util.FileUploadUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    private final JobSeekerProfileService jobSeekerProfileService;
    private final UsersRepository usersRepository;

    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService,
                                      UsersRepository usersRepository) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String jobSeekerProfile(Model model) {
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            if (seekerProfile.isPresent()) {
                jobSeekerProfile = seekerProfile.get();
                if (jobSeekerProfile.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }

        return "job-seeker-profile";
    }

    @PostMapping("/addNew")
    public String addNew(JobSeekerProfile jobSeekerProfile,
                         @RequestParam("image") MultipartFile image,
                         @RequestParam("pdf") MultipartFile pdf,
                         Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUserId());
        }

/*

        List<Skills> skillList = new ArrayList<>();
        model.addAttribute("profile", jobSeekerProfile);
        model.addAttribute("skills", skillList);
*/


        // link each skill to the profile
        for (Skills skill : jobSeekerProfile.getSkills()) {
            skill.setJobSeekerProfile(jobSeekerProfile);
        }

        // Handle file name
        String imageName = "";
        String resumeName = "";

        if (!Objects.equals(image.getOriginalFilename(), "")) {
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(imageName);
        }
        if (!Objects.equals(pdf.getOriginalFilename(), "")) {
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            jobSeekerProfile.setResume(resumeName);
        }

        // Save the profile
        JobSeekerProfile savedProfile = jobSeekerProfileService.addNew(jobSeekerProfile);


        // Define upload directory
        String uploadDir = "photos/candidate/" + savedProfile.getUserAccountId();
        try {

            if (!Objects.equals(image.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }
            if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return "redirect:/dashboard/";

    }

    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") int id, Model model) {

        Optional<JobSeekerProfile> jobSeekerProfile =  jobSeekerProfileService.getOne(id);
        model.addAttribute("profile", jobSeekerProfile.get());

        return "job-seeker-profile";
    }

    /* ResponseEntity<?>
    * ResponseEntity is a Spring class that lets you build a full HTTP response — not just data:
      [Status code, Headers, Body (actual file or message)]
    * <?> means it can return any type (generic wildcard). In this case is:
      + a Resource (for the file),
      + or a String (error message).

    * */
    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam("fileName") String fileName,
                                            @RequestParam("userID") String userId) {

        FileDownloadUtil fileDownloadUtil = new FileDownloadUtil();
        Resource resource = null;

        try {
            resource = fileDownloadUtil.getFileAsResource(
                    "photos/candidate/" + userId, fileName);

        } catch (IOException ioe) {
            return ResponseEntity.badRequest().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

//        System.out.println(resource);
//        System.out.println(resource.getFilename());

        /*
        * "application/octet-stream" → generic binary type (safe for download)
        * "attachment; filename=..." → tells the browser “download this file instead of displaying it.”
        *
        result:
        * Status: 200 OK
        * Header: Content-Disposition: attachment; filename="resume.pdf"
        * Body: the actual file content (Spring automatically streams the file)
        * */
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
