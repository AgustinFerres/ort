package types;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public enum CellValue {
  BLANK,
  X,
  O;

  @Override
  public String toString() {
    return switch (this) {
      case X -> "X";
      case O -> "O";
      default -> " ";
    };
  }
}
