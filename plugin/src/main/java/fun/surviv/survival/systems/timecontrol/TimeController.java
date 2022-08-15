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

package fun.surviv.survival.systems.timecontrol;

import fun.surviv.survival.SurvivalSystem;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * SurvivalSystem; fun.surviv.survival.systems.timecontrol:TimeController
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class TimeController {

    private final SurvivalSystem plugin;
    private final Map<String, Long> counts;

    private final long dayMinutes;
    private final long nightMinutes;

    private final long dayTicks;
    private final long nightTicks;

    public TimeController(SurvivalSystem plugin) {
        this.plugin = plugin;
        this.counts = new HashMap<>();
        dayMinutes = plugin.getTimeControlConfig().get().getDayCycleMinutes();
        nightMinutes = plugin.getTimeControlConfig().get().getNightCycleMinutes();
        dayTicks = convertMinsToTicks(dayMinutes);
        nightTicks = convertMinsToTicks(nightMinutes);
    }

    public static boolean isDay(World world) {
        long time = world.getTime();
        return (time >= 0L && time < 12000L);
    }

    public static boolean isNight(World world) {
        return !isDay(world);
    }

    public void runCycles(final World world) {
        (new BukkitRunnable() {
            public void run() {
                long time = world.getTime();
                if (isDay(world)) {
                    setTime(world, dayTicks);
                } else if (TimeController.isNight(world)) {
                    setTime(world, nightTicks);
                } else {
                    plugin.getLogger().warning(world.getName() + " time " + time + " is impossible");
                }
            }
        }).runTaskTimer(plugin, 0, 1L);
        plugin.getLogger().info("Running day and night cycles for world '" + world.getName() + "'");
    }

    public void setTime(World world, long val) {
        String w = world.getName();
        counts.putIfAbsent(w, 0L);

        double ratio = 1.0D / val / 12000.0D;
        long currentTime = world.getTime();

        if (ratio > 1.0D) {
            currentTime += Math.round(ratio);
            world.setTime(currentTime);
            counts.put(w, 0L);
        }
        if (ratio < 1.0D) {
            long count = counts.get(w);
            if (count <= 0L) {
                currentTime++;
                world.setTime(currentTime);
            } else {
                counts.put(w, count - 1L);
            }
        }

        world.setTime(++currentTime);
    }

    public void setDaylightCycle(boolean value) {
        Bukkit.getWorlds()
                .stream()
                .filter(world -> this.plugin.getTimeControlConfig().get().getEnabledWorlds().contains(world.getName()))
                .forEach(world -> {
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, Boolean.valueOf(value));
                    plugin
                            .getLogger()
                            .warning("Setting GameRule.DO_DAYLIGHT_CYCLE to " + value + " for world '" + world.getName() + "'");
                });
    }

    public long convertMinsToTicks(long min) {
        return min * 60L * 20L;
    }

}
