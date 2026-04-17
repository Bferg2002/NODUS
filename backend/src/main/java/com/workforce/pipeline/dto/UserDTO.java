package com.workforce.pipeline.dto;

/**
 * UserDTO
 *
 * Purpose:
 * - Represents a safe version of the User entity for API responses
 * - Prevents exposing sensitive data (like password)
 * - Avoids lazy loading issues (like skills collection)
 */
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String role;

    // Default constructor (required for JSON serialization/deserialization)
    public UserDTO() {}

    // Constructor for easy mapping from Entity → DTO
    public UserDTO(Integer id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // ===== Getters & Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}