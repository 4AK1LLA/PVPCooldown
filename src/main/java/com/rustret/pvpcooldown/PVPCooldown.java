package com.rustret.pvpcooldown;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.MainLogger;

public class PVPCooldown extends PluginBase {
    @Override
    public void onEnable() {
        MainLogger logger = Server.getInstance().getLogger();
        logger.info("PVPCooldown Enabled");
    }
}
