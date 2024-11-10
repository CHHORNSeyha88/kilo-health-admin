package com.kiloit.onlyadmin.util;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.entity.UserVerification;
import com.kiloit.onlyadmin.database.repository.UserVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;
@RequiredArgsConstructor
@Component
public class Verification {
    private final UserVerificationRepository userVerificationRepository;

    public void verification(UserEntity user, String codeRandom) {
        Optional<UserVerification> verification = userVerificationRepository.findByUser(user);
        UserVerification userVerification;
        if(verification.isEmpty()){
            userVerification = new UserVerification();
            userVerification.setUser(user);
        }
        else
            userVerification = verification.get();
        userVerification.setVerifiedCode(codeRandom);
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(5));
        userVerificationRepository.save(userVerification);
    }
}
