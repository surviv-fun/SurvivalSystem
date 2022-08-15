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
import fun.surviv.survival.database.MongoDB;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.players:PlayerProviderImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class PlayerProviderImpl implements PlayerProvider {

    private final SurvivalSystem plugin;
    private final MongoDB db;

    @Getter
    private final Map<UUID, CachedPlayer> cachedPlayers;
    @Getter
    private final Map<UUID, SurvivalPlayer> survivalPlayers;

    public PlayerProviderImpl(SurvivalSystem plugin) {
        this.plugin = plugin;
        this.db = plugin.getCore().db();
        this.cachedPlayers = new HashMap<>();
        this.survivalPlayers = new HashMap<>();
    }

    @Override
    public CachedPlayer cached(UUID uuid) {
        CachedPlayer player = cachedPlayers.get(uuid);
        if (player == null) {
            player = new CachedPLayerImpl(uuid);
            cachedPlayers.put(uuid, player);
        }
        return player;
    }

    @Override
    public CachedPlayer cached(String name) {
        CachedPlayer player = cachedPlayers.values().stream().filter(current -> current.username() == name).findFirst().get();
        return cached(player.uuid());
    }

    @Override
    public Collection<CachedPlayer> allCached() {
        return cachedPlayers.values();
    }

    @Override
    public SurvivalPlayer online(UUID uuid) {
        return survivalPlayers.get(uuid);
    }

    @Override
    public SurvivalPlayer online(String name) {
        CachedPlayer player = survivalPlayers.values().stream().filter(current -> current.username() == name).findFirst().get();
        return online(player.uuid());
    }

    @Override
    public Collection<SurvivalPlayer> allOnline() {
        return null;
    }

    @Override
    public String getName(UUID uuid) {
        return null;
    }

    @Override
    public UUID getUniqueId(String name) {
        return null;
    }

    @Override
    public boolean isPlayerLoaded(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerLoaded(String name) {
        return false;
    }

    @Override
    public boolean existPlayerByUniqueId(UUID uuid) {
        return false;
    }

    @Override
    public boolean existPlayerByName(String name) {
        return false;
    }

    @Override
    public int playerCount(boolean online) {
        return online ? survivalPlayers.size() : cachedPlayers.size();
    }

    public MongoDB db() {
        return db;
    }

    public Map<UUID, CachedPlayer> getCachedPlayers() {
        return cachedPlayers;
    }

    public Map<UUID, SurvivalPlayer> getSurvivalPlayers() {
        return survivalPlayers;
    }

}
