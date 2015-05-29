/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package antsimulator;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author nbible
 */
public class AntSimulatorDialog extends JFrame
{
  private AntSimulatorPanel antSimulatorPanel;
  
  public AntSimulatorDialog()
  {
    super("Ant Simulator");
    
    setSize(700, 600);
    setResizable(true);
    
    setLocationByPlatform(true);

    buildGUI();
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        antSimulatorPanel.kill();
      }
    });
  }
  
  private void buildGUI()
  {
    antSimulatorPanel = new AntSimulatorPanel();
    
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(antSimulatorPanel, BorderLayout.CENTER);
  }
  
  public static void main(String[] args)
  {
    try
    {
      AntSimulatorDialog dialog = new AntSimulatorDialog();
      dialog.setVisible(true);
    }
    catch(Exception e)
    {
      e.printStackTrace(System.out);
      System.exit(1);
    }
  }
}
