package com.example.cs304.controller;

import com.example.cs304.entity.Professor;
import com.example.cs304.repository.ProfessorRepository;
import com.example.cs304.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin

@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorRepository professorRepository;

    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @GetMapping("/get_professor")
    public Response<?> getProfessor(@RequestParam(required = false) String PID){
        List<Professor> professors = professorRepository.findByCondition(PID);
        if (professors.isEmpty())
            return new Response<>(401,"Invalid PID",null);
        else return new Response<>(200,"Successful",professors);
    }


}
