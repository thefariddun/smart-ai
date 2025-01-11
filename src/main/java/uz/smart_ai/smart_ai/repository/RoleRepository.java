package uz.smart_ai.smart_ai.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.smart_ai.smart_ai.entity.Roles;

@Repository
public interface RoleRepository extends CrudRepository<Roles, Long> {

    @Transactional
    @Modifying
    void deleteByProfileId(Long profileId);

}
