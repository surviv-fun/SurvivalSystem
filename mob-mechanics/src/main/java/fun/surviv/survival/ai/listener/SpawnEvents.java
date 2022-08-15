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

package fun.surviv.survival.ai.listener;

import fun.surviv.survival.ai.MobAI;
import fun.surviv.survival.ai.mobs.boss.BetterGiant;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftSkeleton;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Objects;

/**
 * SurvivalSystem; fun.surviv.survival.ai.listener:SpawnEvents
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class SpawnEvents implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        boolean b = false;
        for (String s : MobAI.settings.configuration.getStringList("BetterMobs")) {
            if (s.equals(entity.getType().toString())) {
                b = true;
                break;
            }
        }
        boolean world = false;
        for (String s : MobAI.settings.configuration.getStringList("Worlds")) {
            if (s.equals(((World) Objects.<World>requireNonNull(entity.getLocation().getWorld())).getName())) {
                world = true;
                break;
            }
        }
        if (b && world) switch (entity.getType()) {
            case CAVE_SPIDER:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("CaveSpider.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("CaveSpider.Health"));
                break;
            case SPIDER:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Spider.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Spider.Health"));
                break;
            case CREEPER:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Creeper.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Creeper.Health"));
                break;
            case ENDERMAN:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Enderman.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Enderman.Health"));
                break;
            case SKELETON:
                if (((CraftSkeleton) entity).getSkeletonType() == Skeleton.SkeletonType.WITHER) {
                    ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("WitherSkeleton.Health"));
                    entity.setHealth(MobAI.settings.configuration.getDouble("WitherSkeleton.Health"));
                    break;
                }
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Skeleton.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Skeleton.Health"));
                break;
            case ZOMBIE:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Zombie.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Zombie.Health"));
                break;
            case WITCH:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Witch.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Witch.Health"));
                break;
            case GUARDIAN:
                if (((Guardian) entity).isElder()) {
                    ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Guardian.Elder.Health"));
                    entity.setHealth(MobAI.settings.configuration.getDouble("Guardian.Elder.Health"));
                    break;
                }
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Guardian.Normal.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Guardian.Normal.Health"));
                break;
            case ZOMBIFIED_PIGLIN:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("PigmanZombie.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("PigmanZombie.Health"));
                break;
            case GHAST:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Ghast.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Ghast.Health"));
                break;
            case BLAZE:
                ((CraftLivingEntity) entity).setMaxHealth(MobAI.settings.configuration.getDouble("Blaze.Health"));
                entity.setHealth(MobAI.settings.configuration.getDouble("Blaze.Health"));
                break;
            case GIANT:
                new BetterGiant((Giant) entity);
                break;

            default:
                break;
        }
    }
}
