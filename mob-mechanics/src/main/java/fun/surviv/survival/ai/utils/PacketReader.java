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

package fun.surviv.survival.ai.utils;

import fun.surviv.survival.utils.Reflection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * SurvivalSystem; fun.surviv.survival.utils:PacketReader
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class PacketReader implements Reflection {
    private Player player;

    private Channel channel;

    public PacketReader(Player player) {
        this.player = player;
    }

    public void inject() {
        CraftPlayer cp = (CraftPlayer) this.player.getPlayer();
        assert cp != null;
        this.channel = (cp.getHandle()).b.b.m;
        this.channel.pipeline().addAfter("decoder", "PacketInjector", (ChannelHandler) new MessageToMessageDecoder<Packet<?>>() {
            protected void decode(ChannelHandlerContext context, Packet<?> packet, List<Object> list) {
                list.add(packet);
            }
        });
    }

    public void unInject() {
        if (this.channel.pipeline().get("PacketInjector") != null)
            this.channel.pipeline().remove("PacketInjector");
    }

}
