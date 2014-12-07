package com.cathaylife.weixinclient.entity;

public class WUserIds {
	private int total;
	private int count;
	private WUserData data;
	private String next_openid;
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the data
	 */
	public WUserData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(WUserData data) {
		this.data = data;
	}
	/**
	 * @return the next_openid
	 */
	public String getNext_openid() {
		return next_openid;
	}
	/**
	 * @param next_openid the next_openid to set
	 */
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

}
