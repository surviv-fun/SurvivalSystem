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

package fun.surviv.survival.bosses;

import fun.surviv.survival.bosses.entities.DarkLord;
import fun.surviv.survival.bosses.entities.Necromancer;
import fun.surviv.survival.bosses.entities.Zombum;
import fun.surviv.survival.bosses.entities._BossUnknown;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.EntityType;

/**
 * SurvivalSystem; fun.surviv.survival.bosses:SurvivBossType
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
@AllArgsConstructor
public enum SurvivBossType {

    UNKNOWN(_BossUnknown.ID, EntityType.UNKNOWN, "unknown"),
    DARKLORD(DarkLord.ID, EntityType.EVOKER, "darklord"),
    ZOMBUM(Zombum.ID, EntityType.ZOMBIE, "zombum"),
    NECROMANCER(Necromancer.ID, EntityType.WITCH, "necromancer");

    @Getter
    private int id;
    @Getter
    private EntityType entityType;
    @Getter
    private String idName;

    public static SurvivBossType byId(int id) {
        try {
            for (SurvivBossType t : values()) {
                if (t.getId() == id) {
                    return t;
                }
            }
            return UNKNOWN;
        } catch (Exception exception) {
            return UNKNOWN;
        }
    }

}
