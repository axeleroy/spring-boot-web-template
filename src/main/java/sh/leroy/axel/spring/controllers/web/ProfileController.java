package sh.leroy.axel.spring.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import sh.leroy.axel.spring.authentication.AuthenticationFacade;
import sh.leroy.axel.spring.model.User;
import sh.leroy.axel.spring.repositories.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepository repository;
    @Autowired
    AuthenticationFacade facade;

    @GetMapping
    public String find() {
        return "redirect:/profile/me";
    }

    @GetMapping("/me")
    public String getUserProfile() {
        Authentication auth = facade.getAuthentication();
        return "redirect:/profile/" + auth.getName();
    }

    @GetMapping("/{username}")
    public String findOne(Model model, @PathVariable String username) {
        model.addAttribute("user",
                repository.findByUsernameIgnoreCase(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username)));
        return "profile/show";
    }

    @GetMapping("/edit")
    public String getUserEdit(Model model) {
        Authentication auth = facade.getAuthentication();
        model.addAttribute("user",
                repository.findByUsernameIgnoreCase(auth.getName())
                        .orElseThrow(() -> new InsufficientAuthenticationException("User not authenticated")));
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute User updatedUser) {
        Authentication auth = facade.getAuthentication();
        User user = repository.findByUsernameIgnoreCase(auth.getName()).orElse(null);

        if (user != null) {
            user.setEmail(updatedUser.getEmail());
            user.setLastName(updatedUser.getLastName());
            user.setFirstName(updatedUser.getFirstName());

            repository.save(user);
        }
        return "redirect:/profile/me";
    }

    @ExceptionHandler({ UsernameNotFoundException.class, InsufficientAuthenticationException.class })
    protected String handleUserNotFound(Exception ex) {
        return "profile/404";
    }

    @ExceptionHandler(BindException.class)
    protected String handleEmailNotValid(BindException ex) {
        return "redirect:/profile/me";
    }
}
