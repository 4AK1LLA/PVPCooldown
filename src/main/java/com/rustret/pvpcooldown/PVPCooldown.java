package com.rustret.pvpcooldown;

import cn.nukkit.plugin.PluginBase;

public class PVPCooldown extends PluginBase {
    @Override
    public void onEnable() {
        CooldownManager manager = new CooldownManager(this);
        getServer().getPluginManager().registerEvents(new MainListener(manager), this);
    }
}
