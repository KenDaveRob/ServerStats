package net.kenssoftware.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Histogram
{
	private ArrayList<Double> means;
    private int sections = -1;
	private Canvas canvas;
	private Context2d g2D;
	private final int canvasWidth;// = 400;
	private final int canvasHeight;// = 300;
	
	public Histogram(int width, int height)
	{
		canvasWidth = width;
		canvasHeight = height;
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
            RootPanel.get().add(new Label("Sorry, your browser doesn't support the HTML5 Canvas element"));
            return;
		}
		
		canvas = Canvas.createIfSupported();
        canvas.setStyleName("mainCanvas");
        canvas.setWidth(canvasWidth + "px");
        canvas.setCoordinateSpaceWidth(canvasWidth);
         
        canvas.setHeight(canvasHeight + "px");      
        canvas.setCoordinateSpaceHeight(canvasHeight);
        //RootPanel.get().add(canvas);//This should be done inside the onModuleLoad method
        g2D = canvas.getContext2d();
	}
	
	private void redraw()
	{
		//double screenWidthFactor = 0;
        double screenHeightFactor = 0;
        double screenSectionSize = ((double)canvasWidth)/((double)sections+1.0d);
        g2D.clearRect(0, 0, canvasWidth, canvasHeight);
        
        //Dont draw anything without data
        if(means != null && sections > 0)
        {
            HashMap<Integer,Integer> frequencies = new HashMap<Integer,Integer>(); //Keeps track of the frequencies for each class
            double highestMean = means.get(means.size()-1);
            double lowestMean = means.get(0);
            //screenWidthFactor = canvasWidth / highestMean;
            double range = highestMean - lowestMean;
            double currentMean = 0;
            int currentFrequencyClass = 1;
            double nextRange = lowestMean + (range/(sections));
            int highestFrequency = -1;
            double currentFrequencyHeight = 0;
            
 
            for(int i = 1; i <= sections; i++) //Initialize all frequencies to 0
                frequencies.put(i, 0);


            for(int i = 0; i < means.size(); i++)
            {         
                currentMean = means.get(i);

                //As long as the currentMean is < nextRange increase the frequency class and nextRange
                while(nextRange < currentMean && Math.abs(nextRange - currentMean) > .00001) 
                {
                    currentFrequencyClass++;
                    nextRange += (range / (sections));
                }
                
                //This if statement maybe redundant, it checks that that the while statement is now false,and
                //increments the frequency for the currentFrequencyClass
                if(nextRange >= currentMean || Math.abs(nextRange - currentMean) < .00001)         
                    frequencies.put(currentFrequencyClass, frequencies.get(currentFrequencyClass) + 1);
            }
            

            
            //Find the highest frequency
            for(int i = 1; i <= sections; i++)
                if(highestFrequency < frequencies.get(i)) highestFrequency = frequencies.get(i);
                        
            screenHeightFactor = canvasHeight/highestFrequency;
            
            //Draw the histogram to the panel
            for(int i = 1; i <= sections; i++)
            {
                currentFrequencyClass = frequencies.get(i);

                currentFrequencyHeight = currentFrequencyClass * screenHeightFactor;
                g2D.beginPath();
                g2D.setStrokeStyle("red");
                g2D.strokeRect(screenSectionSize * i - (screenSectionSize/2.0d), canvasHeight-currentFrequencyHeight, 
                        screenSectionSize, currentFrequencyHeight);
                g2D.closePath();
            }

            //Add text to histogram
            g2D.beginPath();
            g2D.setStrokeStyle("black");
            g2D.strokeText(Double.toString(lowestMean).substring(0, 5), (float)(screenSectionSize/2.0f), (float)canvasHeight-2.0f);
            g2D.strokeText(Double.toString(highestMean).substring(0, 5), (float)(screenSectionSize * sections - (screenSectionSize/2.0f)), (float)canvasHeight-2.0f);
            g2D.strokeText("top freq="+Integer.toString(highestFrequency), 2.0f, 12.0f);
        }
	}
	
	public void drawHistogram(int sections, ArrayList<Double> means)
    {
        this.sections = sections;
        Collections.sort(means);
        this.means = means;
        this.redraw();
    }
	
	public Canvas getCanvas()
	{
		return this.canvas;
	}
	
}