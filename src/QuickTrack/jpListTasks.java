/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack;

//import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
//import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
import org.json.JSONArray;
import org.json.JSONObject;

//import QuickTrack.jpEditTask;

/**
 *
 * @author David Regimbal, Aras Masalaitis, Jesse Wasko, Sumedh Savanur, Gauri
 * Khawadkar <bk.psu.edu>
 */
public class jpListTasks extends javax.swing.JPanel {

    static List<Integer> selected = new ArrayList<Integer>();
    
    /**
     * Creates new form jpListTasks
     */
    public jpListTasks() {
        initComponents();
    }

    public static void setTasks()
    {

        try {

            // Start by calling our server for tasks associated with this user
            JSONObject response = HTTPService.getTasks();
            
            // Grab the response from our call
            JSONArray rows = response.getJSONArray("response");
            
            // Init our JTable for later
            DefaultTableModel model = (DefaultTableModel) jtTasks.getModel();
            
            // Reset the JTable in case we are back a second time
            /*
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            */
            model.setColumnCount(0);
            model.setRowCount(0);

            // Declare column headers
            model.addColumn("Select");
            model.addColumn("id");
            model.addColumn("name");
            model.addColumn("description");
            model.addColumn("duedate");
            model.addColumn("group");
            model.addColumn("");

            // Iterate over our response and add rows
            for(int i = 0; i < rows.length(); i++)
            {
                JSONObject element = rows.getJSONObject(i);
                
                Date duedate = new Date(Integer.parseInt(element.getString("duedate"))*1000L); 
                SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-YYYY");
                
                Boolean isSelected = false;
                
                Boolean isGroup = (element.getString("notify") == "true")?true:false;
                
                model.addRow(new Object[] { isSelected, element.getString("id"), element.getString("name"), element.getString("description"), fmt.format(duedate), isGroup, "edit"  });
            }

            // Declare our edit action for our JButton
            Action edit = new AbstractAction()
            {
                public void actionPerformed(ActionEvent e)
                {
                    
                    JTable table = (JTable)e.getSource();
                    int modelRow = Integer.valueOf( e.getActionCommand() );
                    
                    // This holds the id to this task
                    int id = Integer.parseInt(table.getModel().getValueAt(modelRow, 1).toString());
             
                    // Create a new JFrame to display the edit window
                    JFrame frame = new JFrame("Edit Task ID " + id);
                    
                    // Apply the editTask JPanel
                    frame.getContentPane().add(new jpEditTask(id));
                    
                    // Init
                    frame.setResizable(false);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    
                }


            };

            // Bind our action to the 6th column 
            ButtonColumn buttonColumn = new ButtonColumn(jtTasks, edit, 6);
            buttonColumn.setMnemonic(KeyEvent.VK_D);
            
                        // Set our model and also create our listeners
            jtTasks.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {

                    // On a table change update our local store of selectedAssignmentIDs
                        for(int i = 0; i < jtTasks.getModel().getRowCount(); i++)
                        {
                           // System.out.println("jtResources.getModel().getRowCount() = " + jtResources.getModel().getRowCount());

                            int ResourcesId = Integer.parseInt(jtTasks.getModel().getValueAt(i,1).toString());
                            //System.out.println(selectedResources);
                            if ((Boolean) jtTasks.getModel().getValueAt(i,0))
                            {  

                              //  System.out.println("Selected ID1: " + ResourcesId);

                                // Add the ID if we do not have it already
                                if(!selected.contains(ResourcesId))
                                {
                                    selected.add(ResourcesId);
                                }

                            }
                            else
                            {

                            //    System.out.println("Selected ID2: " + ResourcesId);

                                for (Iterator<Integer> iterator = selected.iterator(); iterator.hasNext(); ) {
                                    Integer id = iterator.next();
                                    if (id == ResourcesId) 
                                    {
                                        iterator.remove();
                                    }
                                }

                          }

                        }     

                        // Show the programmer what IDs are selected
                       System.out.println(selected);
                    }

                });


        } catch (IOException ex) {
            
            // Fatal error; The program could not render/compile
            Logger.getLogger(jpOverview.class.getName()).log(Level.SEVERE, null, ex);
            
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtTasks = new javax.swing.JTable();
        btnGoBack = new javax.swing.JButton();
        label5 = new java.awt.Label();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 153, 153));

        jtTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Select", "ID", "Name", "Description", "Due Date", "Group", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtTasks);

        btnGoBack.setBackground(new java.awt.Color(51, 51, 51));
        btnGoBack.setForeground(new java.awt.Color(204, 204, 204));
        btnGoBack.setText("Go Back");
        btnGoBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoBackActionPerformed(evt);
            }
        });

        label5.setFont(new java.awt.Font("Modern No. 20", 0, 36)); // NOI18N
        label5.setText("Quick Track");

        jButton1.setText("Delete Selected");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGoBack)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGoBack)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGoBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoBackActionPerformed
        // Switch the panel 
        Container parent = this.getParent(); 
        LayoutManager layout = getParent().getLayout();
        if (layout instanceof CardLayout) {
            CardLayout cardLayout = (CardLayout)layout;
            cardLayout.show(parent, "jpOverview");
        }
    }//GEN-LAST:event_btnGoBackActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {

            if(!selected.isEmpty())
            {
                for (Iterator<Integer> iterator = selected.iterator(); iterator.hasNext(); ) {
                    Integer id = iterator.next();
                    iterator.remove();
                    HTTPService.deleteTask(id);
                }
            }
            
            
            setTasks();
            
            // Let the user know we have taken care of it
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Tasks Updated!");  
            
        } catch (IOException ex) {
            Logger.getLogger(jpCreateTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoBack;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jtTasks;
    private java.awt.Label label5;
    // End of variables declaration//GEN-END:variables
}
