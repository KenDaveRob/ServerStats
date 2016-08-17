package net.kenssoftware.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import net.kenssoftware.client.model.StatsData;
import net.kenssoftware.client.model.StatsRequest;
import net.kenssoftware.client.model.TestRequest;
import net.kenssoftware.client.model.TestResult;

@RemoteServiceRelativePath("stat")
public interface StatService extends RemoteService
{
	StatsData getStatsData(StatsRequest request);
	String getTest(int id);
	
	String strTest(String str);
	TestResult resultTest(TestRequest str);
}
