package uz.smart_ai.smart_ai.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.smart_ai.smart_ai.entity.Roles;
import uz.smart_ai.smart_ai.enums.UserRoles;
import uz.smart_ai.smart_ai.repository.RoleRepository;

import java.time.LocalDateTime;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void save(UserRoles role, Long userId) {
        Roles roles = new Roles();
        roles.setProfileId(userId);
        roles.setRole(role);
        roles.setCreatedDate(LocalDateTime.now());
        roleRepository.save(roles);

    }

    public void deleteRoles(Long id) {
        roleRepository.deleteByProfileId(id);
    }
}
