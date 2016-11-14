package chat.client;

public interface NewUserListener<T> {
    void newUser(T name);
}
