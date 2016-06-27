/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subs_control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlbertusK95
 */
public class SubsHome extends javax.swing.JFrame {

    private final String path_to_users = "C:\\Users\\AlbertusK95\\Documents\\NetBeansProjects\\SayHI\\users\\subscriber\\";
    private final String path_to_subslist = "C:\\Users\\AlbertusK95\\Documents\\NetBeansProjects\\SayHI\\users\\subscriber\\subs_list.txt";
    private final String path_to_chathist = "C:\\Users\\AlbertusK95\\Documents\\NetBeansProjects\\SayHI\\users\\subscriber\\";
    private final String hostname, username, fullname;
    private final int listeningPort;

    private List<String> clients;
    private List<PrivChatDialog> dialogs;
    
    private int numFriendsList;
   
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket socket;
    
    /**
     * Creates new form SubsHome
     * @param username
     */
    public SubsHome(String hostname, int listeningPort, String username, String fullname) {
        boolean startVal;
        this.hostname = hostname;
        this.listeningPort = listeningPort;
        this.username = username;
        this.fullname = fullname;
        initComponents();
        initFriendsList();
        initChatHistory();
        initGlobalFriends();
        //startVal = start();
    }
    
    /*
    private boolean start() {
        try {
            socket = new Socket(hostname, listeningPort);
        } catch (Exception ec) {
            System.out.println("Error connectiong to server:" + ec);
            return false;
        }
 
        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        System.out.println(msg);
 
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            System.out.println("Exception creating new Input/output Streams: " + eIO);
            return false;
        }
 
        new SubsHome.ListenFromServer().start();
 
        try {
            output.writeObject("login~" + username + "~" + username + " sedang login...~server~\n");
            output.writeObject("list~" + username + "~" + username + " sedang login...~server~\n");
 
        } catch (IOException eIO) {
            System.out.println("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
 
        return true;
    }
    
    private void disconnect() {
        try {
            // TODO add your handling code here:
            output.writeObject("logout~" + username + "~" + username + " sudah logout...~Server~\n");
        } catch (IOException ex) {
            //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        try {
            if (input != null) {
                input.close();
            }
        } catch (Exception e) {}
        
        try {
            if (output != null) {
                output.close();
            }
        } catch (Exception e) {}
        
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {}
    }
    */
    
