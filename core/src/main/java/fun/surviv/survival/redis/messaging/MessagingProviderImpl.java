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
import fun.surviv.survival.redis.messaging.command.defaults.PingCommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SurvivalSystem; fun.surviv.survival.redis.messaging:MessagingProviderImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public class MessagingProviderImpl implements MessagingProvider {

    private final RedisClient redis;
    private Map<String, MessagingChannel> channels;

    public MessagingProviderImpl(RedisClient redis) {
        this.redis = redis;
        this.channels = new HashMap<>();
        // register default channel
        this.channel(MessagingChannel.newChannel("coresystem", this));
        this.channel("coresystem").commandMap().register(new PingCommand());
    }

    @Override
    public RedisClient redis() {
        return redis;
    }

    @Override
    public Collection<MessagingChannel> messagingChannels() {
        return channels.values();
    }

    @Override
    public MessagingChannel channel(String name) {
        return channels.get(name);
    }

    @Override
    public void channel(MessagingChannel channel) {
        if (!channels.containsKey(channel.getName())) {
            channels.put(channel.getName(), channel);
        }
    }

}
