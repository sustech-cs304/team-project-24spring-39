package com.example.cs304.controller;

import com.example.cs304.repository.EvaluationRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    private final EvaluationRepository evaluationRepository;


    public EvaluationController(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    //    insert into rate (course_id, student_id, rate, difficulty, workload, grading, gain, description) values ('MG003', '00000001', 3, 'normal', 'normal', 'normal', 'normal', 'good');
    @PostMapping
    @Transactional
    public void addEvaluation(@RequestParam("course_id") String course_id, @RequestParam("student_id") String student_id,
                              @RequestParam("rate") int rate, @RequestParam("difficulty") String difficulty,
                              @RequestParam("workload") String workload, @RequestParam("grading") String grading,
                              @RequestParam("gain") String gain, @RequestParam("description") String description) {
        evaluationRepository.addEvaluation(course_id, student_id, rate, difficulty, workload, grading, gain, description);
    }

}