    /**
     * Initiate friends list, chat's history, and global friends
     */
    private void initFriendsList() {
        BufferedReader br = null;
        String[] FriendsList;
        String tempInfo;
        int indexFriendsList = 0;
        
        try {
            numFriendsList = GET_num(username);
            
            //System.out.println("Total friends: " + numFriendsList);
            
            FriendsList = new String[numFriendsList];
             
            br = new BufferedReader(new FileReader(path_to_users + username + "\\friendlist.txt"));
            while ((tempInfo = br.readLine()) != null) {
                FriendsList[indexFriendsList] = tempInfo;
                indexFriendsList++;
            }
            
            Object[][] data = new Object[numFriendsList][2];
            String[] header = {"ID", "Full Name"};
            int idxSplitted;
            
            // mengisi objek data dengan semua user yang terdaftar dalam database
            for (int i = 0; i < numFriendsList; i++) {
                idxSplitted = 0;
                data[i][1] = "";
                for (String splittedVal: FriendsList[i].split(" ")){
                    if (idxSplitted == 0) {
                        data[i][0] = splittedVal;
                    } else {
                        data[i][1] = data[i][1] + splittedVal;
                        if (idxSplitted == 1) {
                            data[i][1] = data[i][1] + " ";
                        }
                    } 
                    idxSplitted++;
                }
            }
        
            tabelFriendsList.setModel(new DefaultTableModel(data, header) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    private void initChatHistory() {
        String path_to_user_chathist = path_to_chathist + username + "\\chathistory\\";
        String[] ChatFileName;
        String[] FriendsList;
        int idx = 0;
        
        // mendapatkan semua friend list 
        FriendsList = GET_ArrayOfFriends(username);
        
        // mendapatkan nama semua file dalam folder chat history
        ChatFileName = GET_ChatFileName();

        Object[][] data = new Object[ChatFileName.length][3];
        String[] header = {"ID", "Full Name", "Chat"};
        
        // menampilkan isi file chat history ke tabel 
        for (String cfn : ChatFileName) {
            System.out.println("chat name: " + cfn);
            
            for (String fl : FriendsList) {
                if ((fl.split(" ")[0]+".txt").equals(cfn)) {
                    data[idx][0] = fl.split(" ")[0];
                    data[idx][1] = fl.split(" ")[1] + " " + fl.split(" ")[2];
                    data[idx][2] = GET_LastLineOfFile(cfn);
                    idx++;
                    break;
                }
            }
        }
        
        // update tabel chat history
        tabelChatHistory.setModel(new DefaultTableModel(data, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
    
    private void initGlobalFriends() {
        BufferedReader br = null;
        String[] GlobFriends;
        String tempInfo;
        int indexGlobFriends = 0;
        int numGlobFriends;
        
        try {    
            numGlobFriends = GET_num("globfriends");
            
            Object[][] tableData = GET_TableData();
            
            GlobFriends = new String[numGlobFriends];
             
            br = new BufferedReader(new FileReader(path_to_subslist));
            while ((tempInfo = br.readLine()) != null) {
                if (!tempInfo.split(" ")[2].equals(username)) {
                    if (numFriendsList > 0) {
                        for (int i = 0; i < numFriendsList; i++) {
                            String tempFullName = tempInfo.split(" ")[0] + " " + tempInfo.split(" ")[1]; 
                            if (tempFullName.equals(tableData[i][1])) {
                                break;
                            } else {
                                if (i == numFriendsList - 1) {
                                    GlobFriends[indexGlobFriends] = tempInfo;
                                    indexGlobFriends++;
                                }
                            }
                        }
                    } else {
                        GlobFriends[indexGlobFriends] = tempInfo;
                        indexGlobFriends++;
                    }
                }
            }
            
            Object[][] data = new Object[indexGlobFriends][2];
            String[] header = {"ID", "Full Name"};
            int idxSplitted;
            
            // mengisi objek data dengan semua user yang terdaftar dalam database
            for (int i = 0; i < indexGlobFriends; i++) {
                idxSplitted = 0;
                data[i][1] = "";
                for (String splittedVal: GlobFriends[i].split(" ")){
                    if (idxSplitted < 2) {
                        data[i][1] = data[i][1] + splittedVal;
                        if (idxSplitted == 0) {
                            data[i][1] = data[i][1] + " ";
                        }
                    } else if (idxSplitted == 2) {
                        data[i][0] = splittedVal;
                    }
                    idxSplitted++;
                }
            }
        
            tabelGlobalFriends.setModel(new DefaultTableModel(data, header) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelFriendsList = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelChatHistory = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelGlobalFriends = new javax.swing.JTable();
        panelChatControl = new javax.swing.JPanel();
        btnDeleteChat = new javax.swing.JButton();
        panelGlobalAddControl = new javax.swing.JPanel();
        btnAddFriend = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N

        tabelFriendsList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Full Name"
            }
        ));
        jScrollPane1.setViewportView(tabelFriendsList);

        tabelChatHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Full Name", "Chat"
            }
        ));
        jScrollPane2.setViewportView(tabelChatHistory);

        tabelGlobalFriends.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Full Name"
            }
        ));
        jScrollPane3.setViewportView(tabelGlobalFriends);

        panelChatControl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chat's history control", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 1, 14))); // NOI18N

