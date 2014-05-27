/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.server;

import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.DbTablesSpecialColums;
import com.customlib.seriousgame.client.Queries;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.models.*;
import com.customlib.seriousgame.server.DataBaseInteraction;
import com.customlib.seriousgame.server.DatabaseConnection;
import com.customlib.seriousgame.server.LuceneBuilder;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.sg.client.*;
import com.customlib.seriousgame.client.models.UserUpdate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.naming.InitialContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.servlet.ServletException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//test
/**
 *
 * @author NERTIL
 */
public class serverImpl extends RemoteServiceServlet implements server {

	static DocumentBuilderFactory dbf;
	static DocumentBuilder db;
	static Document dom;
	static String retval;
	static Element root;
	static Element subRoot;
	static String keylastVisitTime = null;
	IndexerThread indexer=null;
		

	@Override
	public void init() throws ServletException{

		try {
		DataBaseInteraction.init();	
		javax.naming.Context ctx = new InitialContext();	
		
		String index_filename = (String)ctx.lookup("java:comp/env/indexDir");
		FileSystem fs = FileSystems.getDefault();
		indexer = new IndexerThread(new LuceneBuilder(),
					fs.getPath(index_filename),240,1800);
		indexer.start();
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

	}
        
        private void initSGSUserDatabase()throws ServletException{

            String servletContext = getServletContext().getRealPath("");

            DataBaseInteraction.setServletContext(servletContext);
        }
	
	@Override
	public void destroy()
	{
		indexer.stopThread();
	}

	@Override
	public DataBase getData(User currentUser) {
		DataBase db = new DataBase(DataBaseInteraction.getGeneric(DbTables.ages.toString()),
			DataBaseInteraction.getGeneric(DbTables.availability.toString()),
			DataBaseInteraction.getGeneric(DbTables.game_genres.toString()),
			DataBaseInteraction.getGeneric(DbTables.learning_curve.toString()),
			DataBaseInteraction.getGeneric(DbTables.markets.toString()),
			DataBaseInteraction.getPublishers(),
			DataBaseInteraction.getSeriousGames(currentUser.getId()),
			DataBaseInteraction.getGeneric(DbTables.status.toString()),
			DataBaseInteraction.getContributersMessages(currentUser.getId()),
			currentUser,
			DataBaseInteraction.getGeneric(DbTables.effective_learning_time.toString()),
			DataBaseInteraction.getGeneric(DbTables.game_platforms.toString()),
			DataBaseInteraction.getGeneric(DbTables.bandwidth_types.toString()),
			DataBaseInteraction.getGeneric(DbTables.input_device_required.toString()),
			DataBaseInteraction.getGeneric(DbTables.deployment_styles.toString()),
			DataBaseInteraction.getGeneric(DbTables.target_ranges.toString()),
			DataBaseInteraction.getGenericModelWithRelation(DbTables.target_ranges_types.toString(), DbTablesSpecialColums.targetRangeID.toString()),
			DataBaseInteraction.getGeneric(DbTables.component_types.toString()),
			DataBaseInteraction.getGeneric(DbTables.algorithm_types.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_type.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_environments.toString()),
			DataBaseInteraction.getGenericModelWithRelation(DbTables.context_industry_school.toString(), DbTablesSpecialColums.industrySchoolID.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_industry_school_types.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_learner_role_types.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_teacher_role_types.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_learning_topic_types.toString()),
			DataBaseInteraction.getGenericModelWithRelation(DbTables.context_learning_subtopics.toString(), DbTablesSpecialColums.learningTopicID.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_pedagogical_paradigm_types.toString()),
			DataBaseInteraction.getGeneric(DbTables.context_learning_goals_types.toString()),
			DataBaseInteraction.getGenericModelWithRelation(DbTables.context_learning_goals_subtypes.toString(), DbTablesSpecialColums.learningGoalsTypesID.toString()),
			DataBaseInteraction.getGenericModelWithRelation(DbTables.context_learning_goals_soft_skills.toString(), DbTablesSpecialColums.learningGoalsID.toString()));

		return db;
	}

	@Override
	public int exequteQuery(String query) {
		return DataBaseInteraction.executeSql(query);
	}

	@Override
	public ArrayList<GenericModel> getPublishers() {
		return DataBaseInteraction.getPublishers();
	}

	@Override
	public ArrayList<GenericModel> getGeneric(String tableName) {
		return DataBaseInteraction.getGeneric(tableName);
	}

	@Override
	public ArrayList<SeriousGame> getSeriousGames(int currentUserId) {
		return DataBaseInteraction.getSeriousGames(currentUserId);
	}

	@Override
	public SeriousGame insertNewSeriousGame(SeriousGame record) {
		Date now = new Date();
		record.set(SeriousGame.TagDate, now);
		record.set(SeriousGame.TagLastChange, now);
		record.set(SeriousGame.TagVersion, 0);

		int insert = DataBaseInteraction.executeSql(Queries.insertSeriousGame(record));
		if (insert == 0) {
			System.out.println("Unable to insert new serious game");
		}
		String query = Queries.getSeriousGameId((String) record.get(SeriousGame.TagTitle),
			Utilities.getDateString((Date) record.get(SeriousGame.TagDate)));
		int newId = DataBaseInteraction.getIdFromKeys(query);

		DataBaseInteraction.executeSql(Queries.insertSeriousGameRelation(newId, DbTables.technology.toString()));
		DataBaseInteraction.executeSql(Queries.insertSeriousGameRelation(newId, DbTables.learning_environment.toString()));
		indexer.rebuildNeeded();
		return DataBaseInteraction.getSeriousGame((Integer) record.get(SeriousGame.TagOwnerId), newId);
	}

	@Override
	public SeriousGame updateSeriousGame(SeriousGame record) {
		int sgId = record.getId();
		Date now = new Date();
		record.set(SeriousGame.TagLastChange, now);
		record.set(SeriousGame.TagVersion, (Integer) record.get(SeriousGame.TagVersion) + 1);

		updateGeneralInfoAndRelations(record);
		updateTechnology(record);
		updateLearningEnvironment(record);
		updateArchitecture(record);
		updateContextAndAnalysis(record);

		ArrayList<GenericModel> others = record.get(SeriousGame.TagOthers);
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.others.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (others != null && others.size() > 0) {
			for (GenericModel genericModel : others) {
				DataBaseInteraction.executeSql(Queries.insertOther(sgId, genericModel));
			}
		}
		indexer.rebuildNeeded();
		return DataBaseInteraction.getSeriousGame((Integer) record.get(SeriousGame.TagOwnerId), sgId);
	}

	@Override
	public SeriousGame getSeriousGame(int currentUser, int id) {
		return DataBaseInteraction.getSeriousGame(currentUser, id);
	}

	@Override
	public User getUser(String username, String password){

		//if (true)
		//	return DataBaseInteraction.getUser(username, password);
		/**
		 * This part is for using getUser method under webService
		 * implementation. First once i get the service key from
		 * getAccess( webService function) i use checkUser method for
		 * verifying the validity of that user who is going to run on
		 * http://studies.seriousgamessociety.org/ site. Second if the
		 * response of chekuser is true then that user obtain the right
		 * to loggin on SGRepoBE site , updating all the users with
		 * lastvisitDate > of the default one stored in database and all
		 * the of them with lastisitDate ='0000-00-00 00:00:00'.
		 */
            
                User ret=null;
		String key = null;
		String keyXML = null;
		List<UserUpdate> l = new LinkedList<UserUpdate>();
                try
                {
                    initSGSUserDatabase();
                }
                catch(ServletException lse){
                    lse.printStackTrace();
                }
                

		key = getAccess("SGKMS", "sgkms01!");
		boolean flag = false;
		if (key != null) {

			flag = checkUser(key, username, password);
		}
		if (flag) {
			try {

				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();

				keylastVisitTime = serverImpl.getStudiesLastVisitStatic();

				keyXML = getUpdatedUsers(key, keylastVisitTime);
				System.out.println("*****************" + keylastVisitTime + "******************");
				System.out.println("*****************" + keylastVisitTime + "******************");
				System.out.println("*****************" + keylastVisitTime + "******************");
				System.out.println("*****************" + keylastVisitTime + "******************");
//	keyXML = getUserInfo(key, "guest");//ho problemi di controllo di '0000-00-00 00:00:00'
				System.out.println(keyXML);

				if (!keyXML.equals("")) {
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
					Date now = new Date();
					String strDate = sdfDate.format(now);
					serverImpl.setStudiesLastVisitStatic(strDate);

					dom = db.parse(new ByteArrayInputStream(keyXML.getBytes("UTF-8")));
					dom.getDocumentElement().normalize();
					dom.getDoctype();

					System.out.println(keyXML);
					Node nnm = dom.getFirstChild();
					NodeList nd = dom.getElementsByTagName("User");
					System.out.println("ndlenght" + nd.getLength());
					int n = nd.getLength();

					for (int j = 0; j < nd.getLength(); j++) {
						Node ntem = nd.item(j);

						if (ntem.getNodeType() == Node.ELEMENT_NODE) {
							Element e = (Element) ntem;
							Integer idUD = new Integer(e.getElementsByTagName("id").item(0).getTextContent());
							String nameUD = e.getElementsByTagName("name").item(0).getTextContent();
							String usernameUD = e.getElementsByTagName("username").item(0).getTextContent();
							String emailUD = e.getElementsByTagName("email").item(0).getTextContent();
							String passwordUD = e.getElementsByTagName("password").item(0).getTextContent();
							String usertypeUD = e.getElementsByTagName("usertype").item(0).getTextContent();
							Integer blockUD = new Integer(e.getElementsByTagName("block").item(0).getTextContent());
							Integer sendEmailUD = new Integer(e.getElementsByTagName("sendEmail").item(0).getTextContent());
							Integer gidUD = new Integer(e.getElementsByTagName("gid").item(0).getTextContent());
							String registerDateUD = e.getElementsByTagName("registerDate").item(0).getTextContent();
							String lastvisitDateUD = e.getElementsByTagName("lastvisitDate").item(0).getTextContent();
							String activationUD = e.getElementsByTagName("activation").item(0).getTextContent();
							String paramsUD = e.getElementsByTagName("params").item(0).getTextContent();
							UserUpdate up = new UserUpdate(idUD, nameUD, usernameUD, emailUD, passwordUD, usertypeUD, blockUD, sendEmailUD, gidUD, registerDateUD, lastvisitDateUD, activationUD, paramsUD);

							l.add(up);
						}
					}
					System.out.println(l.toString());
					ListIterator iter = l.listIterator();
					int count = 0;
					while (iter.hasNext()) {
						UserUpdate up = (UserUpdate) iter.next();
						System.out.println(count++ + "   *****************" + up);
						serverImpl.getStudiesUser(up.getUsername(), up);

					}
                                        ret = DataBaseInteraction.getUser(username, password);
				}
			} catch (IOException ioe) {
			} catch (ParserConfigurationException pce) {
			} catch (SAXException se) {
			}

			return ret;
		} else {
			return null;
		}
		/**
		 * END webService comment part
		 */
	}
//	@Override
//	public User getUser(String username, String password) {
//        			return DataBaseInteraction.getUser(username, password);
//
//        }

//--------------------Parte nuova aggiunta da Elion Haxhi-----------------------------------
//------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------
	public static void setStudiesUser(UserUpdate up) {
		String query = Queries.insertNewUser(up);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println(query);

		} catch (Exception e) {
			System.out.println("query error: " + e.getMessage());
			System.out.println(query);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}

	}

