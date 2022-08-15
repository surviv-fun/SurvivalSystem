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

import java.util.Collection;
import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.players:PlayerProvider
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 07.08.2022
 */
public interface PlayerProvider {

    CachedPlayer cached(UUID uuid);

    CachedPlayer cached(String name);

    Collection<CachedPlayer> allCached();

    SurvivalPlayer online(UUID uuid);

    SurvivalPlayer online(String name);

    Collection<SurvivalPlayer> allOnline();

    String getName(UUID uuid);

    UUID getUniqueId(String name);

    boolean isPlayerLoaded(UUID uuid);

    boolean isPlayerLoaded(String name);

    boolean existPlayerByUniqueId(UUID uuid);

    boolean existPlayerByName(String name);

    int playerCount(boolean online);

}
