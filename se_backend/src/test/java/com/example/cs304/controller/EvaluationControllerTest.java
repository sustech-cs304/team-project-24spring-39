package com.example.cs304.controller;

import com.example.cs304.entity.Rate;
import com.example.cs304.repository.RateRepository;
import com.example.cs304.response.Response;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EvaluationControllerTest {

    @InjectMocks
    private EvaluationController evaluationController;
    @Mock
    private RateRepository rateRepository;

    @Test
    void shouldAddEvaluationWhenAllParametersAreProvided() {
        // Given
        String Authorization = "Bearer token";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("course_id", "CS101");
        requestBody.put("rate", 5);
        requestBody.put("difficulty", 3);
        requestBody.put("workload", 4);
        requestBody.put("grading", 5);
        requestBody.put("gains", 4);
        requestBody.put("description", "Great course!");

        // When
        Response<?> response = evaluationController.addEvaluation(Authorization, requestBody);

        // Then
        assertEquals(Response.success(null), response);
    }

    @Test
    void shouldAddEvaluationWhenAllParametersAreValid() {
        // Given
        String Authorization = "Bearer token";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("course_id", "CS101");
        requestBody.put("rate", 5);
        requestBody.put("difficulty", 3);
        requestBody.put("workload", 4);
        requestBody.put("grading", 5);
        requestBody.put("gains", 4);
        requestBody.put("description", "Great course!");

        // When
        Response<?> response = evaluationController.addEvaluation(Authorization, requestBody);

        // Then
        assertEquals(Response.success(null), response);
    }

    @Test
    void shouldReturnEvaluationsWhenEvaluationsExist() {
        // Given
        List<Rate> expectedEvaluations = Collections.singletonList(new Rate());
        when(rateRepository.getEvaluations()).thenReturn(expectedEvaluations);

        // When
        Response<?> actualEvaluations = evaluationController.getEvaluations();

        // Then
        assertEquals(Response.success(expectedEvaluations), actualEvaluations);
    }

    @Test
    void shouldReturnEmptyListWhenNoEvaluationsExist() {
        // Given
        when(rateRepository.getEvaluations()).thenReturn(Collections.emptyList());

        // When
        Response<?> actualEvaluations = evaluationController.getEvaluations();

        // Then
        assertEquals(Response.success(Collections.emptyList()), actualEvaluations);
    }

    @Test
    void shouldNotAddEvaluationWhenAuthorizationIsInvalid() {
        // Given
        String Authorization = "Invalid token";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("course_id", "CS101");
        requestBody.put("rate", 5);
        requestBody.put("difficulty", 3);
        requestBody.put("workload", 4);
        requestBody.put("grading", 5);
        requestBody.put("gains", 4);
        requestBody.put("description", "Great course!");

        // When
        Response<?> response = evaluationController.addEvaluation(Authorization, requestBody);

        // Then
        assertNull(response);
    }


}