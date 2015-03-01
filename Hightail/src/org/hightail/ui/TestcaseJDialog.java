package org.hightail.ui;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.hightail.KeyboardShortcuts;
import org.hightail.Testcase;

public class TestcaseJDialog extends javax.swing.JDialog {
    
    protected Testcase testcase;
    protected boolean isNew; // only used for some UI formatting
    protected boolean hasTextChanged = false;
    protected boolean returnValue = false; // false = no changes made, or user cancelled
    
    public boolean getReturnValue() {
        return returnValue;
    }
    
    public TestcaseJDialog(JFrame parent, Testcase testcase, boolean isNew) {
        super(parent,true); // makes it modal
        this.isNew = isNew;
        this.testcase = testcase;
        
        initComponents();
        
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }
            private void update() {
                hasTextChanged = true;
                saveButton.setEnabled(true);
            }
        };
        
        makeShortcuts();
        
        setLocationRelativeTo(parent);
        
        inputTextarea.setText(testcase.getInput());
        expectedOutputTextarea.setText(testcase.getExpectedOutput());
        if (!isNew) {
            programOutputTextarea.setText(testcase.getProgramOutput()); // this is read-only
        } else {
            programOutputTextarea.setEnabled(false);
            programOutputTextarea.setBackground(new java.awt.Color(220,220,220));
            // TODO: find a better way to show that this textarea is disabled (or remove it altogether)
        }
        executionResultLabel.setText(testcase.getExecutionResult().getFormattedResult()); // this is read-only
        executionResultLabel.setForeground(testcase.getExecutionResult().getColor());
        executionTimeLabel.setText(testcase.getExecutionResult().getFormattedTime()); // this is read-only
        
        timeLimitTextField.setText(String.valueOf(testcase.getTimeLimitInSeconds()));
        
        inputTextarea.getDocument().addDocumentListener(documentListener);
        expectedOutputTextarea.getDocument().addDocumentListener(documentListener);
        timeLimitTextField.getDocument().addDocumentListener(documentListener);
    }
    
    private void makeShortcuts() {
        // escape key will close the dialog
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        getRootPane().getActionMap().put("close", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAndClose();
            }
        });
        
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyboardShortcuts.getShortcut("save testcase"), "save");
        getRootPane().getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        
        // when TAB is pressed, cycle textareas instead of writing the \t
        inputTextarea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        inputTextarea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        expectedOutputTextarea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        expectedOutputTextarea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        programOutputTextarea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        programOutputTextarea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
    }
    
    private void confirmAndClose () {
        boolean doClose = !hasTextChanged; // if there was no change, we will just close
        
        if (!doClose) {
            // If there are unsaved changes, display confirm dialog
            int confirmed = JOptionPane.showConfirmDialog(this,
                    "There are unsaved changes. Are you sure?",
                    "Confirm cancel",
                    JOptionPane.YES_NO_OPTION);
            doClose = (confirmed == JOptionPane.YES_OPTION);
        }
        
        if (doClose) {
            // returnValue will be = false
            this.dispose();
        }
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        executionResultLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        executionTimeLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputTextarea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        expectedOutputTextarea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        programOutputTextarea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        timeLimitTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Test case");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Execution result:");

        executionResultLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        executionResultLabel.setText("jLabel2");

        jLabel3.setText("Execution time:");

        executionTimeLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        executionTimeLabel.setText("jLabel4");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Input:");

        inputTextarea.setColumns(20);
        inputTextarea.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        inputTextarea.setRows(5);
        inputTextarea.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jScrollPane2.setViewportView(inputTextarea);

        expectedOutputTextarea.setColumns(20);
        expectedOutputTextarea.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        expectedOutputTextarea.setRows(5);
        expectedOutputTextarea.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jScrollPane1.setViewportView(expectedOutputTextarea);

        programOutputTextarea.setEditable(false);
        programOutputTextarea.setColumns(20);
        programOutputTextarea.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        programOutputTextarea.setRows(5);
        programOutputTextarea.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jScrollPane3.setViewportView(programOutputTextarea);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Expected output:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Program output:");

        saveButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Time limit:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(executionResultLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(executionTimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeLimitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(executionResultLabel)
                        .addComponent(jLabel3)
                        .addComponent(executionTimeLabel)
                        .addComponent(jLabel2)
                        .addComponent(timeLimitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        confirmAndClose();
    }//GEN-LAST:event_formWindowClosing
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        confirmAndClose();
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    private void save() {
        testcase.setInput(inputTextarea.getText());
        testcase.setExpectedOutput(expectedOutputTextarea.getText());
        int timeLimit = Testcase.DEFAULT_TIME_LIMIT;
        try {
            timeLimit = (int) (Double.parseDouble(timeLimitTextField.getText()) * 1000.);
        } catch (NumberFormatException ex) {}
        testcase.setTimeLimit(timeLimit);
        testcase.save();
        this.returnValue = true;
        this.dispose();
    }
    
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        save();
    }//GEN-LAST:event_saveButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel executionResultLabel;
    private javax.swing.JLabel executionTimeLabel;
    private javax.swing.JTextArea expectedOutputTextarea;
    private javax.swing.JTextArea inputTextarea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea programOutputTextarea;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField timeLimitTextField;
    // End of variables declaration//GEN-END:variables
    
}
