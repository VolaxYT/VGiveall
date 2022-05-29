package fr.volax.vgiveaway;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
/*

 */
public class VGiveaway extends JavaPlugin {
    @Getter private VGiveaway instance;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();



    }
}
