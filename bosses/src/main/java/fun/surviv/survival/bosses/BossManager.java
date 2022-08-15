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

package fun.surviv.survival.bosses;

import fun.surviv.survival.bosses.listener.SurvivBossListeners;
import fun.surviv.survival.bosses.timings.TimingsManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * SurvivalSystem; fun.surviv.survival.bosses:BossManager
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public class BossManager {

    @Getter
    private static TimingsManager timingsManager;
    @Getter
    private final JavaPlugin plugin;
    @Getter
    private final SurvivBossListeners listeners;

    public BossManager(JavaPlugin plugin) {
        this.plugin = plugin;
        timingsManager = new TimingsManager(this);
        this.listeners = new SurvivBossListeners(this);
        listeners.addAll(plugin);
        SurvivBoss.init();
        this.registerCommand();
    }

    public static TimingsManager timings() {
        return timingsManager;
    }

    private synchronized void registerCommand() {

    }

    public void disable() {

    }

}
