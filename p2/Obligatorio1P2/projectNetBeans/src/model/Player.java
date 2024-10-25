package model;

import types.CellValue;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public class Player {

  private final String name;
  private String alias;
  private CellValue type;
  private int age;
  private int score;
  private int magicPlay;

  /**
   * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
   */
  public Player(String name, String alias, int age) {
    this.name = name;
    this.alias = alias;
    this.age = age;
    this.score = 0;
    this.magicPlay = 1;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void increaseScore() {
    score++;
  }

  public void setType(CellValue type) {
    this.type = type;
  }

  public String getAlias() {
    return alias;
  }

  public int getMagicPlay() {
    return magicPlay;
  }

  public void setMagicPlay(int cant) {
    this.magicPlay = cant;
  }

  public CellValue getType() {
    return type;
  }

  @Override
  public String toString() {
    return alias;
  }
}
