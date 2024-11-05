package com.kiloit.onlyadmin.database.repository;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r LEFT JOIN FETCH r.permissions AS p WHERE r.id = :id AND r.deletedAt IS NULL AND (r.name != 'Administrator')")
    Optional<RoleEntity> findByIdFetchPermission(@Param("id") Long id);

    @Query("SELECT r FROM RoleEntity AS r WHERE (:name='all' OR r.name LIKE concat('%', :name, '%')) AND r.deletedAt IS NULL AND (r.name != 'Administrator') order by r.id ")
    Page<RoleEntity> findByNameContainsOrderByNameAsc(@Param("name") String name, Pageable pageable);

    Optional<RoleEntity> findByIdAndDeletedAtNull(Long id);

    Optional<RoleEntity> findByCodeAndDeletedAtNull(String code);

    @Query("SELECT r FROM RoleEntity r JOIN FETCH r.permissions AS p WHERE r.name = :roleName AND r.deletedAt IS NULL AND (r.name != 'Administrator')")
    RoleEntity findByName(String roleName);
}
