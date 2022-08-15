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

package fun.surviv.survival.bosses.command.sub;

import fun.surviv.survival.bosses.AbstractSurvivBossEntity;
import fun.surviv.survival.bosses.BossManager;
import fun.surviv.survival.bosses.SurvivBoss;
import fun.surviv.survival.bosses.SurvivBossType;
import fun.surviv.survival.bosses.command.BossSubCommand;
import fun.surviv.survival.utils.ChatUtil;
import io.papermc.paper.entity.LookAnchor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * SurvivalSystem; fun.surviv.survival.bosses.command.sub:SpawnCommand
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
public class SpawnCommand implements BossSubCommand {

    private final BossManager manager;

    public SpawnCommand(BossManager manager) {
        this.manager = manager;
    }

    @Override
    public String getPermission() {
        return "boss.command.boss.spawn";
    }

    @Override
    public boolean executeSubCommand(@NotNull final CommandSender sender, @NotNull final List<String> args) {
        if (args.size() < 1) {
            return false;
        }
        String typeName = args.get(0).toUpperCase();
        SurvivBossType type;
        try {
            type = SurvivBossType.valueOf(typeName);
        } catch (Exception exception) {
            type = SurvivBossType.UNKNOWN;
        }
        if (type == SurvivBossType.UNKNOWN) {
            ChatUtil.reply(sender, "&7Dieser &3Boss &7des typen: &3" + type + " &7existiert nicht.");
            return false;
        }
        AbstractSurvivBossEntity boss = SurvivBoss.get(type).spawnBoss(((Player) sender).getLocation().subtract(new Vector(5, 1, 0)));
        ((Player) sender).lookAt(boss.getLocation(), LookAnchor.EYES);
        ChatUtil.reply(sender, "&7Der Boss: &3" + type.getIdName().toUpperCase() + " &7wurde gespawned.");
        return true;
    }

    @Override
    public List<String> tabCompleteSubCommand(@NotNull final CommandSender sender, @NotNull final String label, final List<String> args) {
        List<String> available = new ArrayList<>();
        if (args.size() == 1) {
            for (SurvivBossType type : SurvivBossType.values()) {
                available.add(type.toString().toLowerCase());
            }

        }
        return available;
    }

}
