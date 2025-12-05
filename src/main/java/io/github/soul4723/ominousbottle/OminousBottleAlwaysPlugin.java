/*
 * This file is part of OminousBottleAlways.
 * Copyright (c) 2024 Soul4723
 *
 * OminousBottleAlways is free software: you can redistribute it and/or modify
 * it under the terms of the MIT License.
 *
 * You should have received a copy of the MIT License along with this program.
 * If not, see <https://opensource.org/licenses/MIT>.
 */

package io.github.soul4723.ominousbottle;

import org.bukkit.plugin.java.JavaPlugin;

public class OminousBottleAlwaysPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CaptainBottleListener(), this);
        getLogger().info("OminousBottleAlways plugin enabled - captains will always drop OMINOUS_BOTTLE");
    }
    
    @Override
    public void onDisable() {
    }
}
