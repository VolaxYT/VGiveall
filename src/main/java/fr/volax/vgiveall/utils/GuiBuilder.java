package fr.volax.vgiveall.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {
    String name();
    int getSize(Player player);
    void contents(Player player, Inventory inv);
    void onClick(Player player, Inventory inv, ItemStack current, int slot);
}
