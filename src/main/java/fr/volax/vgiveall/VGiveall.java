package fr.volax.vgiveall;

import fr.volax.vgiveall.commands.VGiveallCommand;
import fr.volax.vgiveall.utils.ConfigBuilder;
import fr.volax.vgiveall.utils.FileManager;
import fr.volax.vgiveall.utils.GuiBuilder;
import fr.volax.vgiveall.utils.GuiManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
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

        this.getCommand("giveall").setExecutor(new VGiveallCommand());
        this.getCommand("giveall").setTabCompleter(new VGiveallCommand());

        this.getServer().getPluginManager().registerEvents(new GuiManager(), this);

        if(!debugFile.exists()) {
            try {
                debugFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getConsoleSender().sendMessage("§a"+ this.getDescription().getName() + " " + this.getDescription().getVersion() + " launched successfully on a " + this.getServer().getVersion() + " server.");
    }
}
