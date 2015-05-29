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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
  private static final int ANT_COUNT = 2000;
  private static final int FOOD_COUNT = 30;
  private static final int GRID_DIVISIONS = 100;
  private static final double PHEREMONE_PER_MOVEMENT = 0; //0.00005;
  private static final double PHEREMONE_PER_MOVEMENT_WITH_FOOD = 0.01;
  private static final double PHEROMONE_DECAY_RATE = 0.01;
  
  private List<Ant> ants = null;
  private final List<Point> foods = new ArrayList<>();
  private final double[][] pheremoneLevels = new double[GRID_DIVISIONS][GRID_DIVISIONS];
  
  private Point home;
  
  public AntSimulatorPanel()
  {
    initializePheremoneLevels();
    initializeHome();
    initializeFood();
    
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
    
    addComponentListener(new ComponentAdapter()
    {
      @Override
      public void componentResized(ComponentEvent e)
      {
        if(ants == null &&
           getSize().getWidth() > 0 &&
           getSize().getHeight() > 0)
        {
          initializeAnts();
        }
      }
    });
  }
  
  public void initializeAnts()
  {
    ants = new ArrayList<>();
    
    for(int i = 0; i < ANT_COUNT; i++)
    {
      Ant ant = new Ant(this);
      ant.start();
      ants.add(ant);
    }
  }
  
  private void initializeHome()
  {
    int x = (int)Math.round(Math.random() * (double)GRID_DIVISIONS);
    int y = (int)Math.round(Math.random() * (double)GRID_DIVISIONS);
    
    home = new Point(x, y);
  }
  
  private void initializeFood()
  {
    for(int i = 0; i < FOOD_COUNT; i++)
    {
      Point p = null;
      
      while(p == null ||
            Math.sqrt((p.x - home.x) * (p.x - home.x) + (p.y - home.y) * (p.y - home.y)) < 10)
      {
        int x = (int)Math.round(Math.random() * (double)GRID_DIVISIONS);
        int y = (int)Math.round(Math.random() * (double)GRID_DIVISIONS);
        
        p = new Point(x, y);
      }
      
      foods.add(p);
    }
  }
  
  private void initializePheremoneLevels()
  {
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
        pheremoneLevels[i][j] = Math.max(0, pheremoneLevels[i][j] - PHEROMONE_DECAY_RATE);
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
    int x = (int)Math.round(ant.getX() * (double)getWidth());
    int y = (int)Math.round(ant.getY() * (double)getHeight());

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
    
    g.drawRect(x, y, 2, 2);
  }
  
  private Rectangle getScreenRectangleForCell(Point p)
  {
    return getScreenRectangleForCell(p.x, p.y);
  }
    
  private Rectangle getScreenRectangleForCell(int cellX, int cellY)
  {
    Dimension cell = getDimensionsForCell();
    
    int x = (int)Math.round(((double)getWidth() / (double)GRID_DIVISIONS) * (double)cellX);
    int y = (int)Math.round(((double)getHeight() / (double)GRID_DIVISIONS) * (double)cellY);
    
    return new Rectangle(x, y, cell.width, cell.height);
  }
  
  public Point getCellForScreenPercent(double xScreenPercent, double yScreenPercent)
  {
    int cellX = (int)Math.floor(xScreenPercent * (double)GRID_DIVISIONS);
    int cellY = (int)Math.floor(yScreenPercent * (double)GRID_DIVISIONS);
    
    return new Point(cellX, cellY);
  }
  
  public Point2D.Double getScreenPercentForCellCenter(Point p)
  {
    return getScreenPercentForCellCenter(p.x, p.y);
  }

  public Point2D.Double getScreenPercentForCellCenter(int cellX, int cellY)
  {
    double x = ((double)cellX + 0.5) / (double)GRID_DIVISIONS;
    double y = ((double)cellY + 0.5) / (double)GRID_DIVISIONS;
    
    return new Point2D.Double(x, y);
  }
  
  public boolean isCellValid(Point cell)
  {
    return isCellValid(cell.x, cell.y);
  }
  
  public boolean isCellValid(int x, int y)
  {
    return x >= 0 && x < GRID_DIVISIONS &&
           y >= 0 && y < GRID_DIVISIONS;
  }
  
  private Dimension getDimensionsForCell()
  {
    int width = getWidth() / GRID_DIVISIONS;
    int height = getHeight() / GRID_DIVISIONS;
    
    return new Dimension(width, height);
  }
  
  public void updatePheremones(boolean hasFood, double xScreenPercent, double yScreenPercent)
  {
    Point cell = getCellForScreenPercent(xScreenPercent, yScreenPercent);
    
    if(isCellValid(cell))
    {
      double increase;
      
      if(hasFood)
      {
        increase = PHEREMONE_PER_MOVEMENT_WITH_FOOD;
      }
      else
      {
        increase = PHEREMONE_PER_MOVEMENT;
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
    for(Ant ant : ants)
    {
      ant.kill();
    }
  }
}
