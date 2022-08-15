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

package fun.surviv.survival.listener;

import fun.surviv.survival.SurvivalSystem;
import fun.surviv.survival.namespace.Namespaces;
import fun.surviv.survival.utils.ChatUtil;
import fun.surviv.survival.utils.MathUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

/**
 * SurvivalSystem; fun.surviv.survival.listener:DefaultDamageListener
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class DefaultDamageListener implements Listener {

    private final SurvivalSystem plugin;

    public DefaultDamageListener(SurvivalSystem plugin) {
        this.plugin = plugin;
    }

    // default damage modifiers
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        this.generalDamageNerf(event);
        this.craetureDamageNerf(event);
    }

    public void generalDamageNerf(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        if (victim.getType() == EntityType.PLAYER && damager.getType() == EntityType.PLAYER) {
            double damageModifier = plugin.getDamageControlConfig().get().getGeneralDamageMultiplier();
            double pvpDamageMultiplier = plugin.getDamageControlConfig().get().getPvpDamageMultiplier();
            double nerfedDamage = MathUtils.roundToHalf(event.getDamage() * pvpDamageMultiplier * damageModifier);
            if (plugin.getDebugConfig().get().isDebugEntityDamage()) {
                debug(victim, "&7Modified Damage of [&3" + damager.getType() + "&7]:", "&7-  before: &3" + event.getDamage(), "&7-  after: &3" + nerfedDamage, "&7-  modifier-general-pvp: &3" + pvpDamageMultiplier);
            }
            event.setDamage(nerfedDamage);
            return;
        }
        if (damager.getType() == EntityType.PLAYER && victim instanceof Creature) {
            double damageModifier = plugin.getDamageControlConfig().get().getGeneralDamageMultiplier();
            double pveDamageMultiplier = plugin.getDamageControlConfig().get().getPveDamageMultiplier();
            double nerfedDamage = MathUtils.roundToHalf(event.getDamage() * pveDamageMultiplier * damageModifier);
            if (plugin.getDebugConfig().get().isDebugHumanDamage()) {
                debug(damager, "&7Modified Damage of [&3" + damager.getType() + "&7]:", "&7-  before: &3" + event.getDamage(), "&7-  after: &3" + nerfedDamage, "&7-  modifier-general-pve: &3" + pveDamageMultiplier);
            }
            event.setDamage(nerfedDamage);
            return;
        }
        if (damager.getType() != EntityType.PLAYER && victim.getType() == EntityType.PLAYER) {
            double damageModifier = plugin.getDamageControlConfig().get().getGeneralDamageMultiplier();
            double evpDamageMultiplier = plugin.getDamageControlConfig().get().getEvpDamageMultiplier();
            double nerfedDamage = MathUtils.roundToHalf(event.getDamage() * evpDamageMultiplier * damageModifier);
            if (plugin.getDebugConfig().get().isDebugHumanDamage()) {
                debug(damager, "&7Modified Damage of [&3" + damager.getType() + "&7]:", "&7-  before: &3" + event.getDamage(), "&7-  after: &3" + nerfedDamage, "&7-  modifier-general-evp: &3" + evpDamageMultiplier);
            }
            event.setDamage(nerfedDamage);
        }
    }

    private void craetureDamageNerf(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        if (damager.getType().isAlive() && damager.getType().isSpawnable() && damager.getType() != EntityType.PLAYER && victim.getType() == EntityType.PLAYER) {
            double damageLevel = 0;
            double damageGeneral = plugin.getNerfControlConfig().get().getGeneralDamageMultiplier().get(damager.getType());
            int level = damager.getPersistentDataContainer().getOrDefault(Namespaces.MOB_LEVEL, PersistentDataType.INTEGER, 4);
            for (int i = 0; i < level; i++) {
                damageLevel += plugin.getNerfControlConfig().get().getAddDamageLevel().get(damager.getType());
            }
            double nerfedDamage = MathUtils.roundToHalf((event.getDamage() * damageGeneral) + (event.getDamage() * damageLevel));
            if (plugin.getDebugConfig().get().isDebugEntityDamage()) {
                debug(victim, "&7Modified Damage of [&3" + damager.getType() + "&7]:", "&7-  ent-lvl: &3" + level, "&7-  before: &3" + event.getDamage(), "&7-  after: &3" + nerfedDamage, "&7-  modifier-general: &3" + damageGeneral, "&7-  modifier-level: &3" + damageLevel);
            }
            event.setDamage(nerfedDamage);
        }
    }

    private void debug(CommandSender player, String... messages) {
        for (String message : messages) {
            ChatUtil.reply(player, message);
        }
    }

}
