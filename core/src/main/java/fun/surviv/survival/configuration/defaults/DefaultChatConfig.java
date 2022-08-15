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

import fun.surviv.survival.configuration.types.ChatConfig;

import java.util.Map;

/**
 * SurvivalSystem; fun.surviv.survival.configuration.defaults:DefaultChatConfig
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class DefaultChatConfig extends ChatConfig {

    public DefaultChatConfig(String version) {
        super(
                version,
                "{prefix}&r {username-color}{name}&r {suffix}&r &r&#CCCCCC»&r {message-color}{message}&r",
                Map.ofEntries(
                        Map.entry("group", "[group] {name} &r» {message}")
                ),
                Map.ofEntries(
                        Map.entry("group", "[group]")
                ),
                Map.ofEntries(
                        Map.entry("sender", "&o&7Du schreibst {target}: {message}"),
                        Map.entry("target", "&o&7{sender} schreibt dir: {message}")
                )
        );
    }

}
