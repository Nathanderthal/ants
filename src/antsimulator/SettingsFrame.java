/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antsimulator;

import javax.swing.JOptionPane;

/**
 *
 * @author Nathan
 */
public class SettingsFrame extends javax.swing.JFrame
{
  private final AntSimulatorPanel antSimulatorPanel;
  
  public SettingsFrame(AntSimulatorPanel antSimulatorPanel)
  {
    super("Ant Simulator Settings");
    
    this.antSimulatorPanel = antSimulatorPanel;
    
    setAlwaysOnTop(true);
    
    initComponents();
    
    setFieldsFromSettings();
  }

  public final void setFieldsFromSettings()
  {
    SimulationSettings settings = antSimulatorPanel.getSettings();
    
    antCountField.setText(Integer.toString(settings.getAntCount()));
    foodCountField.setText(Integer.toString(settings.getFoodCount()));
    gridDivisionsField.setText(Integer.toString(settings.getGridDivisions()));
    
    stepDistanceField.setText(Double.toString(settings.getSpeed()));
    stepIntervalField.setText(Integer.toString(settings.getUpdateDelay()));
    turnRangeField.setText(Double.toString(settings.getTurnRange()));
    turnRangeWithPheremonesField.setText(Double.toString(settings.getPheremoneTurnRange()));
    antPathMaxStepsField.setText(Integer.toString(settings.getPathMax()));
    staggerInitialExitField.setText(Integer.toString(settings.getStaggerLeavingHome()));
    
    pheremonePerStepField.setText(Double.toString(settings.getPheremonePerMovement()));
    pheremonePerStepWithFoodField.setText(Double.toString(settings.getPheremonePerMovementWithFood()));
    pheremoneDecayRateField.setText(Double.toString(settings.getPheremoneDecayRate()));    
  }
  
