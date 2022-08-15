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

package fun.surviv.survival.bosses;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * SurvivalSystem; fun.surviv.survival.bosses:SurvivBossLootTable
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public class SurvivBossLootTable {

    @Getter
    final Collection<SurvivBossLoot> extraItems;

    @Getter
    private final NamespacedKey namespacedKey;

    public SurvivBossLootTable(NamespacedKey namespacedKey) {
        this.namespacedKey = namespacedKey;
        this.extraItems = new ArrayList<>();
    }

    public void add(@Nullable Random random, ItemStack item) {
        this.extraItems.add(new SurvivBossLoot(item, random));
    }

}
