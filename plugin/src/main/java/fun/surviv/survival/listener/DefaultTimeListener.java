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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

/**
 * SurvivalSystem; fun.surviv.survival.listener:DefaultTimeListener
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class DefaultTimeListener implements Listener {

    private final SurvivalSystem plugin;

    public DefaultTimeListener(SurvivalSystem plugin) {
        this.plugin = plugin;
    }

    // default skip night prevention
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTimeSkip(TimeSkipEvent event) {
        if (event.getSkipReason().equals(TimeSkipEvent.SkipReason.NIGHT_SKIP)) {
            event.setCancelled(true);
        }
    }

}
