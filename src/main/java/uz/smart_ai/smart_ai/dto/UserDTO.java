package uz.smart_ai.smart_ai.dto;

import lombok.Getter;
import lombok.Setter;
import uz.smart_ai.smart_ai.enums.GeneralStatus;

import java.time.LocalDateTime;

@Getter
@Setter

public class UserDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private String password;
    private GeneralStatus status;
    private Boolean visible;
    private LocalDateTime createdAt;

}
