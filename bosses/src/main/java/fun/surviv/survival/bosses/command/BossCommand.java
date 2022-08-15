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

package fun.surviv.survival.bosses.command;

import fun.surviv.survival.bosses.BossManager;
import fun.surviv.survival.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * SurvivalSystem; fun.surviv.survival.bosses.command:BossCommand
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public class BossCommand extends Command {

    private final Map<String, BossSubCommand> subCommands;
    private final BossManager manager;


    public BossCommand(BossManager manager) {
        super("boss");
        this.manager = manager;
        this.subCommands = new HashMap<>();
        setDescription(this.getName() + "  - Boss Manager Command");
        setPermission("boss.command." + this.getName().toLowerCase());
        this.subCommands.put("spawn", null);
    }

    @Override
    public boolean execute(
            @NotNull final CommandSender sender, @NotNull final String commandLabel, final @NotNull String[] args
    ) {

        if (args.length >= 1) {
            BossSubCommand sub = subCommands.get(args[0]);
            if (sub != null) {
                List<String> argsList = new ArrayList<>();
                for (int i = 1; i < args.length; i++) {
                    argsList.add(args[i]);
                }
                return sub.executeSubCommand(sender, argsList);
            }
        }
        ChatUtil.reply(sender, "&cInvalid sub command."); // TODO: Usage
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(
            @NotNull final CommandSender sender, @NotNull final String alias, final @NotNull String[] args
    ) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();
        List<String> available = new ArrayList<>();
        if (args.length == 1) {
            available.addAll(subCommands.keySet());
            StringUtil.copyPartialMatches(args[0], available, completions);
        }
        if (args.length >= 2) {
            BossSubCommand sub = subCommands.get(args[0]);
            if (sub == null) {
                return completions;
            }
            List<String> argsList = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                argsList.add(args[i]);
            }
            if (sender.hasPermission(sub.getPermission() != null ? sub.getPermission() : "*")) {
                available = sub.tabCompleteSubCommand(sender, alias, argsList);
            }
            StringUtil.copyPartialMatches(args[0], available, completions);
        }
        Collections.sort(completions);
        return completions;
    }

}
