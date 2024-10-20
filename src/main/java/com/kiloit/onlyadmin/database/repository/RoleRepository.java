package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByIdAndDeletedAtNull(Long id);

    RoleEntity findByUsers(UserEntity userEntity);

    @Query("select r.id,r.code,r.name,r.module,p.id from RoleEntity r join r.permissions as p where r.id = :id")
    List<Object[]> findByIdFetchPermissions(@Param("id") Long id);

    @Query("select r from RoleEntity r join r.permissions as p where r.id = :id")
    RoleEntity findByIdFetchPermission(@Param("id") Long id);

    @Query("select r from RoleEntity r where (:name='all' or r.name like concat('%', :name, '%')) order by r.id")
    Page<RoleEntity> findByNameContainsOrderByNameAsc(@Param("name") String name, Pageable pageable);

    RoleEntity findByName( String roleName);

}
