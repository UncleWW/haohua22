package com.hh.improve.shiro;

import com.hh.improve.shiro.entity.AuthorizationUser;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SuperAdminTicketUtils {

	public static ConcurrentHashMap<String, AuthorizationUser> ticketMap = new ConcurrentHashMap<String, AuthorizationUser>();
	
	public final static String SUPER_AMDIN_TICKET_SUFFIX = ".superadmin.sknow.com";
	public final static long SUPER_AMDIN_TICKET_TIME = 1000 * 60 * 60;
	
	public static String putTicket(AuthorizationUser user) {
		String ticket = getTicket();
		ticketMap.put(ticket, user);
		return ticket;
	}
	
	public static boolean validTiket(String ticket) {
		clearTicketMap();
		return ticketMap.containsKey(ticket);
	}
	
	private static String getTicket() {
		return UUID.randomUUID() + SUPER_AMDIN_TICKET_SUFFIX;
	}
	
	private static void clearTicketMap() {
		Long currentTime = new Date().getTime();
		Iterator<Map.Entry<String, AuthorizationUser>> it = ticketMap.entrySet().iterator();
		Map.Entry<String, AuthorizationUser> map = null;
		while (it.hasNext()) {
			map = it.next();
			if((currentTime - map.getValue().getTime()) > SUPER_AMDIN_TICKET_TIME) ticketMap.remove(map.getKey());
		}
	}
	
	
	public final static Assertion getSuperAdminAssertion(String ticket) {
		Assertion assertion = null;
		final Map<String, Object> attributes = new HashMap<String, Object>();
		AuthorizationUser user = ticketMap.get(ticket);
		if (null != user) {
			attributes.put("user_id", user.getUserId());
			attributes.put("user_name", user.getUserName());
			attributes.put("password", user.getPassword());
			assertion = new AssertionImpl(new AttributePrincipalImpl(user.getUserAccount(), attributes));
		}
		return assertion;
	}
}
