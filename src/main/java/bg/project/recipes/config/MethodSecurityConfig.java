package bg.project.recipes.config;

import bg.project.recipes.service.RecipeService;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final RecipeService recipeService;

    public MethodSecurityConfig(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new SecurityExpressionHandler(recipeService);
    }
}
