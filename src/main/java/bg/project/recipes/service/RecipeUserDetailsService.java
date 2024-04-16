package bg.project.recipes.service;

import bg.project.recipes.model.entity.RoleEntity;
import bg.project.recipes.model.entity.UserEntity;
import bg.project.recipes.model.user.RecipeUserDetails;
import bg.project.recipes.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RecipeUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public RecipeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username));
    }

    private UserDetails map(UserEntity userEntity) {
        return new RecipeUserDetails(
                userEntity.getId(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getUserRoles()
                        .stream()
                        .map(this::map)
                        .toList()
        );
    }

    private GrantedAuthority map(RoleEntity roleEntity) {
        return new SimpleGrantedAuthority("ROLE_" +
                roleEntity.getRole().name());
    }
}
