/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.ed.hashmusic.view;

import br.ufjf.ed.hashmusic.model.MusicInfo;
import br.ufjf.ed.hashmusic.repository.Mp3Repository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Luis Augusto
 */
public class MainWindow extends javax.swing.JFrame 
{
    private final Mp3Repository repository;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() 
    {
        initComponents();
        setLocationRelativeTo(null);
        
        this.repository = new Mp3Repository();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbarMenu = new javax.swing.JToolBar();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        splitPanel = new javax.swing.JSplitPane();
        scrollPaneTree = new javax.swing.JScrollPane();
        musicTree = new javax.swing.JTree();
        scrollPaneList = new javax.swing.JScrollPane();
        musicList = new javax.swing.JList();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        reportMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hash Music");
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        toolbarMenu.setFloatable(false);
        toolbarMenu.setRollover(true);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add-icon.png"))); // NOI18N
        addButton.setToolTipText("Adicionar");
        addButton.setBorderPainted(false);
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        toolbarMenu.add(addButton);

        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete-icon.png"))); // NOI18N
        deleteButton.setToolTipText("Remover");
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusable(false);
        deleteButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarMenu.add(deleteButton);

        scrollPaneTree.setMinimumSize(new java.awt.Dimension(200, 23));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Repositório");
        musicTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        musicTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                musicTreeValueChanged(evt);
            }
        });
        scrollPaneTree.setViewportView(musicTree);

        splitPanel.setLeftComponent(scrollPaneTree);

        musicList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                musicListMouseClicked(evt);
            }
        });
        scrollPaneList.setViewportView(musicList);

        splitPanel.setRightComponent(scrollPaneList);

        fileMenu.setText("Arquivo");

        exitMenuItem.setText("Sair");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        reportMenu.setText("Relatórios");
        menuBar.add(reportMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbarMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
            .addComponent(splitPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        final File[] dirList = this.repository.showImportDirectoryChooser();
        
        if(dirList != null)
        {
            ProgressDialog progDialog = new ProgressDialog(MainWindow.this, true, repository.getImportDirectoryWorker(dirList));
            progDialog.setVisible(true);
            
            try 
            {
                this.repository.loadRepository();
                this.loadMusicTree();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try 
        {
            this.repository.createRepository();
            this.repository.loadRepository();
            
            this.loadMusicTree();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void musicTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_musicTreeValueChanged
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)musicTree.getLastSelectedPathComponent();
        
        if((selectedNode != null) && (selectedNode.getParent() != null))
        {
            DefaultListModel listModel = new DefaultListModel();
            ArrayList<MusicInfo> musicInfoList = this.repository.filterByArtistAndAlbum(selectedNode.getParent().toString(), selectedNode.getUserObject().toString());
            
            for(MusicInfo info : musicInfoList)
            {
                listModel.addElement(info.getTitle());
            }
            
            this.musicList.setModel(listModel);
        }
    }//GEN-LAST:event_musicTreeValueChanged

    private void musicListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_musicListMouseClicked
        try 
        {
            String selectedMusic = this.musicList.getSelectedValue().toString();
            this.repository.openMusic(repository.getMusicInfo(selectedMusic));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_musicListMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) 
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try 
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) 
        {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) 
        {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new MainWindow().setVisible(true);
            }
        });
    }
    
    private void loadMusicTree()
    {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Repositório");
        
        for(MusicInfo musicInfo : repository.getRepositoryList())
        {
            DefaultMutableTreeNode artistNode = this.getTreeNode(rootNode, musicInfo.getArtist());
            
            if(artistNode == null)
                artistNode = new DefaultMutableTreeNode(musicInfo.getArtist());
            
            DefaultMutableTreeNode albumNode = this.getTreeNode(artistNode, musicInfo.getAlbum());
            
            if(albumNode == null)
            {
                albumNode = new DefaultMutableTreeNode(musicInfo.getAlbum());
                artistNode.add(albumNode);
            }
            
            rootNode.add(artistNode);
        }
        
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        this.musicTree.setModel(treeModel);
    }
    
    private boolean containsTreeNode(DefaultMutableTreeNode rootNode, String childName)
    {
        return (getTreeNode(rootNode, childName) != null);
    }
    
    private DefaultMutableTreeNode getTreeNode(DefaultMutableTreeNode rootNode, String childName)
    {
        Enumeration childrenList = rootNode.children();
        
        while(childrenList.hasMoreElements())
        {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)childrenList.nextElement();
            
            if(child.getUserObject().toString().toLowerCase().equals(childName.toLowerCase()))
                return child;
        }
        
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JList musicList;
    private javax.swing.JTree musicTree;
    private javax.swing.JMenu reportMenu;
    private javax.swing.JScrollPane scrollPaneList;
    private javax.swing.JScrollPane scrollPaneTree;
    private javax.swing.JSplitPane splitPanel;
    private javax.swing.JToolBar toolbarMenu;
    // End of variables declaration//GEN-END:variables
}
