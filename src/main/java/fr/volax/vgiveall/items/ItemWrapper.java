package fr.volax.vgiveall.items;

import fr.volax.vgiveall.givealls.Giveall;
import fr.volax.vgiveall.utils.ItemBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class ItemWrapper {
    @Getter private static final ItemWrapper instance = new ItemWrapper();

    @SneakyThrows
    public void addItem(Item item, Giveall giveall){
        FileConfiguration fileConfiguration = giveall.getFileConfiguration();

        ConfigurationSection items = fileConfiguration.getConfigurationSection("items");

        String number;
        if(items == null) number = "0";
        else number = String.valueOf(items.getKeys(false).size());

        fileConfiguration.set("items."+number+".adminName", item.getAdminName());
        fileConfiguration.set("items."+number+".adminID", item.getAdminID());
        fileConfiguration.set("items."+number+".date", item.getDate());
        fileConfiguration.set("items."+number+".type", item.getItemStack().getType().name());
        fileConfiguration.set("items."+number+".amount", item.getItemStack().getAmount());
        fileConfiguration.save(giveall.getFile());
    }

    public List<Item> parseConfig(FileConfiguration fileConfiguration){
        if(fileConfiguration.getConfigurationSection("items") == null)
            return null;

        List<Item> items = new ArrayList<>();

        for(String number : fileConfiguration.getConfigurationSection("items").getKeys(false)){
            items.add(new Item.Builder()
                    .withItemStack(new ItemBuilder(Material.getMaterial(fileConfiguration.getString("items." + number + ".type")), fileConfiguration.getInt("items." + number + ".amount")).toItemStack())
                    .withAdminID(fileConfiguration.getString("items." + number + ".adminID"))
                    .withAdminName(fileConfiguration.getString("items." + number + ".adminName"))
                    .withDate(fileConfiguration.getString("items." + number + ".date"))
                    .build());
        }

        return items;
    }

    public String toString(List<Item> items){
        if(items.isEmpty())
            return null;
        StringBuilder itemsString = new StringBuilder();

        for(Item item : items)
            itemsString.append("§6- §e" + item.getItemStack().getAmount() + " " + item.getItemStack().getType().name() + " §6| §ePar " + item.getAdminName() + " §6| §eLe " + item.getDate() + "\n");

        return itemsString.toString();
    }
}
