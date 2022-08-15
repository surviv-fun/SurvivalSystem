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

package fun.surviv.survival.systems.chat;

import fun.surviv.survival.SurvivalSystem;
import fun.surviv.survival.permissions.PermissionManager;
import fun.surviv.survival.utils.ChatUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * SurvivalSystem; fun.surviv.survival.systems.chat:DefaultChatListener
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class DefaultChatListener implements Listener {

    private final SurvivalSystem plugin;

    public DefaultChatListener(SurvivalSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        Component originalMessageComponent = event.originalMessage();

        PermissionManager perms = plugin.getPermissionManager();
        String group = perms.getDefaultPlayerGroupId(player.getUniqueId());
        String chatFormat = plugin.getChatConfig().get().getChatFormat();
        String format = plugin.getChatConfig().get().getGroupFormats().getOrDefault(group, chatFormat);

        Component toSend = ChatUtil.chatComponent(perms, player, format, originalMessageComponent);
        for (Player current : Bukkit.getOnlinePlayers()) {
            current.sendMessage(toSend);
        }
        Bukkit.getConsoleSender().sendMessage(toSend);

    }

}
