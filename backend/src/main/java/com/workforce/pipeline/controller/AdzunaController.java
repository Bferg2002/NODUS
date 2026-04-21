package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.service.AdzunaService;
import com.workforce.pipeline.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adzuna")
public class AdzunaController {

    private final AdzunaService adzunaService;
    private final JobService jobService;

    public AdzunaController(AdzunaService adzunaService, JobService jobService) {
        this.adzunaService = adzunaService;
        this.jobService = jobService;
    }

    @GetMapping("/import")
    public List<Job> importJobs(
            @RequestParam String query,
            @RequestParam String location
    ) {
        List<Map<String, Object>> jobs = adzunaService.fetchJobs(query, location);
        return jobService.importFromAdzuna(jobs);
    }
}