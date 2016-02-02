/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack.Panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * AccountsManagement panel is the first panel users will see when they are not yet authenticated.
 * @author David Regimbal, Aras Masalaitis, Jesse Wasko, Sumedh Savanur, Gauri Khawadkar <bk.psu.edu>
 */
public class JPAccountsManagement extends javax.swing.JPanel {
 
    /**
     * Creates Account Management pane.
     * 
     */
    public JPAccountsManagement() {
        
        /**
         * Debugging
         */
        boolean bDebugging = true;
       
        /**
         * Initialize the JFrame for our multiple JPanels which will hold saved account data.
         */
        
        final JFrame jfAcctMngmtPanel = new JFrame("Account Management");
        jfAcctMngmtPanel.setVisible(true);
        jfAcctMngmtPanel.setSize(850,500);
        jfAcctMngmtPanel.setLayout(new BoxLayout(jfAcctMngmtPanel.getContentPane(),BoxLayout.X_AXIS));
        jfAcctMngmtPanel.setResizable(false);
        jfAcctMngmtPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /**
         * Load FontAwesome from the Resources folder to include glyphicons.
         * !! v2 removed
         */
        
        /*
        InputStream is = JPAccountsManagement.class.getResourceAsStream("/Resources/Fonts/fontawesome-webfont.ttf");
        Font fontAwesome = null;
        try{
            fontAwesome = Font.createFont(Font.TRUETYPE_FONT, is);
            fontAwesome = fontAwesome.deriveFont(Font.PLAIN, 24f);
        }catch (IOException | FontFormatException exp) {
            exp.printStackTrace();
        }
        */
        
        /**
         * Manually draw several JPanels to include account data.
         * !! Currently this is hard coded to 4 "accounts"
         */
        JPanel jpAccounts = new JPanel(new GridLayout(0,4));
        
        JPanel[] panel = new JPanel[6];
        for(int i=0; i<5; i++){
            
            panel[i] = new JPanel();
            panel[i].setName("jpBox" + String.valueOf(i));
            panel[i].setLayout(new BoxLayout(panel[i], BoxLayout.PAGE_AXIS));
            panel[i].setBackground(Color.lightGray);
            panel[i].setPreferredSize(new Dimension(200, 100));
            panel[i].setMaximumSize(new Dimension(200, 100));
            panel[i].setMinimumSize(new Dimension(200, 100));
            panel[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
          
            if(bDebugging){
                System.out.println("[JPanel.AccountsManagement] drawn: " + String.valueOf(i));
            }  
        
            /**
             * Adding event listeners to our containers so we can do hover and click events.
             */
            panel[i].addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    
                    if(bDebugging){
                        System.out.println("MOUSE_CLICKED_EVENT:for "+((JPanel)e.getSource()).getName());
                    }
                    
                }
                @Override
                public void mouseEntered(MouseEvent e){
                    
                    JPanel panel = (JPanel) e.getSource();
                    
                    /**
                     * Set the background color for onHover.
                     */
                    panel.setBackground(Color.red); 
                    
                    /**
                     * Set the cursor to the hand to notify users they can click the element.
                     */
                    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
                    panel.setCursor(cursor);
                    
                    if(bDebugging){
                        System.out.println("MOUSE_ENTERED_EVENT:for "+((JPanel)e.getSource()).getName());
                    }
                    
                }
                @Override
                public void mouseExited(MouseEvent e){
                    
                    JPanel panel = (JPanel) e.getSource();
                    
                    /**
                     * Set the background color back from onHover.
                     */
                    panel.setBackground(Color.lightGray);  
                    
                    if(bDebugging){
                        System.out.println("MOUSE_EXITED_EVENT:for "+((JPanel)e.getSource()).getName());
                    }
                    
                }
            });
            
            /**
             * Add each container to our JFrame.
             * Notice we are drawing a margin around our element
             */
            jpAccounts.add(panel[i],BorderLayout.CENTER);
            jpAccounts.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            
            // Declare Connection Name
            JLabel lblConnectionName = new JLabel("JPBOX" + String.valueOf(i));
            
            // Declare Username
            JLabel lblUsername = new JLabel("Usr: jpBox" + String.valueOf(i));
            
            // Declare URL
            JLabel lblUrl = new JLabel("Url: www.jpbox"+ String.valueOf(i)+".com");
            
            /**
             * Make our Connection Name bold.
             */
            EmptyBorder border = new EmptyBorder(0, 0, 20, 0);
            lblConnectionName.setBorder(border);
            Font fontConnectionName = lblUsername.getFont();
            lblConnectionName.setFont(fontConnectionName.deriveFont(fontConnectionName.getStyle() | Font.BOLD));
            
            /**
             * Finally add in our labels to our JPanel container.
             */
            panel[i].add(lblConnectionName, BorderLayout.CENTER);
            panel[i].add(Box.createRigidArea(new Dimension(5,0)));
            panel[i].add(lblUsername, BorderLayout.CENTER);
            panel[i].add(lblUrl, BorderLayout.CENTER);

            
            /**
             * Sruba-dub-dub push changes to our JFrame.
             */
            jfAcctMngmtPanel.revalidate();
            
            if(bDebugging){
                System.out.println("[JPanel.AccountsManagement] container build complete: " + String.valueOf(i));
            } 

        }//for.JPanel
        
        /**
         * Add the Add Account icon to our panel.
         */
        JPanel jpActions = new JPanel(new BorderLayout());
        JPanel jpButtonGroup = new JPanel();
        
        /**
         * Create a button group.
         */
        JButton jbAddAccount = new JButton("Add Account");
        JButton jbModifyAccounts = new JButton("Settings");
        jpButtonGroup.add(jbAddAccount);
        jpButtonGroup.add(jbModifyAccounts);
        
        /**
         * Action listeners for button group.
         */
        jbAddAccount.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
            
                if(bDebugging){
                    System.out.println("[JPanel.AccountsManagement.JButton]: JDAddAccount Dialog Open");
                } 
                                
                JDAddAccount jdAddAccount = new JDAddAccount(jfAcctMngmtPanel,true);
                jdAddAccount.setVisible(true);
                
                if(bDebugging){
                    System.out.println("[JPanel.AccountsManagement.JButton]: JDAddAccount Dialog Close");
                } 
            }
        }); 
        
        jbModifyAccounts.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(bDebugging){
                    System.out.println("[JPanel.AccountsManagement.JButton]: Settings");
                } 
            }
        }); 
        
        jpActions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jpActions.add(jpButtonGroup,BorderLayout.WEST);

        /**
         * A container to hold all the layouts into one.
         */
        JPanel jpAccountContainer = new JPanel(new BorderLayout());
        jpAccountContainer.add(jpActions,BorderLayout.PAGE_START);
        jpAccountContainer.add(jpAccounts,BorderLayout.LINE_START);
        
        /**
         * Add to the JFrame.
         */
        jfAcctMngmtPanel.add(jpAccountContainer);
   
    }//function.JPAccountsManagement
                                   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
