package homework6;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private volatile boolean serverIsShutDown = false;

    public Client() {

        System.out.println("Write message...");
        try{
            openConnection();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    private void openConnection() throws IOException {

        socket = new Socket(Constants.HOST, Constants.PORT);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {

            Thread thread1 = new Thread(() -> {

                try{

                    while(true){
                        String strFromServer = inputStream.readUTF();
                        if(strFromServer.equals(Constants.STOP_WORD)){
                            serverIsShutDown = true;
                            break;
                        }
                        System.out.println("Server: " + strFromServer);
                    }

                }catch(IOException e){
                    e.printStackTrace();
                }

            });

            Thread thread2 = new Thread(() -> {

                while(true){

                    if(serverIsShutDown){
                        break;
                    }
                    String message;
                    Scanner in = new Scanner(System.in);
                    message = in.nextLine();
                    message = message.trim();
                    if(!message.isEmpty()){
                        sendMessage(message);
                    }

                }
            });

            thread1.start();
            thread2.start();
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Server has been shut down");
            socket = null;

        }).start();

    }

    public static void main(String[] args) {

        new Client();

    }

    private void sendMessage(String message) {

        try{
            outputStream.writeUTF(message);

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("ERROR: message not sent");
        }

    }


}
