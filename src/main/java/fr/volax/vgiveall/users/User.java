package fr.volax.vgiveall.users;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

@Data
public class User {
    private final FileConfiguration fileConfiguration;
    private final File file;
    private final UUID uuid;
    private final String id;

    public User(File file, UUID uuid, String id) {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
        this.file = file;
        this.uuid = uuid;
        this.id = id;
    }
}
