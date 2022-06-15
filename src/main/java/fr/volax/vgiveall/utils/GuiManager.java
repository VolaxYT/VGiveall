package fr.volax.vgiveall.utils;

import fr.volax.vgiveall.VGiveall;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public final class GuiManager implements Listener {
    @Getter private static final GuiManager instance = new GuiManager();

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player    player  = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if(event.getCurrentItem() == null) return;

        VGiveall.getInstance().getRegisteredMenus().values().stream()
                .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, event.getInventory(), current, event.getSlot());
                    event.setCancelled(true);
                });
    }

    public void addMenu(GuiBuilder m){
        VGiveall.getInstance().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends GuiBuilder> gClass){
        if(!VGiveall.getInstance().getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = VGiveall.getInstance().getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(player), menu.name());
        menu.contents(player, inv);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(VGiveall.getInstance(), 1);
    }
}
