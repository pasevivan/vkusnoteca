package bg.project.recipes.service;

import bg.project.recipes.exception.ObjectNotFoundException;
import bg.project.recipes.model.dto.user.UserDTO;
import bg.project.recipes.model.dto.user.UserRegisterDTO;
import bg.project.recipes.model.entity.RoleEntity;
import bg.project.recipes.model.entity.UserEntity;
import bg.project.recipes.model.enums.RoleEnum;
import bg.project.recipes.model.mapper.UserMapper;
import bg.project.recipes.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final RoleEntity roleEntity = new RoleEntity();

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper,
            UserDetailsService userDetailsService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
    }

    public boolean isUserOrAdministrator(String userName) {
        return userRepository.
                findByEmail(userName).
                filter(this::isUserOrAdministrator).
                isPresent();
    }

    private boolean isUserOrAdministrator(UserEntity user) {
        return user.getUserRoles().
                stream().
                anyMatch(r -> r.getRole() == RoleEnum.USER
                        || r.getRole() == RoleEnum.ADMIN);
    }


    public void createUserIfNotExist(String email) {

        var userOpt = this.userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            var newUser = new UserEntity().
                    setEmail(email).
                    setPassword(null).
                    setFirstName("New").
                    setLastName("User").
                    setUserRoles(Set.of());
            userRepository.save(newUser);
        }
    }


    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {
        UserEntity userToRegister = userMapper.userDtoToUserEntity(userRegisterDTO);
        userToRegister.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userToRegister = userRepository.save(userToRegister);
        login(userToRegister.getEmail());
    }

    public void login(String userEmail) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
    }

    public void deleteInactiveAccounts() {
        userRepository.deleteInactiveAccounts();
    }

    public UserEntity getUser(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }

    public UserEntity getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User was not found!"));
    }

    public Optional<UserDTO> getUserDetails(Long userId) {
        return userRepository.
                findById(userId).
                map(userMapper::userEntityToUserDTO);
    }

    public void update(Long userId, String username, String firstName, String lastName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        userRepository.updateUserNames(userId, firstName, lastName);
    }

}
