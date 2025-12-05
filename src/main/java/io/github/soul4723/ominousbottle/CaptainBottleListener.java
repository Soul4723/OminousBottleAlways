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

import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Raider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.OminousBottleMeta;

import org.bukkit.event.world.LootGenerateEvent;

import java.util.List;
import java.util.Random;

public class CaptainBottleListener implements Listener {
    
    private static final Random random = new Random();
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLootGenerate(LootGenerateEvent event) {
        if (event == null) {
            return;
        }
        
        Entity entity = event.getEntity();
        if (!isCaptain(entity)) {
            return;
        }
        
        List<ItemStack> loot = event.getLoot();
        if (loot == null) {
            return;
        }

        boolean hasOminousBottle = loot.stream()
            .anyMatch(item -> item != null && item.getType() == Material.OMINOUS_BOTTLE);
        
        if (!hasOminousBottle) {
            ItemStack ominousBottle = createOminousBottle();
            if (ominousBottle != null) {
                loot.add(ominousBottle);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event == null) {
            return;
        }
        
        Entity entity = event.getEntity();
        if (!isCaptain(entity)) {
            return;
        }
        
        List<ItemStack> drops = event.getDrops();
        if (drops == null) {
            return;
        }

        boolean hasOminousBottle = drops.stream()
            .anyMatch(item -> item != null && item.getType() == Material.OMINOUS_BOTTLE);
        
        if (!hasOminousBottle) {
            World world = entity.getWorld();
            if (world == null) {
                return;
            }

            Boolean doMobLoot = world.getGameRuleValue(GameRule.DO_MOB_LOOT);
            if (Boolean.TRUE.equals(doMobLoot)) {
                ItemStack ominousBottle = createOminousBottle();
                if (ominousBottle != null) {
                    drops.add(ominousBottle);
                }
            } else {
                Location location = entity.getLocation();
                if (location != null) {
                    ItemStack ominousBottle = createOminousBottle();
                    if (ominousBottle != null) {
                        world.dropItemNaturally(location, ominousBottle);
                    }
                }
            }
        }
    }
    
    private boolean isCaptain(Entity entity) {
        if (entity == null || !(entity instanceof Raider)) {
            return false;
        }
        
        Raider raider = (Raider) entity;
        if (raider.isPatrolLeader()) {
            return true;
        }

        if (raider.getEquipment() == null) {
            return false;
        }
        
        ItemStack helmet = raider.getEquipment().getHelmet();
        if (helmet == null) {
            return false;
        }
        
        Material type = helmet.getType();
        return type != null && "OMINOUS_BANNER".equals(type.name());
    }
    
    private ItemStack createOminousBottle() {
        ItemStack bottle = new ItemStack(Material.OMINOUS_BOTTLE);
        if (bottle == null) {
            return null;
        }

        int amplifier = random.nextInt(5); // 0 to 4 inclusive

        OminousBottleMeta meta = null;
        if (bottle.getItemMeta() instanceof OminousBottleMeta mb) {
            meta = mb;
        }
        if (meta != null) {
            meta.setAmplifier(amplifier);
            bottle.setItemMeta(meta);
        }
        
        return bottle;
    }
}
