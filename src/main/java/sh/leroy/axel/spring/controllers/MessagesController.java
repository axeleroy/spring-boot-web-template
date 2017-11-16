package sh.leroy.axel.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sh.leroy.axel.spring.model.Message;
import sh.leroy.axel.spring.repositories.MessageRepository;

import java.util.Arrays;

@Controller
public class MessagesController {
    @Autowired
    MessageRepository repository;

    @GetMapping("/message")
    public String messages(Model model) {
        model.addAttribute("messages", repository.findAll());
        return "message/list";
    }

    @GetMapping("/message/{id}")
    public String getMessage(Model model, @PathVariable long id) {
        model.addAttribute("message", repository.findOne(id));
        return "message/show";
    }

    @GetMapping("/message/add")
    public String getForm(Model model) {
        model.addAttribute("message", new Message());
        return "message/add";
    }

    @PostMapping("/message/add")
    public String submit(@ModelAttribute Message message) {
        repository.save(message);
        return "redirect:/message";
    }
}
