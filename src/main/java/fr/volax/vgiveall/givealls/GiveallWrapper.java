package fr.volax.vgiveall.givealls;

import fr.volax.vgiveall.VGiveall;
import fr.volax.vgiveall.items.Item;
import fr.volax.vgiveall.items.ItemWrapper;
import fr.volax.vgiveall.users.UserWrapper;
import fr.volax.vgiveall.utils.ChatUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public final class GiveallWrapper {
    @Getter private static final GiveallWrapper instance = new GiveallWrapper();

    @Getter @Setter private Giveall currentGiveall;
    @Getter @Setter private boolean giveallNow;

    private GiveallWrapper() {
        this.currentGiveall = null;
        this.giveallNow = false;
    }

    public void toggle(){
            this.setGiveallNow(!giveallNow);
    }

    @SneakyThrows
    public Giveall createGiveall(Player player){
        String giveallID = "GV_" + VGiveall.getInstance().randomID(4);
        File giveallFile = new File(VGiveall.getInstance().getGiveallsFolder() + "/" + giveallID + ".yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(giveallFile);

        ArrayList<String> adminInfos = new ArrayList<>(Arrays.asList(player.getName(), UserWrapper.getUser(player.getUniqueId()).getId()));
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //TODO GiveallStatus
        String status = "Started";

        if(!giveallFile.exists()) {
            giveallFile.createNewFile();
            ChatUtil.logMessage("Creation of " + giveallID + "'s folder at " + giveallFile.getAbsolutePath());
            fileConfiguration.set("id", giveallID);
            fileConfiguration.set("createdBy", adminInfos);
            fileConfiguration.set("creationDate", date);
            fileConfiguration.set("startDate", date);
            fileConfiguration.set("status", status);
            fileConfiguration.save(giveallFile);
        }

        return new Giveall.Builder()
                .withFile(giveallFile)
                .withId(giveallID)
                .withAdminName(player.getName())
                .withAdminID(adminInfos.get(1))
                .withGiveallStatus(status)
                .withCreationDate(date)
                .withStartDate(date)
                .build();
    }

    public Giveall getGiveall(String id) {
        File giveallFile = new File(VGiveall.getInstance().getGiveallsFolder() + "/" + id + ".yml");

        if(!giveallFile.exists())
            return null;

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(giveallFile);

        return new Giveall.Builder()
                .withFile(giveallFile)
                .withId(id)
                .withAdminName((String) fileConfiguration.getList("createdBy").get(0))
                .withAdminID((String) fileConfiguration.getList("createdBy").get(1))
                .withGiveallStatus(fileConfiguration.getString("status"))
                .withCreationDate(fileConfiguration.getString("creationDate"))
                .withStartDate(fileConfiguration.getString("startDate"))
                .withEndDate(fileConfiguration.getString("endDate"))
                .withItemsGave(ItemWrapper.getInstance().parseConfig(fileConfiguration))
                .build();
    }

    @SneakyThrows
    public void syncEndDate(String endDate, Giveall giveall){
        FileConfiguration fileConfiguration = giveall.getFileConfiguration();

        fileConfiguration.set("endDate", endDate);
        fileConfiguration.save(giveall.getFile());
    }
}
