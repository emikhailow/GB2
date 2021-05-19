package homework6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private static volatile boolean serverToBeShutDown = false;

    public static void main(String[] args) {

        Socket socket = null;

        try(ServerSocket serverSocket = new ServerSocket(Constants.PORT)){

            System.out.println("Server has been started. Waiting for connection...");
            socket = serverSocket.accept();
            System.out.println("Client connected!");
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            Thread thread1 = new Thread(() -> {

                String message;
                Scanner in = new Scanner(System.in);

                while(true){

                    message = in.nextLine();
                    message = message.trim();
                    try {
                        outputStream.writeUTF(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(message.equals(Constants.STOP_WORD)){
                        serverToBeShutDown = true;
                        break;
                    }

                }

            });
            Thread thread2 = new Thread(() -> {

                while(true){

                    String string = null;
                    try {
                        string = inputStream.readUTF();
                        System.out.println("Message received: " + string);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(serverToBeShutDown){
                        break;
                    }

                }

            });
            thread1.start();
            thread2.start();
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Server is shutting down...");

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

}
