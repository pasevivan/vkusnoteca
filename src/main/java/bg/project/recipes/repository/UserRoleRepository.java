package bg.project.recipes.repository;

import bg.project.recipes.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<RoleEntity, Long> {
}
