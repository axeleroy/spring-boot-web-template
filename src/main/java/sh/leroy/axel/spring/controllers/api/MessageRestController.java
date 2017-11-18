package sh.leroy.axel.spring.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sh.leroy.axel.spring.exceptions.message.MessageIdMissmatchException;
import sh.leroy.axel.spring.exceptions.message.MessageNotFoundException;
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

    @GetMapping("/{id}")
    public Message findOne(@PathVariable long id) {
        return repository.findOne(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody Message message) {
        return repository.save(message);
    }

    @DeleteMapping
    public void delete(@PathVariable long id) {
        repository.findOne(id).orElseThrow(() -> new MessageNotFoundException(id));
        repository.delete(id);
    }

    @PutMapping("/{id}")
    public Message update(@RequestBody Message message, @PathVariable long id) {
        if (message.getId() != id) {
            throw new MessageIdMissmatchException();
        }
        repository.findOne(id).orElseThrow(() -> new MessageNotFoundException(id));
        return repository.save(message);
    }
}
