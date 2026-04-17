package com.workforce.pipeline.dto;

import java.util.List;

/**
 * JobDTO
 *
 * Purpose:
 * - Represents a Job in a simplified format for frontend use
 * - Converts complex relationships (like Skill entities) into simple values (Strings)
 */
public class JobDTO {

    private Integer id;
    private String title;
    private String description;
    private String industry;
    private String region;

    // Instead of List<Skill>, we use List<String> to avoid nested entity issues
    private List<String> skills;

    public JobDTO() {}

    public JobDTO(Integer id, String title, String description,
                  String industry, String region, List<String> skills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.region = region;
        this.skills = skills;
    }

    // ===== Getters & Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}