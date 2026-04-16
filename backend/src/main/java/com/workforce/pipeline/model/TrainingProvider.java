package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

public class TrainingProvider extends User {

    private String organizationName;
    private List<TrainingProgram> programs;

    public TrainingProvider(String name, String email, String password, UserRole role, String organizationName) {
        super(name, email, password, role);
        this.organizationName = organizationName;
        this.programs = new ArrayList<>();
    }

    // ----------------------------
    // PROGRAM MANAGEMENT (MODEL LEVEL)
    // ----------------------------

    public void createProgram(TrainingProgram program) {
        programs.add(program);
    }

    /*
    public void updateProgram(int programId, TrainingProgram updatedProgram) {
        for (int i = 0; i < programs.size(); i++) {
            if (programs.get(i).getId() == programId) {
                programs.set(i, updatedProgram);
                return;
            }
        }
    }
     */

    public List<TrainingProgram> getPrograms() {
        return new ArrayList<>(programs);
    }

    // ----------------------------
    // GETTERS / SETTERS
    // ----------------------------

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}