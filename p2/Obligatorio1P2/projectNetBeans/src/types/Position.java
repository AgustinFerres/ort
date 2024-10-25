package types;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public enum Position {
  A1(0, 0, "A1"),
  A2(0, 1, "A2"),
  A3(0, 2, "A3"),
  B1(1, 0, "B1"),
  B2(1, 1, "B2"),
  B3(1, 2, "B3"),
  C1(2, 0, "C1"),
  C2(2, 1, "C2"),
  C3(2, 2, "C3");

  private final int x;
  private final int y;
  private final String name;

  Position(int x, int y, String name) {
    this.x = x;
    this.y = y;
    this.name = name;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public String getName() {
    return name;
  }

  public static Position getPosition(String name) {
    for (Position pos : Position.values()) {
      if (pos.getName().equals(name)) {
        return pos;
      }
    }
    return null;
  }

  public static Position getPosition(int x, int y) {
    for (Position pos : Position.values()) {
      if (pos.getX() == x && pos.getY() == y) {
        return pos;
      }
    }
    return null;
  }
}
