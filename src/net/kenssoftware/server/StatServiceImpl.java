package net.kenssoftware.server;

import java.util.ArrayList;
import java.util.Random;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import net.kenssoftware.client.model.StatsData;
import net.kenssoftware.client.model.StatsRequest;
import net.kenssoftware.client.model.TestRequest;
import net.kenssoftware.client.model.TestResult;
import net.kenssoftware.client.service.StatService;

public class StatServiceImpl extends RemoteServiceServlet implements StatService
{

	@Override
	public StatsData getStatsData(StatsRequest request) {
		com.google.gwt.core.client.GWT.log("getStatsData called.");
		com.google.gwt.core.client.GWT.log("request: "+request);
		System.out.println("getStatsData called.");
		System.out.println("request: "+request);
		
		ArrayList<Double> means = new ArrayList<Double>();
        Random rand = new Random();
        double currentTotal = 0;
        double meanOfMeans = 0.0d;
        double stdDevMeans = 0.0d;
        double probabilityInRange = 0.0d;
		if(request.distrubtionType.equals("uniform"))
		{
	        //Generate 100 means
	        for(int i = 0; i < request.numberOfMeans; i++)
	        {
	            //Collect 255 random numbers between 6 and 3
	            for(int j = 0; j < request.numbersInMean; j++)
	                currentTotal += rand.nextDouble()*request.max - request.min;
	            
	            currentTotal = currentTotal / (double)request.numbersInMean; //currentTotal becomes current mean
	            means.add(currentTotal); //Add to uniformMeans

	            //Check if mean is between 1 and 2
	            if(currentTotal < request.probMaxRange && currentTotal > request.probMinRange) probabilityInRange++;
	            meanOfMeans += currentTotal;
	            
	            currentTotal = 0;
	        }
	        
	        probabilityInRange /= (double)request.numberOfMeans;
	        meanOfMeans /= (double)request.numberOfMeans;
	        
	        //Find std deviation of Means using sqrt(RiemannSum((Xi - avgX)^2)/(n-1))
	        for(int i = 0; i < request.numberOfMeans; i++)
	            stdDevMeans += Math.pow(means.get(i)-meanOfMeans,2);
	        
	        stdDevMeans = Math.sqrt(stdDevMeans/(double)(request.numberOfMeans - 1.0d));
	        return new StatsData("uniform", meanOfMeans, means, probabilityInRange, stdDevMeans);
		}
		
		else if(request.distrubtionType.equals("exp"))
		{
			//Generate 400 means
	        for(int i = 0; i < request.numberOfMeans; i++)
	        {
	            //Collect 625 random numbers from the exponential distribution using
	            //(-1/lambda)*ln(1-x) where x is a random number between 0 and 1
	            for(int j = 0; j < request.numbersInMean; j++)
	                currentTotal += (-1.0/request.lambda)*Math.log(1.0d-rand.nextDouble());
	            
	            currentTotal = currentTotal / (double)request.numbersInMean; //currentTotal becomes current mean
	            means.add(currentTotal);
	
	            //Check if mean is between 3 and 5
	            if(currentTotal < request.probMaxRange && currentTotal > request.probMinRange) probabilityInRange++;
	            meanOfMeans += currentTotal;
	            
	            currentTotal = 0;
	        }
	        
	        probabilityInRange /= (double)request.numberOfMeans;
	        meanOfMeans /= (double)request.numberOfMeans;
	        
	        //Find std deviation of Means using sqrt(RiemannSum((Xi - avgX)^2)/(n-1))
	        for(int i = 0; i < request.numberOfMeans; i++)
	            stdDevMeans += Math.pow(means.get(i)-meanOfMeans,2);
	        
	        stdDevMeans = Math.sqrt(stdDevMeans/(double)(request.numberOfMeans - 1.0d));
	        return new StatsData("exp", meanOfMeans, means, probabilityInRange, stdDevMeans);
		}
		
		return null;
	}

	@Override
	public String getTest(int id) {
		return "server test id = "+id;
	}
	
	@Override
	public String strTest(String str) {
		return "String called: "+str;
	}

	@Override
	public TestResult resultTest(TestRequest str) {
		TestResult result = new TestResult();
		result.str = "TestResult.str called: "+str.str;
		return result;
	}
	
}
