public class Main {

    public static void main(String[] args) {
        GameSettings gameSettings = new GameSettings();
        gameSettings.initializeState();

        GameController gameController = new GameController(GameSettings.players);
        gameController.startGame();
    }
}