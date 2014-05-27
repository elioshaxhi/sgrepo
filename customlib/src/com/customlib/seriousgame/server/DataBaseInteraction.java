/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.server;

import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.DbTablesSpecialColums;
import com.customlib.seriousgame.client.Queries;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.models.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author NERTIL
 */
public class DataBaseInteraction {

	private static String servletContext = "";
	private static int MaxRow = 1000;

	public static int getMaxRow() {
		return MaxRow;
	}

	public static void setMaxRow(int MaxRow) {
		DataBaseInteraction.MaxRow = MaxRow;
	}
        
        

	/**
	 * Executes the query returning the data that sql server return for the
	 * given query.
	 *
	 * @param query the query to execute
	 * @return a ResultSet containing the data
	 */
//    public static ResultSet execute(String query)
//    {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet result = null;
//        try {
//            connection = DatabaseConnection.getConnection();
//
//            statement = connection.createStatement();
//            result = statement.executeQuery(query);
//        } catch (Exception e) {
//            System.out.println("ZTE DB query error: " + e.getMessage());
//            System.out.println(query);
//        } finally {
//            if (result!=null) try {result.close();}catch (Exception ex) {System.out.println(ex);}
//            if (statement!=null) try {statement.close();}catch (Exception ex) {System.out.println(ex);}
//            if (connection!=null) try {connection.close();}catch (Exception ex) {System.out.println(ex);}
//        }
////        Date endDate = new Date();
////        System.out.println("Tempo esecuzione(ms): " + String .valueOf(endDate.getTime() - startDate.getTime()) + "       Query : " + query);
//        return result;
//////        Connection connection = null;
//////        Statement statement = null;
//////        ResultSet result = null;
//////        try {
//////            connection = DatabaseConnection.getConnection();
//////            statement = connection.createStatement();
//////            result = statement.executeQuery(query);
//////            //Logic goes here
//////
//////        } catch (Exception e) {
//////            System.out.println("ZTE DB query error: " + e.getMessage());
//////            System.out.println(query);
//////        } finally {
//////            if (result!=null) try {result.close();}catch (Exception ex) {System.out.println(ex);}
//////            if (statement!=null) try {statement.close();}catch (Exception ex) {System.out.println(ex);}
//////            if (connection!=null) try {connection.close();}catch (Exception ex) {System.out.println(ex);}
//////        }
//    }
        	public static void setServletContext(String servletContext) {
		DataBaseInteraction.servletContext = servletContext;
		setMaxRow(100);
	}

