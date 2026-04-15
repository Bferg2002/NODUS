package com.workforce.pipeline.model;

public class Role {
    private int id;
    private String region;
    private String industry;
    private double demandScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDemandScore() {
        return demandScore;
    }

    public void setDemandScore(double demandScore) {
        this.demandScore = demandScore;
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
}
