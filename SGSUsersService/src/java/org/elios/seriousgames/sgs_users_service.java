/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elios.seriousgames;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Nertil & Elion
 */
@WebService(serviceName = "sgs_users_service")
public class sgs_users_service {

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "getAccess")
	public String getAccess(@WebParam(name = "appName") String appName, @WebParam(name = "password") String password) {
		return Database.getAccess(appName, password);
	}

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "checkUser")
	public Boolean checkUser(@WebParam(name = "key") String key, @WebParam(name = "username") String username, @WebParam(name = "userpassword") String userpassword) {
		return Database.checkUser(key, username, userpassword);
	}

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "getUserInfo")
	public String getUserInfo(@WebParam(name = "key") String key, @WebParam(name = "username") String username) {
		//TODO write your implementation code here:
		return Database.getUserInfo(key, username);
	}

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "getUpdatedUsers")
	public String getUpdatedUsers(@WebParam(name = "key_session") String key_session, @WebParam(name = "lastUpdateTime") String lastUpdateTime) {
		//TODO write your implementation code here:
		return Database.getLastVisitUser(key_session, lastUpdateTime);
	}
}
