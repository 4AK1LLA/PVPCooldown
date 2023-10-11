# PVPCooldown Plugin

## Overview

This Nukkit plugin designed to prevent players from leaving the server while they are engaged in Player versus Player (PVP) combat. This plugin enforces a cooldown period, during which players cannot exit the server, ensuring that they remain in the game until the cooldown expires.

![GIF](https://github.com/4AK1LLA/PVPCooldown/assets/90700933/a22d8237-e7ca-4a7e-83e3-67e080b5ef37)

## Configuration

To customize the behavior of the PVPCooldown plugin, you can edit the configuration file. Open the `config.yml` file and modify the `total-seconds` section or change messages.

## Permissions

This plugin also includes a permission `cooldown.bypass` for bypassing its restrictions. Players with the appropriate permission can freely leave the server during PVP mode.

## Installation

1. Download release of the plugin from the [Releases](https://github.com/4AK1LLA/PVPCooldown/releases) section.
2. Place the downloaded JAR file into the "plugins" directory of your server.
3. Restart your server to enable the plugin.

## TODO

- Add ability for certain commands to be used even during PVP mode. For instance, the `/tell` command doesn't need to be restricted.