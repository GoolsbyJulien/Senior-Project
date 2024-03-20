package com.jra.api.util;

public class Profiler {


    public long[] times = new long[100];
    private long startTime;

    private long endTime;


    public int nextTime = 0;
    boolean allFilled = false;

    public void start() {
        startTime = System.currentTimeMillis();

    }

    public void end() {
        endTime = System.currentTimeMillis() - startTime;
        times[nextTime] = endTime;

        nextTime++;
        if (nextTime == times.length) {
            nextTime = 0;
        }

    }

    public long getAverage() {

        int max = allFilled ? times.length : nextTime;
        long sum = 0;
        if (max == 0) {
            return times[0];
        }
        for (int i = 0; i < max; i++) {
            sum += times[i];
        }


        return sum / max;
    }

}

