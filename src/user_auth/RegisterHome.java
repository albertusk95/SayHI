/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author AlbertusK95
 */
public class RegisterHome extends javax.swing.JFrame {

    private final String path_to_users = "C:\\Users\\AlbertusK95\\Documents\\NetBeansProjects\\SayHI\\users\\subscriber\\";
    private final String path_to_subsList = "C:\\Users\\AlbertusK95\\Documents\\NetBeansProjects\\SayHI\\users\\subscriber\\subs_list.txt";
    private String registerFirstName;
    private String registerLastName;
    private String registerUsername;
    private String registerPassword;
    
    /**
     * Creates new form RegisterHome
     */
    public RegisterHome() {
        initComponents();  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelSayHI = new javax.swing.JLabel();
        labelRegistration = new javax.swing.JLabel();
        labelUsernameReg = new javax.swing.JLabel();
        labelPasswordReg = new javax.swing.JLabel();
        labelFirstNameReg = new javax.swing.JLabel();
        labelLastNameReg = new javax.swing.JLabel();
        txtFirstNameReg = new javax.swing.JTextField();
        txtLastNameReg = new javax.swing.JTextField();
        txtUsernameReg = new javax.swing.JTextField();
        txtPasswordReg = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelSayHI.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        labelSayHI.setText("SayHI");

        labelRegistration.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        labelRegistration.setText("Registration");

        labelUsernameReg.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        labelUsernameReg.setText("Username");

        labelPasswordReg.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        labelPasswordReg.setText("Password");

        labelFirstNameReg.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        labelFirstNameReg.setText("FirstName");

        labelLastNameReg.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        labelLastNameReg.setText("LastName");

        btnRegister.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFirstNameReg)
                    .addComponent(labelLastNameReg)
                    .addComponent(labelUsernameReg)
                    .addComponent(labelPasswordReg))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFirstNameReg)
                    .addComponent(txtLastNameReg)
                    .addComponent(txtUsernameReg)
                    .addComponent(txtPasswordReg, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelSayHI)
                        .addGap(146, 146, 146))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelRegistration)
                        .addGap(155, 155, 155))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRegister)
                        .addGap(149, 149, 149))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(labelSayHI)
                .addGap(18, 18, 18)
                .addComponent(labelRegistration)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFirstNameReg)
                    .addComponent(txtFirstNameReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLastNameReg)
                    .addComponent(txtLastNameReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsernameReg)
                    .addComponent(txtUsernameReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPasswordReg)
                    .addComponent(txtPasswordReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public boolean isFilled() {
        if (registerFirstName.equals("") || registerLastName.equals("") || registerUsername.equals("") || registerPassword.equals("")) {
            return false;
        } else {
            return true;
        }
    }
    
    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        // TODO add your handling code here:
        
        int uniqueUsername = 0;
        
        registerFirstName = txtFirstNameReg.getText();
        registerLastName = txtLastNameReg.getText();
        registerUsername = txtUsernameReg.getText();
        registerPassword = txtPasswordReg.getText();
        
        if (!isFilled()) {
            JOptionPane.showMessageDialog(this, "You must fill all the fields", "Invalid registration", JOptionPane.ERROR_MESSAGE);
        } else {
            
            /**
             * Menjalankan prosedur penginputan data milik subscriber baru, yaitu:
             * - mengecek apakah username sudah pernah ada di dalam database
             * - insert data baru ke dalam database
             */
            
            try {
                RegistrationProtocol rp = new RegistrationProtocol();
                uniqueUsername = rp.isUniqueUsername();
                if (uniqueUsername == 1) {
                    rp.insertData();
                } else {
                    JOptionPane.showMessageDialog(this, "Username has already been used", "Invalid username", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }//GEN-LAST:event_btnRegisterActionPerformed

    /**
     * Class RegistrationProtocol
     */
    private class RegistrationProtocol {
        
        public boolean isSubsFileEmpty() {
            BufferedReader br = null;
            boolean isEmpty = false;

            try {
                File file = new File(path_to_subsList);

                // jika file tidak ada, maka akan dibuat
                if (!file.exists()) {
                    System.out.println("File doesn't exist. Creating a new one");
                    file.createNewFile();
                }

                br = new BufferedReader(new FileReader(path_to_subsList));     
                if (br.readLine() == null) {
                    System.out.println("File of subscriber list is empty");
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
        
        public int isUniqueUsername() throws IOException {
            BufferedReader br = null;
            String[] userInfo = new String[4];
            String sCurrentLine;
            int uniqueStatus = 1, userInfoCol = 0;
            
            try {
                    br = new BufferedReader(new FileReader(path_to_subsList));
                    while ((sCurrentLine = br.readLine()) != null) {
                        userInfoCol = 0;
                        for (String val: sCurrentLine.split(" ")){
                            userInfo[userInfoCol] = val;
                            userInfoCol++;
                        }
                        // cek kesamaan username
                        if (userInfo[2].equals(registerUsername)) {
                            uniqueStatus = 0;
                            break;
                        }
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
            
            return uniqueStatus;
        }
        
        public void insertData() {
            
            try {
                File file = new File(path_to_subsList);

                // jika file tidak ada, maka akan dibuat
                if (!file.exists()) {
                    System.out.println("File doesn't exist. Creating a new one");
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                if (isSubsFileEmpty()) {
                    bw.write(registerFirstName);
                    bw.write(" ");
                    bw.write(registerLastName);
                    bw.write(" ");
                    bw.write(registerUsername);
                    bw.write(" ");
                    bw.write(registerPassword);
                } else {
                    bw.newLine();
                    bw.write(registerFirstName);
                    bw.write(" ");
                    bw.write(registerLastName);
                    bw.write(" ");
                    bw.write(registerUsername);
                    bw.write(" ");
                    bw.write(registerPassword);
                }
                bw.close();

                // membuat direktori baru untuk user yang baru terdaftar
                File regUser_directory = new File(path_to_users + registerUsername);
                if (!regUser_directory.exists()) {
                    if (regUser_directory.mkdir()) {
                        
                        System.out.println("User's data area is created");
                        
                        try {
                            // membuat file friends list 
                            File fileFriendsList = new File(path_to_users + registerUsername + "\\friendlist.txt");
                            if (fileFriendsList.createNewFile()){
                                System.out.println("Friends list file is created");
                            }else{
                                System.out.println("Friends list file already exists");
                            }
                
                            // membuat folder chat history
                            File chathist_directory = new File(path_to_users + registerUsername + "\\chathistory");
                            if (chathist_directory.mkdir()) {
                                System.out.println("User's chat history area is created");
                            } else {
                                // menampilkan pesan error dalam proses registrasi user baru
                                System.out.println("Failed to create user's chat history area");
                            }
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                       
                    } else {
                        // menampilkan pesan error dalam proses registrasi user baru
                        System.out.println("Failed to create user's data area");
                    }
                }
                
            } catch (IOException e) {
                e.printStackTrace();
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
            java.util.logging.Logger.getLogger(RegisterHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel labelFirstNameReg;
    private javax.swing.JLabel labelLastNameReg;
    private javax.swing.JLabel labelPasswordReg;
    private javax.swing.JLabel labelRegistration;
    private javax.swing.JLabel labelSayHI;
    private javax.swing.JLabel labelUsernameReg;
    private javax.swing.JTextField txtFirstNameReg;
    private javax.swing.JTextField txtLastNameReg;
    private javax.swing.JTextField txtPasswordReg;
    private javax.swing.JTextField txtUsernameReg;
    // End of variables declaration//GEN-END:variables
}
