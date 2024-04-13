package bg.project.recipes.model.dto.category;

import bg.project.recipes.model.enums.CategoryEnum;

public class CategoryDTO {

    private Long id;
    private CategoryEnum category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
