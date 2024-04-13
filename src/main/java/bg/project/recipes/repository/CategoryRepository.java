package bg.project.recipes.repository;

import bg.project.recipes.model.entity.CategoryEntity;
import bg.project.recipes.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByCategory(CategoryEnum category);
}
