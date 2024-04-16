package bg.project.recipes.config;

import bg.project.recipes.service.RecipeService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class OwnerSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private final Authentication authentication;
    private final RecipeService recipeService;
    private Object filterObject;
    private Object returnObject;

    public OwnerSecurityExpressionRoot(Authentication authentication,
                                       RecipeService recipeService) {
        super(authentication);
        this.authentication = authentication;
        this.recipeService = recipeService;
    }

    public boolean isOwner(Long id) {
        if (authentication.getPrincipal() == null) {
            return false;
        }

        var userName = authentication.getName();

        //TODO
//        return recipeService.isOwner(userName, id);
        return false;
    }

    public boolean isUserOrAdministrator() {
        if (authentication.getPrincipal() == null) {
            return false;
        }

        var userName = authentication.getName();

        return true;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}