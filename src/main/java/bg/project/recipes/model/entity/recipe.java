package bg.project.recipes.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
public class recipe extends BaseEntity{
    @Column(nullable = false)
    private String name;

}
