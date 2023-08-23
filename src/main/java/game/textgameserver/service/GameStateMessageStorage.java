package game.textgameserver.service;

import java.util.concurrent.ConcurrentHashMap;

public class GameStateMessageStorage {

    private static ConcurrentHashMap<Long, String> msgs = new ConcurrentHashMap<>();

    public static void putMsg (String msg, Long gameStateId) {
        if (msgs.containsKey(gameStateId)) {
            msgs.put(
                    gameStateId,
                    msgs.get(gameStateId) + "; " + msg
            );
        } else {
            msgs.put(gameStateId, msg);
        }
    }

    public static String takeMsg (Long gameStateId) {
        String msg = null;

        if (msgs.containsKey(gameStateId)) {
            msg = msgs.get(gameStateId);
            msgs.remove(gameStateId);
        }

        return msg;
    }
}
