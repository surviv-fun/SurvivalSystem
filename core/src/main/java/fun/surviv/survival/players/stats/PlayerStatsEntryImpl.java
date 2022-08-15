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

package fun.surviv.survival.players.stats;

/**
 * SurvivalSystem; fun.surviv.survival.players.options:PlayerStatsEntryImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class PlayerStatsEntryImpl implements PlayerStatsEntry {

    private String value;

    public PlayerStatsEntryImpl(String value) {
        this.value = value;
    }

    @Override
    public void value(String value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public boolean asBoolean() {
        return Boolean.valueOf(value);
    }

    @Override
    public int asInt() {
        return Integer.valueOf(value);
    }

    @Override
    public long asLong() {
        return Long.valueOf(value);
    }

    @Override
    public double asDouble() {
        return Double.valueOf(value);
    }

    @Override
    public float asFloat() {
        return Float.valueOf(value);
    }

}
