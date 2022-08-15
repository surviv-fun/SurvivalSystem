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

package fun.surviv.survival.redis;

import fun.surviv.survival.redis.client.RedisClient;
import fun.surviv.survival.redis.client.RedisPubSubListener;
import fun.surviv.survival.redis.conf.RedisConfig;
import fun.surviv.survival.redis.messaging.MessagingProvider;
import fun.surviv.survival.redis.messaging.MessagingProviderImpl;

/**
 * SurvivalSystem; fun.surviv.survival.redis:RedisProviderImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public class RedisProviderImpl implements RedisProvider {

    private RedisClient client;
    private MessagingProvider messagingProvider;

    public RedisProviderImpl(RedisConfig config) {
        this.client = RedisClient.fromConfig(config);
        this.messagingProvider = new MessagingProviderImpl(this.client);
    }

    @Override
    public RedisClient client() {
        return client;
    }

    @Override
    public RedisPubSubListener pubSubListener() {
        return client.pubSubListener();
    }

    @Override
    public MessagingProvider messaging() {
        return messagingProvider;
    }

}
