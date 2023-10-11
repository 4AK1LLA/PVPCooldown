package com.rustret.pvpcooldown;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

import java.util.HashMap;

public class CooldownManager {
    public final int TICKS = 20;
    public final int TOTAL_SECONDS;
    public final float BAR_INDEX;
    public final String TEXT_BAR, TEXT_ENTERED, TEXT_QUIT, TEXT_ON_COMMAND;
    private final Plugin plugin;
    private final HashMap<Long, PVPEntry> fightingPlayers = new HashMap<>();

    public CooldownManager(Plugin plugin) {
        this.plugin = plugin;

        Config cfg = plugin.getConfig();
        TOTAL_SECONDS = cfg.getInt("total-seconds");
        TEXT_BAR = cfg.getString("text-bar");
        TEXT_ENTERED = cfg.getString("text-entered");
        TEXT_QUIT = cfg.getString("text-quit");
        TEXT_ON_COMMAND = cfg.getString("text-on-command");

        BAR_INDEX = 100.0f / TOTAL_SECONDS;
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
