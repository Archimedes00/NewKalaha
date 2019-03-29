import java.util.Scanner;

public class GameController {

    private State state;
    private AI ai;

    public GameController(Player[] players) {
        this.ai = new AI(GameSettings.searchDepth);
        state = new State(players[0], players[1]);
    }

    public void startGame() {
        choosePlayerOne();
//        boardState = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
//        state.setStateArray(boardState);
        drawBoard(state);

        while (!isGameOver(state)) {

            if (state.isHumanPlayerTurn()) {

                System.out.println("\nIt is your turn :)");
                playTurn(chooseAction());
                state.setHumanPlayerTurn(false);
            } else {

                int action = ai.minimax(state);
                playAITurn(action);
                state.setHumanPlayerTurn(true);
                System.out.println("\nAI played: B" + (action + 1));
            }
            drawBoard(state);
        }

        calculateFinalScore(state);
    }

    private void aiPlayed(int action) {

        System.out.println("\n AI played: B" + action + 1);
    }

    private int chooseAction() {
        Scanner input = new Scanner(System.in);


        while (true) {

            String pocketBuffer = input.next();


            if (!pocketBuffer.equals("A1") && !pocketBuffer.equals("A2") && !pocketBuffer.equals("A3") && !pocketBuffer.equals("A4") && !pocketBuffer.equals("A5") && !pocketBuffer.equals("A6")) {
                System.out.println("Couldn't read input. Choose A1, A2, A3, A4, A5 or A6");
                System.out.println("Which pocket would you like to empty?");
            } else if (pocketBuffer.equals("A1")) {

                System.out.println("You chose A1");
                if (state.getHumanPlayer().getPockets()[0].getPieces() != 0) {
                    return 0;
                }
                System.out.println("Your chosen pocket is empty. Choose again");

            } else if (pocketBuffer.equals("A2")) {

                System.out.println("You chose A2");
                if (state.getHumanPlayer().getPockets()[1].getPieces() != 0) {
                    return 1;
                }
                System.out.println("Your chosen pocket is empty. Choose again");
            } else if (pocketBuffer.equals("A3")) {

                System.out.println("You chose A3");
                if (state.getHumanPlayer().getPockets()[2].getPieces() != 0) {
                    return 2;
                }
                System.out.println("Your chosen pocket is empty. Choose again");
            } else if (pocketBuffer.equals("A4")) {

                System.out.println("You chose A4");
                if (state.getHumanPlayer().getPockets()[3].getPieces() != 0) {
                    return 3;
                }
                System.out.println("Your chosen pocket is empty. Choose again");
            } else if (pocketBuffer.equals("A5")) {

                System.out.println("You chose A5");
                if (state.getHumanPlayer().getPockets()[4].getPieces() != 0) {
                    return 4;
                }
                System.out.println("Your chosen pocket is empty. Choose again");
            } else if (pocketBuffer.equals("A6")) {

                System.out.println("You chose A6");
                if (state.getHumanPlayer().getPockets()[5].getPieces() != 0) {
                    return 5;
                }
                System.out.println("Your chosen pocket is empty. Choose again");
            } else {
                System.out.println("Something went wrong. Try again");
                System.out.println("Which pocket would you like to empty?");
            }
        }
    }

    private void playTurn(int action) {


        int pocketBuffer = state.getHumanPlayer().getPockets()[action].getPieces();
        state.getHumanPlayer().getPockets()[action].setPieces(0);

        int[] tempStateArray = state.getStateArray();

        for (int i = 1; i < pocketBuffer + 1; i++) {

            tempStateArray[(action + i) % 13] += 1;

            if (i == pocketBuffer && tempStateArray[(action + i) % 13] == 1 && (action + i) % 13 < 6) {

                int pointBuffer = 0;
                pointBuffer = tempStateArray[(action + i) % 13] + tempStateArray[12 - (action + i) % 13];
                tempStateArray[(action + i) % 13] = 0;
                tempStateArray[12 - (action + i) % 13] = 0;
                tempStateArray[6] += pointBuffer;
            }
        }

        state.setStateArray(tempStateArray);
        if ((action + pocketBuffer) % 13 == 6) {
            System.out.println("You succesfully put your last piece in your store. You get another turn!");
            drawBoard(state);

            playTurn(chooseAction());
        }
    }

