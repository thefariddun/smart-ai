package uz.smart_ai.smart_ai.dto;


import jakarta.validation.constraints.NotBlank;

public class RegistrationDTO {

    @NotBlank(message = "Ismni kiriting")
    private String name;
    @NotBlank(message = "Telefon raqamni kiriting")
    private String phoneNumber;
    @NotBlank(message = "Parol kiriting")
    private String password;

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
}
