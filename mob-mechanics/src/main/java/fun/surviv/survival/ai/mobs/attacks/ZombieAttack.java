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
 * SurvivalSystem; fun.surviv.survival.ai.mobs.attacks:ZombieAttack
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public enum ZombieAttack {
    NORMAL_ATTACK("NORMAL_ATTACK"), BLOOD_RUSH_ATTACK("BLOOD_RUSH_ATTACK"), VAMPIRE_ATTACK("VAMPIRE_ATTACK"), MINIONS_ATTACK("MINIONS_ATTACK");

    private final String name;

    ZombieAttack(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}