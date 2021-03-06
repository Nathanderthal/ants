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
  private double x;
  private double y;
  private double lastAngle;
  
  private final Stack<Point2D.Double> pathFromHome;

  private boolean go = true;
  
  private AntState antState = AntState.PUPATING;
  
  private int stepsUntilExit;
  
  private final AntSimulatorPanel antSimulatorPanel;
  private final SimulationSettings settings;
  
  public Ant(AntSimulatorPanel antSimulatorPanel, SimulationSettings settings, int exitDelaySteps)
  {
    this.antSimulatorPanel = antSimulatorPanel;
    this.settings = settings;
    stepsUntilExit = exitDelaySteps;
    
    if(stepsUntilExit == 0)
    {
      antState = AntState.SCOUTING;
    }
    
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

  public double getLastAngle()
  {
    return lastAngle;
  }
  
  public Stack<Point2D.Double> getPathFromHome()
  {
    return (Stack<Point2D.Double>)pathFromHome.clone();
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
    else if(antState == AntState.PUPATING)
    {
      stepsUntilExit = stepsUntilExit - 1;
      
      if(stepsUntilExit <= 0)
      {
        antState = AntState.SCOUTING;
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
        for(int x = cell.x - 1; x <= cell.x + 1; x++)
        {
          for(int y = cell.y - 1; y <= cell.y + 1; y++)
          {
            if((x != cell.x || y != cell.y) && antSimulatorPanel.isCellValid(x, y))
            {
              double pheremoneValue = antSimulatorPanel.getPheremoneValue(x, y);

              if(pheremoneValue > 0)
              {
                double angleForCell = adjustAngle(getAngleForCell(x, y));
                
//                double angleDelta = lastAngle - angleForCell;
                
                if(angleForCell > lastAngle - settings.getPheremoneTurnRange() / 2 &&
                   angleForCell < lastAngle + settings.getPheremoneTurnRange() / 2)
                {
                  validCells.add(new Point(x, y));
                }
              }
            }
          }
        }
        
        double maxInfluence = 1d;
        Point maxInfluenceCell = null;
        
        for(Point p : validCells)
        {
          double influence = Math.random() + antSimulatorPanel.getPheremoneValue(p.x, p.y);
          
          while(influence == maxInfluence)
          {
            //Make sure equal rolls are re-rolled.
            influence = Math.random() + antSimulatorPanel.getPheremoneValue(p.x, p.y);
          }
          
          if(influence > maxInfluence)
          {
            maxInfluence = influence;
            maxInfluenceCell = p;
          }
        }

        if(maxInfluenceCell != null)
        {
          angle = getAngleForCell(maxInfluenceCell.x, maxInfluenceCell.y);
//          System.out.println("Degrees = " + Math.toDegrees(angle));
        }
        
        if(angle < 0)
        {
          angle = lastAngle + (Math.random() * settings.getTurnRange()) - (settings.getTurnRange() / 2d);
          angle = adjustAngle(angle);
        }
        
//        antSimulatorPanel.updateCellTestDisplay(validCells, maxInfluenceCell);
      }

      if(angle >= 0)
      {
        Point2D.Double lastPoint = new Point2D.Double(x, y);
        
        updateCoordinates(angle);

//        System.out.println("angle   = " + angle);
        
        double dX = x - lastPoint.x;
        double dY = y - lastPoint.y;
        double calculatedAngle = Math.atan2(dY, dX);
        
//        System.out.println("reality = " + adjustAngle(calculatedAngle));
        
        lastAngle = angle;

        antSimulatorPanel.updatePheremones(false, x, y);

        List<Point> foods = antSimulatorPanel.getFoods();

        Point antCell = antSimulatorPanel.getCellForScreenPercent(x, y);

        for(Point food : foods)
        {
          if(food.equals(antCell))
          {
            antState = AntState.RETURNING_WITH_FOOD;
            antSimulatorPanel.updatePheremones(true, x, y);
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

    double rads = Math.atan2((screenPercentCell.y - y) , (screenPercentCell.x - x));

    rads = adjustAngle(rads);

    return rads;
  }
  
  private void updateCoordinates(double angle)
  {
    setCoordinates(x + settings.getSpeed() * Math.cos(angle),
                   y + settings.getSpeed() * Math.sin(angle));
  }
  
  private void setCoordinates(double x, double y)
  {
    this.x = x;
    this.y = y;
    
    if(!antState.isReturning())
    {
      pathFromHome.push(new Point2D.Double(x, y));
      
      if(pathFromHome.size() >= settings.getPathMax())
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
        sleep(settings.getUpdateDelay());
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
