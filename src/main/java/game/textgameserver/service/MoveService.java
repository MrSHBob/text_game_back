package game.textgameserver.service;

import game.textgameserver.dao.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

@Service
public class MoveService {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private ButtonService buttonService;
    @Autowired
    private ButtonActionService buttonActionService;



    public GameState makingMove(Long moveId, User user) {

        // check that move is correct
        PlotButton pushedButton = buttonService.getButtonById(moveId);
        GameState currentGS = gameService.getGameStateByOwnerId(user.getId());
        if (currentGS.getActiveScreen().getId() != pushedButton.getScreenId())
            throw new RuntimeException("Wrong action picked");
        // get actions
        doActions(pushedButton.getActions(), user);
        return gameService.getGameStateByOwnerId(user.getId());
    }

    @Transactional
    private void doActions(String actionsStr, User user) {
        GameState currentGS = gameService.getGameStateByOwnerId(user.getId());

        List<String> actions = Arrays.stream(actionsStr.split(";")).toList();

        actions.forEach(action -> {
            String actCode = action.split(":")[0];
            String actParamsStr = action.split(":")[1];
            String[] actParams = actParamsStr.split("/");

            if (actCode.equals("ACT")) {
                doComplexAction(actParamsStr, currentGS);
            } else {
                makeSubAction(actCode, actParams, currentGS);
            }
        });
    }

    private void doComplexAction(String action, GameState currentGS) {
        String[] params = action.split("/");
        // params amount check
        ButtonAction currentAction = buttonActionService.getActionById(Long.valueOf(params[0]));
        if ((params.length - 1) != getParamsNumber(currentAction)) throw new RuntimeException("Invalid Parameters Amount - "
            + (params.length - 1) + "; instead of - " + currentAction.getType());

        int type = Integer.parseInt(currentAction.getType());
        int ifParNum = type / 100;
        int doParNum = (type - ifParNum * 100) / 10;
        int elseParNum = type - ifParNum * 100 - doParNum * 10;

        Long actId = Long.parseLong(params[0]);

        String[] ifParams = Arrays.copyOfRange(params, 1, ifParNum + 1);
        String[] doParams = Arrays.copyOfRange(params, ifParNum + 1, doParNum + ifParNum + 1);
        String[] elseParams = Arrays.copyOfRange(params, doParNum + ifParNum + 1, elseParNum + doParNum + ifParNum + 1);

        ButtonAction buttonAction = buttonActionService.getActionById(actId);

        if (makeIfAction(currentAction.getIfCode(), ifParams, currentGS)) {
            String code = currentAction.getDoCode();
            if (currentAction.getDoCode().replaceAll("\\W|\\d", "").equals("ACT")) {
                int parIndex = 0;

                if (Pattern.matches("[A][C][T]", code)) {
                    code = code + ":" + doParams[parIndex];
                    parIndex++;
                } else if (Pattern.matches("[A][C][T][:]", code)) {
                    code = code + doParams[parIndex];
                    parIndex++;
                }  else if (Pattern.matches("[A][C][T][:]\\d{1,9}", code)) {}

                StringBuilder sb = new StringBuilder();
                sb.append(code.split(":")[1]);
                for (int i = parIndex; i < doParams.length; i++) {
                    sb.append("/");
                    sb.append(doParams[i]);
                }
                doComplexAction(sb.toString(), currentGS);
            } else {
                makeSubAction(buttonAction.getDoCode(), doParams, currentGS);
            }
        } else {
            String code = currentAction.getElseCode();
            if (code.replaceAll("\\W|\\d", "").equals("ACT")) {
                int parIndex = 0;

                if (Pattern.matches("[A][C][T]", code)) {
                    code = code + ":" + elseParams[parIndex];
                    parIndex++;
                } else if (Pattern.matches("[A][C][T][:]", code)) {
                    code = code + elseParams[parIndex];
                    parIndex++;
                }  else if (Pattern.matches("[A][C][T][:]\\d{1,9}", code)) {}

                StringBuilder sb = new StringBuilder();
                sb.append(code.split(":")[1]);
                for (int i = parIndex; i < elseParams.length; i++) {
                    sb.append("/");
                    sb.append(elseParams[i]);
                }
                doComplexAction(sb.toString(), currentGS);
            } else {
                makeSubAction(buttonAction.getElseCode(), elseParams, currentGS);
            }
        }



    }

