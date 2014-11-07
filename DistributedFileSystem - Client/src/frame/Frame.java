package frame;

import entity.Local;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import service.ClientService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class Frame extends javax.swing.JFrame {

    private final ClientService clientService;
    private Set<String> serverSet;
    private Integer openedFileId;
    private static final String FOLDER = "Pasta";
    private ForcedListSelectionModel model;
    private Stack<String> backStack, forwardStack;

    public Frame(String owner, String serverAddress, int mePort) {
        super("Windows Explorer");
        initComponents();
        extraInits();

        clientService = new ClientService(this, owner, serverAddress, mePort);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        navigationBar = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDirectory = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtFile = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        panelDetails = new javax.swing.JPanel();
        labelNome = new javax.swing.JLabel();
        labelTipo = new javax.swing.JLabel();
        labelTamanho = new javax.swing.JLabel();
        labelDataCriacao = new javax.swing.JLabel();
        labelDataAcesso = new javax.swing.JLabel();
        labelDataModificacao = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        labelProprietario = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnNewFolder = new javax.swing.JButton();
        btnNewFile = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnAvancar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        navigationBar.setText("/");
        navigationBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                navigationBarKeyPressed(evt);
            }
        });

        btnRefresh.setText("Atualizar");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        tableDirectory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pasta ou Arquivo", "Nome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDirectory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDirectoryMouseClicked(evt);
            }
        });
        tableDirectory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableDirectoryKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tableDirectory);

        txtFile.setColumns(20);
        txtFile.setRows(5);
        txtFile.setEnabled(false);
        jScrollPane3.setViewportView(txtFile);

        btnSave.setText("Salvar");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        labelNome.setText("Nome: ");

        labelTipo.setText("Tipo: ");

        labelTamanho.setText("Tamanho: ");

        labelDataCriacao.setText("Data de criação: ");

        labelDataAcesso.setText("Data de acesso: ");

        labelDataModificacao.setText("Data de modificação: ");

        txtName.setEnabled(false);
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });

        labelProprietario.setText("Proprietário: ");

        javax.swing.GroupLayout panelDetailsLayout = new javax.swing.GroupLayout(panelDetails);
        panelDetails.setLayout(panelDetailsLayout);
        panelDetailsLayout.setHorizontalGroup(
            panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTipo)
                    .addComponent(labelTamanho)
                    .addGroup(panelDetailsLayout.createSequentialGroup()
                        .addComponent(labelNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(247, 247, 247)
                .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailsLayout.createSequentialGroup()
                        .addComponent(labelDataCriacao)
                        .addGap(247, 247, 247)
                        .addComponent(labelProprietario))
                    .addComponent(labelDataAcesso)
                    .addComponent(labelDataModificacao))
                .addContainerGap(503, Short.MAX_VALUE))
        );
        panelDetailsLayout.setVerticalGroup(
            panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(labelDataCriacao)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProprietario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTipo)
                    .addComponent(labelDataAcesso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTamanho)
                    .addComponent(labelDataModificacao))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btnNewFolder.setText("Nova pasta");
        btnNewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewFolderActionPerformed(evt);
            }
        });

        btnNewFile.setText("Novo arquivo");
        btnNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewFileActionPerformed(evt);
            }
        });

        btnVoltar.setText("Voltar");
        btnVoltar.setEnabled(false);
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnAvancar.setText("Avançar");
        btnAvancar.setEnabled(false);
        btnAvancar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvancarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAvancar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(navigationBar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNewFolder)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNewFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave))
                            .addComponent(jScrollPane3))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(navigationBar)
                    .addComponent(btnVoltar)
                    .addComponent(btnAvancar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnNewFolder)
                            .addComponent(btnNewFile))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void navigationBarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_navigationBarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            requestReaddir();
            if (backStack.isEmpty()) {
                backStack.push(navigationBar.getText());
            } else {
                String lastPath = backStack.lastElement();
                if (!lastPath.equals(navigationBar.getText())) {
                    backStack.push(navigationBar.getText());
                    if (backStack.size() == 2) {
                        btnVoltar.setEnabled(true);
                    }
                    forwardStack = new Stack<>();
                    btnAvancar.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_navigationBarKeyPressed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        requestReaddir();
        if (backStack.isEmpty()) {
            backStack.push(navigationBar.getText());
        } else {
            String lastPath = backStack.lastElement();
            if (!lastPath.equals(navigationBar.getText())) {
                backStack.push(navigationBar.getText());
                if (backStack.size() == 2) {
                    btnVoltar.setEnabled(true);
                }
                forwardStack = new Stack<>();
                btnAvancar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tableDirectoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDirectoryMouseClicked
        int row = getTableDirectory().getSelectedRow();
        if (row != -1 && evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            tableDirectoryReadEvt(row);
        }
    }//GEN-LAST:event_tableDirectoryMouseClicked

    private void tableDirectoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableDirectoryKeyPressed
        int row = getTableDirectory().getSelectedRow();
        if (row != -1) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                tableDirectoryReadEvt(row);
            } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                tableDirectoryDeleteEvt(row);
            }
        }
    }//GEN-LAST:event_tableDirectoryKeyPressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Date data = new Date();
        entity.Local file = new entity.Local();
        file.setBody(txtFile.getText());
        file.setFsize(txtFile.getText().length());
        file.setUpdated_at((new Timestamp(data.getTime())).toString());
        file.setId(openedFileId);

        Message m = new Message();
        m.setAction(Action.WRITE);
        m.setData(file);
        m.setSrc(clientService.getMe());

        for (String s : getServerSet()) {
            try {
                Connection.send(s, m);
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //alterar column da JTable
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Date data = new Date();
            entity.Local file = new entity.Local();
            file.setFname(txtName.getText());
            file.setUpdated_at((new Timestamp(data.getTime())).toString());
            file.setId(openedFileId);

            Message m = new Message();
            m.setAction(Action.RENAME);
            m.setData(file);
            m.setSrc(clientService.getMe());

            for (String s : getServerSet()) {
                try {
                    Connection.send(s, m);
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtNameKeyPressed

    private void btnNewFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewFolderActionPerformed
        Date data = new Date();

        entity.Local file = new entity.Local();
        file.setFname(JOptionPane.showInputDialog(null, "Nome da pasta: "));
        file.setPath(navigationBar.getText());
        file.setIs_dir(true);
        file.setFsize(0);
        file.setCreated_at((new Timestamp(data.getTime())).toString());
        file.setRead_at((new Timestamp(data.getTime())).toString());
        file.setUpdated_at((new Timestamp(data.getTime())).toString());
        file.setOwner(clientService.getOwner());

        Message m = new Message();
        m.setAction(Action.MKDIR);
        m.setData(file);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        try {
            Connection.send(address, m);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel dtm = (DefaultTableModel) this.getTableDirectory().getModel();
        String[] s = new String[2];
        s[0] = "Pasta";
        s[1] = file.getFname();
        dtm.addRow(s);
    }//GEN-LAST:event_btnNewFolderActionPerformed

    private void btnNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewFileActionPerformed
        Date data = new Date();

        entity.Local file = new entity.Local();
        file.setFname(JOptionPane.showInputDialog(null, "Nome do arquivo: "));
        file.setPath(navigationBar.getText());
        file.setIs_dir(false);
        file.setBody("");
        file.setFsize(0);
        file.setFtype(".txt");
        file.setCreated_at((new Timestamp(data.getTime())).toString());
        file.setRead_at((new Timestamp(data.getTime())).toString());
        file.setUpdated_at((new Timestamp(data.getTime())).toString());
        file.setOwner(clientService.getOwner());

        Message m = new Message();
        m.setAction(Action.CREATE);
        m.setData(file);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        try {
            Connection.send(address, m);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel dtm = (DefaultTableModel) this.getTableDirectory().getModel();
        String[] s = new String[2];
        s[0] = "Arquivo";
        s[1] = file.getFname();
        dtm.addRow(s);
    }//GEN-LAST:event_btnNewFileActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        forwardStack.push(backStack.pop());
        navigationBar.setText(backStack.lastElement());

        btnAvancar.setEnabled(true);
        if (backStack.size() == 1) {
            btnVoltar.setEnabled(false);
        }
        requestReaddir();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnAvancarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvancarActionPerformed
        String path = forwardStack.pop();
        backStack.push(path);
        navigationBar.setText(path);

        btnVoltar.setEnabled(true);
        if (forwardStack.isEmpty()) {
            btnAvancar.setEnabled(false);
        }
        requestReaddir();
    }//GEN-LAST:event_btnAvancarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvancar;
    private javax.swing.JButton btnNewFile;
    private javax.swing.JButton btnNewFolder;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelDataAcesso;
    private javax.swing.JLabel labelDataCriacao;
    private javax.swing.JLabel labelDataModificacao;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelProprietario;
    private javax.swing.JLabel labelTamanho;
    private javax.swing.JLabel labelTipo;
    private javax.swing.JTextField navigationBar;
    private javax.swing.JPanel panelDetails;
    private javax.swing.JTable tableDirectory;
    private javax.swing.JTextArea txtFile;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    private void extraInits() {
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setModel(new ForcedListSelectionModel());
        getTableDirectory().setSelectionModel(getModel());

        backStack = new Stack<>();
    }

    private void tableDirectoryReadEvt(int row) {
        if (getTableDirectory().getValueAt(row, 0).equals(FOLDER)) {
            String folder = getTableDirectory().getValueAt(row, 1) + "/";
            getNavigationBar().setText(getNavigationBar().getText() + folder);
            requestReaddir();
            if (backStack.isEmpty()) {
                backStack.push(navigationBar.getText());
            } else {
                String lastPath = backStack.lastElement();
                if (!lastPath.equals(navigationBar.getText())) {
                    backStack.push(navigationBar.getText());
                    if (backStack.size() == 2) {
                        btnVoltar.setEnabled(true);
                    }
                    forwardStack = new Stack<>();
                    btnAvancar.setEnabled(false);
                }
            }
        } else {
            Date data = new Date();
            entity.Local file = new entity.Local();
            file.setFname((String) getTableDirectory().getValueAt(row, 1));
            file.setPath(getNavigationBar().getText());
            file.setRead_at((new Timestamp(data.getTime())).toString());
            file.setOwner(clientService.getOwner());

            requestRead(file);
            requestGetAttributes(file);
        }
    }

    private void tableDirectoryDeleteEvt(int row) {
        entity.Local file = new entity.Local();
        file.setFname((String) tableDirectory.getValueAt(row, 1));
        file.setPath(navigationBar.getText());
        file.setOwner(clientService.getOwner());
        if (getTableDirectory().getValueAt(row, 0).equals(FOLDER)) {
            requestRmdir(file);
        } else {
            requestDelete(file);
        }
    }

    private void requestReaddir() {
        //serverSet = new HashSet<>();

        Local f = new Local();
        f.setPath(getNavigationBar().getText());
        f.setOwner(clientService.getOwner());

        Message m = new Message();
        m.setAction(Action.READDIR);
        m.setData(f);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        try {
            Connection.send(address, m);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void requestRead(entity.Local file) {
        Message m = new Message();
        m.setAction(Action.READ);
        m.setData(file);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        try {
            Connection.send(address, m);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void requestGetAttributes(entity.Local file) {
        Message m = new Message();
        m.setAction(Action.GET_ATTRIBUTES);
        m.setData(file);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        try {
            Connection.send(address, m);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void requestRmdir(entity.Local file) {
        Message m = new Message();
        m.setAction(Action.RMDIR);
        m.setData(file);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        try {
            Connection.send(address, m);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel dtm = (DefaultTableModel) this.getTableDirectory().getModel();
        dtm.removeRow(tableDirectory.getSelectedRow());
    }

    private void requestDelete(entity.Local file) {
        Message m = new Message();
        m.setAction(Action.DELETE);
        m.setData(file);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        try {
            Connection.send(address, m);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel dtm = (DefaultTableModel) this.getTableDirectory().getModel();
        dtm.removeRow(tableDirectory.getSelectedRow());
    }

    public javax.swing.JButton getBtnNewFile() {
        return btnNewFile;
    }

    public void setBtnNewFile(javax.swing.JButton btnNewFile) {
        this.btnNewFile = btnNewFile;
    }

    public javax.swing.JButton getBtnNewFolder() {
        return btnNewFolder;
    }

    public void setBtnNewFolder(javax.swing.JButton btnNewFolder) {
        this.btnNewFolder = btnNewFolder;
    }

    public javax.swing.JButton getBtnRefresh() {
        return btnRefresh;
    }

    public void setBtnRefresh(javax.swing.JButton btnRefresh) {
        this.btnRefresh = btnRefresh;
    }

    public javax.swing.JButton getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(javax.swing.JButton btnSave) {
        this.btnSave = btnSave;
    }

    public javax.swing.JPanel getjPanel1() {
        return jPanel1;
    }

    public void setjPanel1(javax.swing.JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public javax.swing.JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(javax.swing.JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public javax.swing.JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public void setjScrollPane3(javax.swing.JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    public javax.swing.JSeparator getjSeparator1() {
        return jSeparator1;
    }

    public void setjSeparator1(javax.swing.JSeparator jSeparator1) {
        this.jSeparator1 = jSeparator1;
    }

    public javax.swing.JLabel getLabelDataAcesso() {
        return labelDataAcesso;
    }

    public void setLabelDataAcesso(javax.swing.JLabel labelDataAcesso) {
        this.labelDataAcesso = labelDataAcesso;
    }

    public javax.swing.JLabel getLabelDataCriacao() {
        return labelDataCriacao;
    }

    public void setLabelDataCriacao(javax.swing.JLabel labelDataCriacao) {
        this.labelDataCriacao = labelDataCriacao;
    }

    public javax.swing.JLabel getLabelDataModificacao() {
        return labelDataModificacao;
    }

    public void setLabelDataModificacao(javax.swing.JLabel labelDataModificacao) {
        this.labelDataModificacao = labelDataModificacao;
    }

    public javax.swing.JLabel getLabelNome() {
        return labelNome;
    }

    public void setLabelNome(javax.swing.JLabel labelNome) {
        this.labelNome = labelNome;
    }

    public javax.swing.JLabel getLabelProprietario() {
        return labelProprietario;
    }

    public void setLabelProprietario(javax.swing.JLabel labelProprietario) {
        this.labelProprietario = labelProprietario;
    }

    public javax.swing.JLabel getLabelTamanho() {
        return labelTamanho;
    }

    public void setLabelTamanho(javax.swing.JLabel labelTamanho) {
        this.labelTamanho = labelTamanho;
    }

    public javax.swing.JLabel getLabelTipo() {
        return labelTipo;
    }

    public void setLabelTipo(javax.swing.JLabel labelTipo) {
        this.labelTipo = labelTipo;
    }

    public javax.swing.JTextField getNavigationBar() {
        return navigationBar;
    }

    public void setNavigationBar(javax.swing.JTextField navigationBar) {
        this.navigationBar = navigationBar;
    }

    public javax.swing.JPanel getPanelDetails() {
        return panelDetails;
    }

    public void setPanelDetails(javax.swing.JPanel panelDetails) {
        this.panelDetails = panelDetails;
    }

    public javax.swing.JTable getTableDirectory() {
        return tableDirectory;
    }

    public void setTableDirectory(javax.swing.JTable tableDirectory) {
        this.tableDirectory = tableDirectory;
    }

    public javax.swing.JTextArea getTxtFile() {
        return txtFile;
    }

    public void setTxtFile(javax.swing.JTextArea txtFile) {
        this.txtFile = txtFile;
    }

    public javax.swing.JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(javax.swing.JTextField txtName) {
        this.txtName = txtName;
    }

    public Integer getOpenedFileId() {
        return openedFileId;
    }

    public void setOpenedFileId(Integer openedFileId) {
        this.openedFileId = openedFileId;
    }

    public ForcedListSelectionModel getModel() {
        return model;
    }

    public void setModel(ForcedListSelectionModel model) {
        this.model = model;
    }

    public Set<String> getServerSet() {
        return serverSet;
    }

    public void setServerSet(Set<String> serverSet) {
        this.serverSet = serverSet;
    }
}
