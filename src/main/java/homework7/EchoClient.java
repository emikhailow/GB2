package homework7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoClient extends JFrame {

    private static final String STOP_WORD = "/end";

    private Socket socket;

    private JTextArea chatArea;
    private JTextField inputField;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public EchoClient(){
        try{
            openConnection();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        initGUI();
    }

    private void openConnection() throws IOException {

        socket = new Socket(ChatConstants.HOST, ChatConstants.PORT);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try{
                //auth
                while(true){
                    String strfromServer = inputStream.readUTF();
                    if(strfromServer.equals(ChatConstants.AUTH_OK)){
                        break;
                    }
                    chatArea.append(strfromServer);
                    chatArea.append("\n");
                }
                //read
                while(true){
                    String strFromServer = inputStream.readUTF();
                    if(strFromServer.equals(ChatConstants.STOP_WORD)){
                        break;
                    }else if(strFromServer.startsWith(ChatConstants.CLIENTS_LIST)){
                        chatArea.append("Сейчас онлайн: " + strFromServer);
                    }else{
                        chatArea.append(strFromServer);
                    }
                    chatArea.append("\n");
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }

        }).start();

    }

    private void closeConnection() {

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

    public void initGUI(){

        setBounds(600, 300, 500, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setBounds(100, 100, 150, 30);
        panel.add(inputField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        panel.add(sendButton, BorderLayout.EAST);

        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());

        inputField.addActionListener(e -> sendMessage());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try{
                    outputStream.writeUTF(ChatConstants.STOP_WORD);
                    closeConnection();

                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);

    }

    private void sendMessage() {

        if(!inputField.getText().trim().isEmpty()){
            try {
                outputStream.writeUTF(inputField.getText());
                inputField.setText("");
                inputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Send error occured");
            }

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(EchoClient::new);

    }

}
