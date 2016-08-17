package net.kenssoftware.client.service;

import net.kenssoftware.client.model.StatsRequest;
import net.kenssoftware.client.model.TestRequest;

public interface StatServiceClientInt 
{
	void getStatsData(StatsRequest request);
	void getTest(int id);
	void strTest(String str);
	void resultTest(TestRequest str);
}
