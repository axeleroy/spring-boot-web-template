package sh.leroy.axel.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import sh.leroy.axel.spring.model.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findBySender(String sender);
}