  private boolean setSettingsFromFields()
  {
    SimulationSettings settings = antSimulatorPanel.getSettings();
    
    try
    {
      int antCount = Integer.parseInt(antCountField.getText());
      int foodCount = Integer.parseInt(foodCountField.getText());
      int gridDivisions = Integer.parseInt(gridDivisionsField.getText());

      double stepDistance = Double.parseDouble(stepDistanceField.getText());
      int stepInterval = Integer.parseInt(stepIntervalField.getText());
      double turnRange = Double.parseDouble(turnRangeField.getText());
      double turnRangeWithPheremones = Double.parseDouble(turnRangeWithPheremonesField.getText());
      int antPathMaxSteps = Integer.parseInt(antPathMaxStepsField.getText());
      int staggerInitialAmount = Integer.parseInt(staggerInitialExitField.getText());
      
      double pheremonePerStep = Double.parseDouble(pheremonePerStepField.getText());
      double pheremonePerStepWithFood = Double.parseDouble(pheremonePerStepWithFoodField.getText());
      double pheremoneDecayRate = Double.parseDouble(pheremoneDecayRateField.getText());
      
      settings.setAntCount(antCount);
      settings.setFoodCount(foodCount);
      settings.setGridDivisions(gridDivisions);
      
      settings.setSpeed(stepDistance);
      settings.setUpdateDelay(stepInterval);
      settings.setTurnRange(turnRange);
      settings.setPheremoneTurnRange(turnRangeWithPheremones);
      settings.setPathMax(antPathMaxSteps);
      settings.setStaggerLeavingHome(staggerInitialAmount);
      
      settings.setPheremonePerMovement(pheremonePerStep);
      settings.setPheremonePerMovementWithFood(pheremonePerStepWithFood);
      settings.setPheremoneDecayRate(pheremoneDecayRate);
      
      return true;
    }
    catch(NumberFormatException nfe)
    {
      nfe.printStackTrace(System.out);
      
      JOptionPane.showMessageDialog(this, "Cannot parse all fields!", "Error Parsing Values", JOptionPane.ERROR_MESSAGE);
      
      return false;
    }
  }
  
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    closeButton = new javax.swing.JButton();
    applyButton = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    pheremonePerStepLabel = new javax.swing.JLabel();
    pheremonePerStepWithFoodLabel = new javax.swing.JLabel();
    pheremoneDecayRateLabel = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    jSeparator3 = new javax.swing.JSeparator();
    jSeparator4 = new javax.swing.JSeparator();
    antCountField = new javax.swing.JTextField();
    foodCountField = new javax.swing.JTextField();
    gridDivisionsField = new javax.swing.JTextField();
    stepDistanceField = new javax.swing.JTextField();
    stepIntervalField = new javax.swing.JTextField();
    turnRangeField = new javax.swing.JTextField();
    turnRangeWithPheremonesField = new javax.swing.JTextField();
    antPathMaxStepsField = new javax.swing.JTextField();
    pheremonePerStepField = new javax.swing.JTextField();
    pheremonePerStepWithFoodField = new javax.swing.JTextField();
    pheremoneDecayRateField = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    staggerInitialExitField = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    closeButton.setText("Close");
    closeButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        closeButtonActionPerformed(evt);
      }
    });

    applyButton.setText("Apply");
    applyButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        applyButtonActionPerformed(evt);
      }
    });

    jLabel1.setText("Ant Count:");

    jLabel2.setText("Food Count:");

    jLabel3.setText("Grid Divisions:");

    pheremonePerStepLabel.setText("Pheremone Per Step Without Food:");

    pheremonePerStepWithFoodLabel.setText("Pheremone Per Step With Food:");

    pheremoneDecayRateLabel.setText("Pheremone Decay Rate:");

    jLabel7.setText("Step Distance (Percent of Screen):");

    jLabel8.setText("Step Interval (Milliseconds):");

    jLabel9.setText("Turn Range (Radians):");

    jLabel10.setText("Turn Range With Pheremones (Radians):");

    jLabel11.setText("Ant Path Maximum Number of Steps:");

    jLabel4.setText("Stagger Initial Exit (# of Steps):");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jSeparator2)
          .addComponent(jSeparator3)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(applyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addGap(48, 48, 48)
            .addComponent(antCountField))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel2)
            .addGap(38, 38, 38)
            .addComponent(foodCountField))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel3)
            .addGap(22, 22, 22)
            .addComponent(gridDivisionsField))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(pheremonePerStepLabel)
              .addComponent(pheremonePerStepWithFoodLabel)
              .addComponent(pheremoneDecayRateLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(pheremonePerStepField)
              .addComponent(pheremonePerStepWithFoodField)
              .addComponent(pheremoneDecayRateField)))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel7)
              .addComponent(jLabel8)
              .addComponent(jLabel9)
              .addComponent(jLabel10)
              .addComponent(jLabel11)
              .addComponent(jLabel4))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(stepDistanceField)
              .addComponent(stepIntervalField)
              .addComponent(turnRangeField)
              .addComponent(turnRangeWithPheremonesField)
              .addComponent(antPathMaxStepsField)
              .addComponent(staggerInitialExitField, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jLabel1)
          .addComponent(antCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(foodCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(gridDivisionsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel7)
          .addComponent(stepDistanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel8)
          .addComponent(stepIntervalField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel9)
          .addComponent(turnRangeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel10)
          .addComponent(turnRangeWithPheremonesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel11)
          .addComponent(antPathMaxStepsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel4)
          .addComponent(staggerInitialExitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(pheremonePerStepLabel)
          .addComponent(pheremonePerStepField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(pheremonePerStepWithFoodLabel)
          .addComponent(pheremonePerStepWithFoodField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(pheremoneDecayRateLabel)
          .addComponent(pheremoneDecayRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(39, 39, 39)
        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(applyButton)
          .addComponent(closeButton))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_closeButtonActionPerformed
  {//GEN-HEADEREND:event_closeButtonActionPerformed
    dispose();
  }//GEN-LAST:event_closeButtonActionPerformed

  private void applyButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applyButtonActionPerformed
  {//GEN-HEADEREND:event_applyButtonActionPerformed
    if(setSettingsFromFields())
    {
      antSimulatorPanel.reset();
    }
  }//GEN-LAST:event_applyButtonActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField antCountField;
  private javax.swing.JTextField antPathMaxStepsField;
  private javax.swing.JButton applyButton;
  private javax.swing.JButton closeButton;
  private javax.swing.JTextField foodCountField;
  private javax.swing.JTextField gridDivisionsField;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator3;
  private javax.swing.JSeparator jSeparator4;
  private javax.swing.JTextField pheremoneDecayRateField;
  private javax.swing.JLabel pheremoneDecayRateLabel;
  private javax.swing.JTextField pheremonePerStepField;
  private javax.swing.JLabel pheremonePerStepLabel;
  private javax.swing.JTextField pheremonePerStepWithFoodField;
  private javax.swing.JLabel pheremonePerStepWithFoodLabel;
  private javax.swing.JTextField staggerInitialExitField;
  private javax.swing.JTextField stepDistanceField;
  private javax.swing.JTextField stepIntervalField;
  private javax.swing.JTextField turnRangeField;
  private javax.swing.JTextField turnRangeWithPheremonesField;
  // End of variables declaration//GEN-END:variables
}
