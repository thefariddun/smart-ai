package uz.smart_ai.smart_ai.entity;

import jakarta.persistence.*;
import uz.smart_ai.smart_ai.enums.GeneralStatus;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate;
    private String verificationCode;
    private LocalDateTime codeExpirationTime;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getCodeExpirationTime() {
        return codeExpirationTime;
    }

    public void setCodeExpirationTime(LocalDateTime codeExpirationTime) {
        this.codeExpirationTime = codeExpirationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GeneralStatus getStatus() {
        return status;
    }

    public void setStatus(GeneralStatus status) {
        this.status = status;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
