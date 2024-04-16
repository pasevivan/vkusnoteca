package bg.project.recipes.web;

import bg.project.recipes.model.dto.user.UserDTO;
import bg.project.recipes.model.dto.user.UserRegisterDTO;
import bg.project.recipes.model.entity.UserEntity;
import bg.project.recipes.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute("userModel")
    public UserRegisterDTO initUserModel() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userModel", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:/user/register";
        }
        userService.registerAndLogin(userRegisterDTO);
        return "redirect:/";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/user/login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        UserEntity user = userService.getUser(username);

        UserDTO userProfile = new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );

        model.addAttribute("user", userProfile);

        return "profile";
    }

    @PreAuthorize("isUserOrAdministrator()")
    @GetMapping("/profile/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        var user = userService.getUser(id);

        model.addAttribute("user", user);

        return "profile_edit";
    }

    @PostMapping("/profile_edit")
    public String edit(Principal principal, @Valid UserDTO userProfileDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        UserEntity user = userService.getUser(username);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userProfileDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);

            return "redirect:/user/profile";
        }
        userService.update(
                user.getId(),
                user.getEmail(),
                userProfileDTO.getFirstName(),
                userProfileDTO.getLastName()
        );
        return "redirect:/user/profile";
    }
}
