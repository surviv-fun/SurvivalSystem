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

import java.util.Collection;
import java.util.HashMap;

/**
 * SurvivalSystem; fun.surviv.survival.redis.messaging.command:MessagingCommandMap
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public class MessagingCommandMap {

    private HashMap<String, MessagingCommand> commandMap;

    public MessagingCommandMap() {
        this.commandMap = new HashMap<>();
    }

    public Collection<MessagingCommand> commands() {
        return commandMap.values();
    }

    public void register(MessagingCommand command) {
        if (!commandMap.containsKey(command.getName())) {
            commandMap.put(command.getName(), command);
            if (command.getAliases().size() > 1) {
                command.getAliases().forEach(alias -> {
                    if (!commandMap.containsKey(command.getName())) {
                        commandMap.put(alias, command);
                    }
                });
            }
        }
    }

    public void remove(String command) {
        commandMap.remove(command);
    }

    public MessagingCommand get(String name) {
        return commandMap.get(name);
    }

}
