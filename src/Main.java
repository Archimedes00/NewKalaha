public class Main {

    public static void main(String[] args) {
        GameSettings gameSettings = new GameSettings();
        gameSettings.initializeState();
        gameSettings.initializeStateBuffer();

        GameController gameController = new GameController(GameSettings.players);

        gameController.startGame();
    }
}