package fr.volax.vgiveall.users;

import fr.volax.vgiveall.VGiveall;
import fr.volax.vgiveall.utils.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserWrapper {
    private static final List<User> usersInMemory = new ArrayList<>();

    public static User getUser(Player player){
        File userFile = new File(VGiveall.getInstance().usersFolder + "/" + player.getUniqueId().toString() + ".yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(userFile);

        if(!userFile.exists()){
            try {
                userFile.createNewFile();
                ChatUtil.logMessage("Creation of " + player.getName() + "'s folder at " + userFile.getAbsolutePath().toString());
                fileConfiguration.set("id", "USR_" + VGiveall.randomID(8) + "-" + VGiveall.randomID(8) + "@" + VGiveall.randomID(8));
                fileConfiguration.save(userFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String id = fileConfiguration.getString("id");

        for(User user : usersInMemory){
            if(user.getId().equals(id))
                return user;
        }

        User user = new User(userFile, player, id);
        usersInMemory.add(user);
        return user;
    }
}
