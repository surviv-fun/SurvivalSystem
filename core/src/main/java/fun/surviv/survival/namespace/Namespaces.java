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

package fun.surviv.survival.namespace;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.NamespacedKey;

/**
 * SurvivalSystem; fun.surviv.survival.namespace:Namespaces
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Namespaces {

    public static NamespacedKey MOB_LEVEL;
    public static NamespacedKey BOSS_TYPE;
    private static String namespaceName;

    public static void init(String name) {
        namespaceName = name;
        set();
    }

    public static void set() {
        MOB_LEVEL = new NamespacedKey(namespaceName, "mob_level");
        BOSS_TYPE = new NamespacedKey(namespaceName, "boss_type");
    }

    public static NamespacedKey bossNamespace(String type) {
        return new NamespacedKey(namespaceName + "-boss", type.toLowerCase());
    }

}
