package bg.project.recipes.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Column(nullable = false)
    private boolean active;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RecipeEntity> userRecipes = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> userRoles = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }



    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<RecipeEntity> getUserRecipes() {
        return userRecipes;
    }

    public void setUserRecipes(Set<RecipeEntity> userRecipes) {
        this.userRecipes = userRecipes;
    }

    public Set<RoleEntity> getUserRoles() {
        return userRoles;
    }

    public boolean isActive() {
        return active;
    }

    public UserEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public UserEntity setUserRoles(Set<RoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }
}
