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

package fun.surviv.survival.configuration.defaults;

import fun.surviv.survival.configuration.types.NerfControlConfig;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

/**
 * SurvivalSystem; fun.surviv.survival.configuration.defaults:DefaultNerfControlConfig
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class DefaultNerfControlConfig extends NerfControlConfig {

    public DefaultNerfControlConfig(String version) {
        super(
                version,
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                15
        );
        for (EntityType type : EntityType.values()) {
            if (type.isAlive() && type.isSpawnable() && type != EntityType.PLAYER) {
                getAddDamageLevel().put(type, 0.04);
                getAddHealthLevel().put(type, 0.11);
                getGeneralDamageMultiplier().put(type, 1.2);
                getGeneralHealthMultiplier().put(type, 1.4);
            }
        }
    }

}
