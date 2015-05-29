/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package antsimulator;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author nbible
 */
public class Ant extends Thread
{
  private static final double DEFAULT_SPEED = 0.001;
  private static final int DEFAULT_UPDATE_DELAY = 25;
  private static final double TURN_RANGE = Math.PI / 4d;
  private static final double PHEROMONE_TURN_RANGE = Math.PI;
  private static final int PATH_MAX = 1000;
  
  private double x;
  private double y;
  private double lastAngle;
  
  private Stack<Point2D.Double> pathFromHome;
  
  private double speed;
  
  private boolean go = true;
  
  private AntState antState = AntState.SCOUTING;
  
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
    pathFromHome = new Stack<>();
    
    initialize();
  }
  
  public final void initialize()
  {
    Point home = antSimulatorPanel.getHome();
    Point2D.Double homePoint = antSimulatorPanel.getScreenPercentForCellCenter(home);
    
    setCoordinates(homePoint.x, homePoint.y);
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  public AntState getAntState()
  {
    return antState;
  }

  private void update()
  {
    if(antState.isReturning())
    {
      if(pathFromHome.isEmpty())
      {
        //This shouldn't happen ever.
        //I guess the ant forgot where it lives.
        //It dies.

        kill();
      }
      else
      {
        Point2D.Double p = pathFromHome.pop();
        setCoordinates(p.x, p.y);
        
        if(pathFromHome.isEmpty())
        {
          antState = AntState.SCOUTING;
        }
        else
        {
          antSimulatorPanel.updatePheremones(antState == AntState.RETURNING_WITH_FOOD, x, y);
        }
      }
    }
    else
    {
      double angle = -1;

      if(lastAngle < 0)
      {
        angle = Math.random() * 2d * Math.PI;
      }
      else
      {
        Point cell = antSimulatorPanel.getCellForScreenPercent(x, y);

        List<Point> validCells = new ArrayList<>();
        
        CellLoop:
        for(int i = cell.x - 1; i <= cell.x + 1; i++)
        {
          for(int j = cell.y - 1; j <= cell.y + 1; j++)
          {
            if(i != cell.x && j != cell.y && antSimulatorPanel.isCellValid(i, j))
            {
              double pheremoneValue = antSimulatorPanel.getPheremoneValue(i, j);

              if(pheremoneValue > 0)
              {
                double angleForCell = getAngleForCell(i, j);
                
                double angleDelta = lastAngle - angleForCell;
                
                if(angleDelta < PHEROMONE_TURN_RANGE / 2)
                {
                  validCells.add(new Point(i, j));
                }
              }
            }
          }
        }
        
        //TODO: sort the validCells to give consistent advantage to higher weighted pheromones
        
        for(Point p : validCells)
        {
          if(Math.random() < antSimulatorPanel.getPheremoneValue(p.x, p.y))
          {
            angle = getAngleForCell(p.x, p.y);
            break;
          }
        }

        if(angle < 0)
        {
          angle = lastAngle + (Math.random() * TURN_RANGE) - (TURN_RANGE / 2d);
          angle = adjustAngle(angle);
        }
      }

      if(angle >= 0)
      {
        setCoordinates(x + Ant.this.speed * Math.cos(angle),
                       y + Ant.this.speed * Math.sin(angle));

        lastAngle = angle;

        antSimulatorPanel.updatePheremones(false, x, y);

        List<Point> foods = antSimulatorPanel.getFoods();

        Point antCell = antSimulatorPanel.getCellForScreenPercent(x, y);

        for(Point food : foods)
        {
          if(food.equals(antCell))
          {
            antState = AntState.RETURNING_WITH_FOOD;
            lastAngle = -1;
            break;
          }
        }
      }
    }
  }
  
  private double adjustAngle(double angle)
  {
    while(angle < 0)
    {
      angle = angle + (2d * Math.PI);
    }

    while(angle >= 2d * Math.PI)
    {
      angle = angle - (2d * Math.PI);
    }
    
    return angle;
  }
  
  private double getAngleForCell(int cellX, int cellY)
  {
    Point2D.Double screenPercentCell = antSimulatorPanel.getScreenPercentForCellCenter(cellX, cellY);

    if(x < screenPercentCell.x)
    {
      return Math.atan((y - screenPercentCell.y) / (x - screenPercentCell.x));
    }
    else if(x > screenPercentCell.x)
    {
      return Math.atan((y - screenPercentCell.y) / (x - screenPercentCell.x)) - Math.PI;
    }
    else if(y > screenPercentCell.y)
    {
      return 3 * (Math.PI / 2);
    }
    else
    {
      return Math.PI / 2;
    }
  }
  
  private void setCoordinates(double x, double y)
  {
    this.x = x;
    this.y = y;
    
    if(!antState.isReturning())
    {
      pathFromHome.push(new Point2D.Double(x, y));
      
      if(pathFromHome.size() >= PATH_MAX)
      {
        antState = AntState.RETURNING_WITHOUT_FOOD;
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
