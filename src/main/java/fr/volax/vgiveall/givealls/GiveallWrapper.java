package fr.volax.vgiveall.givealls;

import fr.volax.vgiveall.VGiveall;
import fr.volax.vgiveall.users.User;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GiveallWrapper {
    private static final List<Giveall> giveallsInMemory = new ArrayList<>();

    public static Giveall getGiveall(String id){
        File giveallFile = new File(VGiveall.getInstance().giveallsFolder + "/" + id + ".yml");

        if(!giveallFile.exists())
            return null;

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(giveallFile);

        for(Giveall giveall : giveallsInMemory){
            if(giveall.getId().equals(id))
                return giveall;
        }

        Giveall giveall = new Giveall(giveallFile, id);
        giveallsInMemory.add(giveall);
        return giveall;
    }
}
