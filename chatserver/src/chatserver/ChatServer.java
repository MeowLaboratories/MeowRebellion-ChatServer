/** @author: Meow Laboratories */
package chatserver;

import static chatserver.client.port;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    static String[] users = new String[100];
    static String[] emails = new String[100];
    static String[] password = new String[100];
    static int numUsers;
    static int numChat;

    static String[] chat_history = new String[55555];
    static String[] timeStamp = new String[55555];
    static int[] clientID = new int[55555];
    static ArrayList<client> clientOnline = new ArrayList<client>();

    static ServerSocket serverSocket;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://****.com/panda_riboDB";

    //  Database credentials
    static final String USER = "panda_ribo";
    static final String PASS = "*********";

    public static void main(String[] args) {

        update_user();
        update_chat();

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(0);
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        clientOnline.add(new client());

        System.out.println(get_timestamp());
    }

    public static String get_timestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ssa::dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String timeSTAMP = dtf.format(now);

        return timeSTAMP;
    }

    public static void sendChatALL() throws IOException {

        int i;
        String output = "chat ";
        output = output.concat(ChatServer.timeStamp[numChat]);
        output = output.concat(" ");
        output = output.concat(ChatServer.users[ChatServer.clientID[numChat]]);
        output = output.concat(" ");
        output = output.concat(ChatServer.chat_history[numChat]);

        for (i = 0; i < clientOnline.size() - 1; i++) {
            client tmp = clientOnline.get(i);
            tmp.out.writeUTF(output);
        }
    }

    public static void removeClient(client user) throws IOException {
        int onlineSize, i;
        String biday = user.name;
        clientOnline.remove(user);
        String output = "offline";
        output = output.concat(" ");

        for (i = 0; i < clientOnline.size() - 1; i++) {
            client tmp = clientOnline.get(i);
            output = output.concat(biday);
            tmp.out.writeUTF(output);
        }

        onlineSize = clientOnline.size() - 1;
        System.out.println("online: " + onlineSize);
    }

    public static void sendOnlineClientList(client user) throws IOException {
        int i;
        for (i = 0; i < clientOnline.size() - 1; i++) {
            client tmp = clientOnline.get(i);
            String output = "";
            String output2 = "";

            //sends the user's name to all the other users
            output2 = "online";
            output2 = output2.concat(" ");
            output2 = output2.concat(user.name);
            tmp.out.writeUTF(output2);

            //sends the new user all the other users' names
            output = "online";
            output = output.concat(" ");
            if (tmp.name.equals(user.name) == false) {
                output = output.concat(tmp.name);
                user.out.writeUTF(output);
            }

        }
    }

    //user update: writing in string, reading from DB
    public static void update_user() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Updating users...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT client_id, username, email, password FROM client";
            ResultSet rs = stmt.executeQuery(sql);
            int client_id = 0;
            while (rs.next()) {
                client_id = rs.getInt("client_id");
                String usrName = rs.getString("username");
                String e_mail = rs.getString("email");
                String password = rs.getString("password");

                ChatServer.users[client_id] = usrName;
                ChatServer.emails[client_id] = e_mail;
                ChatServer.password[client_id] = password;

            }
            ChatServer.numUsers = client_id;
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
        }
        System.out.println("Updated users puhipuhi");
    }
    //update chat: writing in string, reading from DB

    public static void update_chat() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Updating chat...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT chat_id, client_id, chat, time_stamp from chat_history";
            ResultSet rs = stmt.executeQuery(sql);

            int chat_id = 0;
            while (rs.next()) {
                //Retrieve by column name
                chat_id = rs.getInt("chat_id");
                int client_id = rs.getInt("client_id");
                String chat = rs.getString("chat");
                String time_stamp = rs.getString("time_stamp");

                ChatServer.chat_history[chat_id] = chat;
                ChatServer.timeStamp[chat_id] = time_stamp;
                ChatServer.clientID[chat_id] = client_id;
            }
            ChatServer.numChat = chat_id;
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
        }
        System.out.println("Updated chat puhipuhi");
    }

    //adding users: from chat client, writing to DB
    public static void add_user(String usrName, String e_mail, String pass) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Adding chat to DB...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO client(client_id, username, email, password)" + "VALUES (" + ++ChatServer.numUsers + ", '" + usrName + "', '" + e_mail + "', '" + pass + "')";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
        }
        update_user();
    }

    //adding chat: writing to DB
    public static void add_chat(int clientID, String chat, String timestamp) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Adding chat to DB...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO chat_history(chat_id, client_id, chat, time_stamp)" + "VALUES (" + ++ChatServer.numChat + ", " + clientID + ", '" + chat + "', '" + timestamp + "')";
            //System.out.println(sql);
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
        }
        System.out.println("Added chat meowmeow");
        update_chat();
    }

    //checking if users exist ///changed in the signup
    public boolean check_username(String usrName) {
        for (int i = 0; i < ChatServer.numUsers; i++) {
            if (ChatServer.users[i] == usrName) {
                System.out.println("Sorry, This user name is already taken.");
                return false;
            }
        }
        return true;
    }
}

//end DBMC
