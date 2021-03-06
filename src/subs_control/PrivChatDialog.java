/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subs_control;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AlbertusK95
 */
public class PrivChatDialog extends javax.swing.JDialog {

    private final String path_to_users = "C:\\Users\\AlbertusK95\\Documents\\NetBeansProjects\\SayHI\\users\\subscriber\\";
    
    private final String pengirim;
    private final String penerima;
 
    private WindowAdapter windowAdapter;
    
    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
 
    /**
     * Creates new form PrivChatDialog
     */
    public PrivChatDialog(SubsHome parent, Socket socket, ObjectInputStream input, ObjectOutputStream output, String pengirim, String penerima) {
        super(parent, false);
 
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.pengirim = pengirim;
        this.penerima = penerima;
        setLocationRelativeTo(parent);
        initComponents();
        windowAdapter = new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
                    String message = "updateChatHistTable~" + pengirim + "~" + "updateChatHistTable" + "~" + penerima + "~\n";

                    // mengirim pesan ke server agar SubsHome dapat mengupdate chat history table
                    output.writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(PrivChatDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                e.getWindow().dispose();
            }
        };
        
        addWindowListener(windowAdapter);
        
    }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtareaChatHistory = new javax.swing.JTextArea();
        txtChatPost = new javax.swing.JTextField();
        btnSendPrivChat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtareaChatHistory.setColumns(20);
        txtareaChatHistory.setRows(5);
        jScrollPane1.setViewportView(txtareaChatHistory);

        txtChatPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChatPostActionPerformed(evt);
            }
        });

        btnSendPrivChat.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        btnSendPrivChat.setText("Send");
        btnSendPrivChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendPrivChatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtChatPost, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSendPrivChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChatPost, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSendPrivChat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void display(String res, int needToGetHist) {
        
        // mengambil chat history dari file jika file tersebut ada
        if (needToGetHist == 1) {
            
            txtareaChatHistory.setText("");
            
            File fileChatHist = new File(path_to_users + pengirim + "\\chathistory\\" + penerima + ".txt");
            if (fileChatHist.exists()) {
                String[] chathist;

                chathist = GET_ChatFromFile();
                
                // menambahkan chathist ke dalam txtareaChatHistory
                for (String ch : chathist) {
                    txtareaChatHistory.setText(txtareaChatHistory.getText() + ch + "\n");
                }
            }
        }
        
        txtareaChatHistory.setText(txtareaChatHistory.getText() + res + "\n");
    }
    
    /**
     * Prosedur mendapatkan banyak global friends dari file subslist
     */
    private int GET_num() {
        BufferedReader br = null;
        int num = 0;
        
        try {
            br = new BufferedReader(new FileReader(path_to_users + pengirim + "\\chathistory\\" + penerima + ".txt"));
            
            while (br.readLine() != null) {
                num++;
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
        
        System.out.println("Num value: " + num);
        
        return num;
    }
    
    /**
     * Prosedur mengambil chat yang telah tersimpan dalam file sebelumnya untuk
     * ditampilkan dalam txtarea sebagai history
     */
    private String[] GET_ChatFromFile() {
        BufferedReader br = null;
        String tempInfo;
        String[] chathist = new String[GET_num()];
        int indexChatHistory = 0;
        
        try {
            br = new BufferedReader(new FileReader(path_to_users + pengirim + "\\chathistory\\" + penerima + ".txt"));
            
            while ((tempInfo = br.readLine()) != null) {
                chathist[indexChatHistory] = tempInfo;
                indexChatHistory++;
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
        
        return chathist;
    }
    
    /**
     * Prosedur mengecek apakah file friendlist.txt kosong atau tidak
     */
    private boolean isSubsFileEmpty(String userSender, String userReceiver) {
        BufferedReader br = null;
        boolean isEmpty = false;

        try {
            br = new BufferedReader(new FileReader(path_to_users + userSender + "\\chathistory\\" + userReceiver + ".txt"));     
            if (br.readLine() == null) {
                System.out.println("File " + userReceiver + ".txt for " + userSender + " is empty");
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
    
    private void sendToChatFile(String msg, String userSender, String userReceiver) {
        
        try {
            File fileChat = new File(path_to_users + userSender + "\\chathistory\\" + userReceiver + ".txt");
            
            if (!fileChat.exists()) {
                if (fileChat.createNewFile()){
                    System.out.println("File " + userReceiver + ".txt for " + userSender + " is created");
                } else{
                    System.out.println("File " + userReceiver + ".txt for " + userSender + " already exists");
                }
            }
          
            FileWriter fw = new FileWriter(fileChat.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (isSubsFileEmpty(userSender, userReceiver)) {
                bw.write(msg);
            } else {
                bw.newLine();
                bw.write(msg);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    private void btnSendPrivChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPrivChatActionPerformed
        
        String msg = pengirim + ": " + txtChatPost.getText();
        
        try {
            // pesan yang akan dikirim ke server
            String message = "postPrivateText~" + pengirim + "~" + txtChatPost.getText() + "~" + penerima + "~\n";
            
            // mengirim pesan yang ditulis ke text area
            display(msg + "\n", 0);
            output.writeObject(message);
            
            // mengirimkan pesan yang ditulis ke dalam file chat history pengirim
            sendToChatFile(msg, pengirim, penerima);
        
            // mengirimkan pesan yang ditulis ke dalam file chat history penerima
            sendToChatFile(msg, penerima, pengirim);
        
            txtChatPost.setText("");
        } catch (IOException ex) {
            Logger.getLogger(SubsHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSendPrivChatActionPerformed

    private void txtChatPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChatPostActionPerformed
        // TODO add your handling code here:
        btnSendPrivChatActionPerformed(evt);
    }//GEN-LAST:event_txtChatPostActionPerformed

    /**
     * @param args the command line arguments
     */
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendPrivChat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtChatPost;
    private javax.swing.JTextArea txtareaChatHistory;
    // End of variables declaration//GEN-END:variables
}
