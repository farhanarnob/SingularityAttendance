package com.farhanarnob.singularityattendance.network.retrofit;

public interface APIClientResponse {

	public void failureOnApiCall(String msg, Object sender);
	public void failureOnNetworkConnection(String msg, Object sender);
	public void successOnApiCall(String msg, Object sender);
}
