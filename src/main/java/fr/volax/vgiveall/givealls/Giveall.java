package fr.volax.vgiveall.givealls;

import fr.volax.vgiveall.items.Item;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class Giveall {
    private FileConfiguration fileConfiguration;
    private File file;
    private String id;
    private String adminName;
    private String adminID, giveallStatus, creationDate, startDate, endDate;
    private List<Item> itemsGave;

    public static class Builder {
        private File file;
        private String id;
        private String adminName;
        private String adminID, giveallStatus, creationDate, startDate = "Not started yet", endDate = "Not finished yet";
        private List<Item> itemsGave = new ArrayList<>();

        public Builder withFile(File file){
            this.file = file;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAdminName(String adminName) {
            this.adminName = adminName;
            return this;
        }

        public Builder withAdminID(String adminID) {
            this.adminID = adminID;
            return this;
        }

        public Builder withGiveallStatus(String giveallStatus) {
            this.giveallStatus = giveallStatus;
            return this;
        }

        public Builder withCreationDate(String creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(String endDate){
            this.endDate = endDate;
            return this;
        }

        public Builder withItemsGave(List<Item> itemsGave){
            this.itemsGave = itemsGave;
            return this;
        }

        @SneakyThrows
        public Giveall build(){
            Giveall giveall = new Giveall();
            giveall.setFileConfiguration(YamlConfiguration.loadConfiguration(file));
            giveall.setFile(file);
            giveall.setId(id);
            giveall.setAdminName(adminName);
            giveall.setAdminID(adminID);
            giveall.setGiveallStatus(giveallStatus);
            giveall.setCreationDate(creationDate);
            giveall.setStartDate(startDate);
            giveall.setEndDate(endDate);
            giveall.setItemsGave(itemsGave);
            return giveall;
        }
    }
}
