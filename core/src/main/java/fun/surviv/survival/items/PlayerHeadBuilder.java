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

package fun.surviv.survival.items;

import fun.surviv.survival.accounts.MinecraftAccountValidator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.items:PlayerHeadBuilder
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public class PlayerHeadBuilder {

    private ItemBuilder itemBuilder;
    private SkullMeta skullMeta;

    private UUID ownerUuid;

    public PlayerHeadBuilder() {
        this.itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
    }

    public PlayerHeadBuilder owner(String owner) throws IllegalArgumentException {
        this.ownerUuid = MinecraftAccountValidator.resolveUUID(owner);
        if(this.ownerUuid == null) throw new IllegalArgumentException("This player does not exist at the mojang database!");
        return this;
    }

    public PlayerHeadBuilder owner(UUID owner) {
        this.ownerUuid = owner;
        return this;
    }

    public PlayerHeadBuilder name(String name) {
        itemBuilder.name(name);
        return this;
    }

    public PlayerHeadBuilder displayName(String name) {
        itemBuilder.displayName(name);
        return this;
    }

    public ItemStack build() {
        ItemStack skull = this.itemBuilder.build();
        skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUuid));
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

}
