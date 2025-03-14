public class UserRegisteredException extends Throwable {
    public UserRegisteredException() {
        super("User is already registered.");
    }
}
