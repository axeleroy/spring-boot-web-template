package sh.leroy.axel.spring.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sh.leroy.axel.spring.model.Message;
import sh.leroy.axel.spring.repositories.MessageRepository;

import java.util.Arrays;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageRepository repository;

    @GetMapping("/add")
    public String getForm(Model model) {
        model.addAttribute("message", new Message());
        return "message/add";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("messages", repository.findAll());
        return "message/list";
    }

    @GetMapping("/{id}")
    public String findOne(Model model, @PathVariable Long id) {
        model.addAttribute("message", repository.findOne(id));
        return "message/show";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Message message) {
        repository.save(message);
        return "redirect:/message";
    }
}
