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

import fun.surviv.survival.serialization.ComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * SurvivalSystem; fun.surviv.survival.items:ItemBuilder
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.meta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.meta = this.itemStack.getItemMeta();
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder displayName(String displayName) {
        meta.displayName(ComponentSerializer.sectionOnly.deserialize(ComponentSerializer.etAndHEX.serialize(ComponentSerializer.etAndHEX.deserialize(displayName))));
        return this;
    }

    public ItemBuilder name(String displayName) {
        return displayName(displayName);
    }

    public ItemBuilder unbreakable(boolean canBreak) {
        meta.setUnbreakable(canBreak);
        return this;
    }

    public ItemBuilder customModelData(int modelId) {
        meta.setCustomModelData(modelId);
        return this;
    }

    public ItemBuilder itemFlag(ItemFlag flag) {
        itemStack.getItemMeta().getItemFlags().add(flag);
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag... flags) {
        for (ItemFlag flag : flags) {
            meta.getItemFlags().add(flag);
        }
        return this;
    }

    public ItemBuilder enchantment(EnchantmentBuilder enchantment) {
        meta.addEnchant(enchantment.getType(), enchantment.getLevel(), enchantment.isIgnoreLevelRestrictions());
        return this;
    }

    public ItemBuilder enchantments(EnchantmentBuilder... enchantments) {
        for (EnchantmentBuilder enchantment : enchantments) {
            meta.addEnchant(enchantment.getType(), enchantment.getLevel(), enchantment.isIgnoreLevelRestrictions());
        }
        return this;
    }

    public ItemBuilder lore(String lore) {
        List<Component> lores = new ArrayList<>();
        lores.add(ComponentSerializer.sectionOnly.deserialize(ComponentSerializer.etAndHEX.serialize(ComponentSerializer.etAndHEX.deserialize(lore))));
        meta.lore(lores);
        return this;
    }

    public ItemBuilder lore(String... loreLines) {
        List<Component> lores = new ArrayList<>();
        for (String line : loreLines) {
            lores.add(ComponentSerializer.sectionOnly.deserialize(ComponentSerializer.etAndHEX.serialize(ComponentSerializer.etAndHEX.deserialize(line))));
        }
        meta.lore(lores);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack complete() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static class EnchantmentBuilder {

        private Enchantment type;
        private int level;
        private boolean ignoreLevelRestrictions = false;

        public EnchantmentBuilder(Enchantment enchantment, int level) {
            this.type = enchantment;
            this.level = level;
        }

        public EnchantmentBuilder(Enchantment enchantment, int level, boolean ignoreLevelRestrictions) {
            this.type = enchantment;
            this.level = level;
            this.ignoreLevelRestrictions = ignoreLevelRestrictions;
        }

        public static EnchantmentBuilder build(Enchantment enchantment, int level) {
            return new EnchantmentBuilder(enchantment, level);
        }

        public static EnchantmentBuilder build(Enchantment enchantment, int level, boolean ignoreLevelRestrictions) {
            return new EnchantmentBuilder(enchantment, level, ignoreLevelRestrictions);
        }

        public Enchantment getType() {
            return type;
        }

        public int getLevel() {
            return level;
        }

        public boolean isIgnoreLevelRestrictions() {
            return ignoreLevelRestrictions;
        }

    }

}
