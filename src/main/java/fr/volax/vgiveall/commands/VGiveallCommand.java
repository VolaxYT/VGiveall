package fr.volax.vgiveall.commands;

import fr.volax.vgiveall.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VGiveallCommand implements CommandExecutor, @Nullable TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player)sender;

        if(!player.hasPermission("vgiveaway.giveaway.use")){
            player.sendMessage("§cIl vous est impossible d'accéder à cette commande !");
            return false;
        }
        // /giveaway give all DIRT 64
        if(args.length == 4){
            if(args[0].equalsIgnoreCase("give")){
                if(args[1].equalsIgnoreCase("all")){
                    Material material = Material.matchMaterial(args[2]);
                    if(material == null){
                        ChatUtil.sendMessage(player, "§eAttention ! L'item " + args[2] + " n'existe pas.");
                        return false;
                    }

                    if(!isInt(args[3])){
                        ChatUtil.sendMessage(player, "§eAttention ! " + args[3] + " n'est pas un chiffre valide.");
                        return false;
                    }

                    ItemStack itemStack = new ItemStack(material, Integer.parseInt(args[3]));

                    for(Player player1 : Bukkit.getServer().getOnlinePlayers()){
                        player1.getInventory().addItem(itemStack);
                    }
                    ChatUtil.sendMessage(player, "§eGive avec succès de §6" + args[3] + " " + args[2] + " §eà l'ensemble des joueurs.");
                    ChatUtil.logMessage(player.getName() + " give => " + args[3] + " " + args[2] + " à @a");
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        // TODO check TODO.volax for args list
        return null;
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
