package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    // =========================
    // ADZUNA IMPORT (FINAL FIX)
    // =========================

    public List<Job> importFromAdzuna(List<Map<String, Object>> jobs) {

        List<Job> savedJobs = new ArrayList<>();

        System.out.println("🔥 IMPORT START - incoming jobs: " + jobs.size());

        for (Map<String, Object> j : jobs) {

            try {
                String title = (String) j.getOrDefault("title", "Untitled");
                String description = (String) j.getOrDefault("description", "");
                String company = (String) j.getOrDefault("company", "Unknown");
                String location = (String) j.getOrDefault("location", "Unknown");

                // ✅ USE ADZUNA UNIQUE ID
                String jobKey = String.valueOf(j.get("id"));

                System.out.println("➡ Processing jobKey: " + jobKey);
                System.out.println("   Title: " + title);
                System.out.println("   Company: " + company);
                System.out.println("   Location: " + location);

                // ✅ DUPLICATE CHECK
                boolean exists = jobRepository.findByJobKey(jobKey).isPresent();
                System.out.println("🔍 Exists? " + exists);

                if (exists) continue;

                Job job = new Job();
                job.setTitle(title);
                job.setDescription(description);
                job.setJobKey(jobKey);
                job.setDataFreshness("FRESH");
                job.setDatePosted(new Date());

                Job saved = jobRepository.save(job);
                savedJobs.add(saved);

                System.out.println("✅ SAVED job id: " + saved.getId());

            } catch (Exception e) {
                System.err.println("❌ Failed to import job");
                e.printStackTrace();
            }
        }

        System.out.println("🏁 IMPORT COMPLETE - saved: " + savedJobs.size());

        return savedJobs;
    }

    // =========================
    // BASIC CRUD (UNCHANGED)
    // =========================

    public Job createJob(Job job) {
        job.setDatePosted(new Date());
        job.setDataFreshness("FRESH");
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Integer id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));
    }

    public Job updateJob(Integer id, Job updated) {
        Job job = getJobById(id);
        job.setTitle(updated.getTitle());
        job.setDescription(updated.getDescription());
        return jobRepository.save(job);
    }

    public boolean deleteJob(Integer id) {
        if (!jobRepository.existsById(id)) return false;
        jobRepository.deleteById(id);
        return true;
    }

    // =========================
    // SKILLS
    // =========================

    public Job addSkillToJob(Integer jobId, Integer skillId) {
        Job job = getJobById(jobId);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        job.getSkillsList().add(skill);
        return jobRepository.save(job);
    }

    public Job removeSkillFromJob(Integer jobId, Integer skillId) {
        Job job = getJobById(jobId);
        job.getSkillsList().removeIf(s -> s.getId().equals(skillId));
        return jobRepository.save(job);
    }

    public Job assignRoleToJob(Integer jobId, Integer roleId) {
        return jobRepository.save(getJobById(jobId));
    }
}