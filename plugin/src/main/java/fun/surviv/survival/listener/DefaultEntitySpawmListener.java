/*
 * Copyright (c) LuciferMorningstarDev <contact@lucifer-morningstar.dev>
 * Copyright (c) surviv.fun <contact@surviv.fun>
 * Copyright (C) surviv.fun team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fun.surviv.survival.listener;

import fun.surviv.survival.SurvivalSystem;
import fun.surviv.survival.namespace.Namespaces;
import fun.surviv.survival.serialization.ComponentSerializer;
import fun.surviv.survival.utils.MathUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataType;

/**
 * SurvivalSystem; fun.surviv.survival.listener:DefaultEntitySpawmListener
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class DefaultEntitySpawmListener implements Listener {

    private final SurvivalSystem plugin;

    public DefaultEntitySpawmListener(SurvivalSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawnCreature(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.PHANTOM && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true);
        }
        this.nerfPhantoms(event);
        this.transformCreatures(event);
    }


    /**
     * This method disables natural phantom spawning
     *
     * @param event
     */
    private void nerfPhantoms(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.PHANTOM && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true);
        }
    }

    private void transformCreatures(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        if (event.getEntityType() == EntityType.PLAYER) {
            return;
        }
        if (event.getEntityType() == EntityType.WARDEN) {
            return;
        }
        if (event.getEntityType() == EntityType.VILLAGER) {
            return;
        }
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            return;
        }
        if (event.getEntityType() == EntityType.WANDERING_TRADER) {
            return;
        }
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            return;
        }

        LivingEntity mob = event.getEntity();
        int maxLevel = plugin.getNerfControlConfig().get().getMaxCreatureLevel();
        int newLevel = createLevel(maxLevel);
        mob.getPersistentDataContainer().set(Namespaces.MOB_LEVEL, PersistentDataType.INTEGER, newLevel);
        double healthLevel = 0;
        double healthGeneral = plugin.getNerfControlConfig().get().getGeneralDamageMultiplier().get(event.getEntity().getType());
        for (int i = 0; i < newLevel; i++) {
            healthLevel += plugin.getNerfControlConfig().get().getAddDamageLevel().get(event.getEntity().getType());
        }
        double nerfedHealth = MathUtils.roundToHalf((mob.getHealth() * healthGeneral) + (mob.getHealth() * healthLevel));

        if (plugin.getDebugConfig().get().isDebugHealth()) {
            SurvivalSystem.debug("Entity Spawned: [" + event.getEntity().getType() + "]:" + "\n" + "   - health-before:" + mob.getHealth() + "\n" + "   - health-all:" + nerfedHealth + "\n" + "   - modify-health-level:" + healthLevel + "\n" + "   - modify-health-general:" + healthGeneral);
        }
        if (plugin.getDebugConfig().get().isDebugLevelsAddNameTag()) {
            mob.setCustomNameVisible(true);
            mob.customName(ComponentSerializer.etOnly.deserialize("&7[DEBUG] &5{" + mob.getType() + "} &3Level&7: " + convertLevelSTR(newLevel, maxLevel)));
        }
        mob.registerAttribute(Attribute.GENERIC_MAX_HEALTH);
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(nerfedHealth);
        mob.setHealth(nerfedHealth);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void deathCreature(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        if (event.getEntityType() == EntityType.PLAYER) {
            return;
        }
        LivingEntity mob = event.getEntity();
        if (mob.getPassengers().size() > 0) {
            for (Entity current : mob.getPassengers()) {
                if (current.getType() == EntityType.ARMOR_STAND) {
                    current.remove();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void deathCreature(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        if (event.getEntityType() == EntityType.PLAYER) {
            return;
        }
        LivingEntity mob = (LivingEntity) event.getEntity();
        if (mob.getPassengers().size() > 0) {
            for (Entity current : mob.getPassengers()) {
                if (current.getType() == EntityType.ARMOR_STAND) {
                    current.remove();
                }
            }
        }
    }

    private String convertLevelSTR(int level, int maxLevel) {
        // 15 levels default
        if (level > 12) {
            return "&4" + level;
        }
        if (level > 8) {
            return "&c" + level;
        }
        if (level > 5) {
            return "&6" + level;
        }
        if (level > 3) {
            return "&1" + level;
        }
        return "&a" + level;
    }

    private int createLevel(int max) {
        MathUtils.RandomProbability rand = new MathUtils.RandomProbability();
        int[] numbers = new int[max];
        float[] populator = new float[max];
        for (int i = 0; i < max; i++) {
            numbers[i] = i + 1;
            populator[i] = (float) (max / (i + 1) * 0.1);
        }
        rand.setIntProbList(numbers, populator);
        return rand.nextNum();
    }


}
