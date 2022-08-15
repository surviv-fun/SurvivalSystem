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

package fun.surviv.survival.ai.mobs.attacks;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.attacks:GuardianAttack
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public enum GuardianAttack {
    NORMAL_ATTACK("NORMAL_ATTACK"), SUFFOCATION_VIBES_ATTACK("SUFFOCATION_VIBES_ATTACK"), NAILING_VIBES_ATTACK("NAILING_VIBES_ATTACK"), INSTANT_LASER_ATTACK("INSTANT_LASER_ATTACK");

    private final String name;

    GuardianAttack(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
