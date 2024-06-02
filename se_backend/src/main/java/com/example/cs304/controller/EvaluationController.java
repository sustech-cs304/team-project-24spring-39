package com.example.cs304.controller;

import com.example.cs304.entity.Rate;
import com.example.cs304.repository.CSrepository;
import com.example.cs304.repository.EvaluationRepository;
import com.example.cs304.repository.RateRepository;
import com.example.cs304.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    private final EvaluationRepository evaluationRepository;
    private final CSrepository csRepository;
    private final RateRepository rateRepository;


    public EvaluationController(EvaluationRepository evaluationRepository, CSrepository csRepository, RateRepository rateRepository) {
        this.evaluationRepository = evaluationRepository;
        this.csRepository = csRepository;
        this.rateRepository = rateRepository;
    }

    @PostMapping("/add_evaluation")
    @Transactional
    public Response addEvaluation(@RequestParam("course_id") String course_id, @RequestParam("student_id") String student_id,
                                  @RequestParam("rate") int rate, @RequestParam("difficulty") String difficulty,
                                  @RequestParam("workload") String workload, @RequestParam("grading") String grading,
                                  @RequestParam("gain") String gain, @RequestParam("description") String description) {
        evaluationRepository.addEvaluation(course_id, student_id, rate, difficulty, workload, grading, gain, description);
        csRepository.changeJudged(course_id, student_id);
        return Response.success(null);
    }

    @GetMapping("/get_evaluations")
    public Response getEvaluations() {
        List<Rate> evaluations = rateRepository.getEvaluations();
        return Response.success(evaluations);
    }

}
