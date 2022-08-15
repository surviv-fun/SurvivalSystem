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
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Objects;

/**
 * SurvivalSystem; fun.surviv.survival.ai.listener:ProjectileEvents
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class ProjectileEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        boolean b = false;
        for (String s : MobAI.settings.configuration.getStringList("BetterMobs")) {
            if (s.equals(projectile.getType().toString())) {
                b = true;
                break;
            }
        }
        boolean world = false;
        for (String s : MobAI.settings.configuration.getStringList("Worlds")) {
            if (s.equals(((World) Objects.<World>requireNonNull(projectile.getLocation().getWorld())).getName())) {
                world = true;
                break;
            }
        }

        if (world && b) if (projectile.getType().equals(EntityType.SKELETON)) {
            event.setCancelled(true);
            return;
        }

        if (projectile.getType().equals(EntityType.GHAST)) {
            event.setCancelled(true);
            return;
        }

        if (projectile.getType().equals(EntityType.WITCH)) {
            event.setCancelled(true);
            return;
        }

        if (projectile.getType().equals(EntityType.SPLASH_POTION)) {
            if (projectile.getShooter() instanceof LivingEntity) {
                LivingEntity e = (LivingEntity) projectile.getShooter();
                assert e != null;
                if (!e.getType().equals(EntityType.PLAYER)) {
                    event.setCancelled(true);
                }
            }
            return;

        }

        if (projectile.getType().equals(EntityType.ARROW)) {
            if (projectile.getShooter() instanceof LivingEntity) {
                LivingEntity e = (LivingEntity) projectile.getShooter();
                assert e != null;
                if (!e.getType().equals(EntityType.PLAYER)) {
                    event.setCancelled(true);
                }
            }
            return;

        }

        if (projectile.getType().equals(EntityType.FIREBALL) && projectile.getShooter() instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) projectile.getShooter();
            assert e != null;
            if (!e.getType().equals(EntityType.PLAYER) && e.getType().equals(EntityType.GHAST)) {
                event.setCancelled(true);
            }
        }
    }

}
