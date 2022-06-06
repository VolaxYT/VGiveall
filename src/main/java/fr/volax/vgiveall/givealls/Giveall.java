package fr.volax.vgiveall.givealls;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

@Data
public class Giveall {
    private final FileConfiguration fileConfiguration;
    private final File file;
    private final String id;

    public Giveall(File file, String id) {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
        this.file = file;
        this.id = id;
    }
}
