package com.workforce.pipeline.model;
import java.util.ArrayList;
import java.util.List;

public class TrainingProgram {

    private int id;
    private String name;
    private String description;
    private List<Skill> skillsTaught;

    public TrainingProgram(String name, String description) {
        this.name = name;
        this.description = description;
        this.skillsTaught = new ArrayList<>();
    }

    // ----------------------------
    // SKILL MANAGEMENT
    // ----------------------------

    public void addSkill(Skill skill) {
        skillsTaught.add(skill);
    }

    public void removeSkill(Skill skill) {
        skillsTaught.remove(skill);
    }

    public List<Skill> getSkillsTaught() {
        return new ArrayList<>(skillsTaught);
    }

    // ----------------------------
    // SKILL MATCH LOGIC
    // ----------------------------

    /*
    --------------------------------------------
    SKILL MATCH LOGIC:
    matchesSkillGap =
      % of missing skills that program teaches

    Example:
    User missing 3 skills
    Program teaches 2 of them
    matchScore = 2/3 = 0.66
    --------------------------------------------
    */

    /*
    public double matchesSkillGap(List<Skill> missingSkills) {
        if (missingSkills == null || missingSkills.isEmpty()) {
            return 0.0;
        }

        int matchCount = 0;

        for (Skill missing : missingSkills) {
            for (Skill taught : skillsTaught) {
                if (taught.getName().equalsIgnoreCase(missing.getName())) {
                    matchCount++;
                    break;
                }
            }
        }

        return (double) matchCount / missingSkills.size();
    }
     */

    // ----------------------------
    // GETTERS / SETTERS
    // ----------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}