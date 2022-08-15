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
import fun.surviv.survival.ai.mobs.end.BetterEnderman;
import fun.surviv.survival.ai.mobs.nether.BetterBlaze;
import fun.surviv.survival.ai.mobs.nether.BetterGhast;
import fun.surviv.survival.ai.mobs.nether.BetterWitherSkeleton;
import fun.surviv.survival.ai.mobs.nether.BetterZombiePigman;
import fun.surviv.survival.ai.mobs.overworld.*;
import fun.surviv.survival.ai.mobs.overworld.water.BetterGuardian;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftSkeleton;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftZombie;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Objects;

/**
 * SurvivalSystem; fun.surviv.survival.ai.listener:EntityTarget
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class EntityTarget implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();
        if (target != null && target.getType().equals(EntityType.PLAYER)) {
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
            if (b && world) {
                BetterCreeper creeper;
                BetterSkeleton skeleton;
                BetterWitch witch;
                BetterSpider spider;
                BetterCaveSpider caveSpider;
                BetterEnderman enderman;
                BetterGuardian guardian;
                BetterZombiePigman pigman;
                BetterGhast ghast;
                BetterBlaze blaze;
                switch (entity.getType()) {
                    case ZOMBIE:
                        if (!((CraftZombie) entity).isBaby()) {
                            BetterZombie zombie = new BetterZombie((Zombie) entity);
                            zombie.trackAndKill((Player) target);
                        }
                        break;
                    case CREEPER:
                        creeper = new BetterCreeper((Creeper) entity);
                        creeper.trackAndKill((Player) target);
                        break;
                    case SKELETON:
                        if (((CraftSkeleton) entity).getSkeletonType() == Skeleton.SkeletonType.WITHER) {
                            BetterWitherSkeleton witherSkeleton = new BetterWitherSkeleton((Skeleton) entity);
                            witherSkeleton.trackAndKill((Player) target);
                            break;
                        }
                        skeleton = new BetterSkeleton((Skeleton) entity);
                        skeleton.getARide();
                        skeleton.trackAndKill((Player) target);
                        break;
                    case WITCH:
                        witch = new BetterWitch((Witch) entity);
                        witch.trackAndKill((Player) target);
                        break;
                    case SPIDER:
                        spider = new BetterSpider((Spider) entity);
                        spider.trackAndKill((Player) target);
                        break;
                    case CAVE_SPIDER:
                        caveSpider = new BetterCaveSpider((CaveSpider) entity);
                        caveSpider.trackAndKill((Player) target);
                        break;
                    case ENDERMAN:
                        enderman = new BetterEnderman((Enderman) entity);
                        enderman.trackAndKill((Player) target);
                        break;
                    case GUARDIAN:
                        guardian = new BetterGuardian((Guardian) entity);
                        guardian.trackAndKill((Player) target);
                        break;
                    case ZOMBIFIED_PIGLIN:
                        pigman = new BetterZombiePigman((PigZombie) entity);
                        pigman.trackAndKill((Player) target);
                        break;
                    case GHAST:
                        ghast = new BetterGhast((Ghast) entity);
                        ghast.trackAndKill((Player) target);
                        break;
                    case BLAZE:
                        blaze = new BetterBlaze((Blaze) entity);
                        blaze.trackAndKill((Player) target);
                        break;
                }
            }
        }
    }

}
