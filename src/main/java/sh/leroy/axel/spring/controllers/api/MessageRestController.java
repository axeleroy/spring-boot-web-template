package sh.leroy.axel.spring.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sh.leroy.axel.spring.model.Message;
import sh.leroy.axel.spring.repositories.MessageRepository;

@RestController
@RequestMapping("/api/message")
public class MessageRestController {
    @Autowired
    MessageRepository repository;

    @GetMapping
    public Iterable findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id]")
    public Message findOne(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody Message message) {
        return repository.save(message);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        if (repository.findOne(id) != null) {
            repository.delete(id);
        }
    }

    @PutMapping("/{id}")
    public Message update(@RequestBody Message message, @PathVariable Long id) {
        if (message.getId() == id && repository.findOne(id) != null) {
            return repository.save(message);
        }
        return null;
    }
}
