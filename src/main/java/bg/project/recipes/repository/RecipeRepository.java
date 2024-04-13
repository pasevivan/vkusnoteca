package bg.project.recipes.repository;

import bg.project.recipes.model.entity.CategoryEntity;
import bg.project.recipes.model.entity.RecipeEntity;
import bg.project.recipes.model.enums.SubcategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface RecipeRepository extends JpaRepository<RecipeEntity,Long>, JpaSpecificationExecutor<RecipeEntity> {
    Set<RecipeEntity> findAllBySubcategory(SubcategoryEnum subcategory);

    Set<RecipeEntity> findAllByCategory(CategoryEntity category);
}
