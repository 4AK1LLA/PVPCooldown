package com.rustret.pvpcooldown;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerQuitEvent;

public class MainListener implements Listener {
    private final CooldownManager manager;

    public MainListener(CooldownManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPVP(EntityDamageByEntityEvent event) {
        if(event.isCancelled() || !(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        manager.start(damager, victim);
        manager.start(victim, damager);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (check(player)) {
            return;
        }

        player.kill();
        manager.stop(player);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        manager.stop(event.getEntity());
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (check(player)) {
            return;
        }

        player.sendMessage(manager.TEXT_ON_COMMAND);
        event.setCancelled();
    }

    private boolean check(Player player) {
        return player.hasPermission("pvpcooldown.bypass") || manager.isNotPVP(player);
    }
}
