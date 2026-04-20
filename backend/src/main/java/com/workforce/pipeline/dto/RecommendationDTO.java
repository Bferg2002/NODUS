package com.workforce.pipeline.dto;

import java.util.List;

/**
 * RecommendationDTO
 *
 * Purpose:
 * - Represents recommendation response data
 * - Used by service/controller output
 */
public class RecommendationDTO {

    private Integer userId;
    private List<JobDTO> recommendedJobs;

    public RecommendationDTO() {}

    public RecommendationDTO(Integer userId, List<JobDTO> recommendedJobs) {
        this.userId = userId;
        this.recommendedJobs = recommendedJobs;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<JobDTO> getRecommendedJobs() {
        return recommendedJobs;
    }

    public void setRecommendedJobs(List<JobDTO> recommendedJobs) {
        this.recommendedJobs = recommendedJobs;
    }
}