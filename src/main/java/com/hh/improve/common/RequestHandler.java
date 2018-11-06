package com.hh.improve.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public class RequestHandler extends HttpServletRequestWrapper {
	
	private Map params;
	public RequestHandler(HttpServletRequest request, Map newParams) {
		super(request);
		this.params = newParams;
	}

	@SuppressWarnings("unchecked")
	public Map getParameterMap() {
		return params;
	}

	@SuppressWarnings("unchecked")
	public Enumeration getParameterNames() {
		Vector l = new Vector(params.keySet());
		return l.elements();
	}

	public String[] getParameterValues(String name) {
		Object v = params.get(name);
		if (v == null) {
			return null;
		} else if (v instanceof String[]) {
			String[] value = (String[]) v;
			for (int i = 0; i < value.length; i++) {
				value[i] = value[i].replaceAll("<", "&lt;");
				value[i] = value[i].replaceAll(">", "&gt;");
				value[i] = value[i].replaceAll("\"", "\\\"");
				value[i] = value[i].replaceAll("\'", "\\'");
			}
			return (String[]) value;
		} else if (v instanceof String) {
			String value = (String) v;
			value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
			value = value.replaceAll("\"", "\\\"");
			value = value.replaceAll("\'", "\\'");
			return new String[] { (String) value };
		} else {
			return new String[] { v.toString() };
		}
	}

	public String getParameter(String name) {
		Object v = params.get(name);
		if (v == null) {
			return null;
		} else if (v instanceof String[]) {
			String[] strArr = (String[]) v;
			if (strArr.length > 0) {
				String value = strArr[0];
				value = value.replaceAll("<", "&lt;");
				value = value.replaceAll("<", "&gt;");
				value = value.replaceAll("\"", "\\\"");
				value = value.replaceAll("\'", "\\'");
				return value;
			} else {
				return null;
			}
		} else if (v instanceof String) {
			String value = (String) v;
			value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
			value = value.replaceAll("\"", "\\\"");
			value = value.replaceAll("\'", "\\'");
			return (String) value;
		} else {
			return v.toString();
		}
	}
}
