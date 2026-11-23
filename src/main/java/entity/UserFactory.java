package entity;

/**
 * Factory for creating User objects.
 */
public class UserFactory {

    public User create(String username, String name, String email) {
        return new User(username, name, email);
    }
}
