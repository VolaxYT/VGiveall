package fr.volax.vgiveall;

import fr.volax.vgiveall.commands.VGiveallCommand;
import fr.volax.vgiveall.listeners.PlayerListener;
import fr.volax.vgiveall.utils.*;
import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * VGiveall for Minecraft 1.18!
 * The project is on https://github.com/VolaxYT/VGiveall/
 *
 * @author Volax
 */
public class VGiveall extends JavaPlugin {
    @Getter private static VGiveall instance;
    @Getter private GuiManager guiManager;
    @Getter private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    public File debugFile, giveallsFolder, usersFolder;

    public void onEnable() {
        instance = this;
        this.registeredMenus = new HashMap<>();
        this.guiManager = new GuiManager();
        this.debugFile = new File(getDataFolder(), "logs.txt");
        this.usersFolder = new File(String.valueOf(getDataFolder().getAbsoluteFile()) + "/users");
        this.giveallsFolder = new File(String.valueOf(getDataFolder().getAbsoluteFile()) + "/givealls");

        ConfigBuilder configBuilder = new ConfigBuilder(new FileManager(this));
        this.saveDefaultConfig();

        this.getCommand("giveall").setExecutor(new VGiveallCommand());
        this.getCommand("giveall").setTabCompleter(new VGiveallCommand());

        this.getServer().getPluginManager().registerEvents(new GuiManager(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if(!debugFile.exists()) {
            try {
                debugFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!usersFolder.exists()){
            usersFolder.mkdir();
            ChatUtil.logMessage("Creation of users folder at " + usersFolder.getAbsolutePath().toString());
        }

        if(!usersFolder.exists()){
            usersFolder.mkdir();
            ChatUtil.logMessage("Creation of givealls folder at " + giveallsFolder.getAbsolutePath().toString());
        }

        this.getServer().getConsoleSender().sendMessage("§a"+ this.getDescription().getName() + " " + this.getDescription().getVersion() + " launched successfully on a " + this.getServer().getVersion() + " server.");
    }

    public static String randomID(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }
}
