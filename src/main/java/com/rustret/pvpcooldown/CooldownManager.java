package com.rustret.pvpcooldown;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;

import java.util.HashMap;

public class CooldownManager {
    public final int TICKS = 20;
    public final int TOTAL_SECONDS = 20;
    public final float BAR_INDEX = 100.0f / TOTAL_SECONDS;
    public final String TEXT_BAR = "PVP mode ends in %d seconds";
    public final String TEXT_ENTERED = "You entered PVP. Don't leave the server or you will die";
    public final String TEXT_QUIT = "You quit PVP";
    public final String TEXT_ON_COMMAND = "You can not use commands during PVP";
    private final Plugin plugin;
    private final HashMap<Long, PVPEntry> fightingPlayers = new HashMap<>();

    public CooldownManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void start(Player player) {
        long playerId = player.getId();
        PVPEntry entry = fightingPlayers.get(playerId);

        if (entry != null) {
            entry.resetTask();
            return;
        }

        player.sendMessage(TEXT_ENTERED);
        entry = new PVPEntry(player, plugin, this);
        fightingPlayers.put(playerId, entry);
        entry.startTask();
    }

    public void stop(Player player) {
        long playerId = player.getId();

        if (!fightingPlayers.containsKey(playerId)) {
            return;
        }

        PVPEntry entry = fightingPlayers.remove(playerId);
        entry.stopTask();
    }

    public void remove(Player player) {
        fightingPlayers.remove(player.getId());
    }

    public boolean isPVP(Player player) {
        return fightingPlayers.containsKey(player.getId());
    }

    public boolean isNotPVP(Player player) {
        return !fightingPlayers.containsKey(player.getId());
    }

    public void clear() {
        for (PVPEntry entry : fightingPlayers.values()) {
            entry.stopTask();
        }

        fightingPlayers.clear();
    }
}
