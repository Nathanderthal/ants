/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package antsimulator;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

/**
 *
 * @author nbible
 */
public class Ant extends Thread
{
  private static final double DEFAULT_SPEED = 0.001;
  private static final int DEFAULT_UPDATE_DELAY = 25;
  private static final double TURN_RANGE = Math.PI / 4d;
  
  private double x;
  private double y;
  private double lastAngle;
  
  private double speed;
  
  private boolean go = true;
  
  private boolean returningWithFood = false;
  
  private AntSimulatorPanel antSimulatorPanel;
  
  public Ant(AntSimulatorPanel panel)
  {
    this(panel, DEFAULT_SPEED);
  }
  
  public Ant(AntSimulatorPanel antSimulatorPanel, double speed)
  {
    this.antSimulatorPanel = antSimulatorPanel;
    this.speed = speed;
    
    x = 0;
    y = 0;
    lastAngle = -1;
    
    initialize();
  }
  
  public final void initialize()
  {
    Point home = antSimulatorPanel.getHome();
    Point2D.Double homePoint = antSimulatorPanel.getScreenPercentForCellCenter(home);
    
    x = homePoint.x;
    y = homePoint.y;
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  public boolean isReturningWithFood()
  {
    return returningWithFood;
  }

  private void update()
  {
    double angle;
    
    if(lastAngle < 0)
    {
      angle = Math.random() * 2d * Math.PI;
    }
    else
    {
      if(returningWithFood)
      {
        Point2D.Double screenPercentHome = antSimulatorPanel.getScreenPercentForCellCenter(antSimulatorPanel.getHome());
        
        if(x < screenPercentHome.x)
        {
          angle = Math.atan((y - screenPercentHome.y) / (x - screenPercentHome.x));
        }
        else if(x > screenPercentHome.x)
        {
          angle = Math.atan((y - screenPercentHome.y) / (x - screenPercentHome.x)) - Math.PI;
        }
        else
        {
          angle = -1;
        }
      }
      else
      {
        Point cell = antSimulatorPanel.getCellForScreenPercent(x, y);
        
        for(int i = cell.x - 1; i <= cell.x + 1; i++)
        {
          for(int j = cell.y - 1; j <= cell.y + 1; j++)
          {
            if(i != cell.x && j != cell.y && antSimulatorPanel.isCellValid(i, j))
            {
              double pheremoneValue = antSimulatorPanel.getPheremoneValue(i, j);
              
              if(pheremoneValue > 0)
              {
                //Here the ants need to have their trajectory affected by
                //the pheremone in some way.
              }
            }
          }
        }
        
        angle = lastAngle + (Math.random() * TURN_RANGE) - (TURN_RANGE / 2d);
      }
      
      while(angle < 0)
      {
        angle = angle + (2d * Math.PI);
      }

      while(angle >= 2d * Math.PI)
      {
        angle = angle - (2d * Math.PI);
      }
    }
    
    if(angle >= 0)
    {
      x = x + Ant.this.speed * Math.cos(angle);
      y = y + Ant.this.speed * Math.sin(angle);

      lastAngle = angle;

      antSimulatorPanel.updatePheremones(returningWithFood, x, y);

      if(returningWithFood)
      {
        Point antCell = antSimulatorPanel.getCellForScreenPercent(x, y);

        if(antSimulatorPanel.getHome().equals(antCell))
        {
          returningWithFood = false;
        }
      }
      else
      {
        List<Point> foods = antSimulatorPanel.getFoods();

        Point antCell = antSimulatorPanel.getCellForScreenPercent(x, y);

        for(Point food : foods)
        {
          if(food.equals(antCell))
          {
            returningWithFood = true;
            break;
          }
        }
      }
    }
  }
  
  @Override
  public void run()
  {
    initialize();
    
    while(go)
    {
      update();
      
      try
      {
        sleep(DEFAULT_UPDATE_DELAY);
      }
      catch(InterruptedException ex)
      {
        break;
      }
    }
  }
  
  public void kill()
  {
    go = false;
  }
}
