package Proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProxy implements NetworkProxy {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean initialized = false;
    private String token;

    public ClientProxy(String serverIP, int port, String token) {
        try {
            clientSocket = new Socket(serverIP, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.token = token;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String message) {
        if (!initialized) {
            initializeConnection();
        }
        if (out != null) {
            System.out.println("Sending message" + message);
            out.println(token + message);
        }
    }

    @Override
    public String receive() {
        if (!initialized) {
            initializeConnection();
        }
        try {
            if (in != null) {
                System.out.println("Received message" + in.readLine());
                return in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        try {
            if (clientSocket != null) clientSocket.close();
            if (out != null) out.close();
            if (in != null) in.close();
            System.out.println("Connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeConnection() {
        // Code to initialize the connection
        System.out.println("Initializing server connection...");
        initialized = true;
    }
}
