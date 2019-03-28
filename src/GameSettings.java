public class GameSettings {

    static final int POCKETS = 6;
    static final int PIECES_IN_POCKETS = 4;
    static final int searchDepth = 1000;
    static Player[] players;


    public void initializeState() {

        Pocket[] pockets = new Pocket[POCKETS];

        for (int i = 0; i < POCKETS; i++) {
            pockets[i] = new Pocket(PIECES_IN_POCKETS);
        }

        Pocket[] pockets2 = new Pocket[POCKETS];

        for (int i = 0; i < POCKETS; i++) {
            pockets2[i] = new Pocket(PIECES_IN_POCKETS);
        }

        Player humanPlayer = new Player("human", new Store(0), pockets);
        Player aiPlayer = new Player("AI", new Store(0), pockets2);

        players = new Player[2];
        players[0] = humanPlayer;
        players[1] = aiPlayer;


    }
}
