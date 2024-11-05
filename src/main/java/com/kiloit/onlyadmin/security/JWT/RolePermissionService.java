package com.kiloit.onlyadmin.security.JWT;
import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RoleRepository roleRepository;
    public Set<String> getPermissionsForRole(String roleName) {
        RoleEntity role = roleRepository.findByName(roleName);
        Set<String> permissions = new HashSet<>();
        if (role != null) {
            for (PermissionEntity permission : role.getPermissions()) {
                permissions.add("SCOPE_" + permission.getName());
            }
        }
        permissions.add("ROLE_" + roleName);
        return permissions;
    }
}