	/**
	 * Executes the query returning the number of rows changed by the query.
	 *
	 * @param query the query to execute
	 * @return the number of rows changed by the query
	 */
	public static int executeSql(String query) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		int retval = 0;
		try {
			connection = DatabaseConnection.getConnection();

			statement = connection.createStatement();
			retval = statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("VRE DB query error: " + e.getMessage());
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

	/**
	 * @param servletContext the servletContext to set
	 */
        /*
	public static void setServletContext(String servletContext) {
		DataBaseInteraction.servletContext = servletContext;
		setMaxRow(100);
	}
        */
        

	public static void init() {
		setMaxRow(100);
	}

	/**
	 * adding code for ordering items arriving from db, i got this item in
	 * order and then, insert the Other item at the end of the arraylist.
	 */
	public static ArrayList<GenericModel> getGeneric(String tableName) {
		ArrayList<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getGenericAll(tableName);
		Connection connection = null;
		Statement statement = null;
		GenericModel other = null;
		ResultSet result = null;
		String name;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				name = result.getString(ModelTag.name.toString());
				if (name.equals("Other")) {
					other = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
					continue;
				}
				retval.add(new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString())));
			}
			Collections.sort(retval, new Comparator<GenericModel>() {

				@Override
				public int compare(GenericModel o1, GenericModel o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			if (other != null) {
				retval.add(other);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

		try {
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return retval;
	}

	public static ArrayList<GenericModel> getPublishers() {
		ArrayList<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getPublisherAll;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(ModelTag.link.toString(), result.getString(ModelTag.link.toString()));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	/**
	 * adding code for ordering items arriving from db, i got this item in
	 * order and then, insert the Other item at the end of the arraylist.
	 */
	public static ArrayList<GenericModelWithRelation> getGenericModelWithRelation(String tableName, String relationColumn) {
		ArrayList<GenericModelWithRelation> retval = new ArrayList<GenericModelWithRelation>();

		String query = Queries.getGenericAll(tableName);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		GenericModelWithRelation other = null;
		String name = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				name = result.getString(ModelTag.name.toString());
				if (name.equals("Other")) {
					other = new GenericModelWithRelation(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()),
						result.getString(ModelTag.description.toString()), result.getInt(relationColumn));
					continue;
				}

				retval.add(new GenericModelWithRelation(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()),
					result.getString(ModelTag.description.toString()), result.getInt(relationColumn)));
			}
			Collections.sort(retval, new Comparator<GenericModel>() {

				@Override
				public int compare(GenericModel o1, GenericModel o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			if (other != null) {
				retval.add(other);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ArrayList<GenericModel> getDataForSeriousGame(String query, String tagId) {
		ArrayList<GenericModel> retval = new ArrayList<GenericModel>();

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(tagId), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ArrayList<GenericModel> getDataForSeriousGameWhithNotes(String query, String tagId) {
		ArrayList<GenericModel> retval = new ArrayList<GenericModel>();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(tagId), result.getString(ModelTag.name.toString()), result.getString(DbTablesSpecialColums.notes.toString()));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ArrayList<GenericModel> getDataForSeriousGameWhithNotesAndSubType(String query, String tagId, String subTypeId) {
		ArrayList<GenericModel> retval = new ArrayList<GenericModel>();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(tagId), result.getString(ModelTag.name.toString()), result.getString(DbTablesSpecialColums.notes.toString()));
				model.set(ModelTag.relationID.toString(), result.getInt(subTypeId));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	private static ArrayList<Integer> getContributorSeriousGameId(int userId) {
		ArrayList<Integer> retval = new ArrayList<Integer>();
		String query = Queries.getContributorSeriousGameId(userId);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				retval.add(result.getInt(DbTablesSpecialColums.seriousGameID.toString()));
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ArrayList<SeriousGame> getSeriousGames(int userId) {
		ArrayList<Integer> contributorSeriousGame = getContributorSeriousGameId(userId);
		ArrayList<SeriousGame> retval = new ArrayList<SeriousGame>();

		String query = Queries.getSeriousGameAll();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				int id = result.getInt(SeriousGame.TagId);
				int idOwner = result.getInt(SeriousGame.TagOwnerId);
				String owner = result.getString(SeriousGame.TagOwner);
				String title = result.getString(SeriousGame.TagTitle);
				int idPublisher = result.getInt(SeriousGame.TagPublisherID);
				String webSite = result.getString(SeriousGame.TagWebSite);
				int yearFirstRelease = result.getInt(SeriousGame.TagYearFirstRelease);
				int yearlastRelease = result.getInt(SeriousGame.TagYearLastRelease);
				int idSource = result.getInt(SeriousGame.TagSourceID);
				int idStatus = result.getInt(SeriousGame.TagStatusID);
				int idAvailability = result.getInt(SeriousGame.TagAvailabilityID);
				Date date = new Date(result.getDate(SeriousGame.TagDate).getTime());
				Date lastChange = new Date(result.getDate(SeriousGame.TagLastChange).getTime());
				int version = result.getInt(SeriousGame.TagVersion);
				String freeDescription = result.getString(SeriousGame.TagFreeDescription);
				String keywords = result.getString(SeriousGame.TagKeywords);
				int idLearningCurve = result.getInt(SeriousGame.TagLearningCurveID);
				String learningCurveNotes = result.getString(SeriousGame.TagLearningCurveNotes);
				int effectiveLearningTimeID = result.getInt(SeriousGame.TagEffectiveLearningTimeID);
				String effectiveLearningTimeNotes = result.getString(SeriousGame.TagEffectiveLearningTimeNotes);

				boolean isContributorCurrentUser = false;
				if (contributorSeriousGame.contains(id)) {
					isContributorCurrentUser = true;
				}

				ArrayList<GenericModel> markets = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.markets.toString(), DbTables.markets_sgs.toString(), DbTablesSpecialColums.marketID.toString()), DbTablesSpecialColums.marketID.toString());
				ArrayList<GenericModel> ages = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.ages.toString(), DbTables.ages_sgs.toString(), DbTablesSpecialColums.ageID.toString()), DbTablesSpecialColums.ageID.toString());
				ArrayList<GenericModel> genres = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.game_genres.toString(), DbTables.genres_sgs.toString(), DbTablesSpecialColums.genreID.toString()), DbTablesSpecialColums.genreID.toString());

				retval.add(new SeriousGame(id, idOwner, owner, title, idPublisher, webSite,
					yearFirstRelease, yearlastRelease, idSource, idStatus, idAvailability,
					date, lastChange, version, freeDescription, keywords, null, idLearningCurve, learningCurveNotes, effectiveLearningTimeID, effectiveLearningTimeNotes,
					markets, ages, genres, isContributorCurrentUser, null, null, null, null, null));
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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
 
    private static ArrayList<Integer> getMatchingIds(String searchText) {
        ArrayList<Integer> ids = new ArrayList<>();
        if (searchText == null || searchText.equals("")) {
            return ids;
        }

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		String index_filename;
		javax.naming.Context ctx;
		try {
			ctx = new InitialContext();
		
			index_filename = (String)ctx.lookup("java:comp/env/indexDir");
		} catch (javax.naming.NamingException ex) {
			Logger.getLogger(DataBaseInteraction.class.getName()).log(Level.SEVERE, null, ex);
			index_filename = "/home/gieze101/tmp/sgindex";
		}
        try (Directory directory = FSDirectory.open(new File(index_filename));
                DirectoryReader ireader = DirectoryReader.open(directory)) {
            IndexSearcher isearcher = new IndexSearcher(ireader);
            QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "content", analyzer);
            Query query;
            try {
                query = parser.parse(searchText);

                ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
                for (ScoreDoc hit : hits) {
                    Document hitDoc = isearcher.doc(hit.doc);
                    Integer value = Integer.parseInt(hitDoc.get("id"));
                    ids.add(value);
                }
            } catch (ParseException ex) {
                Logger.getLogger(DataBaseInteraction.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(DataBaseInteraction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ids;
    }
        
	public static ArrayList<SeriousGame> getSeriousGames(String searchText) {
		ArrayList<SeriousGame> retval = new ArrayList<>();

		//String query = Queries.getSeriousGame(searchText);
                
        String query = Queries.getSeriousGame(getMatchingIds(searchText));
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				int id = result.getInt(SeriousGame.TagId);
				int idOwner = result.getInt(SeriousGame.TagOwnerId);
				String owner = result.getString(SeriousGame.TagOwner);
				String title = result.getString(SeriousGame.TagTitle);
				int idPublisher = result.getInt(SeriousGame.TagPublisherID);
				String webSite = result.getString(SeriousGame.TagWebSite);
				int yearFirstRelease = result.getInt(SeriousGame.TagYearFirstRelease);
				int yearlastRelease = result.getInt(SeriousGame.TagYearLastRelease);
				int idSource = result.getInt(SeriousGame.TagSourceID);
				int idStatus = result.getInt(SeriousGame.TagStatusID);
				int idAvailability = result.getInt(SeriousGame.TagAvailabilityID);
				Date date = new Date(result.getDate(SeriousGame.TagDate).getTime());
				Date lastChange = new Date(result.getDate(SeriousGame.TagLastChange).getTime());
				int version = result.getInt(SeriousGame.TagVersion);
				String freeDescription = result.getString(SeriousGame.TagFreeDescription);
				String keywords = result.getString(SeriousGame.TagKeywords);
				int idLearningCurve = result.getInt(SeriousGame.TagLearningCurveID);
				String learningCurveNotes = result.getString(SeriousGame.TagLearningCurveNotes);
				int effectiveLearningTimeID = result.getInt(SeriousGame.TagEffectiveLearningTimeID);
				String effectiveLearningTimeNotes = result.getString(SeriousGame.TagEffectiveLearningTimeNotes);

				boolean isContributorCurrentUser = false;
				List<Attachment> attachments = getAttachemnts(id);

				ArrayList<GenericModel> markets = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.markets.toString(), DbTables.markets_sgs.toString(), DbTablesSpecialColums.marketID.toString()), DbTablesSpecialColums.marketID.toString());
				ArrayList<GenericModel> ages = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.ages.toString(), DbTables.ages_sgs.toString(), DbTablesSpecialColums.ageID.toString()), DbTablesSpecialColums.ageID.toString());
				ArrayList<GenericModel> genres = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.game_genres.toString(), DbTables.genres_sgs.toString(), DbTablesSpecialColums.genreID.toString()), DbTablesSpecialColums.genreID.toString());

				retval.add(new SeriousGame(id, idOwner, owner, title, idPublisher, webSite,
					yearFirstRelease, yearlastRelease, idSource, idStatus, idAvailability,
					date, lastChange, version, freeDescription, keywords, attachments, idLearningCurve, learningCurveNotes, effectiveLearningTimeID, effectiveLearningTimeNotes,
					markets, ages, genres, isContributorCurrentUser, null, null, null, null, null));
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ArrayList<SeriousGame> getLastSeriousGames(int lastN) {
		ArrayList<SeriousGame> retval = new ArrayList<SeriousGame>();

		String query = Queries.getSeriousGameAllOrderByDate();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < lastN) {
				int id = result.getInt(SeriousGame.TagId);
				int idOwner = result.getInt(SeriousGame.TagOwnerId);
				String owner = result.getString(SeriousGame.TagOwner);
				String title = result.getString(SeriousGame.TagTitle);
				int idPublisher = result.getInt(SeriousGame.TagPublisherID);
				String webSite = result.getString(SeriousGame.TagWebSite);
				int yearFirstRelease = result.getInt(SeriousGame.TagYearFirstRelease);
				int yearlastRelease = result.getInt(SeriousGame.TagYearLastRelease);
				int idSource = result.getInt(SeriousGame.TagSourceID);
				int idStatus = result.getInt(SeriousGame.TagStatusID);
				int idAvailability = result.getInt(SeriousGame.TagAvailabilityID);
				Date date = new Date(result.getDate(SeriousGame.TagDate).getTime());
				Date lastChange = new Date(result.getDate(SeriousGame.TagLastChange).getTime());
				int version = result.getInt(SeriousGame.TagVersion);
				String freeDescription = result.getString(SeriousGame.TagFreeDescription);
				String keywords = result.getString(SeriousGame.TagKeywords);
				int idLearningCurve = result.getInt(SeriousGame.TagLearningCurveID);
				String learningCurveNotes = result.getString(SeriousGame.TagLearningCurveNotes);
				int effectiveLearningTimeID = result.getInt(SeriousGame.TagEffectiveLearningTimeID);
				String effectiveLearningTimeNotes = result.getString(SeriousGame.TagEffectiveLearningTimeNotes);

				boolean isContributorCurrentUser = false;
				List<Attachment> attachments = getAttachemnts(id);

				ArrayList<GenericModel> markets = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.markets.toString(), DbTables.markets_sgs.toString(), DbTablesSpecialColums.marketID.toString()), DbTablesSpecialColums.marketID.toString());
				ArrayList<GenericModel> ages = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.ages.toString(), DbTables.ages_sgs.toString(), DbTablesSpecialColums.ageID.toString()), DbTablesSpecialColums.ageID.toString());
				ArrayList<GenericModel> genres = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.game_genres.toString(), DbTables.genres_sgs.toString(), DbTablesSpecialColums.genreID.toString()), DbTablesSpecialColums.genreID.toString());

				retval.add(new SeriousGame(id, idOwner, owner, title, idPublisher, webSite,
					yearFirstRelease, yearlastRelease, idSource, idStatus, idAvailability,
					date, lastChange, version, freeDescription, keywords, attachments, idLearningCurve, learningCurveNotes, effectiveLearningTimeID, effectiveLearningTimeNotes,
					markets, ages, genres, isContributorCurrentUser, null, null, null, null, null));
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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


	public static SeriousGame getSeriousGame(int userId, int id) {
		ArrayList<Integer> contributorSeriousGame = getContributorSeriousGameId(userId);
		SeriousGame retval = null;

		String query = Queries.getSeriousGameById(id);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				int idOwner = result.getInt(SeriousGame.TagOwnerId);
				String owner = result.getString(SeriousGame.TagOwner);
				String title = result.getString(SeriousGame.TagTitle);
				int idPublisher = result.getInt(SeriousGame.TagPublisherID);
				String webSite = result.getString(SeriousGame.TagWebSite);
				int yearFirstRelease = result.getInt(SeriousGame.TagYearFirstRelease);
				int yearlastRelease = result.getInt(SeriousGame.TagYearLastRelease);
				int idSource = result.getInt(SeriousGame.TagSourceID);
				int idStatus = result.getInt(SeriousGame.TagStatusID);
				int idAvailability = result.getInt(SeriousGame.TagAvailabilityID);
				Date date = new Date(result.getDate(SeriousGame.TagDate).getTime());
				Date lastChange = new Date(result.getDate(SeriousGame.TagLastChange).getTime());
				int version = result.getInt(SeriousGame.TagVersion);
				String freeDescription = result.getString(SeriousGame.TagFreeDescription);
				String keywords = result.getString(SeriousGame.TagKeywords);
				int idLearningCurve = result.getInt(SeriousGame.TagLearningCurveID);
				String learningCurveNotes = result.getString(SeriousGame.TagLearningCurveNotes);
				int effectiveLearningTimeID = result.getInt(SeriousGame.TagEffectiveLearningTimeID);
				String effectiveLearningTimeNotes = result.getString(SeriousGame.TagEffectiveLearningTimeNotes);

				boolean isContributorCurrentUser = false;
				if (contributorSeriousGame.contains(id)) {
					isContributorCurrentUser = true;
				}

				List<Attachment> attachments = getAttachemnts(id);

				List<GenericModel> markets = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.markets.toString(), DbTables.markets_sgs.toString(), DbTablesSpecialColums.marketID.toString()), DbTablesSpecialColums.marketID.toString());
				List<GenericModel> ages = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.ages.toString(), DbTables.ages_sgs.toString(), DbTablesSpecialColums.ageID.toString()), DbTablesSpecialColums.ageID.toString());
				List<GenericModel> genres = getDataForSeriousGame(Queries.getDataTableForSGWhithRelation(id, DbTables.game_genres.toString(), DbTables.genres_sgs.toString(), DbTablesSpecialColums.genreID.toString()), DbTablesSpecialColums.genreID.toString());

				Technology technology = getTechnology(id);
				List<GenericModel> others = getOthers(id);

				LearningEnvironment learningEnvironment = getLearningEnvironment(id);
				Architecture architecture = getArchitecture(id);
				ContextAndAnalysis contextAndAnalysis = getContextAndAnalysis(id);

				retval = new SeriousGame(id, idOwner, owner, title, idPublisher, webSite,
					yearFirstRelease, yearlastRelease, idSource, idStatus, idAvailability,
					date, lastChange, version, freeDescription, keywords, attachments, idLearningCurve, learningCurveNotes, effectiveLearningTimeID, effectiveLearningTimeNotes,
					markets, ages, genres, isContributorCurrentUser, others, technology, learningEnvironment, architecture, contextAndAnalysis);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static int getIdFromKeys(String query) {
		int retval = -1;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				retval = result.getInt("id");
				break;
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static User getUser(String username, String password) {
		User retval = null;
		String query = Queries.getUser(username);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				int id = result.getInt(User.TagId);
				String name = result.getString(User.TagName);
				String pw = result.getString(User.TagPassword);
				if (!Joomla15PasswordHash.check(password, pw)) {
					return null;// password errato
				}
				String email = result.getString(User.TagEmail);
				retval = new User(id, name, username, password, email);

				break;
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ArrayList<ContributersMessages> getContributersMessages(int owner) {
		ArrayList<ContributersMessages> retval = new ArrayList<ContributersMessages>();

		String query = Queries.getContributersMessagesAll(owner);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				int id = result.getInt(ContributersMessages.TagId);
				int idSender = result.getInt(ContributersMessages.TagSenderId);
				String sender = result.getString(ContributersMessages.TagSender);
				int idseriousGame = result.getInt(ContributersMessages.TagSeriousGameId);
				String seriousGame = result.getString(ContributersMessages.TagSeriousGame);

				retval.add(new ContributersMessages(id, idSender, sender, idseriousGame, seriousGame));
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ArrayList<GenericModel> getOthers(int seriousGame) {
		ArrayList<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getDataTableForSG(DbTables.others.toString(), seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(DbTablesSpecialColums.relationTable.toString(), result.getString(DbTablesSpecialColums.relationTable.toString()));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static Technology getTechnology(int seriousGame) {
		Technology retval = null;
		List<GenericModel> deploymentStyle = getDataForSeriousGameWhithNotes(Queries.getDataTableForSGWhithRelation(seriousGame, DbTables.deployment_styles.toString(), DbTables.deployment_styles_sgs.toString(), DbTablesSpecialColums.deploymentStyleID.toString()), DbTablesSpecialColums.deploymentStyleID.toString());
		List<GenericModel> targetRange = getDataForSeriousGameWhithNotesAndSubType(Queries.getDataTableForSGWhithRelation(seriousGame, DbTables.target_ranges.toString(), DbTables.target_ranges_sgs.toString(), DbTablesSpecialColums.targetRangeID.toString()), DbTablesSpecialColums.targetRangeID.toString(), DbTablesSpecialColums.targetRangeTypeID.toString());

		String query = Queries.getDataTableForSG(DbTables.technology.toString(), seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				retval = new Technology(result.getInt(ModelTag.id.toString()),
					result.getInt(DbTablesSpecialColums.seriousGameID.toString()),
					result.getInt(ModelTag.gamePlatformID.toString()),
					result.getInt(ModelTag.inputDeviceReruiredID.toString()),
					result.getString(ModelTag.processor.toString()),
					result.getInt(ModelTag.ram.toString()),
					result.getInt(ModelTag.disk.toString()),
					result.getInt(ModelTag.bandwidthID.toString()),
					deploymentStyle,
					targetRange,
					false);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static LearningEnvironment getLearningEnvironment(int seriousGame) {
		LearningEnvironment retval = null;

		String query = Queries.getDataTableForSG(DbTables.learning_environment.toString(), seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				retval = new LearningEnvironment(result.getInt(ModelTag.id.toString()),
					result.getInt(DbTablesSpecialColums.seriousGameID.toString()),
					result.getString(DbTablesSpecialColums.le_feedback.toString()),
					result.getString(DbTablesSpecialColums.le_motivation.toString()),
					result.getString(DbTablesSpecialColums.le_sociality.toString()),
					result.getString(DbTablesSpecialColums.le_gradualityOfLearning.toString()),
					result.getString(DbTablesSpecialColums.le_transfer.toString()),
					result.getString(DbTablesSpecialColums.le_assessment.toString()),
					result.getString(DbTablesSpecialColums.le_supportToExploitation.toString()),
					result.getString(DbTablesSpecialColums.le_supportToLearnByDoing.toString()),
					result.getString(DbTablesSpecialColums.le_personalizationAndAdaptation.toString()),
					result.getString(DbTablesSpecialColums.le_other.toString()),
					false);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static List<GenericModel> getComponents(int seriousGame) {
		List<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getComponetsForSG(seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(ModelTag.componentTypeID.toString(), result.getInt(ModelTag.componentTypeID.toString()));
				model.set(ModelTag.componentType.toString(), result.getString(ModelTag.componentType.toString()));
				model.set(ModelTag.specificSGCase.toString(), result.getString(ModelTag.specificSGCase.toString()));
				model.set(ModelTag.pedagogicalParadigm.toString(), getPedagogicalParadigms(Queries.getPedagogicalParadigmForComponent(model.getId())));
				model.set(ModelTag.learningGoals.toString(), getLearningGoals(Queries.getLearningGoalsForComponent(model.getId())));

				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("VRE DB query error: " + e.getMessage());
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

	public static List<GenericModel> getAlgorithms(int seriousGame) {
		List<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getAlgorithmForSG(seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(ModelTag.specificSGCase.toString(), result.getString(ModelTag.specificSGCase.toString()));
				model.set(ModelTag.relatedSGComponents.toString(), Utilities.getListFromString(result.getString(ModelTag.relatedSGComponents.toString())));
				//	model.set(ModelTag.specificSGCase.toString(), result.getString(ModelTag.specificSGCase.toString()));
				model.set(ModelTag.algorithmTypesID.toString(), result.getInt(ModelTag.algorithmTypesID.toString()));
				model.set(ModelTag.algorithmType.toString(), result.getString(ModelTag.algorithmType.toString()));

				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("VRE DB query error at algorithm: " + e.getMessage());
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

	public static Architecture getArchitecture(int seriousGame) {
		List<GenericModel> components = getComponents(seriousGame);
		List<GenericModel> algorithm = getAlgorithms(seriousGame);
		List<GenericModel> gameEngines = getArchitectureData(DbTables.game_engines.toString(), seriousGame);
		List<GenericModel> interoperabilityAndStandards = getArchitectureData(DbTables.interoperability_and_standards.toString(), seriousGame);
		List<GenericModel> psichologicalAspects = getArchitectureData(DbTables.psichological_aspects.toString(), seriousGame);
		List<GenericModel> neuroscientificAspect = getArchitectureData(DbTables.neuroscientific_aspects.toString(), seriousGame);
		return new Architecture(components, algorithm, gameEngines, interoperabilityAndStandards, psichologicalAspects, neuroscientificAspect, false);
	}

	public static List<GenericModel> getArchitectureData(String table, int seriousGame) {
		List<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getDataTableForSG(table, seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(ModelTag.specificSGCase.toString(), result.getString(ModelTag.specificSGCase.toString()));
				model.set(ModelTag.relatedSGComponents.toString(), Utilities.getListFromString(result.getString(ModelTag.relatedSGComponents.toString())));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static List<GenericModel> getAlgorithmData(int seriousGame) {
		List<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getAlgorithmForSG(seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(ModelTag.specificSGCase.toString(), result.getString(ModelTag.specificSGCase.toString()));
				model.set(ModelTag.relatedSGComponents.toString(), Utilities.getListFromString(result.getString(ModelTag.relatedSGComponents.toString())));
				model.set(ModelTag.relatedSGComponents.toString(), Utilities.getListFromString(result.getString(ModelTag.relatedSGComponents.toString())));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static ContextAndAnalysis getContextAndAnalysis(int seriousGame) {
		List<GenericModel> contexts = getContexts(seriousGame);
		return new ContextAndAnalysis(contexts, getAnalysis(seriousGame), false);
	}

	public static List<GenericModel> getContexts(int seriousGame) {
		List<GenericModel> retval = new ArrayList<GenericModel>();

		String query = Queries.getContextsForSG(seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				int id = result.getInt(ModelTag.id.toString());
				String name = result.getString(ModelTag.name.toString());
				String description = result.getString(ModelTag.description.toString());
				int typeID = result.getInt(Context.tagTypeID);
				String type = result.getString(Context.tagType);
				if (typeID <= 0) {
					typeID = -1;
					type = "";
				}
				int environmentID = result.getInt(Context.tagEnvironmentID);
				String environment = result.getString(Context.tagEnvironment);
				if (environmentID <= 0) {
					environmentID = -1;
					environment = "";
				}
				int industrySchoolSubTypeID = result.getInt(Context.tagIndustrySchoolSubTypeID);
				int industrySchoolTypeID = result.getInt(Context.tagIndustrySchoolID);
				String industrySchoolDescription = result.getString(Context.tagIndustrySchoolDescription);
				if (typeID <= 0) {
					typeID = -1;
					industrySchoolDescription = "";
				}
				int learnerRoleID = result.getInt(Context.tagLearnerRoleID);
				String learnerRoleDescription = result.getString(Context.tagLearnerRoleDescription);
				if (learnerRoleID <= 0) {
					learnerRoleID = -1;
				}
				int instructorRoleID = result.getInt(Context.tagLearnerRoleID);
				String instructorRoleDescription = result.getString(Context.tagLearnerRoleDescription);
				if (instructorRoleID <= 0) {
					instructorRoleID = -1;
				}
				List<GenericModel> learningTopics = getLearningTopics(Queries.getLearningTopicForContext(id));
				List<GenericModel> pedagogicalParadigms = getPedagogicalParadigms(Queries.getPedagogicalParadigmForContext(id));
				List<GenericModel> learningGoals = getLearningGoals(Queries.getLearningGoalsForContext(id));
				GenericModel model = new GenericModel(id, name, description);

				Context context = new Context(id, name, description,
					typeID, type,
					environmentID, environment,
					industrySchoolTypeID, industrySchoolSubTypeID, industrySchoolDescription,
					learnerRoleID, learnerRoleDescription,
					instructorRoleID, instructorRoleDescription,
					learningTopics,
					pedagogicalParadigms,
					learningGoals);
				model.set(ModelTag.context.toString(), context);

				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static List<GenericModel> getLearningTopics(String query) {
		List<GenericModel> retval = new ArrayList<GenericModel>();

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(ModelTag.relationID.toString(), result.getInt(DbTablesSpecialColums.learningSubTopicID.toString()));
				model.set(ModelTag.subType.toString(), result.getString(ModelTag.subType.toString()));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	/**
	 * this function is for getin all the field of a component in a
	 */
	public static List<GenericModel> getPedagogicalParadigms(String query) {
		List<GenericModel> retval = new ArrayList<GenericModel>();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		GenericModel other = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
//				if (model.getName().equals("Other")) {
//					other = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
//
//				} else
				retval.add(model);

			}

		} catch (Exception e) {
			System.out.println("VRE DB query error: " + e.getMessage());
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

//		Collections.sort(retval, new Comparator<GenericModel>() {
//			public int compare(GenericModel c1, GenericModel c2) {
//				return c1.getName().compareTo(c2.getName()); // use your logic
//			}
//		});
//		if (other != null) {
//			retval.add(other);
//		}
//		return SortOther.getOrderedList(retval);
		return retval;
	}

	public static List<GenericModel> getLearningGoals(String query) {
		List<GenericModel> retval = new ArrayList<GenericModel>();

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				GenericModel model = new GenericModel(result.getInt(ModelTag.id.toString()), result.getString(ModelTag.name.toString()), result.getString(ModelTag.description.toString()));
				model.set(ModelTag.relationID.toString(), result.getInt(DbTablesSpecialColums.learningGoalsSubTypeID.toString()));
				model.set(ModelTag.subType.toString(), result.getString(ModelTag.subType.toString()));
				int learningGoalsSoftSkills = result.getInt(DbTablesSpecialColums.learningGoalsSoftSkillsTypeID.toString());
				if (learningGoalsSoftSkills > 0) {
					model.set(ModelTag.subSubTypeID.toString(), learningGoalsSoftSkills);
				}
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static List<Attachment> getAttachemnts(int seriousGame) {
		List<Attachment> retval = new ArrayList<Attachment>();
		String query = Queries.getDataTableForSG(DbTables.attachments.toString(), seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				Attachment model = new Attachment(result.getInt(ModelTag.id.toString()), result.getInt(DbTablesSpecialColums.seriousGameID.toString()), result.getString(ModelTag.name.toString()), result.getString(DbTablesSpecialColums.originalName.toString()));
				retval.add(model);
			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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

	public static Analysis getAnalysis(int seriousGame) {
		Analysis retval = null;
		String query = Queries.getDataTableForSG(DbTables.analysis.toString(), seriousGame);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			//Logic goes here
			int limit = 0;
			while (result.next() && ++limit < getMaxRow()) {
				retval = new Analysis(result.getString(DbTablesSpecialColums.referenceContext.toString()), result.getString(DbTablesSpecialColums.methodology.toString()),
					result.getInt(DbTablesSpecialColums.effectiveness.toString()), result.getString(DbTablesSpecialColums.effectivenessDescription.toString()),
					result.getInt(DbTablesSpecialColums.efficiency.toString()), result.getString(DbTablesSpecialColums.efficiencyDescription.toString()),
					result.getInt(DbTablesSpecialColums.usability.toString()), result.getString(DbTablesSpecialColums.usabilityDescription.toString()),
					result.getInt(DbTablesSpecialColums.diffusion.toString()), result.getString(DbTablesSpecialColums.diffusionDescription.toString()),
					result.getInt(DbTablesSpecialColums.feedbackAssessmentSupport.toString()), result.getString(DbTablesSpecialColums.feedbackAssessmentSupportDescription.toString()),
					result.getInt(DbTablesSpecialColums.exploitabilityLearningContext.toString()), result.getString(DbTablesSpecialColums.exploitabilityLearningContextDescription.toString()),
					result.getInt(DbTablesSpecialColums.reusabilityDifferentLearningContext.toString()), result.getString(DbTablesSpecialColums.reusabilityDifferentLearningContextDescription.toString()),
					result.getInt(DbTablesSpecialColums.capabilityMotivatingUser.toString()), result.getString(DbTablesSpecialColums.capabilityMotivatingUserDescription.toString()),
					result.getInt(DbTablesSpecialColums.capabilityEngagingUser.toString()), result.getString(DbTablesSpecialColums.capabilityEngagingUserDescription.toString()));

			}
		} catch (Exception e) {
			System.out.println("ZTE DB query error: " + e.getMessage());
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
}
