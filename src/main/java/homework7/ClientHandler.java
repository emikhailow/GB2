package homework7;

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

            this.inputStream = new DataInputStream(socket.getInputStream());

            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.name = "";
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
        server.broadcastMessage(name + " left chat");
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
                        : new ArrayList<>(Arrays.asList(nicknamesListString.split("\\s*,\\s*")));
                nicknamesList.add(name);
                nicknamesList.stream().distinct().collect(Collectors.toList());

                server.broadcastMessageToClients(String.format("[%s]: %s", name, messageText), nicknamesList);

            } else if (messageFromClient.startsWith(ChatConstants.CLIENTS_LIST)) {
                server.broadcastClients();
            }else{
                server.broadcastMessage("[" + name + "]: " + messageFromClient);
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
                    sendMessage(ChatConstants.AUTH_OK + " " + nick);
                    name = nick;
                    server.subscribe(this);
                    server.broadcastMessage(name + " entered chat");
                    return;
                }else{
                    sendMessage("Nick is busy");
                }

            } else {
                sendMessage("Invalid login / pass");
            }

        }

    }
}
