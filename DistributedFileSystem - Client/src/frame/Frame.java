package frame;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import service.ClientService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class Frame extends javax.swing.JFrame {

    private final ClientService clientService;
    private List<String> serverList;
    private Integer openedFileId;
    private static final String FOLDER = "true";

    public Frame(String monitorAddress, int mePort) {
        super("Windows Explorer");
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        extraInits();

        clientService = new ClientService(this, monitorAddress, mePort);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
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
                    .addComponent(navigationBar))
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
            requestReaddir("");
        }
    }//GEN-LAST:event_navigationBarKeyPressed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        requestReaddir("");
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
        entity.File file = new entity.File();
        file.setBody(txtFile.getText());
        file.setFsize(txtFile.getText().length());
        file.setUpdated_at((Timestamp) new Date());
        file.setId(openedFileId);

        Message m = new Message();
        m.setAction(Action.WRITE);
        m.setData(file);
        m.setSrc(clientService.getMe());

        for (String s : serverList) {
            Connection.send(s, m);
        }
        //alterar column da JTable
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            entity.File file = new entity.File();
            file.setFname(txtName.getText());
            file.setUpdated_at((Timestamp) new Date());
            file.setId(openedFileId);

            Message m = new Message();
            m.setAction(Action.RENAME);
            m.setData(file);
            m.setSrc(clientService.getMe());

            for (String s : serverList) {
                Connection.send(s, m);
            }
            //alterar column da JTable
        }
    }//GEN-LAST:event_txtNameKeyPressed

    private void btnNewFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewFolderActionPerformed
        Date data = new Date();

        entity.File file = new entity.File();
        file.setFname(JOptionPane.showInputDialog(null, "Nome da pasta: "));
        file.setPath(navigationBar.getText());
        file.setIs_dir(true);
        file.setFsize(0);
        file.setCreated_at((Timestamp) data);
        file.setRead_at((Timestamp) data);
        file.setUpdated_at((Timestamp) data);
        file.setOwner(clientService.getMe());

        Message m = new Message();
        m.setAction(Action.MKDIR);
        m.setData(file);
        m.setSrc(clientService.getMe());

        for (String s : serverList) {
            Connection.send(s, m);
        }
        //adicionar row a JTable
    }//GEN-LAST:event_btnNewFolderActionPerformed

    private void btnNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewFileActionPerformed
        Date data = new Date();

        entity.File file = new entity.File();
        file.setFname(JOptionPane.showInputDialog(null, "Nome do arquivo: "));
        file.setPath(navigationBar.getText());
        file.setIs_dir(false);
        file.setBody("");
        file.setFsize(0);
        file.setFtype(".txt");
        file.setCreated_at((Timestamp) data);
        file.setRead_at((Timestamp) data);
        file.setUpdated_at((Timestamp) data);
        file.setOwner(clientService.getMe());

        Message m = new Message();
        m.setAction(Action.CREATE);
        m.setData(file);
        m.setSrc(clientService.getMe());

        for (String s : serverList) {
            Connection.send(s, m);
        }
        //adicionar row a JTable
    }//GEN-LAST:event_btnNewFileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewFile;
    private javax.swing.JButton btnNewFolder;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
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
        getTableDirectory().setSelectionModel(new ForcedListSelectionModel());
    }

    private void tableDirectoryReadEvt(int row) {
        if (getTableDirectory().getValueAt(row, 0).equals(FOLDER)) {
            String folder = getTableDirectory().getValueAt(row, 1) + File.separator;
            getNavigationBar().setText(getNavigationBar().getText() + folder);
            requestReaddir(folder);
        } else {
            entity.File file = new entity.File();
            file.setFname((String) getTableDirectory().getValueAt(row, 1));
            file.setPath(getNavigationBar().getText());
            file.setRead_at((Timestamp) new Date());

            requestRead(file);
            requestGetAttributes(file);
        }
    }

    private void tableDirectoryDeleteEvt(int row) {
        entity.File file = new entity.File();
        file.setFname((String) tableDirectory.getValueAt(row, 1));
        file.setPath(navigationBar.getText());
        if (getTableDirectory().getValueAt(row, 0).equals(FOLDER)) {
            requestRmdir(file);
        } else {
            requestDelete(file);
        }
    }

    private void requestReaddir(String folder) {
        serverList = new LinkedList<>();
        Message m = new Message();
        m.setAction(Action.READDIR);
        m.setData(getNavigationBar().getText() + folder);
        m.setSrc(clientService.getMe());

        String address = clientService.getServer().getIp() + ":" + clientService.getServer().getPort();
        Connection.send(address, m);
    }

    private void requestRead(entity.File file) {
        Message m = new Message();
        m.setAction(Action.READ);
        m.setData(file);
        m.setSrc(clientService.getMe());

        Connection.send(serverList.get(0), m);
        //E quando o servidor está inativo?
    }

    private void requestGetAttributes(entity.File file) {
        Message m = new Message();
        m.setAction(Action.GET_ATTRIBUTES);
        m.setData(file);
        m.setSrc(clientService.getMe());

        Connection.send(serverList.get(0), m);
        //E quando o servidor está inativo?
    }

    private void requestRmdir(entity.File file) {
        Message m = new Message();
        m.setAction(Action.RMDIR);
        m.setData(file);
        m.setSrc(clientService.getMe());

        for (String s : serverList) {
            Connection.send(s, m);
        }
        //remover row da JTable
    }

    private void requestDelete(entity.File file) {
        Message m = new Message();
        m.setAction(Action.DELETE);
        m.setData(file);
        m.setSrc(clientService.getMe());

        for (String s : serverList) {
            Connection.send(s, m);
        }
        //remover row da JTable
    }

    public List<String> getServerList() {
        return serverList;
    }

    public void setServerList(List<String> serverList) {
        this.serverList = serverList;
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
}
