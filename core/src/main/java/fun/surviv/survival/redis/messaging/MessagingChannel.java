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

package fun.surviv.survival.redis.messaging;

import fun.surviv.survival.redis.client.RedisClient;
import fun.surviv.survival.redis.messaging.command.MessagingCommand;
import fun.surviv.survival.redis.messaging.command.MessagingCommandMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * SurvivalSystem; fun.surviv.survival.redis.messaging:MessagingChannel
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public interface MessagingChannel {

    String getName();

    MessagingCommandMap commandMap();

    void sendMessage(String message);

    void sendCommand(String server, String command);

    void sendGlobalCommand(String command);

    MessagingProvider service();

    void onMessage(Consumer<String> message);

    class DefaultChannel implements MessagingChannel {

        private final String name;
        private final MessagingCommandMap commandMap;
        private final List<Consumer> messageHandlers;
        private final MessagingProvider service;

        public DefaultChannel(String name, MessagingProvider service) {
            this.name = name;
            this.service = service;
            this.commandMap = new MessagingCommandMap();
            this.messageHandlers = new ArrayList<>();
            this.service().redis().pubSubListener().messageHandlers().add((channel, message) -> {
                if (channel != name) {
                    return;
                }
                if (!message.contains(RedisClient.splitter)) {
                    messageHandlers.forEach(handler -> handler.accept(message));
                    return;
                }
                String session = message.split(RedisClient.splitter)[0];
                try {
                    UUID uuid = UUID.fromString(session);
                } catch (Exception e) {
                    messageHandlers.forEach(handler -> handler.accept(message));
                    return;
                }

                String onlyContent = message.strip().split(RedisClient.splitter)[1];
                String allArgs[] = onlyContent.strip().split(Sep.CMD.get());
                if (allArgs.length > 0) {
                    switch (allArgs[0].toLowerCase()) {
                        case "server": {
                            if (message.startsWith(RedisClient.session)) {
                                return;
                            }
                            try {
                                String server = allArgs[1];
                                if (server.equalsIgnoreCase(RedisClient.server)) {
                                    break;
                                }
                                String command = allArgs[2];
                                MessagingCommand cmd = commandMap.get(command);
                                cmd.execute(this, command, allArgs[3].split("\\s+"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "global": {
                            try {
                                String command = allArgs[1];
                                MessagingCommand cmd = commandMap.get(command);
                                cmd.execute(this, command, allArgs[1].split("\\s+"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        default: {
                            break;
                        }
                    }
                }
            });
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public MessagingCommandMap commandMap() {
            return commandMap;
        }

        @Override
        public void sendMessage(String message) {
            service().redis().sendMessage(name, message);
        }

        @Override
        public void sendCommand(String server, String command) {
            sendMessage("GLOBAL" + Sep.CMD.get() + server + Sep.CMD.get() + command);
        }

        @Override
        public void sendGlobalCommand(String command) {
            sendMessage("GLOBAL" + Sep.CMD.get() + command);
        }

        @Override
        public MessagingProvider service() {
            return service;
        }

        @Override
        public void onMessage(Consumer<String> message) {
            messageHandlers.add(message);
        }

    }

    enum Sep {
        CMD("//::\\\\");
        public String s;

        Sep(String s) {
            this.s = s;
        }

        public String get() {
            return this.s;
        }
    }

    static MessagingChannel newChannel(String name, MessagingProvider service) {
        return new DefaultChannel(name, service);
    }

}
