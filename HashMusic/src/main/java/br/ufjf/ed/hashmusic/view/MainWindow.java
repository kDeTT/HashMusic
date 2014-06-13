/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.ed.hashmusic.view;

import br.ufjf.ed.hashmusic.helper.FileHelper;
import br.ufjf.ed.hashmusic.helper.XmlHelper;
import br.ufjf.ed.hashmusic.model.MusicInfo;
import br.ufjf.ed.hashmusic.view.component.list.IconListRenderer;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Dialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author Luis Augusto
 */
public class MainWindow extends javax.swing.JFrame 
{
    private String pathRepository;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() 
    {
        initComponents();
        setLocationRelativeTo(null);
        
        this.pathRepository = String.format("%s\\%s", System.getProperty("user.dir"), "Repository");
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
        leftScrollPane = new javax.swing.JScrollPane();
        albumTree = new javax.swing.JTree();
        rightScrollPane = new javax.swing.JScrollPane();
        musicList = new javax.swing.JList();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        reportMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hash Music");
        setMinimumSize(new java.awt.Dimension(640, 480));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
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

        albumTree.setPreferredSize(new java.awt.Dimension(200, 64));
        leftScrollPane.setViewportView(albumTree);

        splitPanel.setLeftComponent(leftScrollPane);

        rightScrollPane.setViewportView(musicList);

        splitPanel.setRightComponent(rightScrollPane);

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
            .addComponent(toolbarMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        final File[] dirList = this.showImportDirectoryChooser();
        
        if(dirList != null)
        {
            SwingWorker worker = new SwingWorker() 
            {
                @Override
                protected Void doInBackground() throws InterruptedException 
                {
                    ArrayList<MusicInfo> musicInfoList = new ArrayList<>();
                    
                    for (File dir : dirList) 
                    {
                        if(!isCancelled())
                        {
                            ArrayList<String> allMp3Files = FileHelper.getAllFilesList(dir, "mp3");

                            for (String mp3Path : allMp3Files) 
                            {
                                if(!isCancelled())
                                {
                                    try 
                                    {
                                        Mp3File mp3File = new Mp3File(mp3Path);

                                        if (mp3File.hasId3v2Tag()) 
                                        {
                                            ID3v2 tagv2 = mp3File.getId3v2Tag();
                                            musicInfoList.add(new MusicInfo(tagv2.getArtist(), tagv2.getAlbum(), tagv2.getTitle()));
                                        }
                                    } 
                                    catch (IOException ex) 
                                    {
                                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                                    } 
                                    catch (UnsupportedTagException ex) 
                                    {
                                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                                    } 
                                    catch (InvalidDataException ex)
                                    {
                                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }

                    if(!isCancelled())
                    {
                        try 
                        {
                            if (XmlHelper.saveXml(String.format("%s\\%s", pathRepository, "Music.xml"), musicInfoList))
                                JOptionPane.showMessageDialog(MainWindow.this, "Músicas importadas com sucesso!");
                        } 
                        catch (FileNotFoundException ex)
                        {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        catch (IOException ex) 
                        {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    return null;
                }
            };
            
            ProgressDialog progDialog = new ProgressDialog(MainWindow.this, true, worker);
            progDialog.setVisible(true);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(!FileHelper.checkPathExists(pathRepository))
        {
            if(FileHelper.createDirectory(pathRepository))
                JOptionPane.showMessageDialog(MainWindow.this, "Pasta do repositório criada com sucesso!");
        }

        this.loadRepository();
    }//GEN-LAST:event_formWindowActivated

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
    
    private File[] showImportDirectoryChooser()
    {
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setDialogTitle("Escolha diretórios para importação de músicas MP3");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
            return fileChooser.getSelectedFiles();
        
        return null;
    }
    
    private void loadRepository()
    {
	Map<Object, Icon> icons = new HashMap<>();
        
        for(int i = 0; i < 10; i++)
        {
            icons.put(String.format("Music %s", String.valueOf(i)), MetalIconFactory.getTreeLeafIcon());
        }
        
        musicList.setModel(getMusicList());
        musicList.setCellRenderer(new IconListRenderer(icons));
    }
    
    private void saveRepository()
    {
        
    }
    
    private ListModel getMusicList()
    {
        DefaultListModel model = new DefaultListModel();
        
        for(int i = 0; i < 10; i++)
        {
            model.addElement(String.format("Music %s", String.valueOf(i)));
        }
        
        return model;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTree albumTree;
    private javax.swing.JButton deleteButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane leftScrollPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JList musicList;
    private javax.swing.JMenu reportMenu;
    private javax.swing.JScrollPane rightScrollPane;
    private javax.swing.JSplitPane splitPanel;
    private javax.swing.JToolBar toolbarMenu;
    // End of variables declaration//GEN-END:variables
}