package server;

import java.io.*;
import java.net.Socket;
/**
 * Created by Ksenia on 12.04.2017.
 */
public class SocketProcessor implements Runnable {

    private BufferedReader bufferedReader;
    private DataOutputStream dataOutputStream;

    public SocketProcessor(Socket clientSocket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void run() {
        try {
            String requestMessage = "";
            do {
                requestMessage += (char) bufferedReader.read();
            } while (bufferedReader.ready() );

            RequestHandler request = new RequestHandler(requestMessage);
            ResponseMessenger response = new ResponseMessenger(request);
            response.httpMethod();

            dataOutputStream.writeBytes(response.getResponseMessage());

        } catch
                (Exception e) {
            e.printStackTrace();
        }
    }
}

