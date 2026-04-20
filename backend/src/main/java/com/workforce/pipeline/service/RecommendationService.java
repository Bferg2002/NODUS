package com.workforce.pipeline.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RecommendationService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> generateRecommendations(
            List<String> userSkills,
            List<String> jobSkills,
            String jobTitle
    ) {

        try {
            String prompt = buildPrompt(userSkills, jobSkills, jobTitle);

            // =========================
            // REQUEST BODY
            // =========================
            Map<String, Object> request = new HashMap<>();
            request.put("model", "gpt-4o-mini");

            request.put("messages", List.of(
                    Map.of(
                            "role", "system",
                            "content", "You are a workforce intelligence engine. Return ONLY valid JSON."
                    ),
                    Map.of(
                            "role", "user",
                            "content", prompt
                    )
            ));

            request.put("temperature", 0.3);

            // =========================
            // HEADERS (CRITICAL FOR AUTH)
            // =========================
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + openaiApiKey);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(request, headers);

            // =========================
            // API CALL
            // =========================
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.openai.com/v1/chat/completions",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // =========================
            // PARSE OPENAI RESPONSE
            // =========================
            Map<String, Object> fullResponse =
                    objectMapper.readValue(response.getBody(), Map.class);

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) fullResponse.get("choices");

            Map<String, Object> message =
                    (Map<String, Object>) choices.get(0).get("message");

            String content = (String) message.get("content");

            // =========================
            // RETURN CLEAN JSON ONLY
            // =========================
            return objectMapper.readValue(content, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AI recommendation failed: " + e.getMessage());
        }
    }

    // =========================
    // PROMPT BUILDER
    // =========================
    private String buildPrompt(
            List<String> userSkills,
            List<String> jobSkills,
            String jobTitle
    ) {

        return """
        You are an explainable workforce intelligence system.

        TASK:
        Compare user skills vs job requirements and produce a structured analysis.

        OUTPUT MUST BE VALID JSON ONLY (no markdown, no text):

        {
          "matchScore": number,
          "missingSkills": [],
          "recommendation": "",
          "trainingSuggestions": []
        }

        USER SKILLS:
        %s

        JOB TITLE:
        %s

        REQUIRED JOB SKILLS:
        %s

        RULES:
        - Be precise
        - No filler text
        - Only output JSON
        """.formatted(userSkills, jobTitle, jobSkills);
    }
}