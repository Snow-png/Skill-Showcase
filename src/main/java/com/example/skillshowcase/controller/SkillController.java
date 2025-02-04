package com.example.skillshowcase.controller;

import com.example.skillshowcase.model.Skill;
import com.example.skillshowcase.repository.SkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;

    }

    // Add a new skill
    @PostMapping
    public ResponseEntity<Skill> addSkill(@RequestBody Skill skill) {
        Skill newSkill = skillRepository.save(skill);
        return ResponseEntity.ok(newSkill);
    }

    //  Get all skills
    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    //  Get a skill by ID
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        Optional<Skill> skill = skillRepository.findById(id);
        return skill.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a skill
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skillDetails) {
        return skillRepository.findById(id)
                .map(skill -> {
                    skill.setName(skillDetails.getName());
                    skill.setDescription(skillDetails.getDescription());
                    skill.setProficiencyLevel(skillDetails.getProficiencyLevel());
                    Skill updatedSkill = skillRepository.save(skill);
                    return ResponseEntity.ok(updatedSkill);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a skill
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


