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
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.OminousBottleMeta;

import java.util.List;
import java.util.Random;

public class CaptainBottleListener implements Listener {

    private static final Random RANDOM = new Random();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (!isCaptain(entity)) {
            return;
        }

        World world = entity.getWorld();
        Boolean doMobLoot = world.getGameRuleValue(GameRule.DO_MOB_LOOT);

        if (!Boolean.TRUE.equals(doMobLoot)) {
            return;
        }

        List<ItemStack> drops = event.getDrops();

        boolean hasOminousBottle = drops.stream()
                .anyMatch(item -> item != null && item.getType() == Material.OMINOUS_BOTTLE);

        if (!hasOminousBottle) {
            ItemStack ominousBottle = createOminousBottle();
            drops.add(ominousBottle);
        }
    }

    private boolean isCaptain(Entity entity) {
        if (!(entity instanceof Raider raider)) {
            return false;
        }

        if (raider.isPatrolLeader()) {
            return true;
        }

        EntityEquipment equipment = raider.getEquipment();
        if (equipment == null) {
            return false;
        }

        ItemStack helmet = equipment.getHelmet();
        if (helmet == null) {
            return false;
        }

        Material type = helmet.getType();
        return type == Material.OMINOUS_BANNER;
    }

    private ItemStack createOminousBottle() {
        ItemStack bottle = new ItemStack(Material.OMINOUS_BOTTLE);

        if (bottle.getItemMeta() instanceof OminousBottleMeta meta) {
            int amplifier = RANDOM.nextInt(5);
            meta.setAmplifier(amplifier);
            bottle.setItemMeta(meta);
        }

        return bottle;
    }
}