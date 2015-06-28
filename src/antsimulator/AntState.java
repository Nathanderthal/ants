/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package antsimulator;

/**
 *
 * @author nbible
 */
public enum AntState
{
  PUPATING(false),
  SCOUTING(false),
  RETURNING_WITH_FOOD(true),
  RETURNING_WITHOUT_FOOD(true);
  
  private final boolean returning;

  private AntState(boolean returning)
  {
    this.returning = returning;
  }

  public boolean isReturning()
  {
    return returning;
  }
}
