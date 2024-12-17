package Proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerProxy implements NetworkProxy {
    private static final Logger logger = Logger.getLogger(ServerProxy.class.getName());
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean initialized = false;
    private String token;

    public ServerProxy(int port, String token) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
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
            out.println(message);
        }
    }

    @Override
    public String receive() {
        if (!initialized) {
            initializeConnection();
        }
        try {
            if (in != null) {
                String message = in.readLine();
                System.out.println("Received message" + message);
                if(isValid(message))
                {
                    return message.substring(token.length());
                }
                System.out.println("Invalid token received.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        if(initialized)
        {
            try {
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
                if (out != null) out.close();
                if (in != null) in.close();
                System.out.println("Connection closed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValid(String message) {
        return message.startsWith(token);
    }

    private void initializeConnection() {
        // Code to initialize the connection
        System.out.println("Initializing server connection...");
        initialized = true;
    }
}
