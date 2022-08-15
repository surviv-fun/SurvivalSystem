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

import fun.surviv.survival.bosses.entities.DarkLord;
import fun.surviv.survival.bosses.entities.Necromancer;
import fun.surviv.survival.bosses.entities.Zombum;
import fun.surviv.survival.bosses.entities._BossUnknown;
import fun.surviv.survival.namespace.Namespaces;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

/**
 * SurvivalSystem; fun.surviv.survival.bosses:SurvivBoss
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public abstract class SurvivBoss extends AbstractSurvivBossEntity implements SurvivBossID {

    public static Class<? extends SurvivBoss>[] list = null;
    public static SurvivBoss[] fullList = null;
    public static Map<NamespacedKey, SurvivBoss> fullMap = new HashMap<>();

    @Getter
    private NamespacedKey namespacedKey;

    public SurvivBoss(final SurvivBossType type) {
        super(type);
        this.namespacedKey = Namespaces.bossNamespace(type.toString());
    }

    public SurvivBoss(final int id) {
        super(SurvivBossType.byId(id));
        this.namespacedKey = Namespaces.bossNamespace(getSurvivBossType().toString());
    }

    public static void init() {
        if (list == null) {
            list = new Class[64];
            fullList = new SurvivBoss[64];
            list[UNKNOWN] = _BossUnknown.class;
            list[ZOMBUM] = Zombum.class;
            list[DARKLORD] = DarkLord.class;
            list[NECROMANCER] = Necromancer.class;
        }

        for (int id = 0; id < list.length; id++) {
            try {
                Class clazz = list[id];
                try {
                    fullList[id] = (SurvivBoss) clazz.newInstance();
                } catch (Exception e) {
                    fullList[id] = new _BossUnknown();
                    Bukkit.getServer().getLogger().warning("Error while registering " + clazz.getName() + "\n" + e);
                }
            } catch (Exception exception) {
                fullList[id] = new _BossUnknown();
            }
            if (!fullMap.containsKey(fullList[id].getNamespacedKey())) fullMap.put(fullList[id].getNamespacedKey(), fullList[id]);
        }
    }

    public static SurvivBoss get(int id) {
        try {
            return (SurvivBoss) fullList[id].clone();
        } catch (Exception exception) {
            return null;
        }
    }

    public static SurvivBoss get(NamespacedKey namespacedKey) {
        try {
            return fullMap.get(namespacedKey);
        } catch (Exception exception) {
            return null;
        }
    }

    public static SurvivBoss get(SurvivBossType type) {
        try {
            return (SurvivBoss) fullList[(type.getId())].clone();
        } catch (Exception exception) {
            return null;
        }
    }

    public static SurvivBoss get(String id) {
        id = id.toUpperCase();
        try {
            for (SurvivBoss boss : fullList) {
                if (boss.getSurvivBossType().getIdName() == id) {
                    return (SurvivBoss) boss.clone();
                }
            }
            return get(SurvivBossType.UNKNOWN);
        } catch (Exception exception) {
            return null;
        }
    }

}
