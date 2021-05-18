package homework7;

public interface AuthService {

    void start();
    void stop();

    String getNickByLoginAndPass(String login, String pass);
}
