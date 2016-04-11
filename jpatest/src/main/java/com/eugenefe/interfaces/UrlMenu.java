package com.eugenefe.interfaces;

import java.util.List;

public interface UrlMenu {
	public String getTargetClassName();
	public List<String> getParameters();
	public String getReferer();
	public String getPayload();
}
