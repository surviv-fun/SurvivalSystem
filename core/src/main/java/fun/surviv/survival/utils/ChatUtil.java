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

package fun.surviv.survival.utils;

import fun.surviv.survival.Constants;
import fun.surviv.survival.permissions.PermissionManager;
import fun.surviv.survival.serialization.ComponentSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

/**
 * SurvivalSystem; fun.surviv.survival.utils:ChatUtil
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatUtil {

    /**
     * @param message
     * @return component
     */
    public static Component toColoredComponent(String message) {
        return ComponentSerializer.etAndHEX.deserialize(message);
    }

    /**
     * Translate placeholders of placeholder api ( if not enabled placeholders will not be translated )
     *
     * @param player
     * @param message
     * @return messageReplacement
     */
    public static String replacePlaceholders(Player player, String message) {
        return message; // TODO: replace placeholders
    }

    /**
     * Check if placeholder api is enabled
     *
     * @return enabled
     */
    public static boolean isPlaceholderAPIEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    public static Component chatComponent(PermissionManager perms, Player sender, String format, Component message) {
        String originalMessage = ComponentSerializer.etAndHEX.serialize(message);

        if (sender.hasPermission("core.chat.colorized.hex")) {
            message = ComponentSerializer.etAndHEX.deserialize(originalMessage);
        } else if (sender.hasPermission("core.chat.colorized")) {
            message = ComponentSerializer.etOnly.deserialize(originalMessage);
        }

        String senderGroup = perms.getDefaultPlayerGroupId(sender.getUniqueId());
        String senderPrefixOverwrite = perms.resolvePlayerGroupPrefix(sender.getUniqueId());
        format = format.replace("{world}", sender.getWorld().getName())
                .replace(
                        "{prefix}",
                        senderPrefixOverwrite != null && senderPrefixOverwrite != "" ? senderPrefixOverwrite : perms.getPrefix(sender.getUniqueId())
                )
                .replace(
                        "{prefixes}",
                        senderPrefixOverwrite != null && senderPrefixOverwrite != ""
                                ? senderPrefixOverwrite
                                : perms.getPrefixes(sender.getUniqueId())
                )
                .replace("{name}", sender.getName())
                .replace("{suffix}", perms.getSuffix(sender.getUniqueId()))
                .replace("{suffixes}", perms.getSuffixes(sender.getUniqueId()))
                .replace(
                        "{username-color}",
                        (perms.playerMeta(sender.getUniqueId()).getMetaValue("username-color") != null)
                                ? perms
                                .playerMeta(sender.getUniqueId())
                                .getMetaValue("username-color")
                                : ((perms.groupMeta(senderGroup).getMetaValue("username-color") != null) ? perms
                                        .groupMeta(senderGroup)
                                        .getMetaValue("username-color") : "")
                )
                .replace(
                        "{message-color}",
                        (perms.playerMeta(sender.getUniqueId()).getMetaValue("message-color") != null)
                                ? perms
                                .playerMeta(sender.getUniqueId())
                                .getMetaValue("message-color")
                                : ((perms.groupMeta(senderGroup).getMetaValue("message-color") != null) ? perms
                                        .groupMeta(senderGroup)
                                        .getMetaValue("message-color") : "")
                );
        format = replacePlaceholders(sender, format);
        Component toSend = Component.empty();
        String[] formatParts = format.split("message");

        toSend.append(ComponentSerializer.etAndHEX.deserialize(formatParts[0])).append(message);
        if (formatParts.length > 1) {
            toSend.append(ComponentSerializer.etAndHEX.deserialize(formatParts[1]));
        }

        return toSend;
    }


    /**
     * Reply with a message to a sender
     *
     * @param sender
     * @param message
     * @return success
     */
    public static boolean reply(CommandSender sender, String message) {
        return replySenderComponent(sender, message);
    }

    /**
     * Reply with a message to a sender
     *
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replySenderComponent(CommandSender sender, String message) {
        sender.sendMessage(
                Component.empty()
                        .append(Constants.CHAT_PREFIX_COMPONENT)
                        .append(ComponentSerializer.etAndHEX.deserialize(message))
        );
        return true;
    }

    /**
     * Reply with a message to a sender replyUnPrefixedSenderComponent
     *
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replyUnPrefixedSenderComponent(CommandSender sender, String message) {
        sender.sendMessage(
                ComponentSerializer.etAndHEX.deserialize(message)
        );
        return true;
    }

    /**
     * Reply with a message to a sender replyUnPrefixedSenderComponent
     *
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replyUnPrefixed(CommandSender sender, String message) {
        return replyUnPrefixedSenderComponent(sender, message);
    }

    /**
     * Reply with a component message to a sender
     *
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replySender(CommandSender sender, Component message) {
        sender.sendMessage(message);
        return true;
    }

    /**
     * Reply with a actionbar popup to a player
     *
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replyHologramComponent(Player sender, String message) {
        sender.sendActionBar(
                ComponentSerializer.etAndHEX.deserialize(message)
        );
        return true;
    }

    /**
     * reply with a title
     *
     * @param sender
     * @param message ( The smaller shown text of the title )
     * @return success
     */
    public static boolean replyTitleComponent(Player sender, String message) {
        sender.showTitle(
                Title.title(
                        Component.empty(),
                        ComponentSerializer.etAndHEX.deserialize(message)
                )
        );
        return true;
    }

    public static boolean replyTitleComponent(Player sender, String title, String message) {
        sender.showTitle(
                Title.title(
                        ComponentSerializer.etAndHEX.deserialize(title),
                        ComponentSerializer.etAndHEX.deserialize(message)
                )
        );
        return true;
    }

    /**
     * reply with a title
     *
     * @param sender
     * @param title   ( The bigger shown text of the title )
     * @param message ( The smaller shown text of the title )
     * @param fadeIn  the time to show the title in seconds
     * @param stay    the time to stay of title in seconds
     * @param fadeOut the time to hide the title in seconds
     * @return success
     */
    public static boolean replyTitleComponent(Player sender, String title, String message, int fadeIn, int stay, int fadeOut) {
        sender.showTitle(
                Title.title(
                        ComponentSerializer.etAndHEX.deserialize(title),
                        ComponentSerializer.etAndHEX.deserialize(message),
                        Title.Times.times(
                                Duration.ofSeconds(fadeIn),
                                Duration.ofSeconds(stay),
                                Duration.ofSeconds(fadeOut)
                        )
                )
        );
        return true;
    }

    public static boolean replyTitleComponent(Player sender, String title, String message, int stay) {
        sender.showTitle(
                Title.title(
                        ComponentSerializer.etAndHEX.deserialize(title),
                        ComponentSerializer.etAndHEX.deserialize(message),
                        Title.Times.times(
                                Duration.ofSeconds(1),
                                Duration.ofSeconds(stay),
                                Duration.ofSeconds(1)
                        )
                )
        );
        return true;
    }


}
