package com.vince.aframe.business.test.network;

import com.vince.aframe.base.net.response.BaseBean;

/**
 * Title: OrderItemModel Description: Company: EverGrande
 * 
 * @author Vince.Tian
 * @date 2016年3月1日
 */

public class VersionModel extends BaseBean {
	private Integer latestVersionCode;
	private String latestVersionName;
	private Integer minSupportedVersionCode;
	private String message;
	private String appUrl;

	public Integer getLatestVersionCode() {
		return latestVersionCode;
	}

	public void setLatestVersionCode(Integer latestVersionCode) {
		this.latestVersionCode = latestVersionCode;
	}

	public String getLatestVersionName() {
		return latestVersionName;
	}

	public void setLatestVersionName(String latestVersionName) {
		this.latestVersionName = latestVersionName;
	}

	public Integer getMinSupportedVersionCode() {
		return minSupportedVersionCode;
	}

	public void setMinSupportedVersionCode(Integer minSupportedVersionCode) {
		this.minSupportedVersionCode = minSupportedVersionCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

}
