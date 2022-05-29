package fr.volax.vgiveaway.utils;

import fr.volax.vgiveaway.VGiveaway;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ConfigBuilder {
    public        FileManager   configs;
    public static ConfigBuilder instance;

    public ConfigBuilder(FileManager fileManager) {
        this.configs = fileManager;
        instance = this;
    }

    public String getString(String path, String config){
        return configs.getConfig(config).get().getString(path).replaceAll("&","§");
    }

    public int getInt(String path, String config){
        return configs.getConfig(config).get().getInt(path);
    }

    public double getDouble(String path, String config) {
        return configs.getConfig(config).get().getDouble(path);
    }

    public double getDouble(String path) {
        return VGiveaway.getInstance().getConfig().getDouble(path);
    }

    public Boolean getBoolean(String path, String config){
        return configs.getConfig(config).get().getBoolean(path);
    }

    public void setString(String path, String data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
    }

    public void setInt(String path, int data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
        configs.getConfig(config).reload();
    }

    public void setBoolean(String path, boolean data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
    }

    public void setDouble(String path, double data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
    }

    public String getString(String path){
        return VGiveaway.getInstance().getConfig().getString(path).replaceAll("&","§");
    }

    public int getInt(String path){
        return VGiveaway.getInstance().getConfig().getInt(path);
    }

    public boolean getBoolean(String path){
        return VGiveaway.getInstance().getConfig().getBoolean(path);
    }

    public void set(String path, Object data){
        VGiveaway.getInstance().getConfig().set(path, data);
        VGiveaway.getInstance().saveConfig();
        VGiveaway.getInstance().reloadConfig();
    }

    public void set(String path, Object data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
        configs.getConfig(config).reload();
    }

    public List<?> getList(String path){
        return VGiveaway.getInstance().getConfig().getList(path);
    }

    public Object get(String path){
        return VGiveaway.getInstance().getConfig().get(path);
    }

    public Object get(String path, String config){
        return configs.getConfig(config).get(path);
    }

    public List<?> getList(String path, String config){
        return configs.getConfig(config).get().getList(path);
    }

    public List<String> getListString(String path){
        return (List<String>) VGiveaway.getInstance().getConfig().getList(path);
    }

    public List<String> getListString(String path, String config){
        return (List<String>) configs.getConfig(config).get().getList(path);
    }

    public List<Integer> getListInt(String path){
        return (List<Integer>) VGiveaway.getInstance().getConfig().getList(path);
    }

    public List<Integer> getListInt(String path, String config){
        return (List<Integer>) configs.getConfig(config).get().getList(path);
    }

    public List<World> getListWorlds(String path, String config){
        List<World> worlds = new ArrayList<>();

        for(String worldName : getListString(path, config)){
            worlds.add(Bukkit.getWorld(worldName));
        }

        return worlds;
    }

    public List<World> getListWorlds(String path){
        List<World> worlds = new ArrayList<>();

        for(String worldName : getListString(path)){
            worlds.add(Bukkit.getWorld(worldName));
        }

        return worlds;
    }

    public static ConfigBuilder getInstance() {
        return instance;
    }
}
