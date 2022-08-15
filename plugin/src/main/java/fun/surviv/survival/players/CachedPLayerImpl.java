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

import fun.surviv.survival.players.meta.PlayerMeta;
import fun.surviv.survival.players.meta.PlayerMetaImpl;
import fun.surviv.survival.players.names.NameHistory;
import fun.surviv.survival.players.names.NameHistoryImpl;
import fun.surviv.survival.players.options.PlayerOptions;
import fun.surviv.survival.players.options.PlayerOptionsImpl;
import fun.surviv.survival.players.stats.PlayerStats;
import fun.surviv.survival.players.stats.PlayerStatsImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.players:CachedPLayerImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class CachedPLayerImpl implements CachedPlayer {

    private final OfflinePlayer player;
    private PlayerMeta meta;
    private NameHistory names;
    private PlayerOptions options;

    private PlayerStats stats;

    public CachedPLayerImpl(UUID player) {
        this.player = Bukkit.getOfflinePlayer(player);
        this.meta = new PlayerMetaImpl(this);
        this.names = new NameHistoryImpl(this);
        this.options = new PlayerOptionsImpl(this);
        this.stats = new PlayerStatsImpl(this);
    }

    @Override
    public UUID uuid() {
        return player.getUniqueId();
    }

    @Override
    public String username() {
        return player.getName();
    }

    @Override
    public String displayName() {
        return player.getName();
    }

    @Override
    public boolean online() {
        return player.isOnline();
    }

    @Override
    public NameHistory nameHistory() {
        return names;
    }

    @Override
    public PlayerOptions options() {
        return options;
    }

    @Override
    public PlayerMeta meta() {
        return meta;
    }

    @Override
    public PlayerStats stats() {
        return stats;
    }

}
