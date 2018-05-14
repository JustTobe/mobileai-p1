package com.mobileai.luncert.model.neo4j.relationship;

import com.mobileai.luncert.model.normal.NormalEvent;

import org.neo4j.driver.v1.Value;

public class Reference {

    private static final double L1_WIEGHT = 0.1;

    private static final double L2_WIEGHT = 0.3;

    private static final double L3_WIEGHT = 0.5;

    private static final double L4_WIEGHT = 0.7;
    
    private double riskEstimates;

    private double l1Probability;

    private double l2Probability;

    private double l3Probability;

    private double l4Probability;

    public Reference() {
        l1Probability = 0.25;
        l2Probability = 0.25;
        l3Probability = 0.25;
        l4Probability = 0.25;
        calcRiskEstimates();
    }

    public Reference(Value value) {
        this.riskEstimates = value.get("riskEstimates", 0);
        this.l1Probability = value.get("l1Probability", 0.25);
        this.l2Probability = value.get("l2Probability", 0.25);
        this.l3Probability = value.get("l3Probability", 0.25);
        this.l4Probability = value.get("l4Probability", 0.25);
    }

    public void calcRiskEstimates() {
        riskEstimates = l1Probability * L1_WIEGHT + l2Probability * L2_WIEGHT + l3Probability * L3_WIEGHT + l4Probability * L4_WIEGHT;
    }

    public void setRiskEstimates(double riskEstimates) { this.riskEstimates = riskEstimates; }

    public void setProbability(int typeIdentifier) {
        double l1 = 100 * l1Probability,
                l2 = 100 * l2Probability,
                l3 = 100 * l3Probability,
                l4 = 100 * l4Probability;
        if (typeIdentifier == NormalEvent.DETAIL_ATTACK_PATTERN) l1Probability++;
        else if (typeIdentifier == NormalEvent.STANDARD_ATTACK_PATTERN) l2Probability++;
        else if (typeIdentifier == NormalEvent.META_ATTACK_PATTERN) l3Probability++;
        else l4Probability++;
        
        double total = l1 + l2 + l3 + l4;
        l1 = l1 / total;
        l2 = l2 / total;
        l3 = l3 / total;
        l4 = l4 / total;

        calcRiskEstimates();
    }

    public void setL1Probability(double l1Probability) { this.l1Probability = l1Probability; }

    public void setL2Probability(double l2Probability) { this.l2Probability = l2Probability; }

    public void setL3Probability(double l3Probability) { this.l2Probability = l3Probability; }

    public void setL4Probability(double l4Probability) { this.l2Probability = l4Probability; }

    public double getRiskEstimates() { return riskEstimates; }

    public double getL1Probability() { return l1Probability; }
    
    public double getL2Probability() { return l2Probability; }

    public double getL3Probability() { return l3Probability; }

    public double getL4Probability() { return l4Probability; }

}