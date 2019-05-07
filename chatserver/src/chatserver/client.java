package chatserver;

import static chatserver.ChatServer.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author rb101
 */
public class client implements Runnable {

    private Thread T;
    String name;
    String email;
    String password;
    String ipAddress;
    Socket server;
    DataInputStream in;
    DataOutputStream out;
    int ID;
    static int port = 5000;

    client() {

        T = new Thread(this, "online");
        T.start();

    }

    @Override
    public void run() {

        try {

            System.out.println("Waiting for new client");
            server = serverSocket.accept();

            //
            clientOnline.add(new client());
            //

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            in = new DataInputStream(server.getInputStream());

            name = in.readUTF();
            System.out.println(name);

            out = new DataOutputStream(server.getOutputStream());
            //out.writeUTF("Thank you for connecting to meow rebellion");
            out.writeUTF("Welcome");

            System.out.println("Welcome " + name);
            ID = find_ID(name);
            
            if(ID == -1)
            {
                update_user();
            }

            init();

            while (true) {

                String input = in.readUTF();
                System.out.println(name + ">>>" + input);

                add_chat(ID, input, ChatServer.get_timestamp());
                ChatServer.sendChatALL();

            }
        } catch (Exception e) {
            System.out.println(name + " has logged out");
            try {
                ChatServer.removeClient(this);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    private void init() throws IOException {
        int i;
        String output;

        for (i = 1; i <= ChatServer.numChat; i++) {
            output = "chat ";
            output = output.concat(ChatServer.timeStamp[i]);
            output = output.concat(" ");
            output = output.concat(ChatServer.users[ChatServer.clientID[i]]);
            output = output.concat(" ");
            output = output.concat(ChatServer.chat_history[i]);

            System.out.println("output >>>>>");
            System.out.println(output);

            out.writeUTF(output);
        }

        sendOnlineClientList(this);
    }

    int find_ID(String user) {
        int i;
        for (i = 1; i <= ChatServer.numUsers; i++) {
            if (ChatServer.users[i].equals(user) == true) {
                return i;
            }
        }
        return -1;
    }
}
