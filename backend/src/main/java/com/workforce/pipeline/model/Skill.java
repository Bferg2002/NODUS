package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.DemandLevel;
import lombok.Getter;
import lombok.Setter;

public class Skill {
    @Getter
    private String name;
    DemandLevel demandLevel;

    //setting all skill names to lowercase after trimming blank space
// on the backend
    public void setName(String name) {
        this.name = name.trim().toLowerCase();
    }

    public boolean compareSkills(Skill skill) {
        return this.name.equalsIgnoreCase(skill.getName());
    }

    //returns DemandLevel enum based on jobs with that skill in the repository
    //will edit once repo running
    public static DemandLevel getDemandLevel() {
        int jobCount=20000; //= JobRepositoryCount.bySkill

        if (jobCount > 5000) {
            return DemandLevel.EXTREMELY_HIGH;
        } else if (jobCount > 2500) {
            return DemandLevel.HIGH;
        } else if (jobCount > 1000) {
            return DemandLevel.MEDIUM;
        } else if (jobCount >= 500) {
            return DemandLevel.LOW;
        } else  {
            return DemandLevel.MINIMAL;
        }
    }


}
