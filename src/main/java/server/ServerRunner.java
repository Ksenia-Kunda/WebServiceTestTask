package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kseniya_Kunda on 4/12/2017.
 */
public class ServerRunner {


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);

        while (true) {
            Socket connected = server.accept();
            new Thread(new SocketProcessor(connected)).start();
        }
    }
}
