package com.workforce.pipeline.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AdzunaService {

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.app.key}")
    private String appKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> fetchJobs(String query, String location) {

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);

            String url = String.format(
                    "https://api.adzuna.com/v1/api/jobs/us/search/1?app_id=%s&app_key=%s&what=%s&where=%s",
                    appId,
                    appKey,
                    encodedQuery,
                    encodedLocation
            );

            System.out.println("🌐 CALLING ADZUNA: " + url);

            String response = restTemplate.getForObject(url, String.class);

            if (response == null || response.isEmpty()) {
                System.out.println("❌ EMPTY RESPONSE");
                return new ArrayList<>();
            }

            JsonNode root = mapper.readTree(response);
            JsonNode results = root.path("results");

            if (!results.isArray() || results.size() == 0) {
                System.out.println("⚠ NO JOB RESULTS");
                return new ArrayList<>();
            }

            List<Map<String, Object>> jobs = new ArrayList<>();

            for (JsonNode job : results) {

                Map<String, Object> cleaned = new HashMap<>();

                cleaned.put("id", job.path("id").asText()); // 🔥 ADD THIS FIX

                cleaned.put("title", job.path("title").asText("Untitled"));
                cleaned.put("description", job.path("description").asText(""));
                cleaned.put("company", job.path("company").path("display_name").asText("Unknown"));
                cleaned.put("location", job.path("location").path("display_name").asText("Unknown"));
                cleaned.put("created", job.path("created").asText(""));

                jobs.add(cleaned);
            }

            System.out.println("🏁 FETCH COMPLETE - jobs: " + jobs.size());

            return jobs;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Adzuna fetch failed");
        }
    }
}