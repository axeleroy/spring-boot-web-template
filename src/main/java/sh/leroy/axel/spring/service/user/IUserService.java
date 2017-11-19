package sh.leroy.axel.spring.service.user;

import sh.leroy.axel.spring.dto.UserDto;
import sh.leroy.axel.spring.exceptions.user.EmailExistsException;
import sh.leroy.axel.spring.model.User;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto)
      throws EmailExistsException;
}