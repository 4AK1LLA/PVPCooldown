package com.rustret.pvpcooldown;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.utils.BossBarColor;
import cn.nukkit.utils.DummyBossBar;

public class PVPEntry {
    private int secondsLeft;
    private final DummyBossBar bar;
    private long barId;
    private int taskId = -1;
    private final Player player;
    private final Plugin plugin;
    private final CooldownManager manager;
    private final ServerScheduler scheduler = Server.getInstance().getScheduler();
    private final String enemy;

    public PVPEntry(Player player, String enemy, Plugin plugin, CooldownManager manager) {
        this.player = player;
        this.enemy = enemy;
        this.plugin = plugin;
        this.manager = manager;

        this.bar = new DummyBossBar
                .Builder(player)
                .color(BossBarColor.RED)
                .build();
    }

    public void startTask() {
        secondsLeft = manager.TOTAL_SECONDS;
        barId = player.createBossBar(bar);
        taskId = scheduler.scheduleRepeatingTask(plugin, () -> {
            if (secondsLeft == 0) {
                player.sendMessage(manager.TEXT_QUIT);
                stopTask();
                manager.remove(player);
                return;
            }
            bar.setText(String.format(manager.TEXT_BAR, enemy, secondsLeft));
            bar.setLength(manager.BAR_INDEX * secondsLeft);
            secondsLeft--;
        }, manager.TICKS).getTaskId();
    }

    public void stopTask() {
        player.removeBossBar(barId);
        scheduler.cancelTask(taskId);
    }

    public void resetTask() {
        stopTask();
        taskId = -1;
        startTask();
    }
}
