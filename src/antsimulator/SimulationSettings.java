/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antsimulator;

/**
 *
 * @author Nathan
 */
public class SimulationSettings
{
  private int antCount = 2000;
  private int foodCount = 30;
  private int gridDivisions = 100;
  private double pheremonePerMovement = 0; //0.00005;
  private double pheremonePerMovementWithFood = 0.02;
  private double pheremoneDecayRate = 0.01;
  private double speed = 0.001;
  private int updateDelay = 25;
  private double turnRange = Math.PI / 4d;
  private double pheremoneTurnRange = Math.PI;
  private int pathMax = 1000;
  private int staggerLeavingHome = 0;

  public int getAntCount()
  {
    return antCount;
  }

  public void setAntCount(int antCount)
  {
    this.antCount = antCount;
  }

  public int getFoodCount()
  {
    return foodCount;
  }

  public void setFoodCount(int foodCount)
  {
    this.foodCount = foodCount;
  }

  public int getGridDivisions()
  {
    return gridDivisions;
  }

  public void setGridDivisions(int gridDivisions)
  {
    this.gridDivisions = gridDivisions;
  }

  public double getPheremonePerMovement()
  {
    return pheremonePerMovement;
  }

  public void setPheremonePerMovement(double pheremonePerMovement)
  {
    this.pheremonePerMovement = pheremonePerMovement;
  }

  public double getPheremonePerMovementWithFood()
  {
    return pheremonePerMovementWithFood;
  }

  public void setPheremonePerMovementWithFood(double pheremonePerMovementWithFood)
  {
    this.pheremonePerMovementWithFood = pheremonePerMovementWithFood;
  }

  public double getPheremoneDecayRate()
  {
    return pheremoneDecayRate;
  }

  public void setPheremoneDecayRate(double pheremoneDecayRate)
  {
    this.pheremoneDecayRate = pheremoneDecayRate;
  }

  public double getSpeed()
  {
    return speed;
  }

  public void setSpeed(double speed)
  {
    this.speed = speed;
  }

  public int getUpdateDelay()
  {
    return updateDelay;
  }

  public void setUpdateDelay(int updateDelay)
  {
    this.updateDelay = updateDelay;
  }

  public double getTurnRange()
  {
    return turnRange;
  }

  public void setTurnRange(double turnRange)
  {
    this.turnRange = turnRange;
  }

  public double getPheremoneTurnRange()
  {
    return pheremoneTurnRange;
  }

  public void setPheremoneTurnRange(double pheremoneTurnRange)
  {
    this.pheremoneTurnRange = pheremoneTurnRange;
  }

  public int getPathMax()
  {
    return pathMax;
  }

  public void setPathMax(int pathMax)
  {
    this.pathMax = pathMax;
  }

  public int getStaggerLeavingHome()
  {
    return staggerLeavingHome;
  }

  public void setStaggerLeavingHome(int staggerLeavingHome)
  {
    this.staggerLeavingHome = staggerLeavingHome;
  }
}
