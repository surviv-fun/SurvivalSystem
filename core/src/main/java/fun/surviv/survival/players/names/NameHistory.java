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

package fun.surviv.survival.players.names;

import java.util.List;
import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.players.names:NameHistory
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public interface NameHistory {

    /**
     * uuid()
     *
     * @return uuid
     */
    UUID uuid();

    /**
     * current()
     *
     * @return current
     */
    String current();

    /**
     * entries()
     *
     * @return entries
     */
    List<NameHistoryEntry> entries();

    /**
     * currentEntry()
     *
     * @return entry
     */
    NameHistoryEntry currentEntry();

    /**
     * firstJoined()
     *
     * @return entry
     */
    NameHistoryEntry firstJoined();

    /**
     * Add a name entry into the name history
     *
     * @param name
     */
    boolean addEntry(String name);

}