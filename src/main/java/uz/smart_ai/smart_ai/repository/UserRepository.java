package uz.smart_ai.smart_ai.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.smart_ai.smart_ai.entity.User;
import uz.smart_ai.smart_ai.enums.GeneralStatus;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumberAndVisibleTrue(String phoneNumber);

    Optional<User> findByIdAndVisibleTrue(Long id);

    @Modifying
    @Transactional
    @Query("update User set status=?2 where id=?1")
    void changeStatus(Long id, GeneralStatus generalStatus);
}
