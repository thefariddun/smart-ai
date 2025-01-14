package uz.smart_ai.smart_ai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.smart_ai.smart_ai.dto.RegistrationDTO;
import uz.smart_ai.smart_ai.entity.User;
import uz.smart_ai.smart_ai.enums.GeneralStatus;
import uz.smart_ai.smart_ai.enums.UserRoles;
import uz.smart_ai.smart_ai.exceptions.BadException;
import uz.smart_ai.smart_ai.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmailSendingService emailSendingService;
    @Autowired
    private UserService userService;

    public String registration(RegistrationDTO registrationDTO) {
        logger.info("registration");
        Optional<User> user = userRepository.findByPhoneNumberAndVisibleTrue(registrationDTO.getPhoneNumber());

        if (user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.getStatus().equals(GeneralStatus.IN_REGISTER)) {
                roleService.deleteRoles(existingUser.getId());
                userRepository.delete(existingUser);
            } else {
                throw new BadException("Foydalanuvchi allaqachon mavjud");
            }
        }
        User newUser = new User();
        newUser.setName(registrationDTO.getName());
        newUser.setPhoneNumber(registrationDTO.getPhoneNumber());
        newUser.setPassword(bCryptPasswordEncoder.encode(registrationDTO.getPassword()));
        newUser.setStatus(GeneralStatus.IN_REGISTER);
        newUser.setVisible(true);
        newUser.setCreatedDate(LocalDateTime.now());
        userRepository.save(newUser);
        roleService.save(UserRoles.ROLE_USER, newUser.getId());
        emailSendingService.sendRegistrationEmail(registrationDTO.getPhoneNumber(), newUser.getId());
        return "Tizimga kirdingiz";
    }

    public String regVerification(Long id) {
        User user = userService.getById(id);
        if (user.getStatus().equals(GeneralStatus.IN_REGISTER)) {
            userRepository.changeStatus(id, GeneralStatus.ACTIVE);
            return "Tizimga muvaffaqiyatli kirdingiz";
        }
        throw new BadException("Muvaffaqiyatsiz urinish");
    }
}
