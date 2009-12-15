package com.bradrydzewski.gwt.calendar.client.monthview;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the <code>MultiDayLayoutDescription</code> and <code>
 */
public class AppointmentStackingManager {

    private int highestLayer = 0;

    private HashMap<Integer, ArrayList<AppointmentLayoutDescription>>
            layeredDescriptions =
            new HashMap<Integer, ArrayList<AppointmentLayoutDescription>>();

    public void assignLayer(AppointmentLayoutDescription description) {
        boolean layerAssigned;
        int currentlyInspectedLayer = 0;
        do {
            initLayer(currentlyInspectedLayer);
            layerAssigned = assignLayer(currentlyInspectedLayer, description);
            currentlyInspectedLayer++;
        } while (!layerAssigned);
    }

    public int singleDayLowestOrder(int day) {
        boolean layerFound = false;
        int currentlyInspectedLayer = 0;
        do {
            boolean isLayerAllocated =
                    isLayerAllocated(currentlyInspectedLayer);
            if (isLayerAllocated) {
                if (overlapsWithDescriptionInLayer(
                        layeredDescriptions.get(currentlyInspectedLayer), day,
                        day)) {
                    currentlyInspectedLayer++;
                } else {
                    layerFound = true;
                }
            } else {
                layerFound = true;
            }

        } while (!layerFound);
        return currentlyInspectedLayer;
    }

    public ArrayList<AppointmentLayoutDescription> getDescriptionsInLayer(
            int layer) {
        return layeredDescriptions.get(layer);
    }

    private boolean overlapsWithDescriptionInLayer(
            ArrayList<AppointmentLayoutDescription> layerDescriptions, int start,
            int end) {
        if (layerDescriptions != null) {
            for (AppointmentLayoutDescription layerDescription : layerDescriptions) {
                if (layerDescription
                        .overlapsWithRange(start, end)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean assignLayer(int layer,
    		AppointmentLayoutDescription description) {
        ArrayList<AppointmentLayoutDescription> layerDescriptions =
                layeredDescriptions.get(layer);

        boolean assigned = false;
        if (!overlapsWithDescriptionInLayer(layerDescriptions,
                description.getWeekStartDay(), description.getWeekEndDay())) {
            layerDescriptions.add(description);
            highestLayer = Math.max(highestLayer, layer);
            assigned = true;
        }
        return assigned;
    }

    private boolean isLayerAllocated(int layer) {
        return layeredDescriptions.get(layer) != null;
    }

    private void initLayer(int layerNumber) {
        if (!isLayerAllocated(layerNumber)) {
            layeredDescriptions.put(layerNumber,
                    new ArrayList<AppointmentLayoutDescription>());
        }
    }

    public boolean areThereAppointmentsOn(int day) {
        boolean thereAre = false;
        for (int layersIndex = 0; layersIndex <= highestLayer; layersIndex++) {
            ArrayList<AppointmentLayoutDescription> layerDescriptions =
                    layeredDescriptions.get(layersIndex);
            if (overlapsWithDescriptionInLayer(layerDescriptions, day, day)) {
                thereAre = true;
                break;
            }
        }
        return thereAre;
    }

    public int getHighestLayer()
    {
        return highestLayer;
    }
}
