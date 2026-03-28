package com.example.gardenapp;

        import java.util.ArrayList;
        import java.util.List;

// Crop class
class Crop {
    private String name;
    private int growthTime;        // total days to grow
    private int wateringInterval;  // water every X days

    public Crop(String name, int growthTime, int wateringInterval) {
        this.name = name;
        this.growthTime = growthTime;
        this.wateringInterval = wateringInterval;
    }

    public String getName() {
        return name;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public int getWateringInterval() {
        return wateringInterval;
    }
}

public class ScheduleManager {

    private List<Crop> crops;

    public ScheduleManager() {
        crops = new ArrayList<>();
    }

    public void addCrop(Crop crop) {
        crops.add(crop);
    }

    public List<String> generateSchedule() {
        List<String> schedule = new ArrayList<>();

        for (Crop crop : crops) {
            for (int day = 1; day <= crop.getGrowthTime(); day++) {
                if (day % crop.getWateringInterval() == 0) {
                    schedule.add("Day " + day + ": Water " + crop.getName());
                }
                if (day == crop.getGrowthTime()) {
                    schedule.add("Day " + day + ": Harvest " + crop.getName());
                }
            }
        }
        return schedule;
    }

    // Example main method to test logic
    public static void main(String[] args) {
        ScheduleManager manager = new ScheduleManager();

        manager.addCrop(new Crop("Tomato", 10, 2));
        manager.addCrop(new Crop("Carrot", 8, 3));

        List<String> schedule = manager.generateSchedule();

        for (String entry : schedule) {
            System.out.println(entry);
        }
    }
}