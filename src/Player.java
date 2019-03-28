public class Player {

    private String playerName;
    private Store store;
    private Pocket[] pockets;

    public Player(String playerName, Store store, Pocket[] pockets) {
        this.playerName = playerName;
        this.store = store;
        this.pockets = pockets;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Pocket[] getPockets() {
        return pockets;
    }

    public void setPockets(Pocket[] pockets) {
        this.pockets = pockets;
    }




}