	public static UserUpdate getStudiesUser(String username, UserUpdate ud) {
		UserUpdate retval = null;
		String query = Queries.getUser(username);
		String newquery;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			if (!result.next()) {
				statement.close();
				result.close();
				setStudiesUser(ud);
			} else {

				newquery = Queries.updateaUser(result.getInt("id"), ud);
				System.out.println(newquery);
				statement = connection.createStatement();

				statement.executeUpdate(newquery);

				statement.close();
			}

		} catch (Exception e) {
			System.out.println("connection " + connection);
			System.out.println("statement " + statement);
			System.out.println("result " + result);
			System.out.println("query error:Eliion " + e.getMessage());
			System.out.println(query);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
		return retval;
	}

	public static String getStudiesLastVisitStatic() {
		String retval = null;
		String query = Queries.getLastVisitStatic();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			if (result.next()) {
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy

				Timestamp temp = result.getTimestamp("lastVisitTime");
				java.sql.Date lastVisitDate = new java.sql.Date(result.getDate("lastVisitTime").getTime());
				lastVisitDate.setTime(temp.getTime());
				retval = sdfDate.format(lastVisitDate);
			}

		} catch (Exception e) {
			System.out.println("connection " + connection);
			System.out.println("statement " + statement);
			System.out.println("result " + result);
			System.out.println("query error:Eliion " + e.getMessage());
			System.out.println(query);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
		return retval;

	}

