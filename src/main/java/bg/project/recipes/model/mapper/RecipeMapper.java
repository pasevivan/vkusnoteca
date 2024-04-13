package bg.project.recipes.model.mapper;

import bg.project.recipes.model.dto.recipe.CreateRecipeDTO;
import bg.project.recipes.model.dto.recipe.RecipeDTO;
import bg.project.recipes.model.entity.CategoryEntity;
import bg.project.recipes.model.entity.ImageEntity;
import bg.project.recipes.model.entity.RecipeEntity;
import bg.project.recipes.model.enums.CategoryEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Named("categoriesSet")
    static String mapCategories(CategoryEntity category) {
        return category.getCategory().name();
    }

    @Named("imagesSet")
    static Set<String> mapImages(Set<ImageEntity> images) {
        return images.stream().map(ImageEntity::getImageURL).collect(Collectors.toSet());
    }

    @Named("catMap")
    static CategoryEntity catMap(CategoryEnum category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(category);
        return categoryEntity;
    }

    @Mapping(source = "category", target = "category", qualifiedByName = "catMap")
    RecipeEntity createRecipeDTOToRecipeEntity(CreateRecipeDTO createRecipeDTO);

    @Mapping(source = "category", target = "category", qualifiedByName = "categoriesSet")
    @Mapping(source = "images", target = "images", qualifiedByName = "imagesSet")
    @Mapping(source = "owner.firstName", target = "ownerFirstName")
    @Mapping(source = "owner.lastName", target = "ownerLastName")
    RecipeDTO recipeEntityToRecipeDTO(RecipeEntity recipeEntity);
}
