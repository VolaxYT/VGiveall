package fr.volax.vgiveall;

import fr.volax.vgiveall.commands.VGiveallCommand;
import fr.volax.vgiveall.listeners.PlayerListener;
import fr.volax.vgiveall.menu.GiveallInfosMenu;
import fr.volax.vgiveall.utils.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * VGiveall for Minecraft 1.18!
 * The project is on https://github.com/VolaxYT/VGiveall/
 *
 * @author Volax
 */
public class VGiveall extends JavaPlugin {
    @Getter private static VGiveall instance;
    @Getter private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    @Getter private File debugFile, giveallsFolder, usersFolder;

    @SneakyThrows
    public void onEnable() {
        instance = this;
        this.registeredMenus = new HashMap<>();

        this.debugFile = new File(getDataFolder(), "logs.txt");
        this.usersFolder = new File(getDataFolder().getAbsoluteFile() + "/users");
        this.giveallsFolder = new File(getDataFolder().getAbsoluteFile() + "/givealls");

        this.saveDefaultConfig();

        this.getCommand("giveall").setExecutor(new VGiveallCommand());
        this.getCommand("giveall").setTabCompleter(new VGiveallCommand());

        this.getServer().getPluginManager().registerEvents(new GuiManager(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        GuiManager.getInstance().addMenu(new GiveallInfosMenu());

        if(!debugFile.exists())
            debugFile.createNewFile();

        if(!usersFolder.exists()){
            usersFolder.mkdir();
            ChatUtil.logMessage("Creation of users folder at " + usersFolder.getAbsolutePath());
        }

        if(!giveallsFolder.exists()){
            giveallsFolder.mkdir();
            ChatUtil.logMessage("Creation of givealls folder at " + giveallsFolder.getAbsolutePath());
        }

        this.getServer().getConsoleSender().sendMessage("§a"+ this.getDescription().getName() + " " + this.getDescription().getVersion() + " launched successfully on a " + this.getServer().getVersion() + " server.");
    }

    public String randomID(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }
}
