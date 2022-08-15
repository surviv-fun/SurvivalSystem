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

package fun.surviv.survival.players;

import fun.surviv.survival.SurvivalSystem;
import fun.surviv.survival.players.meta.PlayerMeta;
import fun.surviv.survival.players.names.NameHistory;
import fun.surviv.survival.players.options.PlayerOptions;
import fun.surviv.survival.players.stats.PlayerStats;
import net.kyori.adventure.identity.Identity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.players:SurvivalPlayerImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class SurvivalPlayerImpl implements SurvivalPlayer {

    private final Player player;
    private final CachedPlayer cached;

    public SurvivalPlayerImpl(UUID player) {
        this.cached = new CachedPLayerImpl(player);
        this.player = Bukkit.getPlayer(player);
    }

    public SurvivalPlayerImpl(CachedPlayer player) {
        this.cached = player;
        this.player = Bukkit.getPlayer(player.uuid());
    }

    @Override
    public String username() {
        return player.getName();
    }

    @Override
    public String displayName() {
        return player.getDisplayName();
    }

    @Override
    public boolean online() {
        return player.isOnline();
    }

    @Override
    public NameHistory nameHistory() {
        return cached.nameHistory();
    }

    @Override
    public PlayerOptions options() {
        return cached.options();
    }

    @Override
    public PlayerMeta meta() {
        return cached.meta();
    }

    @Override
    public PlayerStats stats() {
        return cached.stats();
    }

    @Override
    public Identity identity() {
        return player.identity();
    }

    @Override
    public String getCurrentServer() {
        return SurvivalSystem.getInstance().getName();
    }

}
