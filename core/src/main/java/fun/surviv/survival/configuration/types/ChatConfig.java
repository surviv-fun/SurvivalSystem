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
import java.util.Map;

/**
 * SurvivalSystem; fun.surviv.survival.configuration.types:ChatConfig
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
@AllArgsConstructor
@Data
public class ChatConfig implements Serializable {

    private String configVersion;
    private String chatFormat;
    private Map<String, String> groupFormats;
    private Map<String, String> prefixOverwrites;
    private Map<String, String> msgFormats;

}