    @Transactional
    private void makeSubAction(String code, String[] params, GameState currentGS) {
        if (code.contains("&")) {
            String[] commands = code.split("&");
            if (params.length != commands.length) throw new RuntimeException("Error: params.length != commands.length");
            for (int i = 0; i < commands.length; i++) {
                makeSubAction(commands[i],new String[]{params[i]}, currentGS);
            }
        } else {
            // GT
            if (code.equals("GT")) {
                currentGS.setActiveScreen(screenService.getScreenById(Long.parseLong(params[0])));
                gameService.updateGameState(currentGS);
            }
            // SET
            if (code.equals("SET")) {
                if (params[0].equals("Race")) {
                    currentGS.setCharRace(params[1]);
                }
                if (params[0].equals("Class")) {
                    currentGS.setCharClass(params[1]);
                }
                gameService.updateGameState(currentGS);
            }
            //MSG
            if (code.equals("MSG")) {
                GameStateMessageStorage.putMsg(params[0], currentGS.getId());
            }
            // IA/DA
            if ((code.startsWith("IA")) || (code.startsWith("DA"))) {
                int parIndex = 0;

                // complete code
                if (Pattern.matches("[ID][A]", code)) {
                    code = code + params[parIndex];
                    parIndex++;
                } else if (Pattern.matches("[ID][A][SAEI]", code)) { }

                // execute change
                switch (code) {
                    case "IAS": {
                        currentGS.setCharStrength(currentGS.getCharStrength() + Integer.parseInt(params[parIndex]));
                        break;
                    }
                    case "DAS": {
                        currentGS.setCharStrength(currentGS.getCharStrength() - Integer.parseInt(params[parIndex]));
                        break;
                    }
                    case "IAA": {
                        currentGS.setCharAgility(currentGS.getCharAgility() + Integer.parseInt(params[parIndex]));
                        break;
                    }
                    case "DAA": {
                        currentGS.setCharAgility(currentGS.getCharAgility() - Integer.parseInt(params[parIndex]));
                        break;
                    }
                    case "IAE": {
                        currentGS.setCharEndurance(currentGS.getCharEndurance() + Integer.parseInt(params[parIndex]));
                        break;
                    }
                    case "DAE": {
                        currentGS.setCharEndurance(currentGS.getCharEndurance() - Integer.parseInt(params[parIndex]));
                        break;
                    }
                    case "IAI": {
                        currentGS.setCharIntelligence(currentGS.getCharIntelligence() + Integer.parseInt(params[parIndex]));
                        break;
                    }
                    case "DAI": {
                        currentGS.setCharIntelligence(currentGS.getCharIntelligence() - Integer.parseInt(params[parIndex]));
                        break;
                    }
                    default: {
//                        gameService.updateGameState(currentGS);
                    }
                }
            }
            gameService.updateGameState(currentGS);
        }
    }

    private boolean makeIfAction(String ifCode, String[] params, GameState currentGS) {
        String code = ifCode;
        boolean result = false;
        int parIndex = 0;

        if (ifCode.equals("")){
            code = params[parIndex] + params[parIndex + 1] + params[parIndex + 2];
            parIndex++;
        } else if (Pattern.matches("[SAEI][TGN][RIDT]", ifCode)) {
            code = code + params[parIndex] + params[parIndex + 1];
            parIndex++;
            parIndex++;
        } else if (Pattern.matches("[SAEI][TGN][RIDT][<>=]", ifCode)) {
            code = code + params[parIndex];
            parIndex++;
        }  else if (Pattern.matches("[SAEI][TGN][RIDT][<>=]\\d{1,2}", ifCode)) {}

        String attr1 = code.replaceAll("\\W|\\d", "");
        String attr2 = code.replaceAll("\\w|\\d", "");
        String attr3 = code.replaceAll("\\D", "");

        switch (attr1) {
            case "STR": {
                switch (attr2) {
                    case ">": {
                        if (currentGS.getCharStrength() > Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "<": {
                        if (currentGS.getCharStrength() < Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "=": {
                        if (currentGS.getCharStrength() == Integer.parseInt(attr3)) result = true;
                        break;
                    }
                }
                break;
            }
            case "AGI": {
                switch (attr2) {
                    case ">": {
                        if (currentGS.getCharAgility() > Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "<": {
                        if (currentGS.getCharAgility() < Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "=": {
                        if (currentGS.getCharAgility() == Integer.parseInt(attr3)) result = true;
                        break;
                    }
                }
                break;
            }
            case "END": {
                switch (attr2) {
                    case ">": {
                        if (currentGS.getCharEndurance() > Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "<": {
                        if (currentGS.getCharEndurance() < Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "=": {
                        if (currentGS.getCharEndurance() == Integer.parseInt(attr3)) result = true;
                        break;
                    }
                }
                break;
            }
            case "INT": {
                switch (attr2) {
                    case ">": {
                        if (currentGS.getCharIntelligence() > Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "<": {
                        if (currentGS.getCharIntelligence() < Integer.parseInt(attr3)) result = true;
                        break;
                    }
                    case "=": {
                        if (currentGS.getCharIntelligence() == Integer.parseInt(attr3)) result = true;
                        break;
                    }
                }
                break;
            }
//            default: gameService.updateGameState(currentGS);
        }
        return result;
    }

    private int getParamsNumber(ButtonAction act) {
        int result = 0;
        for (String digit : act.getType().split("")) {
            result = result + Integer.valueOf(digit);
        }
        return result;
    }
}
