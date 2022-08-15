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

import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftSkeleton;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * SurvivalSystem; fun.surviv.survival.ai.listener:PlayerMove
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.ADVENTURE) && player.getGameMode().equals(GameMode.SURVIVAL)) for (Entity entity : player.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
            double distance2D = Math.sqrt(Math.pow(player.getLocation().getX() - entity.getLocation().getX(), 2.0D) + Math.pow(player.getLocation().getZ() - entity.getLocation().getZ(), 2.0D));
            Vector vector = entity.getLocation().subtract(player.getLocation()).toVector().setY(-1);
            vector = vector.multiply(0.02D * Math.sqrt(Math.abs(10.0D - distance2D)));
            vector.setY(-9.81D);
            if (entity.getType().equals(EntityType.SKELETON)) {
                if (((CraftSkeleton) entity).getSkeletonType() == Skeleton.SkeletonType.NORMAL && !((LivingEntity) entity).hasPotionEffect(PotionEffectType.SLOW)) entity.setVelocity(vector);
                continue;
            }
            if (entity.getType().equals(EntityType.WITCH) && !((LivingEntity) entity).hasPotionEffect(PotionEffectType.SLOW)) entity.setVelocity(vector);
        }
    }

}
