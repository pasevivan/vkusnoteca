package bg.project.recipes.config;

import bg.project.recipes.repository.UserRepository;
import bg.project.recipes.service.RecipeUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                // define which requests are allowed and which not
                        authorizeRequests().
                // everyone can download static resources (css, js, images)
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
//                requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll().
        antMatchers(
        "/",
        "/user/login",
        "/user/register",
        "/error",
        "/info",
        "/terms_of_use",
        "/data_policy",
        "/contacts",
        "/user/profile",
        "/recipes/recipe_details",
        "/recipes/recipe_search",
        "/recipes/recipes_dashboard/**",
        "/recipes/evict",
        "/maintenance",
        "/api/**"
).permitAll().
                antMatchers(
                        "/recipe/recipe_add",
                        "user/profile_edit"
                ).authenticated().
                // all other pages are available for logger in users
                        anyRequest().
                authenticated().
                and().
                // configuration of form login
                        formLogin().
                // the custom login form
                        loginPage("/user/login").
                // the name of the username form field
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                // the name of the password form field
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                // where to go in case that the login is successful
                        defaultSuccessUrl("/").
                // where to go in case that the login failed
                        failureForwardUrl("/user/login-error").
                and().
                // configure logout
                        logout().
                // which is the logout url, must be POST request
                        logoutUrl("/user/logout").
                // on logout go to the home page
                        logoutSuccessUrl("/").
                // invalidate the session and delete the cookies
                        invalidateHttpSession(true).
                deleteCookies("JSESSIONID");
//                and().
        // allow oauth login
//                        oauth2Login().
//                loginPage("/user/login").
//                successHandler(oAuthSuccessHandler);

        return http.build();
    }


    @Primary
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new RecipeUserDetailsService(userRepository);
    }

}
