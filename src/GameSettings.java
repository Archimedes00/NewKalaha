public class GameSettings {

    static final int POCKETS = 6;
    static final int PIECES_IN_POCKETS = 4;
    static final int searchDepth = 1000;
    static Player[] players;
    static Player[] bufferPlayers;


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

    public void initializeStateBuffer() {
        Pocket[] pockets3 = new Pocket[POCKETS];

        for (int i = 0; i < POCKETS; i++) {
            pockets3[i] = new Pocket(PIECES_IN_POCKETS);
        }

        Pocket[] pockets4 = new Pocket[POCKETS];

        for (int i = 0; i < POCKETS; i++) {
            pockets4[i] = new Pocket(PIECES_IN_POCKETS);
        }

        Player humanPlayer = new Player("humanBuffer", new Store(0), pockets3);
        Player aiPlayer = new Player("AIBuffer", new Store(0), pockets4);

        bufferPlayers = new Player[2];
        bufferPlayers[0] = humanPlayer;
        bufferPlayers[1] = aiPlayer;

    }

}
