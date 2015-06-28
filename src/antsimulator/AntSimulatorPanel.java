/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package antsimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author nbible
 */
public class AntSimulatorPanel extends JPanel
{
  private List<Ant> ants = null;
  private List<Point> foods = null;
  private double[][] pheremoneLevels = null;
  
  private SimulationSettings settings;
  
  private SettingsFrame settingsWindow = null;
//  private List<Point> validCells = null;
//  private Point chosenCell = null;
  
  private Point home;
  
  public AntSimulatorPanel()
  {
    settings = new SimulationSettings();
    
    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        showSettingsWindow();
      }
    });
    
    Timer animationTimer = new Timer(17, new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        repaint();
      }
    });
    
    Timer pheromoneDecayTimer = new Timer(500, new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        decayPheromones();
      }
    });
    
    animationTimer.start();
    pheromoneDecayTimer.start();
    
    reset();
  }

  public SimulationSettings getSettings()
  {
    return settings;
  }
  
  public synchronized void showSettingsWindow()
  {
    if(settingsWindow == null)
    {
      settingsWindow = new SettingsFrame(this);
    }
    
    if(!settingsWindow.isVisible())
    {
      settingsWindow.setFieldsFromSettings();
      settingsWindow.setVisible(true);
    }
  }
  
  public final void reset()
  {
    kill();
    
    initializePheremoneLevels();
    initializeHome();
    initializeFood();
    initializeAnts();
    engageAnts();
  }
  
  public void initializeAnts()
  {
    ants = new ArrayList<>();
    
    int staggerAmount = settings.getStaggerLeavingHome();
    
    int staggerTotal = 0;
    
    for(int i = 0; i < settings.getAntCount(); i++)
    {
      Ant ant = new Ant(this, settings, staggerTotal);
      ants.add(ant);
      staggerTotal = staggerTotal + staggerAmount;
    }
  }
  
  public void engageAnts()
  {
    for(Ant ant : ants)
    {
      ant.start();
    }
  }
  
  private void initializeHome()
  {
    double oneThird = (double)settings.getGridDivisions() / 3d;
    
    int x = (int)Math.round((Math.random() * oneThird) + oneThird);
    int y = (int)Math.round((Math.random() * oneThird) + oneThird);
    
    home = new Point(x, y);
  }
  
  private void initializeFood()
  {
    foods = new ArrayList<>();
    
    for(int i = 0; i < settings.getFoodCount(); i++)
    {
      Point p = null;
      
      while(p == null ||
            Math.sqrt((p.x - home.x) * (p.x - home.x) + (p.y - home.y) * (p.y - home.y)) < 10)
      {
        int x = (int)Math.round(Math.random() * (double)settings.getGridDivisions());
        int y = (int)Math.round(Math.random() * (double)settings.getGridDivisions());
        
        p = new Point(x, y);
      }
      
      foods.add(p);
    }
  }
  
  private void initializePheremoneLevels()
  {
    pheremoneLevels = new double[settings.getGridDivisions()][settings.getGridDivisions()];
    
    for(int i = 0; i < pheremoneLevels.length; i++)
    {
      for(int j = 0; j < pheremoneLevels[i].length; j++)
      {
        pheremoneLevels[i][j] = 0;
      }
    }
  }
  
  private void decayPheromones()
  {
    for(int i = 0; i < pheremoneLevels.length; i++)
    {
      for(int j = 0; j < pheremoneLevels[i].length; j++)
      {
        pheremoneLevels[i][j] = Math.max(0, pheremoneLevels[i][j] - settings.getPheremoneDecayRate());
      }
    }
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics big = bi.getGraphics();
    
    big.setColor(Color.WHITE);
    big.fillRect(0, 0, getWidth(), getHeight());
    
    drawPheremones(big);
    drawHome(big);
    drawFood(big);
    drawAnts(big);
    
    g = g.create();
    
    g.drawImage(bi, 0, 0, null);
  }

  private void drawPheremones(Graphics g)
  {
    for(int i = 0; i < pheremoneLevels.length; i++)
    {
      for(int j = 0; j < pheremoneLevels[i].length; j++)
      {
        double level = pheremoneLevels[i][j];
        Rectangle cellRect = getScreenRectangleForCell(i, j);
        int alpha = (int)Math.round(level * 255d);
        
        if(alpha != 0)
        {
          g.setColor(new Color(0, 255, 0, alpha));
          g.fillRect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
        }
      }
    }
    
//    if(validCells != null)
//    {
//      g.setColor(Color.BLUE);
//      
//      for(Point cell : validCells)
//      {
//        Rectangle cellRect = getScreenRectangleForCell(cell.x, cell.y);
//        g.fillRect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
//      }
//    }
//    
//    if(chosenCell != null)
//    {
//      g.setColor(Color.MAGENTA);
//      Rectangle cellRect = getScreenRectangleForCell(chosenCell.x, chosenCell.y);
//      g.fillRect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
//    }
  }
  
  private void drawHome(Graphics g)
  {
    Rectangle homeRect = getScreenRectangleForCell(home);
    
    g.setColor(Color.DARK_GRAY);
    g.fillRect(homeRect.x, homeRect.y, homeRect.width, homeRect.height);
  }
  
  private void drawFood(Graphics g)
  {
    for(Point food : foods)
    {
      Rectangle foodRect = getScreenRectangleForCell(food);
      
      g.setColor(Color.RED);
      g.fillRect(foodRect.x, foodRect.y, foodRect.width, foodRect.height);
    }
  }
  
  private void drawAnts(Graphics g)
  {
    if(ants != null)
    {
      for(Ant ant : ants)
      {
        drawAnt(ant, g);
      }
    }
  }
  
  private void drawAnt(Ant ant, Graphics g)
  {
    Point antPoint = getScreenPointForScreenPercent(ant.getX(), ant.getY());
    
    AntState antState = ant.getAntState();
    
    if(antState == AntState.RETURNING_WITH_FOOD)
    {
      g.setColor(Color.RED);
    }
    else if(antState == AntState.RETURNING_WITHOUT_FOOD)
    {
      g.setColor(Color.BLUE);
    }
    else
    {
      g.setColor(Color.BLACK);
    }
    
    g.drawRect(antPoint.x, antPoint.y, 2, 2);
    
//    if(chosenCell != null)
//    {
//      Stack<Point2D.Double> pathFromHome = ant.getPathFromHome();
//      
//      if(pathFromHome != null)
//      {
//        if(pathFromHome.size() > 1)
//        {
//          Point2D.Double cellPercent = getScreenPercentForCellCenter(chosenCell);
//          Point cellPoint = getScreenPointForScreenPercent(cellPercent);
//
//          Point current = getScreenPointForScreenPercent(pathFromHome.pop());
//          Point last = getScreenPointForScreenPercent(pathFromHome.peek());
////          pathFromHome.push(current);
//
//          g.setColor(Color.GRAY);
//          g.drawLine(last.x, last.y, cellPoint.x, cellPoint.y);
//          g.drawLine(last.x, last.y, current.x, current.y);
//        }
//        
////        while(pathFromHome.size() > 1)
////        {
////          Point pathP1 = getScreenPointForScreenPercent(pathFromHome.pop());
////          Point pathP2 = getScreenPointForScreenPercent(pathFromHome.peek());
////
////          g.drawLine(pathP1.x, pathP1.y, pathP2.x, pathP2.y);
////        }
//      }
//    }
  }
  
  private Rectangle getScreenRectangleForCell(Point p)
  {
    return getScreenRectangleForCell(p.x, p.y);
  }
    
  private Rectangle getScreenRectangleForCell(int cellX, int cellY)
  {
    Dimension cell = getDimensionsForCell();
    
    int x = (int)Math.floor(((double)getWidth() / (double)settings.getGridDivisions()) * (double)cellX);
    int y = (int)Math.floor(((double)getHeight() / (double)settings.getGridDivisions()) * (double)cellY);
    
    return new Rectangle(x, y, cell.width, cell.height);
  }
  
  public Point getCellForScreenPercent(double xScreenPercent, double yScreenPercent)
  {
    int cellX = (int)Math.floor(xScreenPercent * (double)settings.getGridDivisions());
    int cellY = (int)Math.floor(yScreenPercent * (double)settings.getGridDivisions());
    
    return new Point(cellX, cellY);
  }
  
  public Point2D.Double getScreenPercentForCellCenter(Point p)
  {
    return getScreenPercentForCellCenter(p.x, p.y);
  }

  public Point2D.Double getScreenPercentForCellCenter(int cellX, int cellY)
  {
    double x = ((double)cellX + 0.5) / (double)settings.getGridDivisions();
    double y = ((double)cellY + 0.5) / (double)settings.getGridDivisions();
    
    return new Point2D.Double(x, y);
  }
  
  public boolean isCellValid(Point cell)
  {
    return isCellValid(cell.x, cell.y);
  }
  
  public boolean isCellValid(int x, int y)
  {
    return x >= 0 && x < settings.getGridDivisions() &&
           y >= 0 && y < settings.getGridDivisions();
  }
  
  private Dimension getDimensionsForCell()
  {
    int width = (int)Math.ceil((double)getWidth() / (double)settings.getGridDivisions());
    int height = (int)Math.ceil((double)getHeight() / (double)settings.getGridDivisions());
    
    return new Dimension(width, height);
  }
  
  private Point getScreenPointForScreenPercent(Point2D.Double p)
  {
    return getScreenPointForScreenPercent(p.x, p.y);
  }
  
  private Point getScreenPointForScreenPercent(double pX, double pY)
  {
    int x = (int)Math.round(pX * (double)getWidth());
    int y = (int)Math.round(pY * (double)getHeight());
    
    return new Point(x, y);
  }
  
  public void updatePheremones(boolean hasFood, double xScreenPercent, double yScreenPercent)
  {
    Point cell = getCellForScreenPercent(xScreenPercent, yScreenPercent);
    
    if(isCellValid(cell))
    {
      double increase;
      
      if(hasFood)
      {
        increase = settings.getPheremonePerMovementWithFood();
      }
      else
      {
        increase = settings.getPheremonePerMovement();
      }
      
      pheremoneLevels[cell.x][cell.y] = Math.min(pheremoneLevels[cell.x][cell.y] + increase, 1);
    }
  }
  
  public double getPheremoneValue(int cellX, int cellY)
  {
    return pheremoneLevels[cellX][cellY];
  }

  public List<Point> getFoods()
  {
    return foods;
  }

  public Point getHome()
  {
    return home;
  }
  
  public void kill()
  {
    if(ants != null)
    {
      for(Ant ant : ants)
      {
        ant.kill();
      }
    }
  }
  
//  public void updateCellTestDisplay(List<Point> validCells, Point chosenCell)
//  {
//    this.validCells = validCells;
//    this.chosenCell = chosenCell;
//  }
}
