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

package fun.surviv.survival.redis.client;

import fun.surviv.survival.redis.func.RedisTriConsumer;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * SurvivalSystem; fun.surviv.survival.redis.client:RedisPubSubListener
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public class RedisPubSubListener extends JedisPubSub {

    private final List<Consumer<String>> onPongHandlers;

    private final List<BiConsumer<String, String>> onMessageHandlers;
    private final List<BiConsumer<String, Integer>> onSubscribeHandlers;
    private final List<BiConsumer<String, Integer>> onUnsubscribeHandlers;

    private final List<RedisTriConsumer<String, String, String, Boolean>> onPMessageHandlers;
    private final List<BiConsumer<String, Integer>> onPSubscribeHandlers;
    private final List<BiConsumer<String, Integer>> onPUnsubscribeHandlers;


    public RedisPubSubListener() {
        this.onPongHandlers = new ArrayList<>();

        this.onMessageHandlers = new ArrayList<>();
        this.onSubscribeHandlers = new ArrayList<>();
        this.onUnsubscribeHandlers = new ArrayList<>();

        this.onPMessageHandlers = new ArrayList<>();
        this.onPSubscribeHandlers = new ArrayList<>();
        this.onPUnsubscribeHandlers = new ArrayList<>();
    }

    @Override
    public void onPong(String pattern) {
        for (Consumer consumer : onPongHandlers) {
            try {
                consumer.accept(pattern);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onMessage(String channel, String message) {
        for (BiConsumer consumer : onMessageHandlers) {
            try {
                consumer.accept(channel, message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        for (BiConsumer consumer : onSubscribeHandlers) {
            try {
                consumer.accept(channel, subscribedChannels);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        for (BiConsumer consumer : onUnsubscribeHandlers) {
            try {
                consumer.accept(channel, subscribedChannels);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        for (RedisTriConsumer<String, String, String, Boolean> consumer : onPMessageHandlers) {
            try {
                consumer.accept(pattern, channel, message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        for (BiConsumer consumer : onPSubscribeHandlers) {
            try {
                consumer.accept(pattern, subscribedChannels);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        for (BiConsumer consumer : onPUnsubscribeHandlers) {
            try {
                consumer.accept(pattern, subscribedChannels);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Consumer<String>> pongHandlers() {
        return onPongHandlers;
    }

    public List<BiConsumer<String, String>> messageHandlers() {
        return onMessageHandlers;
    }

    public List<BiConsumer<String, Integer>> subscribeHandlers() {
        return onSubscribeHandlers;
    }

    public List<BiConsumer<String, Integer>> unsubscribeHandlers() {
        return onUnsubscribeHandlers;
    }

    public List<RedisTriConsumer<String, String, String, Boolean>> pMessageHandlers() {
        return onPMessageHandlers;
    }

    public List<BiConsumer<String, Integer>> pSubscribeHandlers() {
        return onPSubscribeHandlers;
    }

    public List<BiConsumer<String, Integer>> pUnsubscribeHandlers() {
        return onPUnsubscribeHandlers;
    }

}
