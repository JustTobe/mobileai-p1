package com.mobileai.luncert.dao.neo4j.node;

import com.mobileai.luncert.utils.IPUtil;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Host")
public class Host {

    @GraphId
    Long id;

    @Property(name = "ip")
    private int ip;

    @Property(name = "reliability")
    private double reliability;

    public Host() {}

    public Host(int ip, double reliability) {
        this.ip = ip;
        this.reliability = reliability;
    }

    public Host(String ip, double reliability) {
        this.ip = IPUtil.ipToInt(ip);
        this.reliability = reliability;
    }

    public void setId(Long id) { this.id = id; }

    public void setIp(int ip) { this.ip = ip; }

    public void setReliability(double reliability) { this.reliability = reliability; }

    public Long getId() { return id; }

    public int getIp() { return ip; }

    public String getIpString() { return IPUtil.ipToString(ip); }

    public double getReliability() { return reliability; }

}