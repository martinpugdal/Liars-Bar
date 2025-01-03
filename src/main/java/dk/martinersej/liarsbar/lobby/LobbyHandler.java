package dk.martinersej.liarsbar.lobby;

public class LobbyHandler {

    private static LobbyHandler instance;

    public LobbyHandler() {
        instance = this;

        // setup npc
        new SelectGameNPC();
    }
}
