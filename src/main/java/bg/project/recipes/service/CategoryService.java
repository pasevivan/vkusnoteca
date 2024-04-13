package bg.project.recipes.service;

import bg.project.recipes.model.dto.category.CategoryDTO;
import bg.project.recipes.model.entity.CategoryEntity;
import bg.project.recipes.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository brandRepository) {
        this.categoryRepository = brandRepository;
    }

    public Set<CategoryDTO> getAllCategories() {
        return categoryRepository.
                findAll().
                stream().
                map(this::mapCategory).
                collect(Collectors.toSet());
    }

    private CategoryDTO mapCategory(CategoryEntity categoryEntity) {
        categoryEntity.setCategory(categoryEntity.getCategory());
        return new CategoryDTO();

    }


}
