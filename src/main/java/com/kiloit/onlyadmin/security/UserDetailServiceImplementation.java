package com.kiloit.onlyadmin.security;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImplementation implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String type="";
        Optional<UserEntity> user = userRepository
        .findUserOrEmail(userName);

        try {
            if(user.isEmpty())
            throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

        if(userName.contains("@gmail.com")) type="email";
        
        CustomUserDetail customUserDetail = new CustomUserDetail(type);
        customUserDetail.setUser(user.get());
        return customUserDetail;
    }
    
}
