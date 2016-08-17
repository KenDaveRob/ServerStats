package net.kenssoftware.client.model;

import java.io.Serializable;
import java.util.ArrayList;

public class StatsData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Double> means;
	private double stdDev;
	private double probabilityInRange;
	private double meanOfMeans;
	private String distributionType;
	
	public StatsData()
	{
		
	}
	
	public StatsData(String distributionType, double meanOfMeans, ArrayList<Double> means, double probabilityInRange, double stdDev)
	{
		this.meanOfMeans = meanOfMeans;
		this.distributionType = distributionType;
		this.means = means;
		this.probabilityInRange = probabilityInRange;
		this.stdDev = stdDev;
	}
	
	public double getMeanOfMeans()
	{
		return meanOfMeans;
	}
	
	public String getDistributionType()
	{
		return distributionType;
	}
	
	public ArrayList<Double> getMeans()
	{
		return means;
	}
	
	public double getProbabilityInRange()
	{
		return probabilityInRange;
	}
	
	public double getStdDev()
	{
		return stdDev;
	}

}
