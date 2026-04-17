package com.workforce.pipeline.service;

import com.workforce.pipeline.dto.GapAnalysisDTO;
import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GapAnalysisService
 *
 * Simple workforce gap calculation service:
 * - compares job demand vs user supply
 * - measures skill mismatches
 *
 * No ML, no external AI — just explainable scoring.
 */
@Service
public class GapAnalysisService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public GapAnalysisService(JobRepository jobRepository,
                              UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    /**
     * Main entry point for analytics
     */
    public List<GapAnalysisDTO> computeGapAnalysis() {

        List<Job> jobs = jobRepository.findAll();
        List<User> users = userRepository.findAll();

        // Group jobs by role title (since no region/industry field exists)
        Map<String, List<Job>> jobsByRole = jobs.stream()
                .filter(j -> j.getRole() != null)
                .collect(Collectors.groupingBy(j -> j.getRole().getTitle()));

        List<GapAnalysisDTO> results = new ArrayList<>();

        for (String roleTitle : jobsByRole.keySet()) {

            List<Job> roleJobs = jobsByRole.get(roleTitle);

            // Simple matching: users with ANY skills (basic model)
            List<User> matchingUsers = users.stream()
                    .filter(u -> u.getSkills() != null && !u.getSkills().isEmpty())
                    .toList();

            double roleGapScore = computeRoleGap(roleJobs.size(), matchingUsers.size());
            double skillGapScore = computeSkillGap(roleJobs, matchingUsers);

            results.add(new GapAnalysisDTO(
                    "ALL", // no region in model → placeholder
                    roleTitle,
                    roleGapScore,
                    skillGapScore
            ));
        }

        return results;
    }

    /**
     * Simple demand vs supply ratio
     */
    private double computeRoleGap(int jobCount, int userCount) {
        if (jobCount == 0) return 0.0;
        if (userCount == 0) return 1.0;

        return (double) jobCount / (jobCount + userCount);
    }

    /**
     * Measures missing skills across job postings
     */
    private double computeSkillGap(List<Job> jobs, List<User> users) {

        Set<String> userSkills = users.stream()
                .flatMap(u -> u.getSkills().stream())
                .map(Skill::getName)
                .collect(Collectors.toSet());

        Set<String> requiredSkills = jobs.stream()
                .flatMap(j -> j.getSkillsList().stream())
                .map(Skill::getName)
                .collect(Collectors.toSet());

        if (requiredSkills.isEmpty()) return 0.0;

        long missing = requiredSkills.stream()
                .filter(skill -> !userSkills.contains(skill))
                .count();

        return (double) missing / requiredSkills.size();
    }
}