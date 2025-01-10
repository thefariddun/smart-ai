package uz.smart_ai.smart_ai.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.smart_ai.smart_ai.enums.UserRoles;

@Table(name = "roles")
@Entity
@Getter
@Setter
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

}
