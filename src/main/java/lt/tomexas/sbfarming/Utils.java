package lt.tomexas.sbfarming;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import lt.tomexas.sbfarming.tasks.GrowthTask;
import lt.tomexas.sbfarming.tasks.SprinklerTask;
import lt.tomexas.sbfarming.info.PlanterInfo;
import lt.tomexas.sbfarming.info.SprinklerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Utils {

    private final Main main = Main.getInstance();

    public void saveSprinklerInfo() {
        try {
            for (Map.Entry<Island, List<SprinklerInfo>> map : main.getSprinklerInfoHashMap().entrySet()) {
                File file = new File(main.getDataFolder() + "/data/" + map.getKey().getUniqueId() + "/sprinklers.yml");
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
                yml.set("sprinklers", null);
                for (SprinklerInfo info : map.getValue()) {
                    String xyz = info.getLocation().getBlockX() + "/" + info.getLocation().getBlockY() + "/" + info.getLocation().getBlockZ();
                    yml.set("sprinklers." + xyz + ".location", info.getLocation());
                    yml.set("sprinklers." + xyz + ".entityUUID", info.getUuid());
                    yml.set("sprinklers." + xyz + ".hasWater", info.hasWater());
                }
                yml.save(file);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void retrieveSprinklerInfo() {
        File file = new File(main.getDataFolder() + "/data");
        String[] islandUUIDs = file.list();
        for (String islandUUID : islandUUIDs) {
            UUID uuid = UUID.fromString(islandUUID);
            Island island = SuperiorSkyblockAPI.getIslandByUUID(uuid);
            File f = new File(main.getDataFolder() + "/data/" + islandUUID + "/sprinklers.yml");
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

            if (yml.getConfigurationSection("sprinklers") != null) {
                for (String key : yml.getConfigurationSection("sprinklers").getKeys(false)) {
                    ConfigurationSection block = yml.getConfigurationSection("sprinklers." + key);
                    addSprinklerToHashMap(island, block.getLocation("location"), getEntity(block.getString("entityUUID")), block.getBoolean("hasWater"));
                    Bukkit.broadcastMessage("SUCCESS");
                }
            }
        }

        new SprinklerTask().runTaskTimer(main, 0L, 60L);
    }

    public void savePlanterInfo() {
        try {
            for (Map.Entry<Island, List<PlanterInfo>> map : main.getPlanterInfoHashMap().entrySet()) {
                File file = new File(main.getDataFolder() + "/data/" + map.getKey().getUniqueId() + "/planters.yml");
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
                yml.set("planters", null);
                for (PlanterInfo info : map.getValue()) {
                    String xyz = info.getLocation().getBlockX() + "/" + info.getLocation().getBlockY() + "/" + info.getLocation().getBlockZ();
                    yml.set("planters." + xyz + ".location", info.getLocation());
                    yml.set("planters." + xyz + ".plantId", info.getPlantId());
                    yml.set("planters." + xyz + ".plantUuid", info.getPlantUUID());
                    yml.set("planters." + xyz + ".plantFullyGrown", info.isPlantFullyGrown());
                    yml.set("planters." + xyz + ".growthState", info.getGrowthState());
                    yml.set("planters." + xyz + ".isWatered", info.isWatered());
                    yml.set("planters." + xyz + ".isTilled", info.isTilled());
                }
                yml.save(file);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void retrievePlanterInfo() {
        File file = new File(main.getDataFolder() + "/data");
        String[] islandUUIDs = file.list();
        for (String islandUUID : islandUUIDs) {
            UUID uuid = UUID.fromString(islandUUID);
            Island island = SuperiorSkyblockAPI.getIslandByUUID(uuid);
            File f = new File(main.getDataFolder() + "/data/" + islandUUID + "/planters.yml");
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

            if (yml.getConfigurationSection("planters") != null) {
                for (String key : yml.getConfigurationSection("planters").getKeys(false)) {
                    ConfigurationSection block = yml.getConfigurationSection("planters." + key);
                    //Location location = block.getLocation("location");
                    //CustomBlock.place("blocks:planter-moist-farmland", location);
                    addPlanterToHashMap(island, block.getLocation("location"), block.getString("plantId"), block.getString("plantUUID"), getEntity(block.getString("plantUuid")), block.getBoolean("plantFullyGrown"), block.getInt("growthState"), block.getBoolean("isWatered"), block.getBoolean("isTilled"));
                    Bukkit.broadcastMessage("SUCCESS");
                }
            }
        }

        new GrowthTask().runTaskTimer(main, 0L, 17L);
    }

    public String parseTime(long time) {
        long gameTime = time;
        long hours = gameTime / 1000 + 6;
        long minutes = (gameTime % 1000) * 60 / 1000;
        if (hours >= 24) {
            hours -= 24;
        }
        String mm = "0" + minutes;
        mm = mm.substring(mm.length() - 2);

        if (hours >= 0 && hours < 10) return "0" + hours + ":" + mm;
        else return hours + ":" + mm;
    }

    public void removeSprinklerFromHashMap(Island island, Location location) {
        List<SprinklerInfo> infoList = main.getSprinklerInfoHashMap().get(island);
        for (Iterator<SprinklerInfo> iterator = infoList.iterator(); iterator.hasNext();) {
            SprinklerInfo info = iterator.next();
            if (info.getLocation().equals(location)) {
                //Bukkit.getScheduler().cancelTask(info.getTaskId());
                iterator.remove();
                Bukkit.broadcastMessage("REMOVED");
            }
        }
    }

    public void addSprinklerToHashMap(Island island, Location location, Entity entity) {
        int taskId = 0;//new SprinklerTask().runTaskTimer(main, 0, 60L).getTaskId();
        if (main.getSprinklerInfoHashMap().get(island) == null) {
            List<SprinklerInfo> infoList = new ArrayList<>();
            infoList.add(new SprinklerInfo(location, entity, entity.getUniqueId().toString(), false, taskId));
            main.getSprinklerInfoHashMap().put(island, infoList);
        } else main.getSprinklerInfoHashMap().get(island).add(new SprinklerInfo(location, entity, entity.getUniqueId().toString(), false, taskId));
        Bukkit.broadcastMessage("ADDED");
    }

    public void addSprinklerToHashMap(Island island, Location location, Entity entity, boolean hasWater) {
        int taskId = 0; //new SprinklerTask().runTaskTimer(main, 0, 60L).getTaskId();
        if (main.getSprinklerInfoHashMap().get(island) == null) {
            List<SprinklerInfo> infoList = new ArrayList<>();
            infoList.add(new SprinklerInfo(location, entity, entity.getUniqueId().toString(), hasWater, taskId));
            main.getSprinklerInfoHashMap().put(island, infoList);
        } else main.getSprinklerInfoHashMap().get(island).add(new SprinklerInfo(location, entity, entity.getUniqueId().toString(), hasWater, taskId));
        Bukkit.broadcastMessage("ADDED");
    }

    public void removePlanterFromHashMap(Island island, Location location) {
        List<PlanterInfo> infoList = main.getPlanterInfoHashMap().get(island);
        for (Iterator<PlanterInfo> iterator = infoList.iterator(); iterator.hasNext();) {
            PlanterInfo info = iterator.next();
            if (info.getLocation().equals(location)) {
                iterator.remove();
                Bukkit.broadcastMessage("REMOVED");
            }
        }
    }

    public void addPlanterToHashMap(Island island, Location location) {
        if (main.getPlanterInfoHashMap().get(island) == null) {
            List<PlanterInfo> infoList = new ArrayList<>();
            infoList.add(new PlanterInfo(location, "", "",null, false, 0, false, false));
            main.getPlanterInfoHashMap().put(island, infoList);
        } else main.getPlanterInfoHashMap().get(island).add(new PlanterInfo(location, "", "", null, false, 0, false, false));
        Bukkit.broadcastMessage("ADDED");
    }

    public void addPlanterToHashMap(Island island, Location location, String plantId, String plantUUID, Entity plantEntity, boolean plantFullyGrown, int growthState, boolean isWatered, boolean isTilled) {
        if (main.getPlanterInfoHashMap().get(island) == null) {
            List<PlanterInfo> infoList = new ArrayList<>();
            infoList.add(new PlanterInfo(location, plantId, plantUUID, plantEntity, plantFullyGrown, growthState, isWatered, isTilled));
            main.getPlanterInfoHashMap().put(island, infoList);
        } else main.getPlanterInfoHashMap().get(island).add(new PlanterInfo(location, plantId, plantUUID, plantEntity, plantFullyGrown, growthState, isWatered, isTilled));
        Bukkit.broadcastMessage("ADDED");
    }

    public CustomStack getSeedFurniture(CustomFurniture plant) {
        String[] id = plant.getId().split("-");
        List<CustomStack> seeds = ItemsAdder.getAllItems("seeds");
        for (CustomStack seed : seeds) {
            if (seed.getId().equalsIgnoreCase(id[0] + "-seeds")) {
                return CustomStack.getInstance(seed.getId());
            }
        }
        return null;
    }

    public CustomStack getSeedProduce(CustomFurniture plant) {
        String[] id = plant.getId().split("-");
        List<CustomStack> foodList = ItemsAdder.getAllItems("food");
        for (CustomStack food : foodList) {
            if (food.getId().equalsIgnoreCase(id[0])) {
                return CustomStack.getInstance(food.getId());
            }
        }
        return null;
    }

    public CustomStack getSeedStack(CustomStack item) {
        List<CustomStack> seeds = ItemsAdder.getAllItems("seeds");
        for (CustomStack seed : seeds) {
            if (item.getId().equalsIgnoreCase(seed.getId())) {
                return seed;
            }
        }
        return null;
    }

    private Entity getEntity(String uuid) {
        List<Entity> entities = Bukkit.getWorld("SuperiorWorld").getEntities();
        for (Entity entity : entities) {
            if (entity.getUniqueId().toString().equals(uuid)) {
                return entity;
            }
        }
        return null;
    }

    public Boolean percentChance(double chance) {
        return Math.random() <= chance;
    }

    private String convertTextToImage(String text) {
        //text = text.toLowerCase(Locale.ROOT);
        for(int i = 0; i < text.length(); i++) {
            if (main.getDictionary().getLetter(String.valueOf(text.charAt(i))) == null) continue;
            text = text.replace(String.valueOf(text.charAt(i)), main.getDictionary().getLetter(String.valueOf(text.charAt(i))));
        }
        return text;
    }

    /*private String addBackground(String title) {
        int titleLength = title.length();
        StringBuilder titleBuilder = new StringBuilder();
        StringBuilder moverBuilder = new StringBuilder();
        for (int i = 0; i < titleLength+1; i++) {
            titleBuilder.append("\uF811\uE257");
            moverBuilder.append("\uF814");
        }
        String bg = titleBuilder.toString();
        String mover = moverBuilder.toString();

        return "\uE254" + bg + "\uF811\uE254" + mover + "" + title;
    }*/
}
