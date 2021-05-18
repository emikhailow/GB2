package homework8;

import java.awt.*;
import java.awt.image.renderable.RenderableImageProducer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler {

    private MyServer server;
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private String name;
    private volatile boolean authorized;
    private volatile long startTime;

    public String getName() {
        return name;
    }

    public void sendMessage(String message) {

        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ClientHandler(MyServer server, Socket socket) {

        try {
            this.server = server;
            this.socket = socket;
            this.startTime = System.currentTimeMillis();
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            this.authorized = false;

            new Thread(() -> {
                checkAuthentificationTimeout();
            }).start();

            new Thread(() -> {

                try {
                    autentification();
                    readMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }


            }).start();

        } catch (IOException e) {
            
            System.out.println("Problem while creating client");
            e.printStackTrace();
        }
    }

    private void closeConnection() {

        server.unubscribe(this);
        if(!name.isEmpty()){
            server.broadcastMessage(String.format("[%s has left chat]", name));
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readMessages() throws IOException {

        while(true){

            String messageFromClient = inputStream.readUTF();
            System.out.println("from " + name + ": " + messageFromClient);
            if(messageFromClient.equals(ChatConstants.STOP_WORD)){
                return;
            }else if(messageFromClient.startsWith(ChatConstants.SEND_TO_LIST) ||
                    messageFromClient.startsWith(ChatConstants.PERSONAL_MESSAGE)){

                String[] splittedString = messageFromClient.split("\\s+");
                if (splittedString.length < 3) {
                    return;
                }
                String keyword = splittedString[0];
                String nicknamesListString = splittedString[1];
                String messageText = splittedString[2];
                List<String> nicknamesList = keyword == ChatConstants.PERSONAL_MESSAGE
                        ? new ArrayList<>(){{add(nicknamesListString.trim());}}
                        : Arrays.asList(nicknamesListString.split("\\s*,\\s*"));

                server.broadcastMessageToClients(String.format("[%s]: %s", name, messageText), nicknamesList);

            } else if (messageFromClient.startsWith(ChatConstants.CLIENTS_LIST)) {
                server.broadcastClients();
            } else{
                server.broadcastMessage(String.format("[%s]: %s", name, messageFromClient));
            }

        }

    }

    private void autentification() throws IOException {

        String message = inputStream.readUTF();
        if(message.startsWith(ChatConstants.AUTH_COMMAND)){

            String[] parts = message.split("\\s+");
            String nick = server.getAuthService().getNickByLoginAndPass(parts[1], parts[2]);
            if(nick != null){

                if(!server.isNickBusy(nick)){

                    authorized = true;
                    sendMessage(ChatConstants.AUTH_OK + " " + nick);
                    name = nick;
                    server.subscribe(this);
                    sendMessage(String.format("%s %s", ChatConstants.TITLE, name));
                    return;

                }else{
                    sendMessage("Nick is busy");
                }

            } else {
                sendMessage("Invalid login / pass");
            }

        }

    }

    private void checkAuthentificationTimeout(){

        while(true){

            if(this.authorized){
                break;
            }else if ((System.currentTimeMillis() - this.startTime) / 1000 > ChatConstants.TIMEOUT){

                sendMessage("[You have reached timeout, connection will be closed]");
                sendMessage(ChatConstants.CLOSE_CONNECTION);
                break;
            }

        }

    }
}
