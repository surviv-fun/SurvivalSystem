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

package fun.surviv.survival.redis.messaging.command;

import fun.surviv.survival.redis.messaging.MessagingChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * SurvivalSystem; fun.surviv.survival.redis.messaging.command:MessagingCommand
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public abstract class MessagingCommand {

    private final String name;
    private List<String> aliases;

    public MessagingCommand(String name) {
        this.name = name;
        aliases = new ArrayList<>();
    }

    public abstract boolean execute(MessagingChannel channel, String alias, String[] args);

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

}