    private void playAITurn(int action) {


        int pocketBuffer = state.getAIPlayer().getPockets()[action].getPieces();
        state.getAIPlayer().getPockets()[action].setPieces(0);

        int[] tempStateArray = state.getStateArray();

        for (int i = 1; i < pocketBuffer + 1; i++) {

            if ((action + i + GameSettings.POCKETS + 1) % 14 == 6) {
                pocketBuffer++;
            } else {
                tempStateArray[(action + i + GameSettings.POCKETS + 1) % 14] += 1;
            }

            if (i == pocketBuffer && tempStateArray[(action + i + GameSettings.POCKETS + 1) % 13] == 1 && (action + i + GameSettings.POCKETS + 1) % 13 > 6) {
                int pointBuffer = 0;
                pointBuffer = tempStateArray[(action + i + GameSettings.POCKETS + 1) % 13] + tempStateArray[12 - (action + i + GameSettings.POCKETS + 1) % 13];
                tempStateArray[(action + i + GameSettings.POCKETS + 1) % 13] = 0;
                tempStateArray[12 - (action + i + GameSettings.POCKETS + 1) % 13] = 0;
                tempStateArray[13] += pointBuffer;
            }
        }

        state.setStateArray(tempStateArray);
        if ((action + pocketBuffer + GameSettings.POCKETS + 1) % 14 == 13) {
            playAITurn(ai.minimax(state));
        }

    }


    private void drawBoard(State state) {

        String[] Anumbers;
        String[] Bnumbers;
        Anumbers = new String[]{"A1", "A2", "A3", "A4", "A5", "A6", "|Bank A|"};
        Bnumbers = new String[]{"|Bank B|", "B6", "B5", "B4", "B3", "B2", "B1  |------|"};

        System.out.println();
        for (int i = 0; i < 7; i++) {
            System.out.print(Bnumbers[i] + "  ");
        }
        System.out.println();
        System.out.println("|      |                          |      |");
        System.out.print("|      |  ");
        for (int i = state.getAIPlayer().getPockets().length - 1; i >= 0; i--) {
            System.out.print(state.getAIPlayer().getPockets()[i].getPieces() + "   ");
        }
        System.out.println("|      |");
        System.out.print("|  " + state.getAIPlayer().getStore().getPieces() + "   |                          |  " + state.getHumanPlayer().getStore().getPieces() + "   |");
        System.out.println();
        System.out.print("|      |  ");
        for (int i = 0; i < state.getHumanPlayer().getPockets().length; i++) {
            System.out.print(state.getHumanPlayer().getPockets()[i].getPieces() + "   ");
        }
        System.out.println("|      |");
        System.out.println("|      |                          |      |");

        System.out.print("|------|  ");
        for (int i = 0; i < 7; i++) {
            System.out.print(Anumbers[i] + "  ");
        }
        System.out.println();
        System.out.println();
    }


    private void choosePlayerOne() {
        System.out.println("\nWelcome to Kalaha. Would you like to make the first move? \t y/n ?");
        Scanner input = new Scanner(System.in);
        String c = input.next();

        if (c.equals("y")) {
            state.setHumanPlayerTurn(true);
        } else if (c.equals("n")) {
            state.setHumanPlayerTurn(false);
        } else {
            System.out.println("Invalid input. Human player is chosen to start!");
            state.setHumanPlayerTurn(true);
        }
    }


    private void calculateFinalScore(State state) {

        if (state.getStateArray()[6] > state.getStateArray()[13]) {
            System.out.println("Player A has won the game!");
        }
        if (state.getStateArray()[6] == state.getStateArray()[13]) {
            System.out.println("Holy Moly the game is tied!");
        } else {
            System.out.println("AI has won the game!");
        }
        System.out.println("Thanks for playing :)");

    }


    private boolean isGameOver(State state) {

        int humanBuffer = 0;
        int aiBuffer = 0;
        for (int i = 0; i < GameSettings.POCKETS; i++) {
            humanBuffer = humanBuffer + state.getStateArray()[i];
            aiBuffer = aiBuffer + state.getStateArray()[i + GameSettings.POCKETS + 1];
        }

        if (humanBuffer == 0 || aiBuffer == 0) {
            return true;
        }

        return false;
    }

}
