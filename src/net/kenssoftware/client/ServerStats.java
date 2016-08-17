package net.kenssoftware.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import net.kenssoftware.client.service.StatServiceClientImpl;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ServerStats implements EntryPoint {
	/**
	 * This is the entry point method.
	 */

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		StatServiceClientImpl clientImpl = new StatServiceClientImpl(GWT.getModuleBaseURL() + "stat");
		RootPanel.get().add(clientImpl.getGUI());
	}

	
}
