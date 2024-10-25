package model;

import types.Position;
import types.CellValue;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public class Board {

  private final CellValue[][] board = new CellValue[3][3];
  private Player winner;

  public Board() {
    this.clear();
  }

  public CellValue getPosition(Position pos) {
    return board[pos.getX()][pos.getY()];
  }

  public Set<Position> getAvailableMoves() {
    Set<Position> availableMoves = new HashSet<>();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (CellValue.BLANK.equals(board[i][j])) {
          availableMoves.add(Position.getPosition(i, j));
        }
      }
    }

    return availableMoves;
  }

  private void setPosition(Position pos, CellValue cellValue) {
    if (pos == null) {
      throw new IllegalArgumentException("Invalid position");
    }

    if (cellValue == CellValue.BLANK) {
      throw new IllegalArgumentException("Invalid cell value");
    }

    if (board[pos.getX()][pos.getY()] != CellValue.BLANK) {
      throw new IllegalArgumentException("Position already taken");
    }

    board[pos.getX()][pos.getY()] = cellValue;
  }

  public boolean playerWon(Player player) {
    CellValue type = player.getType();

    // Check horizontal and vertical lines
    for (int i = 0; i < 3; i++) {
      if ((board[i][0] == type && board[i][1] == type && board[i][2] == type) ||
          (board[0][i] == type && board[1][i] == type && board[2][i] == type)) {
        return true;
      }
    }

    // Check diagonal lines
    return (board[0][0] == type && board[1][1] == type && board[2][2] == type) ||
        (board[0][2] == type && board[1][1] == type && board[2][0] == type);
  }

  public boolean isFull() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == CellValue.BLANK) {
          return false;
        }
      }
    }
    return true;
  }

  public void useMagicPlay(Player player) {
    if (player.getMagicPlay() == 0) {
      throw new IllegalArgumentException("No magic plays left");
    }

    player.setMagicPlay(0);
    this.clear();
  }

  public void play(Player player, Position pos) {
    setPosition(pos, player.getType());
  }

  public void setWinner(Player player) {
    this.winner = player;
  }

  public Player getWinner() {
    return this.winner;
  }

  private void clear() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = CellValue.BLANK;
      }
    }
  }

}
