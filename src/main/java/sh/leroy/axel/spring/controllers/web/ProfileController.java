package sh.leroy.axel.spring.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sh.leroy.axel.spring.authentication.AuthenticationFacade;
import sh.leroy.axel.spring.repositories.UserRepository;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepository repository;
    @Autowired
    AuthenticationFacade facade;

    @GetMapping("/")
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
                repository.findByUsernameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username)));
        return "profile/show";
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    protected String handleUserNotFound(UsernameNotFoundException ex) {
        return "profile/404";
    }
}
