/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elios.seriousgames;

/**
 *
 * @author Nertil & Elion
 */
public class User {

	public static String tagName = "name";
	public static String tagUsername = "username";
	public static String tagEmail = "email";
	public static String tagPassword = "password";
	public static String tagUsertype = "usertype";
	public static String tagBlock = "block";
	public static String tagSendEmail = "sendEmail";
	public static String tagGid = "gid";
	public static String tagRegisterDate = "registerDate";
	public static String tagLastvisitDate = "lastvisitDate";
	public static String tagActivation = "activation";
	public static String tagParams = "params";
	private int id;
	private String name;
	private String username;
	private String email;
	private String password;
	private String usertype;
	private int block;
	private int sendEmail;
	private int gid;
	private String registerDate;
	private String lastvisitDate;
	private String activation;
	private String params;

	/**
	 * il costruttotr della classe User
	 *
	 * @param id
	 * @param name
	 * @param username
	 * @param email
	 * @param password
	 * @param usertype
	 * @param block
	 * @param sendEmail
	 * @param gid
	 * @param registerDate
	 * @param lastvisitDate
	 * @param activation
	 * @param params
	 */
	public User(int id, String name, String username, String email, String password, String usertype, int block, int sendEmail, int gid, String registerDate, String lastvisitDate, String activation, String params) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.usertype = usertype;
		this.block = block;
		this.sendEmail = sendEmail;
		this.gid = gid;
		this.registerDate = registerDate;
		this.lastvisitDate = lastvisitDate;
		this.activation = activation;
		this.params = params;
	}

	/**
	 * Get the value of name
	 *
	 * @return the value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the value of id
	 *
	 * @return the value of id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the value of username
	 *
	 * @return the value of username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the value of email
	 *
	 * @return the value of email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Get the value of password
	 *
	 * @return the value of password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Get the value of usertype
	 *
	 * @return the value of usertype
	 */
	public String getUsertype() {
		return usertype;
	}

	/**
	 * Get the value of block
	 *
	 * @return the value of block
	 */
	public int getBlock() {
		return block;
	}

	/**
	 * Get the value of sendEmail
	 *
	 * @return the value of sendEmail
	 */
	public int getSendEmail() {
		return sendEmail;
	}

	/**
	 * Get the value of gid
	 *
	 * @return the value of gid
	 */
	public int getGid() {
		return gid;
	}

	/**
	 * Get the value of registerDate
	 *
	 * @return the value of registerDate
	 */
	public String getRegisterDate() {
		return registerDate;
	}

	/**
	 * Get the value of lastvisitDate
	 *
	 * @return the value of lastvisitDate
	 */
	public String getLastvisitDate() {
		return lastvisitDate;
	}

	/**
	 * Get the value of activation
	 *
	 * @return the value of activation
	 */
	public String getActivation() {
		return activation;
	}

	/**
	 * Get the value of params
	 *
	 * @return the value of params
	 */
	public String getParams() {
		return params;
	}
}
