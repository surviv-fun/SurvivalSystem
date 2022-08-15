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

package fun.surviv.survival.bosses.timings;

import fun.surviv.survival.bosses.BossManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * SurvivalSystem; fun.surviv.survival.bosses.timings:TimingsManager
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public class TimingsManager {

    @Getter
    private final BossManager manager;
    private final Map<UUID, Consumer<Long>> bossTickTasks;
    @Getter
    private long currentTick;

    public TimingsManager(BossManager manager) {
        this.manager = manager;
        this.bossTickTasks = new HashMap<>();
        this.currentTick = Bukkit.getServer().getCurrentTick();
        new Thread(() -> {
            currentTick = Bukkit.getServer().getCurrentTick();
            (new BukkitRunnable() {
                public void run() {
                    TimingsManager.this.currentTick = Bukkit.getServer().getCurrentTick();
                    TimingsManager.this.bossTickTasks.values().forEach(task -> {
                        try {
                            task.accept(TimingsManager.this.currentTick);
                        } catch (Exception e) {
                        }
                    });
                }
            }).runTaskTimerAsynchronously(manager.getPlugin(), 0L, 1L);
        }, "bosses-timings").start();
    }

    public UUID addAsyncTask(Consumer<Long> task) {
        UUID uuid = UUID.randomUUID();
        this.bossTickTasks.put(uuid, task);
        return uuid;
    }

    public boolean removeAsyncTask(UUID uuid) {
        this.bossTickTasks.remove(uuid);
        return true;
    }

}
