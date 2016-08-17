package net.kenssoftware.client.model;

import java.io.Serializable;

public class StatsRequest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double min;
	public double max;
	public double lambda;
	public int numberOfMeans;
	public int numbersInMean;
	public String distrubtionType;
	public double probMinRange;
	public double probMaxRange;
	
	public StatsRequest()
	{
		
	}

	@Override
	public String toString() {
		return "StatsRequest [min=" + min + ", max=" + max + ", lambda=" + lambda + ", numberOfMeans=" + numberOfMeans
				+ ", numbersInMean=" + numbersInMean + ", distrubtionType=" + distrubtionType + ", probMinRange="
				+ probMinRange + ", probMaxRange=" + probMaxRange + "]";
	}
}
