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

package fun.surviv.survival.ai;

import fun.surviv.survival.ai.listener.*;
import fun.surviv.survival.aiconf.Settings;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * SurvivalSystem; fun.surviv.survival.ai:MobAI
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class MobAI {

    @Getter
    public static Settings settings;

    @Getter
    private static MobAI instance;
    @Getter
    private final JavaPlugin plugin;

    public MobAI(JavaPlugin plugin) {
        this.plugin = plugin;
        this.settings = new Settings();

        instance = this;

        Bukkit.getPluginManager().registerEvents(new EntityTarget(), plugin);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), plugin);
        Bukkit.getPluginManager().registerEvents(new ExplosionEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new ProjectileEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new PotionSplash(), plugin);
        Bukkit.getPluginManager().registerEvents(new TeleportEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new SpawnEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
    }

}
