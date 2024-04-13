package bg.project.recipes.model.dto.recipe;

import java.util.Set;

public class RecipeDTO {

    private Long id;
    private String name;
    private String category;
    private String subcategory;
    private String making;
    private String products;
    private int cooking_time_minutes;
    private Set<String> images;
    private String difficulty;
    private String author;

    public RecipeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getMaking() {
        return making;
    }

    public void setMaking(String making) {
        this.making = making;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public int getCooking_time_minutes() {
        return cooking_time_minutes;
    }

    public void setCooking_time_minutes(int cooking_time_minutes) {
        this.cooking_time_minutes = cooking_time_minutes;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
