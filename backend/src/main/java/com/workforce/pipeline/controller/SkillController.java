package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
