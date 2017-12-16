package sh.leroy.axel.spring.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sh.leroy.axel.spring.dto.UserDto;
import sh.leroy.axel.spring.exceptions.user.EmailExistsException;
import sh.leroy.axel.spring.exceptions.user.UsernameExistsException;
import sh.leroy.axel.spring.model.User;
import sh.leroy.axel.spring.repositories.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;
     
    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto)
      throws EmailExistsException {
         
        if (emailExist(accountDto.getEmail())) {  
            throw new EmailExistsException(
              "There is an account with that email adress: "
              +  accountDto.getEmail());
        }
        if (usernameExists(accountDto.getUsername())) {
            throw new UsernameExistsException(
                "There is an account with that username: "
                + accountDto.getUsername());
        }

        return new User(accountDto);
    }
    private boolean emailExist(String email) {
        User user = repository.findByEmailIgnoreCase(email);
        return user != null;
    }

    private boolean usernameExists(String username) {
        User user = repository.findByUsernameIgnoreCase(username).orElse(null);
        return user != null;
    }
}