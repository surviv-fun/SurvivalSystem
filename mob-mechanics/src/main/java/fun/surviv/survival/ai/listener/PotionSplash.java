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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

import java.util.Objects;

/**
 * SurvivalSystem; fun.surviv.survival.ai.listener:PotionSplash
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class PotionSplash implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPotionSplash(PotionSplashEvent event) {
        if (event.getPotion().getShooter() != null && event.getPotion().getShooter() instanceof Entity) {
            boolean b = false;
            for (String s : MobAI.settings.configuration.getStringList("BetterMobs")) {
                if (s.equals(((Entity) event.getPotion().getShooter()).getType().toString())) {
                    b = true;
                    break;
                }
            }
            boolean world = false;
            for (String s : MobAI.settings.configuration.getStringList("Worlds")) {
                if (s.equals(((World) Objects.<World>requireNonNull(((Entity) event.getPotion().getShooter()).getLocation().getWorld())).getName())) {
                    world = true;
                    break;
                }
            }
            if (b && world && !((Entity) event.getPotion().getShooter()).getType().equals(EntityType.PLAYER)) event.setCancelled(true);
        }
    }

}
