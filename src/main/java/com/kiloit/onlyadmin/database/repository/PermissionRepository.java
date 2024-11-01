package com.kiloit.onlyadmin.database.repository;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.model.role.response.PermissionStatusResponse;

public interface PermissionRepository extends JpaRepository<PermissionEntity,Long>{

    PermissionEntity findByName(String permissionName);
    Set<PermissionEntity> findAllByIdIn(Set<Long> ids);

    @Query("""
            SELECT new com.kiloit.onlyadmin.model.role.response.PermissionStatusResponse(
                p.id, p.name, p.module,
                CASE 
                    WHEN :roleId IS NULL AND :module IS NULL THEN false
                    WHEN :roleId IS NULL AND p.module = :module THEN true
                    WHEN :roleId IS NOT NULL AND r.id IS NOT NULL THEN true
                    ELSE false
                END
            )
            FROM PermissionEntity p
            LEFT JOIN p.roles r ON r.id = :roleId
            WHERE (
                (:roleId IS NULL AND :module IS NULL) OR
                (:roleId IS NULL AND p.module = :module) OR
                (:roleId IS NOT NULL)
            )
    """)
    Page<PermissionStatusResponse> findAllPermission(@Param("roleId") Long roleId,@Param("module") String module, Pageable pageable);
    
    
}
