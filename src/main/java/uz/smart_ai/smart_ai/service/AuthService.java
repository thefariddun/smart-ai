package uz.smart_ai.smart_ai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.smart_ai.smart_ai.dto.LoginDTO;
import uz.smart_ai.smart_ai.dto.RegistrationDTO;
import uz.smart_ai.smart_ai.entity.User;
import uz.smart_ai.smart_ai.enums.GeneralStatus;
import uz.smart_ai.smart_ai.enums.UserRoles;
import uz.smart_ai.smart_ai.exceptions.BadException;
import uz.smart_ai.smart_ai.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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
        logger.info("Ro‘yxatdan o‘tish jarayoni boshlandi");
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

        String verificationCode = generateVerificationCode(6);
        newUser.setVerificationCode(verificationCode);
        newUser.setCodeExpirationTime(LocalDateTime.now().plusMinutes(10));

        userRepository.save(newUser);
        roleService.save(UserRoles.ROLE_USER, newUser.getId());
        emailSendingService.sendRegistrationEmail(registrationDTO.getPhoneNumber(), verificationCode);
        return "Tasdiqlash kodi yuborildi";
    }

    public String verifyRegistration(String phoneNumber, String verificationCode) {
        logger.info("Email ga kod yuborish boshlandi");
        User user = userRepository.findByPhoneNumberAndVisibleTrue(phoneNumber)
                .orElseThrow(() -> new BadException("Foydalanuvchi topilmadi"));

        if (!user.getVerificationCode().equals(verificationCode)) {
            throw new BadException("Tasdiqlash kodi noto‘g‘ri");
        }

        if (user.getCodeExpirationTime().isBefore(LocalDateTime.now())) {
            throw new BadException("Tasdiqlash kodi muddati tugagan");
        }

        userRepository.changeStatus(user.getId(), GeneralStatus.ACTIVE);
        return "Hisobingiz muvaffaqiyatli tasdiqlandi";
    }

    public static String generateVerificationCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public String login(LoginDTO loginDTO) {
        User user = userRepository.findByPhoneNumberAndVisibleTrue(loginDTO.getPhoneNumber())
                .orElseThrow(() -> new BadException("Foydalanuvchi topilmadi"));

        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadException("Noto'g'ri parol");
        }

        if (user.getStatus().equals(GeneralStatus.IN_REGISTER)) {
            throw new BadException("Foydalanuvchi hali tasdiqlanmagan");
        }

        if (user.getStatus().equals(GeneralStatus.BLOCKED)) {
            throw new BadException("Sizning hisobingiz bloklangan");
        }

        return "Login muvaffaqiyatli amalga oshirildi";
    }
}
