/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elios.seriousgames;

import java.io.StringWriter;
import java.util.List;
import java.util.ListIterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Nertil & Elion
 */
public class XmlParser {

	static DocumentBuilderFactory dbf;
	static DocumentBuilder db;
	static Document dom;
	static String retval;
	static Element root;
	static Element subRoot;

	/**
	 * Impotso i componenti neccesari per la costruzione del formato xml
	 * imposto il nome del parent
	 *
	 * @throws ParserConfigurationException
	 */
	public XmlParser() throws ParserConfigurationException {
		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		dom = db.newDocument();
		retval = "";
		root = dom.createElement("SGSuser");
		dom.appendChild(root);
	}

	/**
	 * questo metodo setta i campit di ciascun user e vine chiamato
	 * cilicamente fino alla iterazione completa dei user
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
	 *
	 * valore di ritorno null perche è un void
	 */
	public static void setXml(int id, String name, String username, String email, String password, String usertype, int block, int sendEmail, int gid, String registerDate, String lastvisitDate, String activation, String params) {

		subRoot = dom.createElement("User");
		root.appendChild(subRoot);

		Element testEle = dom.createElement("id");
		testEle.appendChild(dom.createTextNode(String.valueOf(id)));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("name");
		testEle.appendChild(dom.createTextNode(name));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("username");
		testEle.appendChild(dom.createTextNode(username));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("email");
		testEle.appendChild(dom.createTextNode(email));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("password");
		testEle.appendChild(dom.createTextNode(password));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("usertype");
		testEle.appendChild(dom.createTextNode(usertype));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("block");
		testEle.appendChild(dom.createTextNode(String.valueOf(block)));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("sendEmail");
		testEle.appendChild(dom.createTextNode(String.valueOf(sendEmail)));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("gid");
		testEle.appendChild(dom.createTextNode(String.valueOf(gid)));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("registerDate");
		testEle.appendChild(dom.createTextNode(registerDate.toString()));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("lastvisitDate");
		testEle.appendChild(dom.createTextNode(lastvisitDate.toString()));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("activation");
		testEle.appendChild(dom.createTextNode(activation));
		subRoot.appendChild(testEle);

		testEle = dom.createElement("params");
		testEle.appendChild(dom.createTextNode(params));
		subRoot.appendChild(testEle);

	}

	/**
	 * prende in output una lista di user e chiama al interno setXml fino
	 * alla durata della lungheza della lista, in fine transfroma la stringa
	 * in formato xml
	 *
	 * @param coda
	 * @return una stringa di tipo xml
	 */
	public static String getXML(List<User> coda) {
		try {

			ListIterator iter = coda.listIterator();
			User user = null;
			while (iter.hasNext()) {
				user = (User) iter.next();
				setXml(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getUsertype(), user.getBlock(), user.getSendEmail(), user.getGid(), user.getRegisterDate(), user.getLastvisitDate(), user.getActivation(), user.getParams());
				user = null;
			}


			try {
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(dom), new StreamResult(writer));
				retval = writer.getBuffer().toString().replaceAll("\n|\r", "");
			} catch (TransformerConfigurationException ex) {
				System.out.println("Error while trying to transform DocumentBuilder " + ex);
			} catch (TransformerException ex) {
				System.out.println("Error while trying to transform DocumentBuilder " + ex);
			}
		} catch (Exception e) {
			String message = "Exception throwed in method \"createTest\"";
			message += "\\n";
			message += "\\n" + e.getStackTrace();
			System.out.println("Error 1" + e);
		}
		return retval;
	}
}
