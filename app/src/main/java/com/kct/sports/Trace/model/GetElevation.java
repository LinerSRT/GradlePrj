package com.kct.sports.Trace.model;

import java.util.List;

public class GetElevation {
    List<Data> results;
    String status;

    public class Data {
        double elevation;

        public double getElevation() {
            return this.elevation;
        }
    }

    public List<Data> getResults() {
        return this.results;
    }

    public String getStatus() {
        return this.status;
    }
}
