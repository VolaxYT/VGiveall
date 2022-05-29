package fr.volax.vgiveaway;

import fr.volax.vgiveaway.commands.VGiveawayCommand;
import fr.volax.vgiveaway.utils.ConfigBuilder;
import fr.volax.vgiveaway.utils.FileManager;
import fr.volax.vgiveaway.utils.GuiBuilder;
import fr.volax.vgiveaway.utils.GuiManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * VGiveaway for Minecraft 1.18!
 * The project is on https://github.com/VolaxYT/VGiveaway/
 *
 * @author Volax
 */
public class VGiveaway extends JavaPlugin {
    @Getter private static VGiveaway instance;
    @Getter private GuiManager guiManager;
    @Getter private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;
    public File debugFile;

    public void onEnable() {
        instance = this;
        this.registeredMenus = new HashMap<>();
        this.guiManager = new GuiManager();
        this.debugFile = new File(getDataFolder(), "logs.txt");

        ConfigBuilder configBuilder = new ConfigBuilder(new FileManager(this));
        this.saveDefaultConfig();

        this.getCommand("vgiveaway").setExecutor(new VGiveawayCommand());
        this.getCommand("vgiveaway").setTabCompleter(new VGiveawayCommand());

        this.getServer().getPluginManager().registerEvents(new GuiManager(), this);

        if(!debugFile.exists()) {
            try {
                debugFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getConsoleSender().sendMessage("§aVGiveaway " + this.getDescription().getVersion() + " launched successfully on a " + this.getServer().getVersion() + " server.");
    }
}
