package com.mobileai.luncert.model.neo4j.node;

import com.mobileai.luncert.utils.IPUtil;

import org.neo4j.driver.v1.Value;

public class Host {

    private int ip;

    private double riskEstimates;

    public Host() {}

    public Host(Value value) {
        this.ip = value.get("ip", 0);
        this.riskEstimates = value.get("riskEstimates", 0);
    }

    public void setIp(int ip) { this.ip = ip; }

    public int getIp() { return ip; }

    public String getIpString() { return IPUtil.ipToString(ip); }

    public void setRiskEstimates(double riskEstimates) { this.riskEstimates = riskEstimates; }

    public double getRiskEstimates() { return riskEstimates; }

}