package bg.project.recipes.model.entity;

import bg.project.recipes.model.enums.CategoryEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RecipeEntity> recipes = new HashSet<>();

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Set<RecipeEntity> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<RecipeEntity> recipes) {
        this.recipes = recipes;
    }
}
