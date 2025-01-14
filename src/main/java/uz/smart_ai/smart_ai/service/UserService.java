package uz.smart_ai.smart_ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.smart_ai.smart_ai.entity.User;
import uz.smart_ai.smart_ai.exceptions.BadException;
import uz.smart_ai.smart_ai.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findByIdAndVisibleTrue(id).orElseThrow(
                () -> new BadException("User not found"));
    }
}
