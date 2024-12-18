package Proxy;

public interface NetworkProxy {
    void send(String message);
    String receive();
    void close();
}
