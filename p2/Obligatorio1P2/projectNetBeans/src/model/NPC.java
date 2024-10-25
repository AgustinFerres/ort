package model;

import types.Position;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public class NPC extends Player {

  public NPC() {
    super("Computer", "NPC", 0);
  }

  public Position getMove(Board currentBoard) {
    Random random = new Random();

    if (currentBoard == null) {
      throw new IllegalArgumentException("Invalid board");
    }

    Set<Position> availableMoves = currentBoard.getAvailableMoves();

    if (availableMoves.isEmpty()) {
      throw new IllegalArgumentException("No available moves");
    }

    return List.copyOf(availableMoves).get(random.nextInt(availableMoves.size()));
  }
}
