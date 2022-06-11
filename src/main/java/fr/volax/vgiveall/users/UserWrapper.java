package fr.volax.vgiveall.users;

import fr.volax.vgiveall.VGiveall;
import fr.volax.vgiveall.utils.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class UserWrapper {
    public static User getUser(UUID uuid){
        File userFile = new File(VGiveall.getInstance().getUsersFolder() + "/" + uuid.toString() + ".yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(userFile);

        if(!userFile.exists()){
            try {
                userFile.createNewFile();
                ChatUtil.logMessage("Creation of " + uuid + "'s folder at " + userFile.getAbsolutePath());
                fileConfiguration.set("id", "USR_" + VGiveall.getInstance().randomID(8) + "-" + VGiveall.getInstance().randomID(8) + "@" + VGiveall.getInstance().randomID(8));
                fileConfiguration.save(userFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String id = fileConfiguration.getString("id");
        return new User(userFile, uuid, id);
    }
}
