package fr.volax.vgiveaway.utils;

import fr.volax.vgiveaway.VGiveaway;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiManager implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player    player  = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if(event.getCurrentItem() == null) return;

        VGiveaway.getInstance().getRegisteredMenus().values().stream()
                .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, event.getInventory(), current, event.getSlot());
                    event.setCancelled(true);
                });
    }

    public void addMenu(GuiBuilder m){
        VGiveaway.getInstance().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends GuiBuilder> gClass){
        if(!VGiveaway.getInstance().getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = VGiveaway.getInstance().getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(VGiveaway.getInstance(), 1);
    }
}
