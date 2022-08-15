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

import net.kyori.adventure.identity.Identity;

import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.players:SurvivalPlayer
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 07.08.2022
 */
public interface SurvivalPlayer extends CachedPlayer {

    /**
     * Get the players {@link Identity } object
     *
     * @return - the players identity object
     */
    Identity identity();

    /**
     * Get the players {@link UUID }
     *
     * @return - the UUID of the player
     */
    default UUID uuid() {
        return this.identity().uuid();
    }

    /**
     * Get the current server of the player
     *
     * @return The current server id as String or null if offline
     */
    String getCurrentServer();

}
