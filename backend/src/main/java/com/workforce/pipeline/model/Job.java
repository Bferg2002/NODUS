package com.workforce.pipeline.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;

public class Job {
    @Getter
    private int id;
    @Getter
    private String title;
    @Getter
    private String description;
    @Getter
    private Date datePosted;
    @Getter
    private String dataFreshness;
    private ArrayList <Skill> skillsList = new ArrayList<>() ;

    public Job(int id, Date datePosted, String description, String title) {
        this.id = id;
        this.datePosted = datePosted;
        this.description = description;
        this.title = title;
    }

    public Job(int id, ArrayList skillsList, String dataFreshness, Date datePosted, String description, String title) {
        this.id = id;
        this.skillsList = skillsList;
        this.dataFreshness = dataFreshness;
        this.datePosted = datePosted;
        this.description = description;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setDataFreshness(String dataFreshness) {
        this.dataFreshness = dataFreshness;
    }

    public ArrayList<Skill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(ArrayList<Skill> skillsList) {
        this.skillsList = skillsList;
    }

    public void addSkill(Skill skill){
        skillsList.add(skill);
    }
    public void removeSkill(Skill skill){
        skillsList.remove(skill);
    }
}


