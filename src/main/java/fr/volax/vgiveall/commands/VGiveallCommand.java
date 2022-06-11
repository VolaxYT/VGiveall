package fr.volax.vgiveall.commands;

import fr.volax.vgiveall.VGiveall;
import fr.volax.vgiveall.givealls.Giveall;
import fr.volax.vgiveall.givealls.GiveallWrapper;
import fr.volax.vgiveall.items.Item;
import fr.volax.vgiveall.items.ItemWrapper;
import fr.volax.vgiveall.users.User;
import fr.volax.vgiveall.users.UserWrapper;
import fr.volax.vgiveall.utils.ChatUtil;
import fr.volax.vgiveall.utils.ItemBuilder;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VGiveallCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return false;

        if(!player.hasPermission("vgiveaway.giveaway.use")){
            player.sendMessage("&cIl vous est impossible d'accéder à cette commande !");
            return false;
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("start")){
                if(GiveallWrapper.getInstance().isGiveallNow()){
                    ChatUtil.sendMessage(player, "&eUn giveall est déjà en cours.");
                    return false;
                }

                GiveallWrapper.getInstance().toggle();
                Giveall giveall = GiveallWrapper.getInstance().createGiveall(player);
                GiveallWrapper.getInstance().setCurrentGiveall(giveall);

                ChatUtil.broadcastMessage("&eUn giveall vient d'être lancé !");
                ChatUtil.sendMessage(player, "&eID du giveall: &6" + giveall.getId());
                return false;
            }

            if(args[0].equalsIgnoreCase("stop")){
                if(!GiveallWrapper.getInstance().isGiveallNow()){
                    ChatUtil.sendMessage(player, "&eAucun giveall n'est en cours.");
                    return false;
                }

                GiveallWrapper.getInstance().toggle();
                GiveallWrapper.getInstance().syncEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), GiveallWrapper.getInstance().getCurrentGiveall());
                GiveallWrapper.getInstance().setCurrentGiveall(null);
                ChatUtil.broadcastMessage("&eLe giveall vient de s'arrêter !");
                return false;
            }
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("getuser")){
                if(!Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {
                    ChatUtil.sendMessage(player, "&eLe joueur " + args[1] + " ne s'est jamais connecté sur le serveur.");
                    return false;
                }
                User user = UserWrapper.getUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                player.sendMessage("§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="+
                        "\n§6Informations de §e" + args[1] +
                        "\n§6ID: §e" + user.getId() +
                        "\n§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

                return false;
            }

            if(args[0].equalsIgnoreCase("info")){
                File giveallFile = new File(VGiveall.getInstance().getGiveallsFolder() + "/" + args[1] + ".yml");

                if(!giveallFile.exists()){
                    ChatUtil.sendMessage(player, args[1] + " n'est pas un ID de giveall. (/giveall list)");
                    return false;
                }
                Giveall giveall = GiveallWrapper.getInstance().getGiveall(args[1]);

                player.sendMessage("§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="+
                        "\n§6Informations du giveall §e" + giveall.getId() +
                        "\n§6Crée par: §e" + giveall.getAdminName() + " §6| §e" + giveall.getAdminID() +
                        "\n§6Date de création: §e" + giveall.getCreationDate() +
                        "\n§6Date de début: §e" + giveall.getStartDate() +
                        "\n§6Date de fin: §e" + giveall.getEndDate() +
                        "\n§6Status: §e" + giveall.getGiveallStatus() +
                        "\n "+
                        "\n§6Items: §e" +
                        (ItemWrapper.getInstance().toString(giveall.getItemsGave()) == null ? "\n§6- §eAucun item n'a encore été give." : "\n" + ItemWrapper.getInstance().toString(giveall.getItemsGave())) +
                        "\n§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                return false;
            }
        }

        if(args.length == 4){
            if(args[0].equalsIgnoreCase("give")){
                if(args[1].equalsIgnoreCase("all")){
                    Material material = Material.matchMaterial(args[2]);
                    if(material == null){
                        ChatUtil.sendMessage(player, "&eL'item " + args[2] + " n'existe pas.");
                        return false;
                    }

                    if(!isInt(args[3])){
                        ChatUtil.sendMessage(player, "&e" + args[3] + " n'est pas un chiffre valide.");
                        return false;
                    }

                    if(!GiveallWrapper.getInstance().isGiveallNow()){
                        ChatUtil.sendMessage(player, "&eAucun giveall est en cours.");
                        return false;
                    }

                    ItemStack itemStack = new ItemStack(material, Integer.parseInt(args[3]));

                    for(Player player1 : Bukkit.getServer().getOnlinePlayers())
                        player1.getInventory().addItem(itemStack);

                    ItemWrapper.getInstance().addItem(new Item.Builder()
                            .withItemStack(new ItemBuilder(itemStack.getType(), Integer.parseInt(args[3])).toItemStack())
                            .withDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                            .withAdminName(player.getName())
                            .withAdminID(UserWrapper.getUser(player.getUniqueId()).getId())
                            .build(), GiveallWrapper.getInstance().getCurrentGiveall());

                    ChatUtil.sendMessage(player, "&eGive avec succès de &6" + args[3] + " " + args[2] + " &eà l'ensemble des joueurs.");
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
        List<String> result = new ArrayList<>();

        if(args.length == 1){
            for(String str : new ArrayList<>(Arrays.asList("give", "getUser", "info", "start", "stop"))){
                if(str.toLowerCase().startsWith(args[0].toLowerCase())){
                    result.add(str);
                }
            }
            return result;
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("give")){
                for(String str : new ArrayList<>(Arrays.asList("all"))){
                    if(str.toLowerCase().startsWith(args[1].toLowerCase())){
                        result.add(str);
                    }
                }
                return result;
            }
        }

        if(args.length == 3){
            if(args[0].equalsIgnoreCase("give")){
                for(Material material : Material.values()){
                    if(material.toString().toLowerCase().startsWith(args[2].toLowerCase())){
                        result.add(material.toString());
                    }
                }
                return result;
            }
        }

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
