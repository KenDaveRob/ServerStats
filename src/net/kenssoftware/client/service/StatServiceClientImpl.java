package net.kenssoftware.client.service;


import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import net.kenssoftware.client.gui.MainGUI;
import net.kenssoftware.client.model.StatsData;
import net.kenssoftware.client.model.StatsRequest;
import net.kenssoftware.client.model.TestRequest;
import net.kenssoftware.client.model.TestResult;


public class StatServiceClientImpl implements StatServiceClientInt
{
	private MainGUI gui;
	private StatServiceAsync service;
	
	public StatServiceClientImpl(String url)
	{
		//com.google.gwt.core.client.GWT.log("url: "+url);
		System.out.println("url: "+url);
		this.service = GWT.create(StatService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget)this.service;
		endpoint.setServiceEntryPoint(url);
		
		this.gui = new MainGUI(this);
	}
	
	
	
	public MainGUI getGUI()
	{
		return this.gui;
	}



	@Override
	public void getStatsData(StatsRequest request) {
		this.service.getStatsData(request, new StatsCallback());	
		
	}



	@Override
	public void getTest(int id) {
		this.service.getTest(id, new StatsCallback());
	}
	
	@Override
	public void strTest(String str) //Client RPC calls
	{
		this.service.strTest(str, new StatsCallback());
	}
	
	@Override
	public void resultTest(TestRequest str) {
		this.service.resultTest(str, new StatsCallback());
		
	}
	
	private class StatsCallback implements AsyncCallback
	{

		@Override
		public void onFailure(Throwable caught) {
			com.google.gwt.core.client.GWT.log("StatsCallback onFailure called, caught = "+caught);
		}

		@Override
		public void onSuccess(Object result) {
			com.google.gwt.core.client.GWT.log("onSuccess");
			
			if(result != null && result instanceof String)
			{
				 gui.outputTextArea.setText((String)result);
			}
			
			else if(result != null && result instanceof StatsData)
			{
				StatsData statResult = (StatsData)result;
				if(statResult.getDistributionType().contentEquals("uniform"))
				{
					//Output results to text area
			        gui.outputTextArea.setText(gui.outputTextArea.getText()+
			                "The simulated probability that U(-3,6) has a mean between 1 and 2 is "+statResult.getProbabilityInRange()+"\n"+
			                "The mean of 100 means taken from 255 samples of U(-3,6) is "+statResult.getMeanOfMeans()+"\n"+
			                "The standard devation of the means of U(-3,6) is "+statResult.getStdDev()+"\n\n");

			        //Draw histogram of means
			        gui.histogram.drawHistogram(
			                java.lang.Integer.parseInt(gui.numberOfClassesTextBox.getText()), statResult.getMeans());
				}
				
				else if(statResult.getDistributionType().contentEquals("exp"))
				{
					//Output results to text area
					gui.outputTextArea.setText(gui.outputTextArea.getText()+
			                "The simulated probability that Exp(.2) has a mean between 3 and 5 is "+statResult.getProbabilityInRange()+"\n"+
			                "The mean of 400 means taken from 625 samples of Exp(.2) is "+statResult.getMeanOfMeans()+"\n"+
			                "The standard devation of the means of Exp(.2) is "+statResult.getStdDev()+"\n\n");
			        
			        //Draw histogram of means
					gui.histogram.drawHistogram(
			                Integer.parseInt(gui.numberOfClassesTextBox.getText()), statResult.getMeans());
				}
			}
			else if(result != null && result instanceof String)
				gui.outputTextArea.setText((String)result);
			else if(result != null && result instanceof TestResult)
			{
				TestResult testResult = (TestResult)result;
				gui.outputTextArea.setText(testResult.str);
			}
			else
			{
				com.google.gwt.core.client.GWT.log("Poorly formed or nonexistant result object, result ?= null: "+(result == null));
			}
		}
		
	}




}

