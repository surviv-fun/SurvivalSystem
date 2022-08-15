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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * SurvivalSystem; fun.surviv.survival.utils:StaticUtil
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StaticUtil {

    public static int getOnlinePlayerCountInWorld(World world) {
        return world.getPlayerCount();
    }

    public static int getEntityCountInWorld(World world) {
        return world.getEntityCount();
    }

    public static String getBukkitVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static String formatTimeNow() {
        LocalTime localtime = LocalTime.now();
        int h = localtime.getHour();
        int m = localtime.getMinute();
        int s = localtime.getSecond();
        String hh = String.valueOf(h);
        String mm = String.valueOf(m);
        String ss = String.valueOf(s);
        if (h < 10) {
            hh = "0" + h;
        }
        if (m < 10) {
            mm = "0" + m;
        }
        if (s < 10) {
            ss = "0" + s;
        }
        String date = hh + ":" + mm + ":" + ss;
        return date;
    }

    public static String formatDateNow() {
        LocalDate localDate = LocalDate.now();
        int dd = localDate.getDayOfMonth();
        int mm = localDate.getMonthValue();
        int yyyy = localDate.getYear();
        String date = dd + ":" + mm + ":" + yyyy;
        if (dd < 10) {
            date = "0" + dd + ":" + mm + ":" + yyyy;
        }
        if (mm < 10) {
            date = dd + ":0" + mm + ":" + yyyy;
        }
        if (dd < 10 && mm < 10) {
            date = "0" + dd + ":0" + mm + ":" + yyyy;
        }
        return date;
    }

    public static String getDateAsString(LocalDate localDate) {
        int dd = localDate.getDayOfMonth();
        int mm = localDate.getMonthValue();
        int yyyy = localDate.getYear();
        String date = dd + ":" + mm + ":" + yyyy;
        if (dd < 10) {
            date = "0" + dd + ":" + mm + ":" + yyyy;
        }
        if (mm < 10) {
            date = dd + ":0" + mm + ":" + yyyy;
        }
        if (dd < 10 && mm < 10) {
            date = "0" + dd + ":0" + mm + ":" + yyyy;
        }
        return date;
    }

}

