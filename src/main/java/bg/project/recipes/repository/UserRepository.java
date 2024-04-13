package bg.project.recipes.repository;

import bg.project.recipes.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(@Email String email);

    @Transactional
    @Modifying
    @Query("delete from UserEntity user where user.active = false")
    void deleteInactiveAccounts();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserEntity user set user.firstName=:firstName, user.lastName=:lastName where user.id=:id")
    void updateUserNames(
            @Param("id") Long userId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
    );
}
