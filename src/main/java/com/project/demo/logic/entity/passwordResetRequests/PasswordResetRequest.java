package com.project.demo.logic.entity.passwordResetRequests;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "passwordResetRequest")
@Entity
public class PasswordResetRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    @Column(unique = true, nullable = false)
    private LocalDateTime expirationDate;
    @Column(nullable = false)
    private String resetCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }
}
