/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author AlbertusK95
 */
public class ServerAdmin extends javax.swing.JFrame {

    private final String path_to_config;
    private String hostName;
    private String listeningPort;
    
    /**
     * Creates new form ServerAdmin
     */
    public ServerAdmin() {
        initComponents();
        path_to_config = "C:\\Users\\AlbertusK95\\Documents\\NetBeansProjects\\SayHI\\users\\admin\\server_config.txt";
        if (isConfigFileEmpty()) {
            hostName = null;
            listeningPort = null;
        } else {
            hostName = GET_SERVERINFO()[0];
            listeningPort = GET_SERVERINFO()[1];
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelServerAdmin = new javax.swing.JLabel();
        labelHostServerAdmin = new javax.swing.JLabel();
        txtHostServerAdmin = new javax.swing.JTextField();
        labelPortServerAdmin = new javax.swing.JLabel();
        txtPortServerAdmin = new javax.swing.JTextField();
        btnRunServerAdmin = new javax.swing.JButton();
        btnUpdateConfig = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelServerAdmin.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        labelServerAdmin.setText("Server Admin");

        labelHostServerAdmin.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        labelHostServerAdmin.setText("Host Name");

        labelPortServerAdmin.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        labelPortServerAdmin.setText("Listening Port");

        btnRunServerAdmin.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        btnRunServerAdmin.setText("Run Server");
        btnRunServerAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunServerAdminActionPerformed(evt);
            }
        });

        btnUpdateConfig.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        btnUpdateConfig.setText("Update Config");
        btnUpdateConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateConfigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(labelServerAdmin))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUpdateConfig)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRunServerAdmin))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelHostServerAdmin)
                                .addGap(37, 37, 37)
                                .addComponent(txtHostServerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelPortServerAdmin)
                                .addGap(18, 18, 18)
                                .addComponent(txtPortServerAdmin)))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(labelServerAdmin)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHostServerAdmin)
                    .addComponent(txtHostServerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPortServerAdmin)
                    .addComponent(txtPortServerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRunServerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunServerAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunServerAdminActionPerformed
        // TODO add your handling code here:
        
        // menjalankan server
        runServer();
    }//GEN-LAST:event_btnRunServerAdminActionPerformed

    private void btnUpdateConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateConfigActionPerformed
        // TODO add your handling code here:
        
        // update nilai host name dan listening port server
        updateConfig();        
    }//GEN-LAST:event_btnUpdateConfigActionPerformed

    /**
     * GETTER
     */
    private String[] GET_SERVERINFO() {
        BufferedReader br = null;
        String[] serverInfo = new String[2];
        String tempInfo;
        int serverInfoIndex = 0;
        
        try {
            br = new BufferedReader(new FileReader(path_to_config));
            while ((tempInfo = br.readLine()) != null) {
                serverInfo[serverInfoIndex] = tempInfo;
                serverInfoIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return serverInfo;
    }
    
    private void updateConfig() {
        try {
            hostName = txtHostServerAdmin.getText();
            listeningPort = txtPortServerAdmin.getText();
        
            File file = new File(path_to_config);

            // jika file tidak ada, maka akan dibuat
            if (!file.exists()) {
                System.out.println("File doesn't exist. Creating a new one");
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(hostName);
            bw.newLine();
            bw.write(listeningPort);
            bw.close();

            System.out.println("Successfully updating config file");

        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    private boolean isConfigFileEmpty() {
        BufferedReader br = null;
        boolean isEmpty = false;
        
        try {
            File file = new File(path_to_config);

            // jika file tidak ada, maka akan dibuat
            if (!file.exists()) {
                System.out.println("File doesn't exist. Creating a new one");
                file.createNewFile();
            }
            
            br = new BufferedReader(new FileReader(path_to_config));     
            if (br.readLine() == null) {
                System.out.println("Server config file is empty");
                isEmpty = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return isEmpty;
    }
    
    private void runServer() {
        
        // cek apakah file kosong atau tidak
        if (hostName == null && listeningPort == null) {
            JOptionPane.showMessageDialog(this, "Config file is empty", "Invalid config status", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Running SayHIServer");
            SayHIServer shs = new SayHIServer();
            shs.start();
        }
      
    }
    
    /**
     * Class SayHIServer yang akan menangani prosedur pembentukan dan komunikasi pada server
     */
    private class SayHIServer {
 
        private int uniqueId;
        private final ArrayList<SayHIServer.ClientThread> clients;
        private final int port;
        private boolean keepGoing;

        public SayHIServer() {
            port = Integer.parseInt(listeningPort);
            clients = new ArrayList<>();
        }

        public void start() {
            keepGoing = true;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (keepGoing) {
                    System.out.println("SayHIServer is waiting for Clients on port " + port + ".");
                    Socket socket = serverSocket.accept();
                    if (!keepGoing) {
                        break;
                    }
                    SayHIServer.ClientThread t = new SayHIServer.ClientThread(socket);
                    clients.add(t);
                    t.start();
                    send("login~" + t.username + "~" + t.username + " sedang login...~Server~\n");
                }
                try {
                    serverSocket.close();
                    for (int i = 0; i < clients.size(); ++i) {
                        SayHIServer.ClientThread tc = clients.get(i);
                        try {
                            tc.sInput.close();
                            tc.sOutput.close();
                            tc.socket.close();
                        } catch (IOException ioE) {
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception closing the server and clients: " + e);
                }
            } catch (IOException e) {
                String msg = "Exception on new ServerSocket: " + e + "\n";
                System.out.println(msg);
            }
        }
 
        private synchronized void send(String message) {
            for (int i = clients.size(); --i >= 0;) {
                SayHIServer.ClientThread ct = clients.get(i);
                if (!ct.writeMsg(message)) {
                    clients.remove(i);
                    System.out.println("Disconnected Client " + ct.username + " removed from list.");
                }
            }
        }
 
        private String getClients() {
            String s = "";
            for (ClientThread clientThread : clients) {
                s += clientThread.username + ":";
            }
            s += "---";
            System.out.println(s);
            return s;
        }
 
        private synchronized void remove(int id) {
            for (int i = 0; i < clients.size(); ++i) {
                SayHIServer.ClientThread ct = clients.get(i);
                if (ct.id == id) {
                    clients.remove(i);
                    return;
                }
            }
        }

        private class ClientThread extends Thread {

            private Socket socket;
            private ObjectInputStream sInput;
            private ObjectOutputStream sOutput;
            private int id;
            private String username;

            public ClientThread(Socket socket) {
                id = ++uniqueId;
                this.socket = socket;
                System.out.println("Menciptakan Object Input/Output Streams");
                try {
                    sOutput = new ObjectOutputStream(socket.getOutputStream());
                    sInput = new ObjectInputStream(socket.getInputStream());
                    String message = (String) sInput.readObject();
                    username = message.split("~")[1];
                    System.out.println(username + " masuk.");
                } catch (IOException e) {
                    System.out.println("Exception creating new Input/output Streams: " + e);
                } catch (ClassNotFoundException e) {
                }
            }

            @Override
            public void run() {
                boolean keepGoing = true;
                while (keepGoing) {

                    String message;
                    try {
                        message = sInput.readObject().toString();
                    } catch (IOException e) {
                        System.out.println(username + " Exception reading Streams: " + e);
                        break;
                    } catch (ClassNotFoundException e2) {
                        break;
                    }

                    String type = message.split("~")[0];
                    String pengirim = message.split("~")[1];
                    String text = message.split("~")[2];
                    String kepada = message.split("~")[3];
                    String response;

                    switch (type) {
                        case "postText":
                            response = "recieveText~" + pengirim + "~" + text + "~" + kepada + "~\n";
                            send(response);
                            break;
                        case "postPrivateText":
                            response = "recievePrivateText~" + pengirim + "~" + text + "~" + kepada + "~\n";
                            send(response);
                            break;
                        case "login":
                            response = "login~" + pengirim + "~" + text + "~" + kepada + "~\n";
                            send(response);
                            break;
                        case "logout":
                            response = "logout~" + pengirim + "~" + text + "~" + kepada + "~\n";
                            send(response);
                            break;
                        case "updateChatHistTable":
                            response = "updateChatHistTable~" + pengirim + "~" + "updateChatHistTable" + "~" + kepada + "~\n";
                            send(response);
                            break;
                        case "list":
                            response = "list~server~" + getClients() + "~ ~ ~ ~ ~\n";
                            send(response);
                            break;
                    }
                }

                remove(id);
                close();
            }

            private void close() {
                try {
                    if (sOutput != null) {
                        sOutput.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (sInput != null) {
                        sInput.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (Exception e) {
                }
            }

            private boolean writeMsg(String msg) {
                if (!socket.isConnected()) {
                    close();
                    return false;
                }
                try {
                    sOutput.writeObject(msg);
                } catch (IOException e) {
                    System.out.println("Error sending message to " + username);
                    System.out.println(e.toString());
                }
                return true;
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRunServerAdmin;
    private javax.swing.JButton btnUpdateConfig;
    private javax.swing.JLabel labelHostServerAdmin;
    private javax.swing.JLabel labelPortServerAdmin;
    private javax.swing.JLabel labelServerAdmin;
    private javax.swing.JTextField txtHostServerAdmin;
    private javax.swing.JTextField txtPortServerAdmin;
    // End of variables declaration//GEN-END:variables
}
