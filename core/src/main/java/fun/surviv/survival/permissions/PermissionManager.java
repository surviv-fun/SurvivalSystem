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

package fun.surviv.survival.permissions;

import fun.surviv.survival.configuration.JsonConfig;
import fun.surviv.survival.configuration.types.ChatConfig;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;

import java.util.SortedMap;
import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.permissions:PermissionManager
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class PermissionManager {

    @Getter
    private final LuckPerms luckPerms;
    @Getter
    private final JsonConfig<ChatConfig> chatConfig;

    public PermissionManager(JsonConfig<ChatConfig> chatConfig, LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
        this.chatConfig = chatConfig;
    }

    public String getDefaultPlayerGroupId(UUID uuid) {
        try {
            return luckPerms.getUserManager().getUser(uuid).getPrimaryGroup();
        } catch (Exception except) {
            return null;
        }
    }

    public String resolveGroupPrefix(String groupId) {
        return chatConfig.get().getPrefixOverwrites().getOrDefault(groupId, "");
    }

    public String resolvePlayerGroupPrefix(UUID uuid) {
        return resolveGroupPrefix(getDefaultPlayerGroupId(uuid));
    }

    public boolean isHigherGroup(String group_should_be_higher, String group_should_be_lower) {
        int higher = luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight().getAsInt() : 0;
        int lower = luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight().getAsInt() : 0;
        if (higher <= lower) {
            return false;
        }
        return true;
    }

    public boolean isHigherPlayer(UUID player_should_be_higher, UUID player_should_be_lower) {
        return isHigherGroup(resolvePlayerGroupPrefix(player_should_be_higher), resolvePlayerGroupPrefix(player_should_be_lower));
    }

    public CachedMetaData playerMeta(UUID player) {
        return loadUser(player).getCachedData().getMetaData();
    }

    public CachedMetaData groupMeta(String group) {
        return getLuckPerms().getGroupManager().getGroup(group).getCachedData().getMetaData();
    }

    public User loadUser(UUID player) {
        return getLuckPerms().getUserManager().getUser(player);
    }

    public Group loadUser(String group) {
        return getLuckPerms().getGroupManager().getGroup(group);
    }

    public String getPrefix(UUID player) {
        String prefix = playerMeta(player).getPrefix();
        return (prefix != null) ? prefix : "";
    }

    public String getSuffix(UUID player) {
        String suffix = playerMeta(player).getSuffix();
        return (suffix != null) ? suffix : "";
    }

    public String getPrefixes(UUID player) {
        SortedMap<Integer, String> map = playerMeta(player).getPrefixes();
        StringBuilder prefixes = new StringBuilder();
        for (String prefix : map.values()) {
            prefixes.append(prefix);
        }
        return prefixes.toString();
    }

    public String getSuffixes(UUID player) {
        SortedMap<Integer, String> map = playerMeta(player).getSuffixes();
        StringBuilder suffixes = new StringBuilder();
        for (String prefix : map.values()) {
            suffixes.append(prefix);
        }
        return suffixes.toString();
    }

}
