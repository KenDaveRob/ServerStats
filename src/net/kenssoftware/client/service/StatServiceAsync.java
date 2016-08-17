package net.kenssoftware.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.kenssoftware.client.model.StatsData;
import net.kenssoftware.client.model.StatsRequest;
import net.kenssoftware.client.model.TestRequest;

public interface StatServiceAsync
{
	void getStatsData(StatsRequest request, AsyncCallback callback);
	void getTest(int id, AsyncCallback callback);
	
	void strTest(String str, AsyncCallback callback);
	void resultTest(TestRequest str, AsyncCallback callback);
}
