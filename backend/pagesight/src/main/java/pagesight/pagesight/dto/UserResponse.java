package pagesight.pagesight.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private List<String> roles;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, String email, LocalDateTime createdAt, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
