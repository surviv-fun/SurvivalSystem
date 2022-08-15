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

package fun.surviv.survival.aiconf;

import fun.surviv.survival.ai.MobAI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

/**
 * SurvivalSystem; fun.surviv.survival.aiconf:Settings
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class Settings {
    public ConfigManager.SimpleConfig configuration = (new ConfigManager(MobAI.getInstance().getPlugin())).getNewConfig("MobAI.yml");

    public Settings() {
        if (!this.configuration.contains("Worlds")) {
            ArrayList<String> list = new ArrayList<>();
            for (World world : Bukkit.getWorlds())
                list.add(world.getName());
            this.configuration.set("Worlds", list, new String[]{"Add all the worlds affected by Better MobAI here", "Remove worlds from here to disable the Better MobAI on these"});
        }
        if (!this.configuration.contains("BetterMobs")) {
            ArrayList<String> list = new ArrayList<>();
            list.add(EntityType.BLAZE.toString());
            list.add(EntityType.CAVE_SPIDER.toString());
            list.add(EntityType.CREEPER.toString());
            list.add(EntityType.ENDERMAN.toString());
            list.add(EntityType.GHAST.toString());
            list.add(EntityType.GUARDIAN.toString());
            list.add(EntityType.ZOMBIFIED_PIGLIN.toString());
            list.add(EntityType.SKELETON.toString());
            list.add(EntityType.SPIDER.toString());
            list.add(EntityType.WITCH.toString());
            list.add(EntityType.ZOMBIE.toString());
            list.add(EntityType.GIANT.toString());
            this.configuration.set("BetterMobs", list, new String[]{"Add all the mobs affected by Better MobAI here", "Remove mobs from here to disable the Better MobAI on these"});
        }
        if (!this.configuration.contains("CaveSpider")) {
            this.configuration.set("CaveSpider.Health", Integer.valueOf(12), "Edit the cave spider parameters here");
            this.configuration.set("CaveSpider.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("CaveSpider.NormalAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("CaveSpider.NormalAttack.Damage", Integer.valueOf(10));
            this.configuration.set("CaveSpider.NormalAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("CaveSpider.PoisonAttack.Disable", Boolean.FALSE);
            this.configuration.set("CaveSpider.PoisonAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("CaveSpider.PoisonAttack.Damage", Integer.valueOf(8));
            this.configuration.set("CaveSpider.PoisonAttack.PoisoningTime", Integer.valueOf(7));
            this.configuration.set("CaveSpider.PoisonAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("CaveSpider.SkyAttack.Disable", Boolean.FALSE);
            this.configuration.set("CaveSpider.SkyAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("CaveSpider.SkyAttack.Damage", Integer.valueOf(13));
            this.configuration.set("CaveSpider.SkyAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("CaveSpider.WebAttack.Disable", Boolean.FALSE);
            this.configuration.set("CaveSpider.WebAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("CaveSpider.WebAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Spider")) {
            this.configuration.set("Spider.Health", Integer.valueOf(16), "Edit the spider parameters here");
            this.configuration.set("Spider.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("Spider.NormalAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Spider.NormalAttack.Damage", Integer.valueOf(10));
            this.configuration.set("Spider.NormalAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Spider.PoisonAttack.Disable", Boolean.FALSE);
            this.configuration.set("Spider.PoisonAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Spider.PoisonAttack.Damage", Integer.valueOf(8));
            this.configuration.set("Spider.PoisonAttack.PoisoningTime", Integer.valueOf(7));
            this.configuration.set("Spider.PoisonAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Spider.SkyAttack.Disable", Boolean.FALSE);
            this.configuration.set("Spider.SkyAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Spider.SkyAttack.Damage", Integer.valueOf(13));
            this.configuration.set("Spider.SkyAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Spider.WebAttack.Disable", Boolean.FALSE);
            this.configuration.set("Spider.WebAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Spider.WebAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Creeper")) {
            this.configuration.set("Creeper.Health", Integer.valueOf(20), "Edit the creeper parameters here");
            this.configuration.set("Creeper.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("Creeper.NormalAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Creeper.ImplosionExplosionAttack.Disable", Boolean.FALSE);
            this.configuration.set("Creeper.ImplosionExplosionAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Creeper.ImplosionExplosionAttack.ImplosionRadius", Integer.valueOf(10));
            this.configuration.set("Creeper.ChargedCreeperAttack.Disable", Boolean.FALSE);
            this.configuration.set("Creeper.ChargedCreeperAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Creeper.ChargedSuperCreeperAttack.Disable", Boolean.FALSE);
            this.configuration.set("Creeper.ChargedSuperCreeperAttack.Speed", Double.valueOf(1.3D));
        }
        if (!this.configuration.contains("Enderman")) {
            this.configuration.set("Enderman.Health", Integer.valueOf(40), "Edit the enderman parameters here");
            this.configuration.set("Enderman.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("Enderman.NormalAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Enderman.NormalAttack.Damage", Integer.valueOf(7));
            this.configuration.set("Enderman.NormalAttack.ConfusionTime", Integer.valueOf(4));
            this.configuration.set("Enderman.NormalAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Enderman.JumperAttack.Disable", Boolean.FALSE);
            this.configuration.set("Enderman.JumperAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Enderman.JumperAttack.Damage", Integer.valueOf(10));
            this.configuration.set("Enderman.JumperAttack.ConfusionTime", Integer.valueOf(6));
            this.configuration.set("Enderman.JumperAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Skeleton")) {
            this.configuration.set("Skeleton.Health", Integer.valueOf(20), "Edit the skeleton parameters here");
            this.configuration.set("Skeleton.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("Skeleton.NormalAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Skeleton.NormalAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("Skeleton.BurningArrowAttack.Disable", Boolean.FALSE);
            this.configuration.set("Skeleton.BurningArrowAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Skeleton.BurningArrowAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("Skeleton.PoisonArrowAttack.Disable", Boolean.FALSE);
            this.configuration.set("Skeleton.PoisonArrowAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Skeleton.PoisonArrowAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("Skeleton.NailingArrowAttack.Disable", Boolean.FALSE);
            this.configuration.set("Skeleton.NailingArrowAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Skeleton.NailingArrowAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("Skeleton.GetARide.Disable", Boolean.FALSE);
            this.configuration.set("Skeleton.GetARide.Radius", Integer.valueOf(10));
            this.configuration.set("Skeleton.ArrowRain.Disable", Boolean.FALSE);
            this.configuration.set("Skeleton.ArrowRain.Speed", Double.valueOf(1.3D));
            this.configuration.set("Skeleton.ArrowRain.Arrows", Integer.valueOf(6));
            this.configuration.set("Skeleton.ArrowRain.NextAttackDelay", Integer.valueOf(3));
        }
        if (!this.configuration.contains("Zombie")) {
            this.configuration.set("Zombie.Health", Integer.valueOf(20), "Edit the zombie parameters here");
            this.configuration.set("Zombie.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("Zombie.NormalAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Zombie.NormalAttack.Damage", Integer.valueOf(6));
            this.configuration.set("Zombie.NormalAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("Zombie.BloodRushAttack.Disable", Boolean.FALSE);
            this.configuration.set("Zombie.BloodRushAttack.Speed", Double.valueOf(1.5D));
            this.configuration.set("Zombie.BloodRushAttack.Damage", Integer.valueOf(8));
            this.configuration.set("Zombie.BloodRushAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("Zombie.MinionsAttack.Disable", Boolean.FALSE);
            this.configuration.set("Zombie.MinionsAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Zombie.VampireAttack.Disable", Boolean.FALSE);
            this.configuration.set("Zombie.VampireAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Zombie.VampireAttack.Damage", Integer.valueOf(8));
            this.configuration.set("Zombie.VampireAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Witch")) {
            this.configuration.set("Witch.Health", Integer.valueOf(26), "Edit the witch parameters here");
            this.configuration.set("Witch.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("Witch.NormalAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.NormalAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Witch.BlackMagicAttack.Disable", Boolean.FALSE);
            this.configuration.set("Witch.BlackMagicAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.BlackMagicAttack.EffectLength", Integer.valueOf(7));
            this.configuration.set("Witch.BlackMagicAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Witch.FireAttack.Disable", Boolean.FALSE);
            this.configuration.set("Witch.FireAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.FireAttack.EffectLength", Integer.valueOf(7));
            this.configuration.set("Witch.FireAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Witch.FireCircleAttack.Disable", Boolean.FALSE);
            this.configuration.set("Witch.FireCircleAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.FireCircleAttack.Radius", Integer.valueOf(2));
            this.configuration.set("Witch.FireCircleAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Witch.LavaAttack.Disable", Boolean.FALSE);
            this.configuration.set("Witch.LavaAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.LavaAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Witch.PoisonAttack.Disable", Boolean.FALSE);
            this.configuration.set("Witch.PoisonAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.PoisonAttack.EffectLength", Integer.valueOf(7));
            this.configuration.set("Witch.PoisonAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Witch.SlownessAttack.Disable", Boolean.FALSE);
            this.configuration.set("Witch.SlownessAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.SlownessAttack.EffectLength", Integer.valueOf(5));
            this.configuration.set("Witch.SlownessAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Witch.EndermitesAttack.Disable", Boolean.FALSE);
            this.configuration.set("Witch.EndermitesAttack.Speed", Double.valueOf(1.3D));
            this.configuration.set("Witch.EndermitesAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Guardian")) {
            this.configuration.set("Guardian.Normal.Health", Integer.valueOf(30), "Edit the guardian parameters here");
            this.configuration.set("Guardian.Elder.Health", Integer.valueOf(80));
            this.configuration.set("Guardian.TrackingSpeed", Double.valueOf(0.5D));
            this.configuration.set("Guardian.NormalAttack.Speed", Double.valueOf(0.5D));
            this.configuration.set("Guardian.NormalAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Guardian.InstantAttack.Disable", Boolean.FALSE);
            this.configuration.set("Guardian.InstantAttack.Speed", Double.valueOf(0.5D));
            this.configuration.set("Guardian.InstantAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Guardian.NailingVibesAttack.Disable", Boolean.FALSE);
            this.configuration.set("Guardian.NailingVibesAttack.Speed", Double.valueOf(0.5D));
            this.configuration.set("Guardian.NailingVibesAttack.EffectLength", Integer.valueOf(4));
            this.configuration.set("Guardian.NailingVibesAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Guardian.SuffocationVibesAttack.Disable", Boolean.FALSE);
            this.configuration.set("Guardian.SuffocationVibesAttack.Speed", Double.valueOf(0.5D));
            this.configuration.set("Guardian.SuffocationVibesAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("PigmanZombie")) {
            this.configuration.set("PigmanZombie.Health", Integer.valueOf(20), "Edit the pigman zombie parameters here");
            this.configuration.set("PigmanZombie.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("PigmanZombie.NormalAttack.Speed", Integer.valueOf(1));
            this.configuration.set("PigmanZombie.NormalAttack.Damage", Integer.valueOf(6));
            this.configuration.set("PigmanZombie.NormalAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("PigmanZombie.LightningAttack.Disable", Boolean.FALSE);
            this.configuration.set("PigmanZombie.LightningAttack.Speed", Integer.valueOf(1));
            this.configuration.set("PigmanZombie.LightningAttack.Damage", Integer.valueOf(6));
            this.configuration.set("PigmanZombie.LightningAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("PigmanZombie.SwordAttack.Disable", Boolean.FALSE);
            this.configuration.set("PigmanZombie.SwordAttack.Speed", Integer.valueOf(1));
            this.configuration.set("PigmanZombie.SwordAttack.Damage", Integer.valueOf(8));
            this.configuration.set("PigmanZombie.SwordAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("PigmanZombie.ThrowSwordAttack.Disable", Boolean.FALSE);
            this.configuration.set("PigmanZombie.ThrowSwordAttack.Damage", Integer.valueOf(8));
            this.configuration.set("PigmanZombie.ThrowSwordAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("WitherSkeleton")) {
            this.configuration.set("WitherSkeleton.Health", Integer.valueOf(20), "Edit the wither skeleton parameters here");
            this.configuration.set("WitherSkeleton.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("WitherSkeleton.NormalAttack.Speed", Integer.valueOf(1));
            this.configuration.set("WitherSkeleton.NormalAttack.Damage", Integer.valueOf(8));
            this.configuration.set("WitherSkeleton.NormalAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("WitherSkeleton.WitherSkullAttack.Disable", Boolean.FALSE);
            this.configuration.set("WitherSkeleton.WitherSkullAttack.Speed", Integer.valueOf(1));
            this.configuration.set("WitherSkeleton.WitherSkullAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("WitherSkeleton.SwordAttack.Disable", Boolean.FALSE);
            this.configuration.set("WitherSkeleton.SwordAttack.Speed", Integer.valueOf(1));
            this.configuration.set("WitherSkeleton.SwordAttack.Damage", Integer.valueOf(10));
            this.configuration.set("WitherSkeleton.SwordAttack.NextAttackDelay", Integer.valueOf(3));
            this.configuration.set("WitherSkeleton.ThrowSwordAttack.Disable", Boolean.FALSE);
            this.configuration.set("WitherSkeleton.ThrowSwordAttack.Damage", Integer.valueOf(10));
            this.configuration.set("WitherSkeleton.ThrowSwordAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Ghast")) {
            this.configuration.set("Ghast.Health", Integer.valueOf(10), "Edit the ghast parameters here");
            this.configuration.set("Ghast.TrackingSpeed", Double.valueOf(0.5D));
            this.configuration.set("Ghast.NormalAttack.Speed", Double.valueOf(0.5D));
            this.configuration.set("Ghast.NormalAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Ghast.HighSpeedAttack.Disable", Boolean.FALSE);
            this.configuration.set("Ghast.HighSpeedAttack.Speed", Double.valueOf(0.5D));
            this.configuration.set("Ghast.HighSpeedAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Ghast.MultiAttack.Disable", Boolean.FALSE);
            this.configuration.set("Ghast.MultiAttack.Speed", Double.valueOf(0.5D));
            this.configuration.set("Ghast.MultiAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Blaze")) {
            this.configuration.set("Blaze.Health", Integer.valueOf(10), "Edit the blaze parameters here");
            this.configuration.set("Blaze.TrackingSpeed", Integer.valueOf(1));
            this.configuration.set("Blaze.NormalAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Blaze.NormalAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Blaze.HeatAttack.Disable", Boolean.FALSE);
            this.configuration.set("Blaze.HeatAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Blaze.HeatAttack.EffectLength", Integer.valueOf(5));
            this.configuration.set("Blaze.HeatAttack.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Blaze.SmokeAttack.Disable", Boolean.FALSE);
            this.configuration.set("Blaze.SmokeAttack.Speed", Integer.valueOf(1));
            this.configuration.set("Blaze.SmokeAttack.EffectLength", Integer.valueOf(5));
            this.configuration.set("Blaze.SmokeAttack.NextAttackDelay", Integer.valueOf(5));
        }
        if (!this.configuration.contains("Giant")) {
            this.configuration.set("Giant.Health", Integer.valueOf(200), "Edit the giant parameters here");
            this.configuration.set("Giant.TrackingSpeed", Double.valueOf(0.5D));
            this.configuration.set("Giant.MiniZombieHealth", Integer.valueOf(20));
            this.configuration.set("Giant.ThrowMiniZombie.Disable", Boolean.FALSE);
            this.configuration.set("Giant.ThrowMiniZombie.Speed", Double.valueOf(0.5D));
            this.configuration.set("Giant.ThrowMiniZombie.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Giant.ThrowBoulder.Disable", Boolean.FALSE);
            this.configuration.set("Giant.ThrowBoulder.Speed", Double.valueOf(0.5D));
            this.configuration.set("Giant.ThrowBoulder.Damage", Integer.valueOf(14));
            this.configuration.set("Giant.ThrowBoulder.DamageRadius", Integer.valueOf(6));
            this.configuration.set("Giant.ThrowBoulder.NextAttackDelay", Integer.valueOf(5));
            this.configuration.set("Giant.Earthquake.Disable", Boolean.FALSE);
            this.configuration.set("Giant.Earthquake.Speed", Double.valueOf(0.5D));
            this.configuration.set("Giant.Earthquake.Damage", Integer.valueOf(8));
            this.configuration.set("Giant.Earthquake.Radius", Integer.valueOf(10));
            this.configuration.set("Giant.Earthquake.NextAttackDelay", Integer.valueOf(8));
            this.configuration.set("Giant.LightningStriker.Disable", Boolean.FALSE);
            this.configuration.set("Giant.LightningStriker.Speed", Double.valueOf(0.5D));
            this.configuration.set("Giant.LightningStriker.DamageRadius", Integer.valueOf(10));
            this.configuration.set("Giant.LightningStriker.NextAttackDelay", Integer.valueOf(8));
        }
        this.configuration.saveConfig();
    }
}