	public static void setStudiesLastVisitStatic(String date) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = Queries.setLastVisitStatic(date);

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(query);
			//Logic goes here

		} catch (Exception e) {
			System.out.println("query error:Eliion " + e.getMessage());
			System.out.println(query);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}

	}

//---------------------------------Fine parte Elion Haxhi-----------------------------------
//------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------
	@Override
	public ArrayList<ContributersMessages> getContributersMessages(int owner) {
		return DataBaseInteraction.getContributersMessages(owner);
	}

	private void updateGeneralInfoAndRelations(SeriousGame record) {
		boolean generalInfoChanges = (Boolean) record.get(SeriousGame.TagGeneralIfoChanges);
		boolean relationsChanges = (Boolean) record.get(SeriousGame.TagRelationsChanges);
		if (!generalInfoChanges && !relationsChanges) {
			return;
		}

		int sgId = record.getId();

		int insert = DataBaseInteraction.executeSql(Queries.updateSeriousGame(record));
		if (insert == 0) {
			System.out.println("Unable to update serious game id=" + sgId);
		}
		List<GenericModel> ages = record.get(SeriousGame.TagAges);
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.ages_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (ages != null && ages.size() > 0) {
			DataBaseInteraction.executeSql(Queries.insertRelations(DbTables.ages_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), DbTablesSpecialColums.ageID.toString(), sgId, ages));
		}

		List<GenericModel> markets = record.get(SeriousGame.TagMarkets);
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.markets_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (markets != null && markets.size() > 0) {
			DataBaseInteraction.executeSql(Queries.insertRelations(DbTables.markets_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), DbTablesSpecialColums.marketID.toString(), sgId, markets));
		}

		List<GenericModel> genres = record.get(SeriousGame.TagGenres);
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.genres_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (genres != null && genres.size() > 0) {
			DataBaseInteraction.executeSql(Queries.insertRelations(DbTables.genres_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), DbTablesSpecialColums.genreID.toString(), sgId, genres));
		}
		List<Attachment> newAttachments = record.get(SeriousGame.TagAttachments);
		List<Attachment> oldAattachments = DataBaseInteraction.getAttachemnts(sgId);
		if (newAttachments != null && oldAattachments != null && newAttachments.size() != oldAattachments.size()) {

			List<Integer> ids = new ArrayList<Integer>();
			List<String> files = new ArrayList<String>();
			for (Attachment oldAtt : oldAattachments) {
				boolean isInNew = false;
				for (Attachment newAtt : newAttachments) {
					if (newAtt.getId().intValue() == oldAtt.getId().intValue()) {
						isInNew = true;
						break;
					}
				}
				if (!isInNew) {
					ids.add(oldAtt.getId());
					files.add((String) oldAtt.get(ModelTag.name.toString()));
				}
			}
			DataBaseInteraction.executeSql(Queries.delete(DbTables.attachments.toString(), ModelTag.id.toString(), ids));
			removeFiles(files);
		}
	}

	private void updateTechnology(SeriousGame record) {
		Technology technology = record.get(SeriousGame.TagTechnology);
		if (!technology.hasChanges()) {
			return;
		}

		int sgId = record.getId();

		DataBaseInteraction.executeSql(Queries.updateTechnology(technology));

		List<GenericModel> deploymentStyle = technology.getDeploymentStyle();
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.deployment_styles_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (deploymentStyle != null && deploymentStyle.size() > 0) {
			DataBaseInteraction.executeSql(Queries.insertRelationsWhithNotes(DbTables.deployment_styles_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), DbTablesSpecialColums.deploymentStyleID.toString(), sgId, deploymentStyle));
		}

		List<GenericModel> targetRange = technology.getTargetRange();
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.target_ranges_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (targetRange != null && targetRange.size() > 0) {
			DataBaseInteraction.executeSql(Queries.insertRelationsWhithNotesAndSubType(DbTables.target_ranges_sgs.toString(), DbTablesSpecialColums.seriousGameID.toString(), DbTablesSpecialColums.targetRangeID.toString(), DbTablesSpecialColums.targetRangeTypeID.toString(), record.getId(), targetRange));
		}
	}

	private void updateLearningEnvironment(SeriousGame record) {
		LearningEnvironment learningEnvironment = record.get(SeriousGame.TagLearningEnvironment);
		if (!learningEnvironment.hasChanges()) {
			return;
		}
		DataBaseInteraction.executeSql(Queries.updateLearningEnvironment(learningEnvironment));
	}

	private void updateArchitecture(SeriousGame record) {
		Architecture architecture = record.get(SeriousGame.TagArchitecture);
		if (!architecture.hasChanges()) {
			return;
		}

		int sgId = record.getId();

		List<GenericModel> components = architecture.getComponents();
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.components.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (components != null && components.size() > 0) {
			for (GenericModel component : components) {
				int insert = DataBaseInteraction.executeSql(Queries.insertComponent(component, sgId));
				if (insert == 0) {
					System.out.println("Unable to insert new component");
					continue;
				}
				String query = Queries.getComponentID(sgId, component.getName());
				int newId = DataBaseInteraction.getIdFromKeys(query);

				List<GenericModel> pedagogicalParadigms = component.get(ModelTag.pedagogicalParadigm.toString());
				DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.components_pedagogical_paradigms.toString(), DbTablesSpecialColums.componentID.toString(), newId));
				if (pedagogicalParadigms != null && pedagogicalParadigms.size() > 0) {
					DataBaseInteraction.executeSql(Queries.insertPedagogicalParadigmsForComponent(pedagogicalParadigms, newId));
				}
				List<GenericModel> learningGoals = component.get(ModelTag.learningGoals.toString());
				DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.components_learning_goals.toString(), DbTablesSpecialColums.componentID.toString(), newId));
				if (learningGoals != null && learningGoals.size() > 0) {
					DataBaseInteraction.executeSql(Queries.insertLearningGoalsForComponent(learningGoals, newId));
				}
			}
		}
		List<GenericModel> newComponentList = DataBaseInteraction.getComponents(sgId);
		architecture.setComponents(newComponentList);

		insertAlgorithm(DbTables.algorithms.toString(), architecture.getAlgorithms(), sgId);
		insertArchitectureData(DbTables.game_engines.toString(), architecture.getGameEngines(), sgId);
		insertArchitectureData(DbTables.interoperability_and_standards.toString(), architecture.getInteroperabilityAndStandards(), sgId);
		insertArchitectureData(DbTables.psichological_aspects.toString(), architecture.getPsichologicalAspects(), sgId);
		insertArchitectureData(DbTables.neuroscientific_aspects.toString(), architecture.getNeuroscientificAspects(), sgId);
	}

	private void updateContextAndAnalysis(SeriousGame record) {
		ContextAndAnalysis contextAndAnalysis = record.get(SeriousGame.TagContextAndAnalysi);
		if (!contextAndAnalysis.hasChanges()) {
			return;
		}

		int sgId = record.getId();

		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.contexts.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		List<GenericModel> contexts = contextAndAnalysis.getContexts();
		for (GenericModel model : contexts) {
			Context context = model.get(ModelTag.context.toString());
			int insert = DataBaseInteraction.executeSql(Queries.insertContext(context, sgId));
			if (insert == 0) {
				System.out.println("Unable to insert new context");
				continue;
			}
			String query = Queries.getContextID(sgId, context.getName());
			int newId = DataBaseInteraction.getIdFromKeys(query);

			List<GenericModel> learningTopics = context.get(Context.tagLearningTopics);
			DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.context_learning_topics.toString(), DbTablesSpecialColums.contextID.toString(), newId));
			if (learningTopics != null && learningTopics.size() > 0) {
				DataBaseInteraction.executeSql(Queries.insertLearningTopics(learningTopics, newId));
			}
			List<GenericModel> pedagogicalParadigms = context.get(Context.tagPedagogicalParadigm);
			DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.context_pedagogical_paradigms.toString(), DbTablesSpecialColums.contextID.toString(), newId));
			if (pedagogicalParadigms != null && pedagogicalParadigms.size() > 0) {
				DataBaseInteraction.executeSql(Queries.insertPedagogicalParadigmsForContext(pedagogicalParadigms, newId));
			}
			List<GenericModel> learningGoals = context.get(Context.tagLearningGoals);
			DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.context_learning_goals.toString(), DbTablesSpecialColums.contextID.toString(), newId));
			if (learningGoals != null && learningGoals.size() > 0) {
				DataBaseInteraction.executeSql(Queries.insertLearningGoalsForContext(learningGoals, newId));
			}
		}
		contextAndAnalysis.setContexts(DataBaseInteraction.getContexts(sgId));
		Analysis analysis = contextAndAnalysis.getAnalysis();
		DataBaseInteraction.executeSql(Queries.deleteRelations(DbTables.analysis.toString(), DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (analysis != null) {
			DataBaseInteraction.executeSql(Queries.insertAnalysis(analysis, sgId));
		}
	}

	private void insertAlgorithm(String table, List<GenericModel> dataList, int sgId) {
		DataBaseInteraction.executeSql(Queries.deleteRelations(table, DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (dataList != null && dataList.size() > 0) {
			DataBaseInteraction.executeSql(Queries.insertAlgorithm(table, dataList, sgId));
		}
	}

	private void insertArchitectureData(String table, List<GenericModel> dataList, int sgId) {
		DataBaseInteraction.executeSql(Queries.deleteRelations(table, DbTablesSpecialColums.seriousGameID.toString(), sgId));
		if (dataList != null && dataList.size() > 0) {
			DataBaseInteraction.executeSql(Queries.insertArchitectureData(table, dataList, sgId));
		}
	}
//	private void insertArchitectureData(String table, List<GenericModel> dataList, int sgId) {
//		DataBaseInteraction.executeSql(Queries.deleteRelations(table, DbTablesSpecialColums.seriousGameID.toString(), sgId));
//		if (dataList != null && dataList.size() > 0) {
//			DataBaseInteraction.executeSql(Queries.insertArchitectureData(table, dataList, sgId));
//		}
//	}

	private void removeFiles(List<String> files) {
		String location = getServletContext().getRealPath("") + File.separator + Utilities.uploadedFolder;
		for (String fileName : files) {
			File temp = new File(location, fileName);
			if (temp.exists() && temp.isFile()) {
				temp.delete();
			}
		}
	}

	private static Boolean checkUser(java.lang.String key, java.lang.String username, java.lang.String userpassword) {
		org.elios.seriousgames.SgsUsersService_Service service = new org.elios.seriousgames.SgsUsersService_Service();
		org.elios.seriousgames.SgsUsersService port = service.getSgsUsersServicePort();
		return port.checkUser(key, username, userpassword);
	}

	private static String getAccess(java.lang.String appName, java.lang.String password) {
		org.elios.seriousgames.SgsUsersService_Service service = new org.elios.seriousgames.SgsUsersService_Service();
		org.elios.seriousgames.SgsUsersService port = service.getSgsUsersServicePort();
		return port.getAccess(appName, password);
	}

	private static String getUpdatedUsers(java.lang.String keySession, java.lang.String lastUpdateTime) {
		org.elios.seriousgames.SgsUsersService_Service service = new org.elios.seriousgames.SgsUsersService_Service();
		org.elios.seriousgames.SgsUsersService port = service.getSgsUsersServicePort();
		return port.getUpdatedUsers(keySession, lastUpdateTime);
	}

	private static String getUserInfo(java.lang.String key, java.lang.String username) {
		org.elios.seriousgames.SgsUsersService_Service service = new org.elios.seriousgames.SgsUsersService_Service();
		org.elios.seriousgames.SgsUsersService port = service.getSgsUsersServicePort();
		return port.getUserInfo(key, username);
	}

}
