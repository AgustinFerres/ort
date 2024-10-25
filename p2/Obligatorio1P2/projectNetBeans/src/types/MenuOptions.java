package types;

/**
 * @author Agustin Ferres, 323408; Facundo San Andrea, 258053
 */
public enum MenuOptions {
  REGISTER_PLAYER(1, "Registrar un jugador"),
  PLAY_GAME(2, "Jugar una partida"),
  PLAY_GAME_WITH_COMPUTER(3, "Jugar una partida contra la computadora"),
  SHOW_RANKING(4, "Ver el ranking de jugadores"),
  EXIT(5, "Salir");

  private final int order;
  private final String description;

  MenuOptions(int order, String description) {
    this.order = order;
    this.description = description;
  }

  public static MenuOptions getOption(int option) {
    for (MenuOptions menuOption : MenuOptions.values()) {
      if (menuOption.order == option) {
        return menuOption;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return order + ". " + description;
  }
}
