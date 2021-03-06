/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author rb101
 */
public class chatWindow extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form chatWindow
     */
    static String userName;
    Thread T;

    static Socket socket;
    static final String serverName = "103.84.159.230";
    static final int port = 5000;
    static OutputStream outToServer;
    public static DataOutputStream out;
    static InputStream inFromServer;
    public static DataInputStream in;
    static String user;
    static ArrayList<String> clientOnline = new ArrayList<String>();

    public chatWindow(String user) {
        initComponents();

        this.user = user;
        T = new Thread(this, user);
        T.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sendMessageBox = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        msgSaveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatBox = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        onlineList = new javax.swing.JTextArea();
        notificationBar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Meow Rebellion");

        sendMessageBox.setColumns(20);
        sendMessageBox.setLineWrap(true);
        sendMessageBox.setRows(5);
        jScrollPane2.setViewportView(sendMessageBox);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        msgSaveButton.setText("Save MSG");
        msgSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msgSaveButtonActionPerformed(evt);
            }
        });

        chatBox.setEditable(false);
        chatBox.setColumns(20);
        chatBox.setRows(5);
        jScrollPane1.setViewportView(chatBox);

        onlineList.setEditable(false);
        onlineList.setColumns(20);
        onlineList.setRows(5);
        jScrollPane3.setViewportView(onlineList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(notificationBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(msgSaveButton))
                        .addGap(0, 8, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notificationBar, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(msgSaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        String chat;
        try {
            chat = sendMessageBox.getText();

            if (chat.length() == 0) {

            } else {
                out.writeUTF(chat);
                sendMessageBox.setText("");
                notificationBar.setText("Message SENT @ " + get_timestamp());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void msgSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msgSaveButtonActionPerformed
        // TODO add your handling code here:

        String chat = "";
        try {
            chat = chatBox.getText();

            Files.write(Paths.get("saveChat.txt"), chat.getBytes(), StandardOpenOption.WRITE);
            
            notificationBar.setText("Message Saved");
            //notificationBar.setForeground(Color.red);

        } catch (NoSuchFileException ex) {
            try {
                Files.write(Paths.get("saveChat.txt"), chat.getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (IOException ex1) {
                Logger.getLogger(chatWindow.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_msgSaveButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea chatBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton msgSaveButton;
    private javax.swing.JLabel notificationBar;
    private static javax.swing.JTextArea onlineList;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea sendMessageBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        System.out.println("Chat Client Initiating");
        chatBox.setLineWrap(true);
        chatBox.setWrapStyleWord(true);
        //chatBox.setText("Chat Client Initiating");

        onlineList.setLineWrap(true);
        onlineList.setWrapStyleWord(true);
        onlineList.setText("SERVER\n");

        //DefaultCaret caret = (DefaultCaret) chatBox.getCaret();
       // caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            socket = new Socket(serverName, port);

            System.out.println("Just connected to " + socket.getRemoteSocketAddress());
            outToServer = socket.getOutputStream();
            out = new DataOutputStream(outToServer);

            //out.writeUTF("Hello from " + client.getLocalSocketAddress());
            out.writeUTF(user);

            inFromServer = socket.getInputStream();
            in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            chatBox.append("Client Initiated\n");
            //onlineList.append(user+"\n");
            //client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (in.available() > 0) {
                    String input = in.readUTF();
                    if (input.charAt(0) == 'c' && input.charAt(1) == 'h' && input.charAt(2) == 'a' && input.charAt(3) == 't') {
                        String[] chat = input.split("\\s", 4); //splits input in 4 segements {command,timestamp, usr , chat}
                        System.out.println(chat[0]);
                        System.out.println(chat[1]);
                        System.out.println(chat[2]);
                        System.out.println(chat[3]);

                        chatBox.append(chat[2] + " >>\n"); //username
                        chatBox.append(chat[3] + " \n"); //chat
                        chatBox.append(chat[1] + " \n\n"); //time
                        chatBox.setCaretPosition(chatBox.getText().length());
                        
                    }//chatBox.append(input);
                    else if (input.charAt(0) == 'o' && input.charAt(1) == 'n' && input.charAt(2) == 'l' && input.charAt(3) == 'i' && input.charAt(4) == 'n' && input.charAt(5) == 'e') {
                        String[] chat = input.split("\\s", 2); //splits input in 4 segements {command,timestamp, usr , chat}
                        System.out.println(chat[0]);
                        System.out.println(chat[1]);

                        clientOnline.add(chat[1]);

                        onlineList.append(chat[1] + "\n");
                        
                    } else if (input.charAt(0) == 'o' && input.charAt(1) == 'f' && input.charAt(2) == 'f' && input.charAt(3) == 'l' && input.charAt(4) == 'i' && input.charAt(5) == 'n' && input.charAt(6) == 'e') {
                        String[] chat = input.split("\\s", 2); //splits input in 4 segements {command,timestamp, usr , chat}
                        System.out.println(chat[0]);
                        System.out.println(chat[1]);

                        clientOnline.remove(chat[1]);

                        refreshList();
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    private void refreshList() {
        onlineList.setText("SERVER\n");
        int i;
        int l = clientOnline.size();
        for (i = 0; i < l; i++) {
            String tmp = clientOnline.get(i);
            onlineList.append(tmp + "\n");
        }
    }
    public static String get_timestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ssa::dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String timeSTAMP = dtf.format(now);

        return timeSTAMP;
    }
}
