import java.util.ArrayList;

public class AI {

    public int depth;
    int maxValueCount;
    int minValueCount;
    int[] utilityArray = new int[GameSettings.POCKETS];




    public AI(int depth) {
        this.depth = depth;
    }

    public int minimax(State state) {

        State originalState = new State(GameSettings.bufferPlayers[0], GameSettings.bufferPlayers[1]);
        originalState.setStateArray(state.getStateArray());

        for (int i = 0; i < GameSettings.POCKETS; i++) {
            if (state.getStateArray()[i + GameSettings.POCKETS + 1] != 0) {
//                state.setStateArray(originalState.getStateArray());
                maxValueCount = 0;
                minValueCount = 0;
                depth = GameSettings.searchDepth;
                minValue(result(state, i));
                utilityArray[i] = utility(state);
                state.setStateArray(originalState.getStateArray());
            }
            else {
                utilityArray[i] = -99999;
            }
        }
        state.setStateArray(originalState.getStateArray());
        int aiAction = optimalAction(utilityArray);
        //System.out.println(aiAction);
        return aiAction;
    }

    private int maxValue(State state) {
        if (terminalTest(state, depth)) {
            return utility(state);
        }

        maxValueCount += 1;
        depth -= 1;

        int maxEval = -99999;
        for (int i = 0; i < GameSettings.POCKETS; i++) {
            if (state.getStateArray()[i] != 0) {

                State stateBuffer = result(state, i);

                if (!stateBuffer.isHumanPlayerTurn()) {
                    maxEval = max(maxEval, maxValue(result(state, i)));
                }
                else {
                    maxEval = max(maxEval, minValue(result(state, i)));
                }
            }
        }
        return maxEval;
    }

    private int minValue(State state) {
        if (terminalTest(state, depth)) {
            return utility(state);
        }
        minValueCount += 1;
        depth -= 1;

        int minEval = 99999;
        for (int i = 0; i < GameSettings.POCKETS; i++) {
            if (state.getStateArray()[i] != 0) {


                State stateBuffer = result(state, i);
                if (stateBuffer.isHumanPlayerTurn()) {
                    minEval = min(minEval, minValue(result(state, i)));
                }
                else {
                    minEval = min(minEval, maxValue(result(state, i)));
                }
            }
        }

        return 0;
    }

    private int optimalAction(int[] utilityArray) {


        int a = utilityArray[0];
        int b = 0;

        for (int i = 1; i < utilityArray.length; i++) {

            if (a < utilityArray[i]) {
                a = utilityArray[i];
                b = i;
            }
        }
        return b;
    }

    private State result(State state, int action) {

        State tempStateArray;
        if (state.isHumanPlayerTurn()) {
            tempStateArray = playTurnResult(state, action);
        }
        else {
            tempStateArray = playAITurnResult(state, action);
        }
        return tempStateArray;
    }

    private int max(int a, int b) {
        if (a == b) {
            return a;
        }
        else if (a > b) {
            return a;
        }
        else return b;
    }

    private int min(int a, int b) {
        if (a == b) {
            return a;
        }
        else if (a < b) {
            return a;
        }
        else return b;
    }

    private int utility(State state) {

        return state.getAIPlayer().getStore().getPieces() - state.getHumanPlayer().getStore().getPieces();
    }

    private boolean terminalTest(State state, int depth) {

        int humanBuffer = 0;
        int aiBuffer = 0;
        for (int i = 0; i < GameSettings.POCKETS; i++) {
            humanBuffer = humanBuffer + state.getStateArray()[i];
            aiBuffer = aiBuffer + state.getStateArray()[i + GameSettings.POCKETS + 1];
        }
        if (depth == 0) {
            return true;
        }
        else if (humanBuffer == 0 || aiBuffer == 0) {
            return true;
        }
        return false;
    }

    private State playTurnResult(State state, int action) {

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

        state.setStateArray(tempStateArray);  /*   SKAL DET HER VÆRE HER!?   */
        // Tjekker om du rammer din sidste sten i din egen bank, for så at få en ekstra tur
        if ((action + pocketBuffer) % 13 == 6) {
            //System.out.println("You succesfully put your last piece in your store. You get another turn!");
            state.setHumanPlayerTurn(true);
            return state;
        }
        state.setHumanPlayerTurn(false);
        return state;
    }

    private State playAITurnResult(State state, int action) {

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

         state.setStateArray(tempStateArray); /*OG SKAL DET HER VÆRE HER YO!? */

        if ((action + pocketBuffer + GameSettings.POCKETS + 1) % 14 == 13) {
            // Dette gøgl virker
            state.setHumanPlayerTurn(false);
            return state;
        }

        state.setHumanPlayerTurn(true);
        return state;
    }




    /* HER PRØVER VI EN NY MINIMAX FUNKTION */


    public int minimax2 (State state, int depth) {

        State originalState = new State(GameSettings.bufferPlayers[0], GameSettings.bufferPlayers[1]);
        originalState.setStateArray(state.getStateArray());

        if (terminalTest(state, depth)) {
            state.setStateArray(originalState.getStateArray());
            return utility(state);
        }

        for (int i = 0; i < GameSettings.POCKETS; i++) {

            if (state.getStateArray()[i + GameSettings.POCKETS + 1] != 0) {

                if (!state.isHumanPlayerTurn()) {
                    int maxEval = -99999;

                    for (State child : childsOfState(state)) {
                        int eval = minimax2(child, depth - 1);
                        state.setHumanPlayerTurn(true);
                        maxEval = max(maxEval, eval);
                    }
                    utilityArray[i] = maxEval;
//                    return maxEval;
                } else {
                    int minEval = 99999;

                    for (State child : childsOfState(state)) {
                        int eval = minimax2(child, depth - 1);
                        state.setHumanPlayerTurn(false);
                        minEval = min(minEval, eval);
                    }
                    utilityArray[i] = minEval;
//                    return minEval;
                }
            }
            else {
                utilityArray[i] = -999999;
            }
        }
        state.setStateArray(originalState.getStateArray());
        return optimalAction(utilityArray);
    }



    private ArrayList<State> childsOfState(State state) {
        ArrayList<State> tempStateArray = new ArrayList<>();

        for (int i = 0; i < GameSettings.POCKETS; i++) {
            if (state.getAIPlayer().getPockets()[i].getPieces() != 0) {
                tempStateArray.add(result(state, i));
            }
        }
        return tempStateArray;
    }
























}
