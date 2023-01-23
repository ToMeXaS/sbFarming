package lt.tomexas.sbfarming.info;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class PlanterInfo {

    private Location location;
    private String plantId;
    private String plantUUID;
    private Entity plantEntity;
    private boolean plantFullyGrown;
    private int growthState;
    private boolean watered;
    private boolean tilled;

    public PlanterInfo(Location location, String plantId, String plantUUID, Entity plantEntity, boolean plantFullyGrown, int growthState, boolean watered, boolean tilled) {
        this.location = location;
        this.plantId = plantId;
        this.plantUUID = plantUUID;
        this.plantEntity = plantEntity;
        this.plantFullyGrown = plantFullyGrown;
        this.growthState = growthState;
        this.watered = watered;
        this.tilled = tilled;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public Entity getPlantEntity() {
        return plantEntity;
    }

    public void setPlantEntity(Entity plantEntity) {
        this.plantEntity = plantEntity;
    }

    public String getPlantUUID() {
        return plantUUID;
    }

    public void setPlantUUID(String plantUUID) {
        this.plantUUID = plantUUID;
    }

    public boolean isPlantFullyGrown() {
        return plantFullyGrown;
    }

    public void setPlantFullyGrown(boolean plantFullyGrown) {
        this.plantFullyGrown = plantFullyGrown;
    }

    public int getGrowthState() {
        return growthState;
    }

    public void setGrowthState(int growthState) {
        this.growthState = growthState;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public boolean isTilled() {
        return tilled;
    }

    public void setTilled(boolean tilled) {
        this.tilled = tilled;
    }
}
