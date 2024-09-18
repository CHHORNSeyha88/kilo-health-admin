package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
}
