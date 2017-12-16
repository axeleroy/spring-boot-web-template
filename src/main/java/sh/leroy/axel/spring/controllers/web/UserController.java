package sh.leroy.axel.spring.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import sh.leroy.axel.spring.dto.UserDto;
import sh.leroy.axel.spring.exceptions.user.EmailExistsException;
import sh.leroy.axel.spring.exceptions.user.UsernameExistsException;
import sh.leroy.axel.spring.model.User;
import sh.leroy.axel.spring.repositories.UserRepository;
import sh.leroy.axel.spring.service.user.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserRepository repository;

    @Autowired
    UserService service;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return showLoginForm(model);
    }

    @GetMapping("/login/error")
    public String loginFailure(Model model) {
        model.addAttribute("loginError", true);
        return showLoginForm(model);
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("logout", true);
        return showLoginForm(model);
    }

    @GetMapping("/unauthorized")
    public String accessDenied() {
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserDto accountDto,
            BindingResult result, WebRequest request, Errors errors, Model model) {
        User registered = null;

        if (!result.hasErrors()) {
            try {
                registered = service.registerNewUserAccount(accountDto);
            } catch (EmailExistsException e) {
                model.addAttribute("emailExists", true);
                e.printStackTrace();
            } catch (UsernameExistsException e) {
                model.addAttribute("usernameExists", true);
            }
        } else {
            errors.getAllErrors().forEach(e -> {
                ObjectError resolvable = (ObjectError) e;
                switch (resolvable.getDefaultMessage()) {
                    case "may not be empty":
                        model.addAttribute("emptyField", true);
                        break;
                    case "Invalid email":
                        model.addAttribute("invalidEmail", true);
                        break;
                    case "Invalid username":
                        model.addAttribute("invalidUsername", true);
                        break;
                    case "Passwords don't match":
                        model.addAttribute("passwordMismatch", true);
                    default:
                        break;
                }
            });
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        } else {
            repository.save(registered);
            model.addAttribute("registerSuccessful", true);
        }

        return showLoginForm(model);
    }
}