        btnDeleteChat.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        btnDeleteChat.setText("Delete");
        btnDeleteChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteChatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChatControlLayout = new javax.swing.GroupLayout(panelChatControl);
        panelChatControl.setLayout(panelChatControlLayout);
        panelChatControlLayout.setHorizontalGroup(
            panelChatControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChatControlLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(btnDeleteChat)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        panelChatControlLayout.setVerticalGroup(
            panelChatControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChatControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDeleteChat, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGlobalAddControl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Global friends control", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 1, 14))); // NOI18N

        btnAddFriend.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        btnAddFriend.setText("Add");
        btnAddFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGlobalAddControlLayout = new javax.swing.GroupLayout(panelGlobalAddControl);
        panelGlobalAddControl.setLayout(panelGlobalAddControlLayout);
        panelGlobalAddControlLayout.setHorizontalGroup(
            panelGlobalAddControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlobalAddControlLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(btnAddFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGlobalAddControlLayout.setVerticalGroup(
            panelGlobalAddControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlobalAddControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddFriend, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelChatControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelGlobalAddControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(panelChatControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(panelGlobalAddControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Prosedur mendapatkan teks pada baris terakhir suatu file 
     */
    private String GET_LastLineOfFile(String whatVal) {
        BufferedReader br = null;
        String lastLine = "";
        String sCurrentLine;
        
        try {
            br = new BufferedReader(new FileReader(path_to_users + username + "\\chathistory\\" + whatVal));
            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;
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
        
        return lastLine;
    }
    
    /**
     * Prosedur mendapatkan array of nama file chat
     */
    private String[] GET_ChatFileName() {
        
        Collection<String> files = new ArrayList<String>();
        File dir = new File(path_to_users + username + "\\chathistory");
        
        if(dir.isDirectory()){
            File[] listFiles = dir.listFiles();

            for(File file : listFiles){
                if(file.isFile()) {
                    files.add(file.getName());
                }
            }
        }

        return files.toArray(new String[]{});
    }
    
    /**
     * Prosedur mendapatkan banyak global friends dari file subslist
     */
    private int GET_num(String whatNum) {
        BufferedReader br = null;
        int num = 0;
        
        try {
            if (whatNum.equals("globfriends")) {
                br = new BufferedReader(new FileReader(path_to_subslist));
            } else {
                br = new BufferedReader(new FileReader(path_to_users + whatNum + "\\friendlist.txt"));
            }
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
        
        if (!whatNum.equals("globfriends")) {
            numFriendsList = num;
        }
        
        return num;
    }
   
    /**
     * Prosedur mendapatkan array of global friends dan friends list
     */
    private String[] GET_ArrayOfFriends(String whatFriend) {
        BufferedReader br = null;
        String tempInfo;
        String[] arrFriends = new String[GET_num(whatFriend)];
        int indexFriends = 0;
        
        try {
            if (whatFriend.equals("globfriends")) {
                br = new BufferedReader(new FileReader(path_to_subslist));
            } else {
                br = new BufferedReader(new FileReader(path_to_users + username + "\\friendlist.txt"));
            }
            
            while ((tempInfo = br.readLine()) != null) {
                arrFriends[indexFriends] = tempInfo;
                indexFriends++;
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
        
        return arrFriends;
    }
    
    /**
     * Prosedur mengkonversi data pada JTabel menjadi array
     */
    private Object[][] GET_TableData () {
        DefaultTableModel dtm = (DefaultTableModel) tabelFriendsList.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0 ; i < nRow ; i++) {
            for (int j = 0 ; j < nCol ; j++) {
                tableData[i][j] = dtm.getValueAt(i, j);
            }
        }
        return tableData;
    }
    
    /**
     * Prosedur mendapatkan isi dari file FriendList.txt
     */
    /*
    private String[] GET_FriendList() {
        BufferedReader br0 = null;
        BufferedReader br1 = null;
        int indexFriendsList = 0;
        String[] FriendsList = {};
        String tempInfo;
        
        // mengambil isi friends list dari database
        try {
            numFriendsList = 0;
            
            br0 = new BufferedReader(new FileReader(path_to_users + username + "\\friendlist.txt"));
            while (br0.readLine() != null) {
                numFriendsList++;
            }
            
            System.out.println("Friends list counted: " + numFriendsList);

            FriendsList = new String[numFriendsList];
            
            br1 = new BufferedReader(new FileReader(path_to_users + username + "\\friendlist.txt"));
            while ((tempInfo = br1.readLine()) != null) {
                FriendsList[indexFriendsList] = tempInfo;
                indexFriendsList++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br0.close();
                br1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return FriendsList;
    }
    */
    
    /**
     * Prosedur mengecek apakah file friendlist.txt kosong atau tidak
     */
    private boolean isSubsFileEmpty(String userID) {
        BufferedReader br = null;
        boolean isEmpty = false;

        try {
            br = new BufferedReader(new FileReader(path_to_users + userID + "\\friendlist.txt"));     
            if (br.readLine() == null) {
                System.out.println("File friendlist.txt is empty");
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

    /**
     * Prosedur menambahkan seorang global friend ke dalam file friendlist.txt
     * @param friendID username dari user yang ingin ditambahkan
     * @param friendFullName fullname dari user yang ingin ditambahkan
     * @param pov 1 untuk menambah user ke dalam friend list. 2 untuk menambah diri sendiri ke friend list user yang baru ditambahkan ke dalam friend list
     */
    private void addFriendToFile(String friendID, String friendFullName, int pov) {
            String friendFirstName = friendFullName.split(" ")[0];
            String friendLastName = friendFullName.split(" ")[1];
            File file;
            
            try {
                if (pov == 1) {
                    file = new File(path_to_users + username + "\\friendlist.txt");
                } else {
                    file = new File(path_to_users + friendID + "\\friendlist.txt");
                }
                
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                if (isSubsFileEmpty(friendID)) {
                    if (pov == 1) {
                        bw.write(friendID);
                    } else {
                        bw.write(username);
                    }
                    bw.write(" ");
                    bw.write(friendFirstName);
                    bw.write(" ");
                    bw.write(friendLastName);
                } else {
                    bw.newLine();
                    if (pov == 1) {
                        bw.write(friendID);
                    } else {
                        bw.write(username);
                    }
                    bw.write(" ");
                    bw.write(friendFirstName);
                    bw.write(" ");
                    bw.write(friendLastName);
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    /**
     * Prosedur menghapus file chat history seorang user yang dipilih
     */
    private void deleteChatHistoryFile(String userID) {
        
        // hapus file chat history user yang dipilih
        try {
            File file = new File(path_to_users + username + "\\chathistory\\" + userID + ".txt");

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted");
            }else {
                System.out.println("Delete operation is failed");
            }
    	} catch(Exception e){	
            e.printStackTrace();
    	}
        
    }
    
    /**
     * Prosedur menambahkan user dari Global Friends ke dalam daftar pertemanan seorang user
     * @param evt 
     */
    private void btnAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFriendActionPerformed
        // TODO add your handling code here:
        String[] FriendsList;
        int idxRow = tabelGlobalFriends.getSelectedRow();
        
        if(idxRow != -1) {
            // mengambil username dan fullname dari user yang dipilih
            String clickedElmt_ID = (tabelGlobalFriends.getModel().getValueAt(idxRow, 0).toString());
            String clickedElmt_FullName = (tabelGlobalFriends.getModel().getValueAt(idxRow, 1).toString());
            
            System.out.println("Selected: " + clickedElmt_ID + " " + clickedElmt_FullName);
            
            // menambahkan ke dalam file friends list
            addFriendToFile(clickedElmt_ID, clickedElmt_FullName, 1);
            
            // menambahkan ke dalam tabel friends list
            //FriendsList = GET_FriendList();
            
            FriendsList = GET_ArrayOfFriends(username);
            Object[][] data = new Object[numFriendsList][2];
            String[] header = {"ID", "Full Name"};
            int idxSplitted;
            
            // mengisi objek data dengan semua user yang terdaftar dalam database
            for (int i = 0; i < numFriendsList; i++) {
                idxSplitted = 0;
                data[i][1] = "";
                for (String splittedVal: FriendsList[i].split(" ")){
                    if (idxSplitted == 0) {
                        data[i][0] = splittedVal;
                    }
                    else {
                        data[i][1] = data[i][1] + splittedVal;
                        if (idxSplitted == 1) {
                            data[i][1] = data[i][1] + " ";
                        }
                    } 
                    idxSplitted++;
                }
            }
        
            // update isi tabel friendslist
            tabelFriendsList.setModel(new DefaultTableModel(data, header) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            
            // menghapus user di global friends yang dipilih untuk ditambahkan ke friends list
            int modelIndex = tabelGlobalFriends.convertRowIndexToModel(idxRow); 
            DefaultTableModel model = (DefaultTableModel)tabelGlobalFriends.getModel();
            model.removeRow(modelIndex);
            
            // menambahkan this.user ke dalam friend list user yang baru di add (2 arah)
            addFriendToFile(clickedElmt_ID, fullname, 2);
            
        }
    }//GEN-LAST:event_btnAddFriendActionPerformed

    private void btnDeleteChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteChatActionPerformed
        // TODO add your handling code here:
        int idxRow = tabelChatHistory.getSelectedRow();
        
        if(idxRow != -1) {
            // mengambil username dan fullname dari user yang dipilih
            String clickedElmt_ID = (tabelChatHistory.getModel().getValueAt(idxRow, 0).toString());
            
            // menghapus file chat history 
            deleteChatHistoryFile(clickedElmt_ID);
            
            // menghapus chat user yang dipilih pada tabel chat history
            int modelIndex = tabelChatHistory.convertRowIndexToModel(idxRow); 
            DefaultTableModel model = (DefaultTableModel)tabelChatHistory.getModel();
            model.removeRow(modelIndex);
        }
    }//GEN-LAST:event_btnDeleteChatActionPerformed

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
            java.util.logging.Logger.getLogger(SubsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubsHome(args[0], Integer.parseInt(args[1]), args[2], args[3]).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFriend;
    private javax.swing.JButton btnDeleteChat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelChatControl;
    private javax.swing.JPanel panelGlobalAddControl;
    private javax.swing.JTable tabelChatHistory;
    private javax.swing.JTable tabelFriendsList;
    private javax.swing.JTable tabelGlobalFriends;
    // End of variables declaration//GEN-END:variables

    /*
    class ListenFromServer extends Thread {
 
        @Override
        public void run() {
            while (true) {
                try {
                    String msg = (String) input.readObject();
                    String res;
                    String type = msg.split("~")[0];
                    String pengirim = msg.split("~")[1];
                    String text = msg.split("~")[2];
                    String kepada = msg.split("~")[3];
                    switch (type) {
                        case "recievePrivateText":
                            res = pengirim + ": " + text;
 
                            if (kepada.equals(username)) {
                                for (PrivChatDialog pMDialog : dialogs) {
                                    if (pMDialog.getName().equals(pengirim)) {
                                        pMDialog.display(res);
                                        pMDialog.setVisible(true);
                                        break;
                                    }
                                }
                            }
 
                            break;
                        case "login":
                            viewTextArea.setText(viewTextArea.getText() + pengirim + " sedah login..." + "\n");
                            clients.add(pengirim);
                            break;
                        case "logout":
                            viewTextArea.setText(viewTextArea.getText() + pengirim + " telah logout..." + "\n");
                            clients.remove(pengirim);
                            for (PrivChatDialog pMDialog : dialogs) {
                                if (pMDialog.getName().equals(pengirim)) {
                                    dialogs.remove(pMDialog);
                                    break;
                                }
                            }
                            break;
                        case "list":
                            setTable(text);
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("Server has close the connection: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }
        }
 
        private void setTable(String text) {
            int rows = text.split(":").length - 1;
            Object[][] data = new Object[rows][1];
 
            for (int i = 0; i < rows; i++) {
                String t = text.split(":")[i];
                data[i][0] = t;
 
                boolean ada = false;
                for (PrivChatDialog pMDialog : dialogs) {
                    if (pMDialog.getName().equals(t)) {
                        ada = true;
                    }
                }
 
                if (!ada) {
                    PrivChatDialog pmd = new PrivChatDialog(SubsHome.this, socket, input, output, username, t);
                    pmd.setName(t);
                    pmd.setTitle(username + "/" + t);
                    dialogs.add(pmd);
                }
            }
 
            String[] header = {"Clients"};
 
            clientTable.setModel(new DefaultTableModel(data, header) {
 
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        }
    }
    */

}
