package net.kenssoftware.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import net.kenssoftware.client.model.StatsRequest;
import net.kenssoftware.client.service.StatServiceClientImpl;

public class MainGUI extends Composite 
{
	public TextArea outputTextArea;
    private Button uniformButton;
    private Button expButton;
    private Button testButton;
    public TextBox numberOfClassesTextBox;
    private Label numberOfClassesLabel;
    public Histogram histogram;
    private VerticalPanel mainPanel;
    private StatServiceClientImpl clientImpl;
    
    public MainGUI(){}
    
	public MainGUI(StatServiceClientImpl clientImpl)
	{
		this.clientImpl = clientImpl;
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		uniformButton = new Button("Run U(-3,6) Test");
        //uniformButton.addClickHandler(handler)
        uniformButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                uniformButtonActionPerformed(event);
            }
        });
        
        expButton = new Button("Run Exp(.2=lambda) Test");
        expButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                expButtonActionPerformed(event);
            }
        });
        
        testButton = new Button("Test Server");
        testButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                testButtonActionPerformed(event);
            }
        });
        
        numberOfClassesLabel = new Label("Number of classes: ");
        numberOfClassesLabel.getElement().getStyle().setColor("black");

        numberOfClassesTextBox = new TextBox();
        numberOfClassesTextBox.setText("20");
        numberOfClassesTextBox.setWidth("2em");

        outputTextArea = new TextArea();
        outputTextArea.setCharacterWidth(75);
        outputTextArea.setVisibleLines(25);
        
        VerticalPanel bottomPanel = new VerticalPanel();
        HorizontalPanel inputPanel = new HorizontalPanel();
        inputPanel.setSpacing(4);
        HorizontalPanel histogramPanel = new HorizontalPanel();
        histogramPanel.setBorderWidth(0);       
        histogramPanel.setSpacing(0);
        
        //histogramPanel = new Histogram(mainPanel);
        histogram = new Histogram(510,320);
        
        histogramPanel.add(histogram.getCanvas());
        mainPanel.add(histogramPanel);
        mainPanel.add(bottomPanel);
        bottomPanel.add(outputTextArea);
        bottomPanel.add(inputPanel);
        inputPanel.add(uniformButton);
        inputPanel.add(expButton);
        //inputPanel.add(testButton);//Debugging only
        inputPanel.add(numberOfClassesLabel);
        inputPanel.add(numberOfClassesTextBox);
        mainPanel.getElement().setId("program-div");
	}
	
	private void expButtonActionPerformed(ClickEvent event)
	{		
		StatsRequest expRequest = new StatsRequest();
		expRequest.min = Double.NaN;
		expRequest.max = Double.NaN;
		expRequest.lambda = .2d;
		expRequest.numberOfMeans = 400;
		expRequest.numbersInMean = 625;
		expRequest.distrubtionType = "exp";
		expRequest.probMinRange = 3.0d;
		expRequest.probMaxRange = 5.0d;
		clientImpl.getStatsData(expRequest);
	}
	
	private void uniformButtonActionPerformed(ClickEvent event)
    {
		StatsRequest uniformRequest = new StatsRequest();
		uniformRequest.min = 3;
		uniformRequest.max = 9;
		uniformRequest.lambda = Double.NaN;
		uniformRequest.numberOfMeans = 100;
		uniformRequest.numbersInMean = 255;
		uniformRequest.distrubtionType = "uniform";
		uniformRequest.probMinRange = 1.0d;
		uniformRequest.probMaxRange = 2.0d;
		clientImpl.getStatsData(uniformRequest);
    }
	
	private void testButtonActionPerformed(ClickEvent event)
	{
		clientImpl.getTest(10);
	}

}
