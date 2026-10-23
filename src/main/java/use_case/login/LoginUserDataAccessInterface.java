package use_case.login;

import use_case.common.NotFoundException;
import entity.User;

public interface LoginUserDataAccessInterface {
   /**
    *
    * @param token the user's github access token
    * @return the associated user, or an error
    */
   User get(String token) throws NotFoundException;
}
