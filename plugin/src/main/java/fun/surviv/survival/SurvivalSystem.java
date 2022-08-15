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

package fun.surviv.survival;

import com.google.common.collect.Lists;
import fun.surviv.survival.bosses.BossManager;
import fun.surviv.survival.bosses.command.BossCommand;
import fun.surviv.survival.configuration.JsonConfig;
import fun.surviv.survival.configuration.defaults.*;
import fun.surviv.survival.configuration.types.*;
import fun.surviv.survival.listener.*;
import fun.surviv.survival.namespace.Namespaces;
import fun.surviv.survival.permissions.PermissionManager;
import fun.surviv.survival.players.PlayerProvider;
import fun.surviv.survival.players.PlayerProviderImpl;
import fun.surviv.survival.systems.chat.DefaultChatListener;
import fun.surviv.survival.systems.timecontrol.TimeController;
import fun.surviv.survival.utils.ChatUtil;
import fun.surviv.survival.utils.Resources;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

/**
 * SurvivalSystem; fun.surviv.survival:SurvivalSystem
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class SurvivalSystem extends JavaPlugin {

    public static String VERSION;

    public static boolean LOADED = false;

    @Getter
    private static SurvivalSystem instance;
    @Getter
    private CoreSystemImpl core;
    @Getter
    private JsonConfig<PluginConfig> pluginConfig;
    @Getter
    private JsonConfig<DebugConfig> debugConfig;
    @Getter
    private JsonConfig<DatabaseConfig> databaseConfig;
    @Getter
    private JsonConfig<ChatConfig> chatConfig;
    @Getter
    private JsonConfig<TimeControlConfig> timeControlConfig;
    @Getter
    private JsonConfig<DamageContig> damageControlConfig;
    @Getter
    private JsonConfig<NerfControlConfig> nerfControlConfig;
    @Getter
    private PermissionManager permissionManager;
    @Getter
    private PlayerProviderImpl playerProvider;

    @Getter
    private TimeController timeController;

    @Getter
    private BossManager bossManager;

    @Getter
    private SurvivMobMechanics mobMechanics;

    public static void debug(String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

    @Override
    public void onLoad() {
        // throw runtime exception to stop plugin
        if (instance != null) {
            throw new RuntimeException("This plugin can only be loaded once... Please restart the server.");
        }
        instance = this;
        super.onLoad();
    }

    @Override
    public void onEnable() {
        VERSION = Resources.readToString("version.txt").trim();
        this.loadConfigurations();
        this.loadLuckPerms();
        core = new CoreSystemImpl(databaseConfig.get());
        Core.set(core);
        Core.system().perms(permissionManager); // set permission manager instance
        playerProvider = new PlayerProviderImpl(this);
        this.registerServices();
        Namespaces.init(this.getName().toLowerCase());
        this.registerCommandsAndListeners();

        mobMechanics = new SurvivMobMechanics(this);
        bossManager = new BossManager(this, mobMechanics);

        // hopefully when world loaded
        (new BukkitRunnable() {
            public void run() {
                instance.registerTimeController();
                SurvivalSystem.LOADED = true;
            }
        }).runTaskLater(this, 20 * 5); // run 5 sec after loaded

        ChatUtil.replySenderComponent(Bukkit.getConsoleSender(), "&aPlugin Enabled");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        timeController.setDaylightCycle(true);
        ChatUtil.replySenderComponent(Bukkit.getConsoleSender(), "&cPlugin Disabled");
        this.bossManager.disable();
        super.onDisable();
    }

    private void registerCommandsAndListeners() {
        Bukkit.getPluginManager().registerEvents(new DefaultPlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DefaultChatListener(this), this);

        Bukkit.getPluginManager().registerEvents(new DefaultGrowAndSpreadListener(this), this);

        Bukkit.getPluginManager().registerEvents(new DefaultEntitySpawmListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DefaultDamageListener(this), this);

        Bukkit.getPluginManager().registerEvents(new DefaultTimeListener(this), this);

        Bukkit.getCommandMap().registerAll("bukkit-overwrites", Lists.newArrayList(
                // TODO: create default command overwrites
        ));

        Bukkit.getCommandMap().registerAll("core", Lists.newArrayList(
                // TODO: add commands to register
        ));

        Bukkit.getCommandMap().register("surviv-bosses", new BossCommand(this.bossManager));
    }

    private void registerServices() {
        Bukkit.getServicesManager().register(CoreSystem.class, core, this, ServicePriority.Highest);
        Bukkit.getServicesManager().register(PermissionManager.class, permissionManager, this, ServicePriority.Highest);
        Bukkit.getServicesManager().register(PlayerProvider.class, playerProvider, this, ServicePriority.Highest);
    }

    private void registerTimeController() {
        timeController = new TimeController(this);
        (new BukkitRunnable() {
            public void run() {
                instance.getTimeController().setDaylightCycle(false);
                Bukkit.getWorlds().stream().filter(world -> instance.getTimeControlConfig().get().getEnabledWorlds().contains(world.getName())).forEach(timeController::runCycles);
            }
        }).runTask(this);
    }

    private void loadLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        LuckPerms luckPerms = provider.getProvider();
        permissionManager = new PermissionManager(chatConfig, luckPerms);
    }

    private void loadConfigurations() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        // this.saveDefaultConfig();
        // this.reloadConfig();

        // load general pluginConfig
        try {
            File pluginConfigFile = new File(this.getDataFolder(), "config.json");
            this.pluginConfig = new JsonConfig<>(PluginConfig.class, pluginConfigFile);
            this.pluginConfig.setDefault(PluginConfig.class, new DefaultPluginConfig(VERSION + "-do_not_change"));
            this.pluginConfig.load(true);
            this.pluginConfig.save(false);
            if (this.pluginConfig.get().getConfigVersion().split("-")[1] != VERSION) {
                // TODO: rename and replace (or shutdown ??)
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // define config dir
        File configPath = new File(this.getDataFolder(), Constants.CONFIG_PATH_NAME);
        if (!configPath.exists()) {
            configPath.mkdirs();
        }

        // load general debugConfig
        try {
            File debugConfigFile = new File(configPath, "debug.json");
            this.debugConfig = new JsonConfig<>(DebugConfig.class, debugConfigFile);
            this.debugConfig.setDefault(DebugConfig.class, new DefaultDebugConfig(VERSION + "-do_not_change"));
            this.debugConfig.load(true);
            this.debugConfig.save(false);
            if (this.debugConfig.get().getConfigVersion().split("-")[1] != VERSION) {
                // TODO: rename and replace (or shutdown ??)
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // load general databaseConfig
        try {
            File databaseConfigFile = new File(configPath, "database.json");
            this.databaseConfig = new JsonConfig<>(DatabaseConfig.class, databaseConfigFile);
            this.databaseConfig.setDefault(DatabaseConfig.class, new DefaultDatabaseConfig(VERSION + "-do_not_change"));
            this.databaseConfig.load(true);
            this.databaseConfig.save(false);
            if (this.pluginConfig.get().getConfigVersion().split("-")[1] != VERSION) {
                // TODO: rename and replace (or shutdown ??)
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // load general chatConfig
        try {
            File chatConfigFile = new File(configPath, "chat.json");
            this.chatConfig = new JsonConfig<>(ChatConfig.class, chatConfigFile);
            this.chatConfig.setDefault(ChatConfig.class, new DefaultChatConfig(VERSION + "-do_not_change"));
            this.chatConfig.load(true);
            this.chatConfig.save(false);
            if (this.chatConfig.get().getConfigVersion().split("-")[1] != VERSION) {
                // TODO: rename and replace (or shutdown ??)
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // load general timeControlConfig
        try {
            File timeControlConfigFile = new File(configPath, "timeControl.json");
            this.timeControlConfig = new JsonConfig<>(TimeControlConfig.class, timeControlConfigFile);
            this.timeControlConfig.setDefault(TimeControlConfig.class, new DefaultTimeControlConfig(VERSION + "-do_not_change"));
            this.timeControlConfig.load(true);
            this.timeControlConfig.save(false);
            if (this.timeControlConfig.get().getConfigVersion().split("-")[1] != VERSION) {
                // TODO: rename and replace (or shutdown ??)
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // load general timeControlConfig
        try {
            File damageControlConfigFile = new File(configPath, "damageControl.json");
            this.damageControlConfig = new JsonConfig<>(DamageContig.class, damageControlConfigFile);
            this.damageControlConfig.setDefault(DamageContig.class, new DefaultDamageContig(VERSION + "-do_not_change"));
            this.damageControlConfig.load(true);
            this.damageControlConfig.save(false);
            if (this.damageControlConfig.get().getConfigVersion().split("-")[1] != VERSION) {
                // TODO: rename and replace (or shutdown ??)
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // load general nerfControlConfig
        try {
            File nerfControlConfigFile = new File(configPath, "nerfControl.json");
            this.nerfControlConfig = new JsonConfig<>(NerfControlConfig.class, nerfControlConfigFile);
            this.nerfControlConfig.setDefault(NerfControlConfig.class, new DefaultNerfControlConfig(VERSION + "-do_not_change"));
            this.nerfControlConfig.load(true);
            this.nerfControlConfig.save(false);
            if (this.nerfControlConfig.get().getConfigVersion().split("-")[1] != VERSION) {
                // TODO: rename and replace (or shutdown ??)
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


}
