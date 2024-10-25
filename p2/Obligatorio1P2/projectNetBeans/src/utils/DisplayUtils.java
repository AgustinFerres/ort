package utils;

import model.Board;
import model.Player;
import types.CellValue;
import types.MenuOptions;
import types.Position;

import java.util.List;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public class DisplayUtils {

  private static final String RESET = "\u001B[0m";
  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String YELLOW = "\u001B[33m";
  private static final String BLUE = "\u001B[34m";

  /**
   * Displays the board with the current position highlighted.
   *
   * @param board a 2D array representing the board
   * @param currentPos the current position on the board
   */
  public static void showBoard(Board[][] board, Position currentPos) {
    StringBuilder sb = new StringBuilder();

    // Append the outer top border
    sb.append(generateHzBorder(currentPos, 0, true));

    for (int i = 0; i < 3; i++) {
      for (int x = 0; x < 3; x++) {
        // Append the start of the row
        sb.append(generateVtBorder(currentPos, i, 0, true));
        for (int j = 0; j < 3; j++) {
          Board currentBoard = board[i][j];
          for (int y = 0; y < 3; y++) {
            // Get and append the value of the current cell
            sb.append(generateCellValue(currentBoard, Position.getPosition(x, y)))
                .append(y < 2 ? " | " : " ");
          }
          // Append the right border of a single 3x3 board
          sb.append(generateVtBorder(currentPos, i, j, false));
        }
        // Append the horizontal border between rows or end of the last row
        sb.append(x < 2 ? generateHzRowBorder(currentPos, i) : "\n");
      }
      // Append the bottom border between boards
      sb.append(generateHzBorder(currentPos, i, false));
    }

    // Print the final board display
    System.out.println(sb.toString());
  }


  /**
   * Generates a horizontal border string for the board display.
   *
   * @param currentPos the current position on the board
   * @param i the index of the row or column being processed
   * @param topBorder a boolean indicating if the border is the top border
   * @return a string representing the horizontal border
   */
  private static String generateHzBorder(Position currentPos, int i, boolean topBorder) {
    StringBuilder sb = new StringBuilder();

    // Array to keep track of selected positions
    boolean[] selected = new boolean[3];
    for (int j = 0; j < 3; j++) {
      // Determine if the current position is selected based on the border type
      selected[j] = topBorder ? currentPos.getX() == i && currentPos.getY() == j
          : (currentPos.getX() == i || currentPos.getX() - 1 == i) && currentPos.getY() == j;
    }

    // Build the border string based on the selected positions
    sb.append(selected[0] ? YELLOW : GREEN).append("************")
        .append(selected[0] || selected[1] ? YELLOW : GREEN).append("*")
        .append(selected[1] ? YELLOW : GREEN).append("***********")
        .append(selected[1] || selected[2] ? YELLOW : GREEN).append("*")
        .append(selected[2] ? YELLOW : GREEN).append("************\n")
        .append(RESET);

    return sb.toString();
  }

  /**
   * Generates a horizontal border string for the row display within the board.
   *
   * @param currentPos the current position on the board
   * @param i the index of the row being processed
   * @return a string representing the horizontal border between rows
   */
  private static String generateHzRowBorder(Position currentPos, int i) {
    StringBuilder sb = new StringBuilder();

    // Array to keep track of selected positions
    boolean[] selected = new boolean[3];
    for (int j = 0; j < 3; j++) {
      // Determine if the current position is selected based on the border type
      selected[j] = currentPos.getX() == i && currentPos.getY() == j;
    }

    // Build the border string based on the selected positions
    sb.append(selected[0] ? YELLOW : GREEN) // Horizontal border between rows
        .append("\n*").append(RESET)
        .append("---+---+---").append(selected[0] || selected[1] ? YELLOW : GREEN)
        .append("*").append(RESET)
        .append("---+---+---").append(selected[1] || selected[2] ? YELLOW : GREEN)
        .append("*").append(RESET)
        .append("---+---+---").append(selected[2] ? YELLOW : GREEN)
        .append("*\n").append(RESET);

    return sb.toString();
  }

  /**
   * Generates a vertical border string for the board display.
   *
   * @param currentPos the current position on the board
   * @param i the index of the row being processed
   * @param j the index of the column being processed
   * @param leftBorder a boolean indicating if the border is the left border
   * @return a string representing the vertical border
   */
  private static String generateVtBorder(Position currentPos, int i, int j, boolean leftBorder) {
    StringBuilder sb = new StringBuilder();

    boolean selected;

    if (leftBorder) {
      selected = currentPos.getX() == i && currentPos.getY() == 0;
    } else {
      selected = currentPos.getX() == i && (currentPos.getY() == j || currentPos.getY() - 1 == j);
    }

    sb.append(selected ? YELLOW : GREEN).append("* ").append(RESET);

    return sb.toString();
  }

  /**
   * Generates the string representation of a cell value on the board.
   *
   * @param board the board containing the cell
   * @param pos the position of the cell on the board
   * @return a string representing the cell value, colored based on its content
   */
  private static String generateCellValue(Board board, Position pos) {
    StringBuilder sb = new StringBuilder();

    // Get the cell value at the specified position
    CellValue type = board.getPosition(pos);

    // Determine the color based on the cell value or if there is a winner
    String color = board.getWinner() != null ? getTypeColor(board.getWinner().getType()) : getTypeColor(type);

    // Append the colored cell value to the StringBuilder
    sb.append(color).append(type).append(RESET);

    return sb.toString();
  }

  private static String getTypeColor(CellValue type) {
    return switch (type) {
      case X -> RED;
      case O -> BLUE;
      default -> RESET;
    };
  }

  public static void showMenu() {
    StringBuilder sb = new StringBuilder();

    sb.append(generateDelimiter());

    for (MenuOptions option : MenuOptions.values()) {
      sb.append(BLUE).append(option).append("\n");
    }

    sb.append(generateDelimiter());

    System.out.println(sb.toString());
  }

  /**
   * Displays the ranking of players.
   *
   * @param players a list of Player objects representing the players and their scores
   */
  public static void showRanking(List<Player> players) {
    StringBuilder sb = new StringBuilder();


    int longestAlias = players.stream()
        .map(Player::getAlias)
        .mapToInt(String::length)
        .max()
        .orElse(0);

    // Append the top delimiter
    sb.append(generateDelimiter());

    // Append each player's alias and score
    for (Player player : players) {
      sb.append(BLUE)
          .append(player.getAlias())
          .append(" ".repeat(longestAlias - player.getAlias().length()))
          .append(" | ")
          .append("#".repeat(Math.max(0, player.getScore())))
          .append("\n");
    }

    // Append the bottom delimiter
    sb.append(generateDelimiter());

    // Print the final ranking display
    System.out.println(sb.toString());
  }

  private static String generateDelimiter() {
    return GREEN + "-".repeat(50) + RESET + "\n";
  }

  /**
   * Clears the console screen.
   * Only works on Unix-based systems.
   */
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

}
