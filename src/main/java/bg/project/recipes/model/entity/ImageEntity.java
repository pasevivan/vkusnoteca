package bg.project.recipes.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class ImageEntity extends BaseEntity{
    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }
}
