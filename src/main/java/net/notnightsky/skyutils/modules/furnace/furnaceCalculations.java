package net.notnightsky.skyutils.modules.furnace;

public class furnaceCalculations {

    public static class FurnaceInfo {
        public final int fuelRemaining;
        public final int fuelTotal;
        public final int cookElapsed;
        public final int cookTotal;
        public final int totalItemsToSmelt;
        public final int itemsSmelted;

        public final float cookPercent;
        public final float fuelPercent;
        public final int totalTimeForAllItems;

        public FurnaceInfo(int fuelRemaining, int fuelTotal, int cookElapsed, int cookTotal, int totalItemsToSmelt, int itemsSmelted, int totalTimeForAllItems) {
            this.fuelRemaining = fuelRemaining;
            this.fuelTotal = fuelTotal;
            this.cookElapsed = cookElapsed;
            this.cookTotal = cookTotal;
            this.totalItemsToSmelt = totalItemsToSmelt;
            this.itemsSmelted = itemsSmelted;
            this.totalTimeForAllItems = totalTimeForAllItems;

            this.cookPercent = cookTotal <= 0 ? 0f : (float) cookElapsed / cookTotal;
            this.fuelPercent = fuelTotal <= 0 ? 0f : (float) fuelRemaining / fuelTotal;
        }

        //formating done by chatgpt
        public String formatTicks(int ticks) {
            if (ticks <= 0) return "0s";
            int sec = ticks / 20;
            int m = sec / 60;
            int s = sec % 60;
            return (m > 0) ? String.format("%d:%02d", m, s) : s + "s";
        }

        public String remainingTimeString() {
            int currentTimeRemaining = Math.max(0, cookTotal - cookElapsed);
            int remainingItemsCount = Math.max(0, totalItemsToSmelt - 1);
            int timeForRemainingItems = remainingItemsCount * cookTotal;
            int totalRemainingTime = currentTimeRemaining + timeForRemainingItems;

            return formatTicks(totalRemainingTime);
        }

        public String totalTimeString() {
            return formatTicks(totalItemsToSmelt * cookTotal);
        }

        public String fuelLeftString() {
            return formatTicks(fuelRemaining);
        }

        public String fuelPercentString() {
            if (fuelTotal <= 0) return "0%";
            int percent = Math.round((float) fuelRemaining / fuelTotal * 100);
            return percent + "%";
        }

        public String cookPercentString() {
            if (cookTotal <= 0) return "0%";
            int percent = Math.round((float) cookElapsed / cookTotal * 100);
            return percent + "%";
        }
    }

    public static FurnaceInfo fromProperties(int fr, int ft, int ce, int ct, int totalItems, int itemsSmelted) {

        int totalTimeForAllItems = 0;
        if (ct > 0 && totalItems > 0) {

            totalTimeForAllItems = ct * totalItems;
        } else if (totalItems > 0) {

            totalTimeForAllItems = totalItems * 200;
        }

        return new FurnaceInfo(fr, ft, ce, ct, totalItems, itemsSmelted, totalTimeForAllItems);
    }
}
