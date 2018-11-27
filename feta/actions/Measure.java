package feta.actions;

import feta.actions.stoppingconditions.StoppingCondition;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Measure extends SimpleAction {

    private long startTime_=10;
    private long interval_=10;
    public String measureName_ = "output/measurements.dat";
    public BufferedWriter bw_ = null;
    // Need to think how this will work alternating between directed and undirected networks.
    private boolean measureDegDist_=false;

    public Measure() {
        stoppingConditions_= new ArrayList<StoppingCondition>();
    }

    public void execute() {
        setUpMeasureWriter(measureName_);
        network_.setUpDegDistWriters(measureName_);
        long time = startTime_;
        network_.buildUpTo(time);
        try {
            while (!stoppingConditionsExceeded_(network_) && network_.linksToBuild_.size() > 0) {
                network_.buildUpTo(time);
                network_.calcMeasurements();
                bw_.write(network_.measureToString()+"\n");
                network_.writeDegDist();
                time += interval_;
            }
            bw_.close();
            network_.closeWriters();
        } catch (IOException e) {
            System.out.println("Problem writing measurements to: "+measureName_);
            e.printStackTrace();
        }

    }

    public void setUpMeasureWriter(String fname) {
        try{
            FileWriter fw = new FileWriter(fname);
            bw_= new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseActionOptions(JSONObject obj) {
        Long start = (Long) obj.get("Start");
        if (start != null)
            startTime_=start;

        Long interval = (Long) obj.get("Interval");
        if (interval != null) {
            if (interval >= 0) {
                interval_= interval;
            } else {
                System.err.println("Invalid interval");
            }
        }
        String measureFName = (String) obj.get("FileName");
        if (measureFName != null) {
            measureName_=measureFName;
        }
    }
}
