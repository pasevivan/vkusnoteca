package bg.project.recipes.service;

import bg.project.recipes.model.dto.recipe.CreateRecipeDTO;
import bg.project.recipes.model.dto.recipe.RecipeDTO;
import bg.project.recipes.model.entity.CategoryEntity;
import bg.project.recipes.model.entity.RecipeEntity;
import bg.project.recipes.model.entity.UserEntity;
import bg.project.recipes.model.enums.CategoryEnum;
import bg.project.recipes.model.enums.RoleEnum;
import bg.project.recipes.model.enums.SubcategoryEnum;
import bg.project.recipes.model.mapper.RecipeMapper;
import bg.project.recipes.repository.CategoryRepository;
import bg.project.recipes.repository.RecipeRepository;
import bg.project.recipes.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final RecipeMapper recipeMapper;


    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, CategoryRepository categoryRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.recipeMapper = recipeMapper;
    }

    public boolean isOwner(String userName, Long recipeId) {
        boolean isOwner = recipeRepository.
                findById(recipeId).
                filter(o -> o.getAuthor().getEmail().equals(userName)).
                isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository.
                findByEmail(userName).
                filter(this::isAdmin).
                isPresent();
    }

    private boolean isAdmin(UserEntity user) {
        return user.getUserRoles().
                stream().
                anyMatch(r -> r.getRole() == RoleEnum.ADMIN);
    }

    public Set<RecipeDTO> getAllRecipes() {
        return recipeRepository.
                findAll().
                stream().
                map(recipeMapper::recipeEntityToRecipeDTO).collect(Collectors.toSet());
    }

    public Page<RecipeDTO> getAllRecipesPageable(Pageable pageable) {
        return recipeRepository.
                findAll(pageable).
                map(recipeMapper::recipeEntityToRecipeDTO);

    }

    @Cacheable("recipeByCategory")
    public Set<RecipeDTO> getRecipesByCategory(String category) {
        CategoryEntity categoryEntity = categoryRepository.findByCategory(CategoryEnum.valueOf(category)).orElseThrow();
        return recipeRepository.
                findAllByCategory(categoryEntity).
                stream().
                map(recipeMapper::recipeEntityToRecipeDTO).collect(Collectors.toSet());
    }

    @Cacheable("recipeBySubcategory")
    public Set<RecipeDTO> getRecipesBySubcategory(String subcategory) {
        return recipeRepository.
                findAllBySubcategory(SubcategoryEnum.valueOf(subcategory)).
                stream().
                map(recipeMapper::recipeEntityToRecipeDTO).collect(Collectors.toSet());
    }



    @Cacheable("recipeById")
    public Optional<RecipeDTO> findRecipeById(Long recipeId) {
        return recipeRepository.
                findById(recipeId).
                map(recipeMapper::recipeEntityToRecipeDTO);
    }

    public void addRecipe(CreateRecipeDTO addRecipeDTO, UserDetails userDetails) throws IOException {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);


        RecipeEntity newRecipe = recipeMapper.
                createRecipeDTOToRecipeEntity(addRecipeDTO);

        UserEntity owner = userRepository.findByEmail(userDetails.getUsername()).
                orElseThrow();

        CategoryEntity categoryEntity = categoryRepository.findByCategory(addRecipeDTO.getCategory()).orElseThrow();
        newRecipe.setCategory(categoryEntity);

        newRecipe.setStatus(false);
        newRecipe.setAuthor(owner);


    }



    @CacheEvict(cacheNames = "recipes", allEntries = true)
    public void refresh() {
    }
}
