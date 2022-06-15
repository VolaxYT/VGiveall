package fr.volax.vgiveall.menu;

import fr.volax.vgiveall.givealls.Giveall;
import fr.volax.vgiveall.givealls.GiveallWrapper;
import fr.volax.vgiveall.items.Item;
import fr.volax.vgiveall.utils.GuiBuilder;
import fr.volax.vgiveall.utils.ItemBuilder;
import fr.volax.vgiveall.utils.MetadataUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveallInfosMenu implements GuiBuilder {
    @Override
    public String name() {
        return "§6Informations du giveall";
    }

    @Override
    public int getSize(Player player) {
        Giveall giveall = GiveallWrapper.getInstance().getGiveall(MetadataUtil.getMetadataString("player.givealls.lastid", player));

        int guiSize = (int) (Math.ceil((giveall.getItemsGave().size() / 9.0) ) * 9) + 18;

        return Math.min(guiSize, 54);

    }

    @Override
    public void contents(Player player, Inventory inv) {
        Giveall giveall = GiveallWrapper.getInstance().getGiveall(MetadataUtil.getMetadataString("player.givealls.lastid", player));
        List<Item> giveallItems = giveall.getItemsGave();

        int guiSize = (int) (Math.ceil((giveall.getItemsGave().size() / 9.0) ) * 9) + 18;
        int whereStart = 9;

        ItemStack font = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setName("§r").toItemStack();

        for(int i= 0; i <= 8; i++)
            inv.setItem(i, font);

        for(int i= guiSize - 9; i <= guiSize - 1; i++)
            inv.setItem(i, font);

        if(!giveallItems.isEmpty()){
            for(Item item : giveallItems){
                inv.setItem(whereStart, new ItemBuilder(item.getItemStack().getType(), 1).setName("§6Item: §e" + item.getItemStack().getType().name()).setLore("§6Quantité: §e" + item.getItemStack().getAmount(), "§r", "§6Donné par: §e" + item.getAdminName(), "§6Donné le: §e" + item.getDate()).toItemStack());
                whereStart++;
            }
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

    }
}
