package bg.project.recipes.model.dto.recipe;

import bg.project.recipes.model.enums.CategoryEnum;
import bg.project.recipes.model.enums.SubcategoryEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class CreateRecipeDTO {
    @NotBlank
    private String name;

    @NotNull
    private CategoryEnum category;

    @NotNull
    private SubcategoryEnum subcategory;
    private String image;

    @NotNull
    @Min(1)
    private int portions;
    @NotBlank
    private String products;
    @NotBlank
    private String making;

    @NotNull
    @Positive
    private int cooking_time_minutes;

    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public SubcategoryEnum getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubcategoryEnum subcategory) {
        this.subcategory = subcategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getMaking() {
        return making;
    }

    public void setMaking(String making) {
        this.making = making;
    }

    public int getCooking_time_minutes() {
        return cooking_time_minutes;
    }

    public void setCooking_time_minutes(int cooking_time_minutes) {
        this.cooking_time_minutes = cooking_time_minutes;
    }
}
