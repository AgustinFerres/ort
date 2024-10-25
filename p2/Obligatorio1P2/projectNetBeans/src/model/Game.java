package model;

import types.CellValue;
import types.MenuOptions;
import types.Position;
import utils.DisplayUtils;

import java.util.*;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public class Game {

  private final Board[][] board = new Board[3][3];
  private final List<Player> players = new ArrayList<>();

  public Game() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = new Board();
      }
    }
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public Player getPlayer(String name, Player selectedPlayer) {
    for (Player player : players.stream().filter(player -> !player.equals(selectedPlayer)).toList()) {
      if (player.getAlias().equals(name)) {
        return player;
      }
    }

    throw new IllegalArgumentException("Player not found");
  }

  public Board getBoard(Position pos, List<Board> availableBoards) {
    if (pos == null) {
      throw new IllegalArgumentException("Invalid position");
    }

    Board board = this.board[pos.getX()][pos.getY()];

    if (availableBoards.contains(board)) {
      return board;
    }

    return availableBoards.stream().findAny().orElseThrow(() -> new IllegalArgumentException("No available boards"));
  }

  private Player selectPlayer(Scanner scanner, Player selectedPlayer) {
    if (selectedPlayer != null) {
      System.out.println(Arrays.toString(players.stream().filter(player -> !player.equals(selectedPlayer)).toArray()));
    } else {
      System.out.println(Arrays.toString(players.toArray()));
    }

    return getPlayer(scanner.nextLine(), selectedPlayer);
  }

  /**
   * Starts the main game loop.
   * This method displays the welcome message, shows the menu, and handles user input to navigate through different game options.
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);

    // Display welcome message
    System.out.println("Bienvenidos");

    // Pause for 2 seconds
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    // Clear the screen
    DisplayUtils.clearScreen();

    // Main game loop
    while (true) {
      // Show the menu
      DisplayUtils.showMenu();

      MenuOptions option = null;

      // Read user input until a valid option is selected
      while (option == null) {
        try {
          String input = scanner.nextLine();
          option = MenuOptions.getOption(Integer.parseInt(input));
        } catch (NumberFormatException e) {
          System.out.println("Invalid input");
        }
      }

      // Handle the selected menu option
      switch (option) {
      case REGISTER_PLAYER:
        registerPlayer();
        break;
      case PLAY_GAME:
        startGame();
        break;
      case PLAY_GAME_WITH_COMPUTER:
        startGameVsComputer();
        break;
      case SHOW_RANKING:
        DisplayUtils.showRanking(players.stream().sorted(Comparator.comparingInt(Player::getScore).reversed()).toList());
        break;
      case EXIT:
        System.exit(0);
        break;
      }
    }
  }

  /**
   * Starts a game between two players.
   * This method initializes the game, selects the players, and handles the game loop.
   */
  private void startGame() {
    Scanner scanner = new Scanner(System.in);

    // Reset the magic play for all players
    players.forEach(player -> player.setMagicPlay(1));
    // Get a list of available boards
    List<Board> availableBoards = new ArrayList<>(Arrays.stream(board).map(Arrays::asList).flatMap(List::stream).toList());

    // Check if there are enough players to start the game
    if (players.size() < 2) {
      System.out.println("Not enough players to start the game");
      return;
    }

    // Select player 1
    System.out.println("Select player 1:");
    Player player1 = selectPlayer(scanner, null);
    player1.setType(CellValue.X);

    // Select player 2
    System.out.println("Select player 2:");
    Player player2 = selectPlayer(scanner, player1);
    player2.setType(CellValue.O);

    // Initialize the current player, board, and position
    Player currentPlayer = player1;
    Board currentBoard = null;
    Position currentPos = null;

    // Flag to check if it's the first move
    boolean firstMove = true;

    // Start the game loop
    while (true) {
      System.out.println(currentPlayer.getName() + " turn");

      try {
        // Get the move from the player
        System.out.println("Enter move:");
        String move = scanner.nextLine();

        if (firstMove) { // If it's the first move, get the board and position
          String[] parts = move.split(", ");
          currentBoard = getBoard(Position.getPosition(parts[0]), availableBoards);
          currentPos = Position.getPosition(parts[1]);
          firstMove = false;
        } else { // If it's not the first move
          if (move.equals("X")){
            break;
          }

          if (move.equals("M")) {
            currentBoard.useMagicPlay(currentPlayer);

            System.out.println("Enter move:");
            move = scanner.nextLine();
          }

          currentPos = Position.getPosition(move);
        }

        // Play the move, if the player wins, break the loop
        if (this.playMove(currentBoard, currentPlayer, currentPos, availableBoards)) {
          break;
        }

        // Update the current player and board
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        currentBoard = getBoard(currentPos, availableBoards);
        this.showBoard(currentBoard);

      } catch (Exception e) { // Catch any exceptions and print the message
        System.out.println(e.getMessage());
      }
    }


  }

  /**
   * Starts a game against the computer.
   * This method initializes the game, selects the player, and handles the game loop.
   */
  private void startGameVsComputer () {
    Scanner scanner = new Scanner(System.in);
    // Get a list of available boards
    List<Board> availableBoards = new ArrayList<>(Arrays.stream(board).map(Arrays::asList).flatMap(List::stream).toList());

    // Check if there are enough players to start the game
    if (players.isEmpty()) {
      System.out.println("Not enough players to start the game");
      return;
    }

    // Select player 1
    System.out.println("Select player:");
    Player player1 = selectPlayer(scanner, null);
    player1.setType(CellValue.X);

    // Initialize the computer player
    NPC computer = new NPC();
    computer.setType(CellValue.O);

    // Initialize the current player, board, and position
    Player currentPlayer = player1;
    Board currentBoard = null;
    Position currentPos = null;

    boolean firstMove = true;

    while (true) {
      System.out.println(currentPlayer.getName() + "'s turn");
      try {
        if (currentPlayer.equals(computer)) { // If it's the computer's turn
          currentPos = computer.getMove(currentBoard);
        } else {
          // Get the move from the player
          System.out.println("Enter move:");
          String move = scanner.nextLine();

          if (firstMove) { // If it's the first move, get the board and position
            String[] parts = move.split(", ");
            currentBoard = getBoard(Position.getPosition(parts[0]), availableBoards);
            currentPos = Position.getPosition(parts[1]);
            firstMove = false;
          } else { // If it's not the first move
            if (move.equals("X")){
              break;
            }

            currentPos = Position.getPosition(move);
          }
        }

        // Play the move, if the player wins, break the loop
        if (this.playMove(currentBoard, currentPlayer, currentPos, availableBoards)) {
          break;
        }

        // Update the current player and board
        currentPlayer = currentPlayer.equals(player1) ? computer : player1;
        currentBoard = getBoard(currentPos, availableBoards);
        this.showBoard(currentBoard);

      } catch (Exception e) { // Catch any exceptions and print the message
        System.out.println(e.getMessage());
      }
    }

  }

  /**
   * Plays a move on the given board for the specified player at the given position.
   *
   * @param board The board on which the move is to be played.
   * @param player The player making the move.
   * @param pos The position on the board where the move is to be played.
   * @param availableBoards The list of available boards in the game.
   * @return true if the move results in a win or a tie, false otherwise.
   */
  private boolean playMove(Board board, Player player, Position pos, List<Board> availableBoards) {
    // Check if the arguments are valid
    if (board == null || player == null || pos == null || availableBoards == null) {
      throw new IllegalArgumentException("Invalid argument");
    }

    // Play the move on the board
    board.play(player, pos);

    // Check if the player has won on this board
    if (board.playerWon(player)) {
      board.setWinner(player);
      availableBoards.remove(board);
    }

    // Check if the board is full
    if (board.isFull()) {
      availableBoards.remove(board);
    }

    // Check if the player has won the game
    if (this.playerWon(player)) {
      System.out.println(player.getName() + " wins!");
      this.showBoard(board);
      player.increaseScore();
      return true;
    }

    // Check if the game is a tie
    if (this.isATie()) {
      System.out.println("It's a tie!, no one wins");
      this.showBoard(board);
      return true;
    }

    // Return false if the game is not over
    return false;
  }

  private void showBoard(Board currentBoard) {

    Position currentPos = null;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j].equals(currentBoard)) {
          currentPos = Position.getPosition(i, j);
          break;
        }
      }
    }

    DisplayUtils.showBoard(board, currentPos);
  }

  private void registerPlayer() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter player name:");
    String name = scanner.nextLine();
    System.out.println("Enter player alias:");
    String alias = scanner.nextLine();

    boolean aliasExists = playerExists(alias);

    while (aliasExists) {
      System.out.println("Alias already exists. Enter a different alias:");
      alias = scanner.nextLine();
      aliasExists = playerExists(alias);
    }

    System.out.println("Enter player age:");
    int age = Integer.parseInt(scanner.nextLine());

    this.addPlayer(new Player(name, alias, age));
  }

  private boolean playerExists(String alias) {
    return this.players.stream().anyMatch(player -> player.getAlias().equals(alias));
  }

  private boolean playerWon(Player player) {
    // Check horizontal and vertical lines
    for (int i = 0; i < 3; i++) {
      if (player.equals(board[i][0].getWinner()) && player.equals(board[i][1].getWinner()) && player.equals(board[i][2].getWinner()) ||
          player.equals(board[0][i].getWinner()) && player.equals(board[1][i].getWinner()) && player.equals(board[2][i].getWinner())) {
        return true;
      }
    }

    // Check diagonal lines
    return player.equals(board[0][0].getWinner()) && player.equals(board[1][1].getWinner()) && player.equals(board[2][2].getWinner()) ||
        player.equals(board[0][2].getWinner()) && player.equals(board[1][1].getWinner()) && player.equals(board[2][0].getWinner());
  }

  private boolean isATie() {
    List<Board> boards = Arrays.stream(board).map(Arrays::asList).flatMap(List::stream).toList();

    return boards.stream().allMatch(Board::isFull);
  }
}

