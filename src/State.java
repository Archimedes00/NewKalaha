public class State {

    private Player humanPlayer;
    private Player AIPlayer;
    private boolean humanPlayerTurn;
    private int[] stateArray;

    public State() {

    }

    public State(Player humanPlayer, Player AIPlayer) {
        this.humanPlayer = humanPlayer;
        this.AIPlayer = AIPlayer;
        stateArray = new int[GameSettings.POCKETS * 2 + 2];
    }

    public boolean isHumanPlayerTurn() {
        return humanPlayerTurn;
    }

    public void setHumanPlayerTurn(boolean humanPlayerTurn) {
        this.humanPlayerTurn = humanPlayerTurn;
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public void setHumanPlayer(Player humanPlayer) {
        this.humanPlayer = humanPlayer;
    }

    public Player getAIPlayer() {
        return AIPlayer;
    }

    public void setAIPlayer(Player AIPlayer) {
        this.AIPlayer = AIPlayer;
    }


    public void setStateArray(int[] stateArray) {

        for (int i = 0; i < GameSettings.POCKETS; i++) {
            getHumanPlayer().getPockets()[i].setPieces(stateArray[i]);
        }
        getHumanPlayer().getStore().setPieces(stateArray[GameSettings.POCKETS]);
//        state.getHumanPlayer().getStore().setPieces(999);

        for (int i = 7; i <= GameSettings.POCKETS * 2; i++) {
           getAIPlayer().getPockets()[i - GameSettings.POCKETS - 1].setPieces(stateArray[i]);
        }
        getAIPlayer().getStore().setPieces(stateArray[GameSettings.POCKETS * 2 + 1]);
    }

    public int[] getStateArray() {
//        stateArray = new int[GameSettings.POCKETS * 2 + 2];
        for (int i = 0; i < GameSettings.POCKETS; i++) {
            stateArray[i] = getHumanPlayer().getPockets()[i].getPieces();
        }
        stateArray[GameSettings.POCKETS] = getHumanPlayer().getStore().getPieces();

        for (int i = GameSettings.POCKETS + 1; i <= GameSettings.POCKETS * 2; i++) {
            stateArray[i] = getAIPlayer().getPockets()[i - GameSettings.POCKETS - 1].getPieces();
        }
        stateArray[GameSettings.POCKETS * 2 + 1] = getAIPlayer().getStore().getPieces();

        return stateArray;
    }
}
