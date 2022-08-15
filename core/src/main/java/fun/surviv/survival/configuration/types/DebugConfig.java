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

package fun.surviv.survival.configuration.types;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * SurvivalSystem; fun.surviv.survival.configuration.types:DebugConfig
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 09.08.2022
 */
@AllArgsConstructor
@Data
public class DebugConfig implements Serializable {

    private String configVersion;
    private boolean debugEntityDamage;
    private boolean debugHumanDamage;
    private boolean debugLevels;
    private boolean debugLevelsAddNameTag;
    private boolean debugHealth;
    private boolean debugPlayerJoin;
    private boolean debugPlayerQuit;
    private boolean debugPhantom;
    private boolean debugBedEnter;
    private boolean debugSkipTime;
    private boolean debugWorldProtection;
    private boolean debugWorldProtectFlag;
    private boolean debugTeleport;
    private boolean debugLocation;
    private boolean debugPortals;
    private boolean debugRegions;
    private boolean debugRegionBoss;
    private boolean debugBossDeath;
    private boolean debugZomBum;
    private boolean debugZomBumDeath;
    private boolean debugOthers;

}
