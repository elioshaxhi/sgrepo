/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client;

import com.customlib.seriousgame.client.models.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class Queries {

	public static String getAgesAll = "SELECT * FROM " + DbTables.ages;

	public static String getLastVisitStatic() {
		return "SELECT * FROM vre.vre_lastVisitTime WHERE id=5";
	}

	public static String setLastVisitStatic(String date) {
		return "UPDATE vre.vre_lastVisitTime  SET lastVisitTime ='" + date + "' WHERE id=5";
	}

	public static String getGenericAll(String tableName) {
		return "SELECT * FROM " + tableName + " ORDER BY " + ModelTag.name.toString();
	}

	public static String insertGeneric(GenericModel record, String tableName) {
		String description = record.getDescription();

		String query = "INSERT INTO  " + tableName + " ("
			+ ModelTag.id.toString() + " , " + ModelTag.name.toString() + " , "
			+ ModelTag.description.toString() + ") VALUES ("
			+ "NULL ,  '" + Utilities.replaceSpecialCharacter(record.getName())
			+ "', tagDescription);";
		if (description == null) {
			query = query.replaceAll("tagDescription", "NULL");
		} else {
			query = query.replaceAll("tagDescription", "'" + Utilities.replaceSpecialCharacter(description) + "'");
		}
		return query;
	}
	public static String getPublisherAll = "SELECT * FROM " + DbTables.publishers;
	public static String getTargetRangeTypeAll = "SELECT * FROM " + DbTables.target_ranges_types;

	public static String insertPublisher(GenericModel record) {
		String description = record.getDescription();
		String link = record.get(ModelTag.link.toString());

		String query = "INSERT INTO  " + DbTables.publishers + " ("
			+ ModelTag.id.toString() + " , " + ModelTag.name.toString() + " , "
			+ ModelTag.description.toString() + " ," + ModelTag.link.toString() + ") VALUES ("
			+ "NULL ,  '" + Utilities.replaceSpecialCharacter(record.getName())
			+ "', tagDescription , tagLink);";
		if (description == null) {
			query = query.replaceAll("tagDescription", "NULL");
		} else {
			query = query.replaceAll("tagDescription", "'" + Utilities.replaceSpecialCharacter(description) + "'");
		}
		if (link == null) {
			query = query.replaceAll("tagLink", "NULL");
		} else {
			query = query.replaceAll("tagLink", "'" + Utilities.replaceSpecialCharacter(link) + "'");
		}
		return query;
	}
        
        private static StringBuilder addCommonColumns(StringBuilder queryBuilder)
        {
            queryBuilder.append("SELECT ")
                    .append(SeriousGame.TableName).append(".").append(SeriousGame.TagId)
                    .append(", ")
                    .append(SeriousGame.TableName)
                    .append(".").append(SeriousGame.TagOwnerId).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagTitle).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagPublisherID).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagWebSite).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagYearFirstRelease).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagYearLastRelease).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagSourceID).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagStatusID).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagAvailabilityID).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagDate).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagLastChange).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagVersion).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagFreeDescription).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagKeywords).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagLearningCurveID).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagLearningCurveNotes).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagEffectiveLearningTimeID).append(", ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagEffectiveLearningTimeNotes).append(", ")
                    .append(User.TableName).append(".").append(User.TagUserName)
                    .append(" as ").append(SeriousGame.TagOwner)
                    .append(" FROM ").append(SeriousGame.TableName)
                    .append(" inner join ").append(User.TableName)
                    .append(" on ")
                    .append(User.TableName).append(".").append(User.TagId)
                    .append(" = ")
                    .append(SeriousGame.TableName).append(".").append(SeriousGame.TagOwnerId); 
            return queryBuilder;
        }

	public static String getSeriousGameAll() {
            StringBuilder queryBuilder = new StringBuilder();
            addCommonColumns(queryBuilder).append(";");
            return queryBuilder.toString();
	}

	public static String getSeriousGame(String searchText) {
            StringBuilder queryBuilder = new StringBuilder();
            addCommonColumns(queryBuilder)
                    .append(" WHERE ")
                    .append(SeriousGame.TableName).append(".").append(SeriousGame.TagTitle)
                    .append(" LIKE '%").append(searchText).append("%'");
            return queryBuilder.toString();
	}
        
        public static String getSeriousGame(ArrayList<Integer> ids) {
            StringBuilder ids_clause = new StringBuilder();
      
            if (ids.isEmpty()) {
                ids_clause.append("TRUE");
            } else {
                ids_clause.append(SeriousGame.TableName).append(".")
                        .append(SeriousGame.TagId).append(" IN (");
                ids_clause.append(ids.get(0));
                for (int i=1; i<ids.size(); ++i) {
                    ids_clause.append(",").append(ids.get(i));
                }
                ids_clause.append(")");
            }
            StringBuilder queryBuilder = new StringBuilder();
            addCommonColumns(queryBuilder)
                    .append(" WHERE ").append(ids_clause);
            return queryBuilder.toString();
	}

	public static String getSeriousGameAllOrderByDate() {
            StringBuilder queryBuilder = new StringBuilder();
            addCommonColumns(queryBuilder)
                    .append("  ORDER BY ")
                    .append(SeriousGame.TableName).append(".")
                    .append(SeriousGame.TagDate)
                    .append(" DESC ");
            return queryBuilder.toString();
	}

	public static String getSeriousGameById(int id) {
            StringBuilder queryBuilder = new StringBuilder();
            addCommonColumns(queryBuilder)
                    .append(" WHERE ")
                    .append(SeriousGame.TableName).append(".").append(SeriousGame.TagId)
                    .append(" = ").append(id);
            return queryBuilder.toString();
	}

    public static String getGenresAnalytics()
    {
        return "SELECT count(seriousgameId), GenreId, vre_game_genres.Name FROM vre.vre_genres_sgs inner join vre.vre_game_genres on vre_game_genres.id = GenreID group by GenreId;";
    }

	public static String insertSeriousGame(SeriousGame record) {
		String query = "INSERT INTO  " + SeriousGame.TableName + " ("
			+ SeriousGame.TagId + " , "
			+ SeriousGame.TagOwnerId + " , "
			+ SeriousGame.TagTitle + " , "
			+ SeriousGame.TagDate + " , "
			+ SeriousGame.TagLastChange + " , "
			+ SeriousGame.TagVersion + " , "
			+ SeriousGame.TagFreeDescription + " , "
			+ SeriousGame.TagKeywords + ") VALUES ("
			+ "NULL , " + record.get(SeriousGame.TagOwnerId) + ","
			+ " '" + Utilities.replaceSpecialCharacter((String) record.get(SeriousGame.TagTitle)) + "', "
			+ "'" + Utilities.getDateString((Date) record.get(SeriousGame.TagDate)) + "', "
			+ "'" + Utilities.getDateString((Date) record.get(SeriousGame.TagLastChange)) + "', "
			+ (Integer) record.get(SeriousGame.TagVersion) + ", "
			+ "'" + Utilities.replaceSpecialCharacter((String) record.get(SeriousGame.TagFreeDescription)) + "', "
			+ "'" + Utilities.replaceSpecialCharacter((String) record.get(SeriousGame.TagKeywords)) + "');";
		return query;
	}

	public static String insertNewUser(UserUpdate udu) {

		String query = "INSERT INTO  " + DbTables.users + " ("
			+ UserUpdate.tagId + " , "
			+ UserUpdate.tagName + " , "
			+ UserUpdate.tagUsername + " , "
			+ UserUpdate.tagEmail + " , "
			+ UserUpdate.tagPassword + " , "
			+ UserUpdate.tagUsertype + " , "
			+ UserUpdate.tagBlock + " , "
			+ UserUpdate.tagSendEmail + " , "
			+ UserUpdate.tagGid + " , "
			+ UserUpdate.tagRegisterDate + " , "
			+ UserUpdate.tagLastvisitDate + " , "
			+ UserUpdate.tagActivation + " , "
			+ UserUpdate.tagParams + ") VALUES ("
			+ "NULL , '" + Utilities.replaceSpecialCharacter(udu.getName()) + "', "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getUsername()) + "', "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getEmail()) + "', "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getPassword()) + "', "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getUsertype()) + "', "
			+ (Integer) udu.getBlock() + ", "
			+ (Integer) udu.getSendEmail() + ", "
			+ (Integer) udu.getGid() + ", "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getRegisterDate()) + "', "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getLastvisitDate()) + "', "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getActivation()) + "', "
			+ "'" + Utilities.replaceSpecialCharacter(udu.getParams()) + "');";
		return query;
	}

	public static String updateaUser(int id, UserUpdate udu) {

		String query = "UPDATE  " + DbTables.users + " SET "
			+ UserUpdate.tagName + " =  '" + Utilities.replaceSpecialCharacter(udu.getName()) + "', "
			+ UserUpdate.tagUsername + " = '" + Utilities.replaceSpecialCharacter(udu.getUsername()) + "', "
			+ UserUpdate.tagEmail + " ='" + Utilities.replaceSpecialCharacter(udu.getEmail()) + "', "
			+ UserUpdate.tagPassword + " ='" + Utilities.replaceSpecialCharacter(udu.getPassword()) + "', "
			+ UserUpdate.tagUsertype + " ='" + Utilities.replaceSpecialCharacter(udu.getUsertype()) + "', "
			+ UserUpdate.tagBlock + " = " + String.valueOf(udu.getBlock()) + " , "
			+ UserUpdate.tagSendEmail + " = " + String.valueOf(udu.getSendEmail()) + " , "
			+ UserUpdate.tagGid + " = " + String.valueOf(udu.getGid()) + " , "
			+ UserUpdate.tagRegisterDate + " ='" + Utilities.replaceSpecialCharacter(udu.getRegisterDate()) + "', "
			+ UserUpdate.tagLastvisitDate + " ='" + Utilities.replaceSpecialCharacter(udu.getLastvisitDate()) + "', "
			+ UserUpdate.tagActivation + " ='" + Utilities.replaceSpecialCharacter(udu.getActivation()) + "', "
			+ UserUpdate.tagParams + " ='" + Utilities.replaceSpecialCharacter(udu.getParams()) + "' ";
		query += " WHERE " + UserUpdate.tagId + " = " + id;

		return query;
	}

	public static String updateSeriousGame(SeriousGame record) {
		Integer publisher = record.get(SeriousGame.TagPublisherID);
		String webSite = record.get(SeriousGame.TagWebSite);
		Integer yearFirstRelease = record.get(SeriousGame.TagYearFirstRelease);
		Integer yearLastRelease = record.get(SeriousGame.TagYearLastRelease);
		Integer source = record.get(SeriousGame.TagSourceID);
		Integer status = record.get(SeriousGame.TagStatusID);
		Integer availability = record.get(SeriousGame.TagAvailabilityID);
		Integer learningCurve = record.get(SeriousGame.TagLearningCurveID);
		String learningCurveNotes = record.get(SeriousGame.TagLearningCurveNotes);
		Integer effectiveLearningTimeID = record.get(SeriousGame.TagEffectiveLearningTimeID);
		String effectiveLearningTimeNotes = record.get(SeriousGame.TagEffectiveLearningTimeNotes);

		String query = "UPDATE  " + SeriousGame.TableName + " SET "
			+ SeriousGame.TagTitle + " =  '" + Utilities.replaceSpecialCharacter((String) record.get(SeriousGame.TagTitle)) + "', "
			+ SeriousGame.TagDate + " = '" + Utilities.getDateString((Date) record.get(SeriousGame.TagDate)) + "', "
			+ SeriousGame.TagLastChange + " ='" + Utilities.getDateString((Date) record.get(SeriousGame.TagLastChange)) + "', "
			+ SeriousGame.TagVersion + " = " + (Integer) record.get(SeriousGame.TagVersion) + ", "
			+ SeriousGame.TagFreeDescription + " = '" + Utilities.replaceSpecialCharacter((String) record.get(SeriousGame.TagFreeDescription)) + "', "
			+ SeriousGame.TagKeywords + " = '" + Utilities.replaceSpecialCharacter((String) record.get(SeriousGame.TagKeywords)) + "' ";
		if (publisher != null && publisher > 0) {
			query += " , " + SeriousGame.TagPublisherID + " = " + String.valueOf(publisher);
		} else {
			query += " , " + SeriousGame.TagPublisherID + " = NULL";
		}
		if (webSite != null) {
			query += " , " + SeriousGame.TagWebSite + " = '" + Utilities.replaceSpecialCharacter(webSite) + "'";
		} else {
			query += " , " + SeriousGame.TagWebSite + " = NULL";
		}
		if (yearFirstRelease != null && yearFirstRelease > 0) {
			query += " , " + SeriousGame.TagYearFirstRelease + " = " + String.valueOf(yearFirstRelease);
		} else {
			query += " , " + SeriousGame.TagYearFirstRelease + " = NULL";
		}
		if (yearLastRelease != null && yearLastRelease > 0) {
			query += " , " + SeriousGame.TagYearLastRelease + " = " + String.valueOf(yearLastRelease);
		} else {
			query += " , " + SeriousGame.TagYearLastRelease + " = NULL";
		}
		if (source != null && source > 0) {
			query += " , " + SeriousGame.TagSourceID + " = " + String.valueOf(source);
		} else {
			query += " , " + SeriousGame.TagSourceID + " = NULL";
		}
		if (status != null && status > 0) {
			query += " , " + SeriousGame.TagStatusID + " = " + String.valueOf(status);
		} else {
			query += " , " + SeriousGame.TagStatusID + " = NULL";
		}
		if (availability != null && availability > 0) {
			query += " , " + SeriousGame.TagAvailabilityID + " = " + String.valueOf(availability);
		} else {
			query += " , " + SeriousGame.TagAvailabilityID + " = NULL";
		}
		if (learningCurve != null && learningCurve > 0) {
			query += " , " + SeriousGame.TagLearningCurveID + " = " + String.valueOf(learningCurve);
		} else {
			query += " , " + SeriousGame.TagLearningCurveID + " = NULL";
		}

		if (learningCurveNotes != null) {
			query += " , " + SeriousGame.TagLearningCurveNotes + " = '" + learningCurveNotes + "' ";
		} else {
			query += " , " + SeriousGame.TagLearningCurveNotes + " = NULL";
		}
		if (effectiveLearningTimeID != null && effectiveLearningTimeID > 0) {
			query += " , " + SeriousGame.TagEffectiveLearningTimeID + " = " + String.valueOf(effectiveLearningTimeID);
		} else {
			query += " , " + SeriousGame.TagEffectiveLearningTimeID + " = NULL";
		}
		if (effectiveLearningTimeNotes != null) {
			query += " , " + SeriousGame.TagEffectiveLearningTimeNotes + " = '" + String.valueOf(effectiveLearningTimeNotes) + "' ";
		} else {
			query += " , " + SeriousGame.TagEffectiveLearningTimeNotes + " = NULL";
		}

		query += " WHERE " + SeriousGame.TableName + "." + SeriousGame.TagId + " = " + record.getId();

		return query;
	}

	public static String getSeriousGameId(String title, String date) {
		return "SELECT " + SeriousGame.TagId
			+ " FROM " + SeriousGame.TableName
			+ " WHERE " + SeriousGame.TagTitle + " = '" + Utilities.replaceSpecialCharacter(title)
			+ "' AND " + SeriousGame.TagDate + " = '" + date + "'";
	}

	public static String getIdFromKey(String tableName, String keyName, String keyValue) {
		return "SELECT id FROM " + tableName
			+ " WHERE " + keyName + " = '" + Utilities.replaceSpecialCharacter(keyValue) + "'";
	}

	public static String getIdListHavingKey(String tableName, String keyName, int keyValue, String tag) {
		return "SELECT " + tag + " FROM " + tableName
			+ " WHERE " + keyName + " = " + keyValue;
	}

	public static String getDataTableForSGWhithRelation(int idSG, String table, String relationTable, String tagId) {
		return "SELECT * FROM " + relationTable
			+ " inner join " + table + " on " + table + "." + ModelTag.id.toString() + " = " + relationTable + "." + tagId + " "
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG;
	}

	public static String getseriousGameAges(int idSG) {
		return "SELECT * FROM " + DbTables.ages_sgs + " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG;
	}

	public static String getseriousGameGenres(int idSG) {
		return "SELECT * FROM " + DbTables.genres_sgs
			+ " inner join " + DbTables.game_genres.toString() + " on " + DbTables.game_genres.toString() + "." + ModelTag.id.toString() + " = " + DbTables.genres_sgs + "." + DbTablesSpecialColums.genreID + " "
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG;
	}

	public static String getseriousGameOwner(int idOwner) {
		return "SELECT * FROM " + DbTables.users + " WHERE id = " + idOwner;
	}

	public static String insertRelations(String tableName, String firstColumnName, String secondColumnName, int firstColumnValue, ArrayList<Integer> secondColumnValues) {
		if (secondColumnValues == null || secondColumnValues.isEmpty()) {
			return "";
		}
		String retval = "INSERT INTO " + tableName + " (id, " + firstColumnName + ", " + secondColumnName + ") VALUES ";
		for (Iterator<Integer> it = secondColumnValues.iterator(); it.hasNext();) {
			retval += " (NULL, " + firstColumnValue + ", " + it.next() + ")";
			if (it.hasNext()) {
				retval += ",";
			} else {
				retval += ";";
			}
		}

		return retval;
	}

	public static String insertRelations(String tableName, String firstColumnName, String secondColumnName, int firstColumnValue, List<GenericModel> secondColumnValues) {
		if (secondColumnValues == null || secondColumnValues.isEmpty()) {
			return "";
		}
		String retval = "INSERT INTO " + tableName + " (id, " + firstColumnName + ", " + secondColumnName + ") VALUES ";

		for (Iterator<GenericModel> it = secondColumnValues.iterator(); it.hasNext();) {
			retval += " (NULL, " + firstColumnValue + ", " + String.valueOf(it.next().getId()) + ")";
			if (it.hasNext()) {
				retval += ",";
			} else {
				retval += ";";
			}
		}

		return retval;
	}

	public static String insertRelationsWhithNotes(String tableName, String firstColumnName, String secondColumnName, int firstColumnValue, List<GenericModel> secondColumnValues) {
		if (secondColumnValues == null || secondColumnValues.isEmpty()) {
			return "";
		}
		String retval = "INSERT INTO " + tableName + " (id, " + firstColumnName + ", " + secondColumnName + ", " + DbTablesSpecialColums.notes + ") VALUES ";

		for (Iterator<GenericModel> it = secondColumnValues.iterator(); it.hasNext();) {
			GenericModel genericModel = it.next();
			String notes = genericModel.getDescription();
			retval += " (NULL, " + firstColumnValue + ", " + String.valueOf(genericModel.getId()) + ", tagNotes)";
			retval = Utilities.replaceStringTagInQuery(retval, notes, "tagNotes");
			if (it.hasNext()) {
				retval += ",";
			} else {
				retval += ";";
			}
		}

		return retval;
	}

	public static String insertRelationsWhithNotesAndSubType(String tableName, String firstColumnName, String secondColumnName, String thirdColumnName,
		int firstColumnValue, List<GenericModel> secondColumnValues) {
		if (secondColumnValues == null || secondColumnValues.isEmpty()) {
			return "";
		}
		String retval = "INSERT INTO " + tableName + " (id, " + firstColumnName + ", " + secondColumnName + ", " + DbTablesSpecialColums.notes + ", " + thirdColumnName + ") VALUES ";

		for (Iterator<GenericModel> it = secondColumnValues.iterator(); it.hasNext();) {
			GenericModel genericModel = it.next();
			String notes = genericModel.getDescription();
			Integer subTypeId = genericModel.get(ModelTag.relationID.toString());
			retval += " (NULL, " + firstColumnValue + ", " + String.valueOf(genericModel.getId()) + ", tagNotes, tagRelation)";
			retval = Utilities.replaceIDTagInQuery(retval, subTypeId, "tagRelation");
			retval = Utilities.replaceStringTagInQuery(retval, notes, "tagNotes");
			if (it.hasNext()) {
				retval += ",";
			} else {
				retval += ";";
			}
		}

		return retval;
	}

	public static String deleteRelations(String tableName, String columnName, int columnValue) {
		return "DELETE FROM " + tableName + " WHERE " + columnName + " = " + columnValue;
	}

	public static String delete(String tableName, String key, List<Integer> idList) {
		if (idList == null && idList.isEmpty()) {
			return "";
		}
		String retval = "DELETE FROM " + tableName + " WHERE ";
		for (Iterator<Integer> it = idList.iterator(); it.hasNext();) {
			retval += "(" + key + " = " + it.next() + ")";
			if (it.hasNext()) {
				retval += " OR ";
			}

		}
		return retval;
	}

	public static String getUser(String username) {
		return "SELECT * FROM " + DbTables.users
			+ " WHERE " + UserUpdate.tagUsername + " = '" + username + "'";
	}

	public static String getUserById(int id) {
		return "SELECT * FROM " + User.TableName
			+ " WHERE " + User.TagId + " = " + id + "";
	}

	public static String getContributersMessagesAll(int seriousGameOwner) {
		return "SELECT " + ContributersMessages.TableName + "." + ContributersMessages.TagId + ", "
			+ ContributersMessages.TableName + "." + ContributersMessages.TagSenderId + ", "
			+ User.TableName + "." + User.TagUserName + " as " + ContributersMessages.TagSender + ", "
			+ ContributersMessages.TableName + "." + ContributersMessages.TagSeriousGameId + ", "
			+ SeriousGame.TableName + "." + SeriousGame.TagTitle + " as " + ContributersMessages.TagSeriousGame
			+ " FROM " + ContributersMessages.TableName //la riga prima del from non deve avvere la virgola
			+ " inner join " + User.TableName + " on " + User.TableName + "." + User.TagId + " = " + ContributersMessages.TableName + "." + ContributersMessages.TagSenderId
			+ " inner join " + SeriousGame.TableName + " on " + SeriousGame.TableName + "." + SeriousGame.TagId + " = " + ContributersMessages.TableName + "." + ContributersMessages.TagSeriousGameId
			+ " WHERE " + SeriousGame.TableName + "." + SeriousGame.TagOwnerId + "=" + seriousGameOwner;
	}

	public static String insertContributersMessage(int seriousGame, int sender) {
		return "INSERT INTO " + DbTables.contributors_messages + " (id, " + ContributersMessages.TagSenderId + ", " + DbTablesSpecialColums.seriousGameID.toString() + ") VALUS (NULL, " + sender + ", " + seriousGame + ")";
	}

	public static String deleteContributersMessage(int sender, int seriousGame) {
		return "DELETE FROM " + DbTables.contributors_messages + " WHERE (" + ContributersMessages.TagSenderId + "=" + sender + ") AND (" + DbTablesSpecialColums.seriousGameID.toString() + "=" + seriousGame + ")";
	}

	public static String getContributorSeriousGameId(int userId) {
		return "SELECT * FROM " + DbTables.contributors + " WHERE (" + DbTablesSpecialColums.userID.toString() + "=" + userId + ")";
	}

	public static String insertAttachment(String name, String originalName, int seriousGameId) {
		String query = "INSERT INTO  " + DbTables.attachments.toString() + " ("
			+ ModelTag.id.toString() + " , " + DbTablesSpecialColums.seriousGameID.toString() + ", " + ModelTag.name.toString() + ", " + ModelTag.originalName.toString() + ") VALUES ("
			+ "NULL , " + seriousGameId + ",  '" + Utilities.replaceSpecialCharacter(name) + "', '" + Utilities.replaceSpecialCharacter(originalName) + "');";
		return query;
	}

	public static String insertOther(int seriousGame, GenericModel other) {
		String query = "INSERT INTO " + DbTables.others + " (id, " + ModelTag.name.toString() + ", " + ModelTag.description.toString() + ", " + DbTablesSpecialColums.relationTable + ", " + DbTablesSpecialColums.seriousGameID
			+ ") VALUES (NULL, '" + Utilities.replaceSpecialCharacter(other.getName()) + "', TagOtherDescription, '" + other.get(DbTablesSpecialColums.relationTable.toString()) + "', " + seriousGame + ")";
		query = Utilities.replaceStringTagInQuery(query, other.getDescription(), "TagOtherDescription");
		return query;
	}

	public static String getDataTableForSG(String table, int seriousGame) {
		return "SELECT * FROM " + table + " WHERE (" + DbTablesSpecialColums.seriousGameID.toString() + "=" + seriousGame + ")";
	}

	public static String insertSeriousGameRelation(int seriousGameID, String table) {
		String query = "INSERT INTO  " + table + " ("
			+ ModelTag.id.toString() + " , "
			+ DbTablesSpecialColums.seriousGameID.toString() + ") VALUES ("
			+ "NULL , " + seriousGameID + ");";
		return query;
	}

	public static String updateTechnology(Technology record) {
		Integer gamePlatformID = record.getGamePlatformID();
		Integer seriousGameID = record.getSeriousGameID();
		if (seriousGameID == null || seriousGameID < 1) {
			System.out.println("Serious gameId null or smaller than zero");
			return "";
		}
		Integer inputDeviceReruiredID = record.getInputDeviceReruiredID();
		String processor = record.getProcessor();
		Integer ram = record.getRAM();
		Integer disk = record.getDisk();
		Integer bandwidthID = record.getBandwidthID();

		String query = "UPDATE " + DbTables.technology + " SET "
			+ ModelTag.gamePlatformID + " = TagGamePlatformID, "
			+ ModelTag.inputDeviceReruiredID + " = TagInputDeviceReruiredID, "
			+ ModelTag.processor + " = TagProcessor, "
			+ ModelTag.ram + " = TagRAM, "
			+ ModelTag.disk + " = TagDisk, "
			+ ModelTag.bandwidthID + " = TagBandwidthID "
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + seriousGameID;

		query = Utilities.replaceIDTagInQuery(query, gamePlatformID, "TagGamePlatformID");
		query = Utilities.replaceIDTagInQuery(query, inputDeviceReruiredID, "TagInputDeviceReruiredID");
		query = Utilities.replaceStringTagInQuery(query, processor, "TagProcessor");
		query = Utilities.replaceIntegerTagInQuery(query, ram, "TagRAM");
		query = Utilities.replaceIntegerTagInQuery(query, disk, "TagDisk");
		query = Utilities.replaceIDTagInQuery(query, bandwidthID, "TagBandwidthID");

		return query;
	}

	public static String updateLearningEnvironment(LearningEnvironment record) {
		Integer seriousGameID = record.getSeriousGameID();
		if (seriousGameID == null || seriousGameID < 1) {
			System.out.println("Serious gameId null or smaller than zero");
			return "";
		}
		String feedback = record.getFeedback();
		String motivation = record.getMotivation();
		String sociality = record.getSociality();
		String gradualityOfLearning = record.getGradualityOfLearning();
		String transfer = record.getTransfer();
		String assessment = record.getAssessment();
		String supportToExploitation = record.getSupportToExploitation();
		String supportToLearnByDoing = record.getSupportToLearnByDoing();
		String personalizationAndAdaptation = record.getPersonalizationAndAdaptation();
		String other = record.getOther();

		String query = "UPDATE " + DbTables.learning_environment + " SET "
			+ DbTablesSpecialColums.le_feedback + " = TagFeedback, "
			+ DbTablesSpecialColums.le_motivation + " = TagMotivation, "
			+ DbTablesSpecialColums.le_sociality + " = TagSociality, "
			+ DbTablesSpecialColums.le_gradualityOfLearning + " = TagGradualityOfLearning, "
			+ DbTablesSpecialColums.le_transfer + " = TagTransfer, "
			+ DbTablesSpecialColums.le_assessment + " = TaGassessment, "
			+ DbTablesSpecialColums.le_supportToExploitation + " = TagSupportToExploitation, "
			+ DbTablesSpecialColums.le_supportToLearnByDoing + " = TagSupportToLearnByDoing, "
			+ DbTablesSpecialColums.le_personalizationAndAdaptation + " = TagPersonalizationAndAdaptation, "
			+ DbTablesSpecialColums.le_other + " = TagOther "
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + seriousGameID;

		query = Utilities.replaceStringTagInQuery(query, feedback, "TagFeedback");
		query = Utilities.replaceStringTagInQuery(query, motivation, "TagMotivation");
		query = Utilities.replaceStringTagInQuery(query, sociality, "TagSociality");
		query = Utilities.replaceStringTagInQuery(query, gradualityOfLearning, "TagGradualityOfLearning");
		query = Utilities.replaceStringTagInQuery(query, transfer, "TagTransfer");
		query = Utilities.replaceStringTagInQuery(query, assessment, "TaGassessment");
		query = Utilities.replaceStringTagInQuery(query, supportToExploitation, "TagSupportToExploitation");
		query = Utilities.replaceStringTagInQuery(query, supportToLearnByDoing, "TagSupportToLearnByDoing");
		query = Utilities.replaceStringTagInQuery(query, personalizationAndAdaptation, "TagPersonalizationAndAdaptation");
		query = Utilities.replaceStringTagInQuery(query, other, "TagOther");

		return query;
	}

	public static String insertComponent(GenericModel componet, int seriousGame) {
		String query = "INSERT INTO " + DbTables.components + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.seriousGameID + ", "
			+ ModelTag.name + ", "
			+ ModelTag.description + ", "
			+ ModelTag.componentTypeID + ", "
			+ ModelTag.specificSGCase + ") VALUES ";

		Integer typeID = componet.get(ModelTag.componentTypeID.toString());
		String name = componet.getName();
		String description = componet.getDescription();
		String sgCase = componet.get(ModelTag.specificSGCase.toString());
		query += "(NULL, "
			+ seriousGame + ", "
			+ "TagName, "
			+ "TagDescription, "
			+ typeID + ", "
			+ "TagSpecificSGCase);";
		query = Utilities.replaceStringTagInQuery(query, name, "TagName");
		query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
		query = Utilities.replaceStringTagInQuery(query, sgCase, "TagSpecificSGCase");

		return query;
	}

	/**
	 * i've to create a function for inserting end gettin value from at
	 * algorithm model. With this query i update the database for eventualy
	 * insertion and deletaion of algorithm items.
	 *
	 */
	public static String insertAlgorithm(String table, List<GenericModel> dataList, int seriousGame) {
		String query = "INSERT INTO " + DbTables.algorithms + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.seriousGameID + ", "
			+ ModelTag.name + ", "
			+ ModelTag.description + ", "
			+ ModelTag.specificSGCase + ", "
			+ ModelTag.relatedSGComponents + ", "
			+ ModelTag.algorithmTypesID + ") VALUES ";
		for (Iterator<GenericModel> it = dataList.iterator(); it.hasNext();) {
			GenericModel algorithm = it.next();
			Integer typeID = algorithm.get(ModelTag.algorithmTypesID.toString());
			String name = algorithm.getName();
			String description = algorithm.getDescription();
			String sgCase = algorithm.get(ModelTag.specificSGCase.toString());
			String relatedSGComponents = Utilities.getStringFromList((List<Integer>) algorithm.get(ModelTag.relatedSGComponents.toString()));

			query += "(NULL, "
				+ seriousGame + ", "
				+ "TagName, "
				+ "TagDescription, "
				+ "TagSpecificSGCase, "
				+ "TagRelatedSGComponents, "
				+ typeID + ")";
			query = Utilities.replaceStringTagInQuery(query, name, "TagName");
			query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
			query = Utilities.replaceStringTagInQuery(query, sgCase, "TagSpecificSGCase");
			query = Utilities.replaceStringTagInQuery(query, relatedSGComponents, "TagRelatedSGComponents");
			if (it.hasNext()) {
				query += ",";
			} else {
				query += ";";
			}
		}
		return query;
	}

	public static String getComponetsForSG(int idSG) {
		return "SELECT " + DbTables.components + "." + ModelTag.id + ", "
			+ DbTables.components + "." + ModelTag.name + ", "
			+ DbTables.components + "." + ModelTag.description + ", "
			+ DbTables.components + "." + ModelTag.componentTypeID + ", "
			+ DbTables.component_types + "." + ModelTag.name + " AS " + ModelTag.componentType + ", "
			+ DbTables.components + "." + ModelTag.specificSGCase
			+ " FROM " + DbTables.component_types
			+ " inner join " + DbTables.components + " on " + DbTables.components + "." + ModelTag.componentTypeID.toString() + " = " + DbTables.component_types + "." + ModelTag.id + " "
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG;
	}

	/**
	 * I use this query for getin the information on first database call.
	 * With the corrispective relation of vre_algorithms_types.
	 */
	public static String getAlgorithmForSG(int idSG) {
		return "SELECT " + DbTables.algorithms + "." + ModelTag.id + ", "
			+ DbTables.algorithms + "." + ModelTag.name + ", "
			+ DbTables.algorithms + "." + ModelTag.description + ", "
			+ DbTables.algorithms + "." + ModelTag.specificSGCase + ", "
			+ DbTables.algorithms + "." + ModelTag.relatedSGComponents + ", "
			+ DbTables.algorithms + "." + ModelTag.algorithmTypesID + ", "
			+ DbTables.algorithm_types + "." + ModelTag.name + " AS " + ModelTag.algorithmType
			+ " FROM " + DbTables.algorithm_types
			+ " inner join " + DbTables.algorithms + " on " + DbTables.algorithms + "." + ModelTag.algorithmTypesID.toString() + " = " + DbTables.algorithm_types + "." + ModelTag.id + " "
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG;
	}

	public static String insertArchitectureData(String table, List<GenericModel> dataList, int seriousGame) {
		String query = "INSERT INTO " + table + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.seriousGameID + ", "
			+ ModelTag.name + ", "
			+ ModelTag.description + ", "
			+ ModelTag.specificSGCase + ", "
			+ ModelTag.relatedSGComponents + ") VALUES ";

		for (Iterator<GenericModel> it = dataList.iterator(); it.hasNext();) {
			GenericModel record = it.next();
			String name = record.getName();
			String description = record.getDescription();
			String sgCase = record.get(ModelTag.specificSGCase.toString());
			String relatedSGComponents = Utilities.getStringFromList((List<Integer>) record.get(ModelTag.relatedSGComponents.toString()));
			query += "(NULL, "
				+ seriousGame + ", "
				+ "TagName, "
				+ "TagDescription, "
				+ "TagSpecificSGCase, "
				+ "TagRelatedSGComponents)";
			query = Utilities.replaceStringTagInQuery(query, name, "TagName");
			query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
			query = Utilities.replaceStringTagInQuery(query, sgCase, "TagSpecificSGCase");
			query = Utilities.replaceStringTagInQuery(query, relatedSGComponents, "TagRelatedSGComponents");
			if (it.hasNext()) {
				query += ",";
			} else {
				query += ";";
			}
		}
		return query;
	}

	public static String insertContext(Context record, int seiousGame) {
		String name = record.getName();
		String description = record.getDescription();
		Integer type = record.get(Context.tagTypeID);
		Integer environmentID = record.get(Context.tagEnvironmentID);
		Integer industrySchoolID = record.get(Context.tagIndustrySchoolSubTypeID);
		String industrySchoolDescription = record.get(Context.tagIndustrySchoolDescription);
		Integer learnerRoleID = record.get(Context.tagLearnerRoleID);
		String learnerRoleDescription = record.get(Context.tagLearnerRoleDescription);
		Integer instructorRoleID = record.get(Context.tagInstructorRoleID);
		String instructorRoleDescription = record.get(Context.tagInstructorRoleDescription);

		String query = "INSERT INTO " + DbTables.contexts + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.seriousGameID + ", "
			+ ModelTag.name + ", "
			+ ModelTag.description + ", "
			+ Context.tagTypeID + ", "
			+ Context.tagEnvironmentID + ", "
			+ Context.tagIndustrySchoolID + ", "
			+ Context.tagIndustrySchoolDescription + ", "
			+ Context.tagLearnerRoleID + ", "
			+ Context.tagLearnerRoleDescription + ", "
			+ Context.tagInstructorRoleID + ", "
			+ Context.tagInstructorRoleDescription + ") VALUES "
			+ "(NULL, " + seiousGame + ", "
			+ "TagName, "
			+ "TagDescription, "
			+ "tagTypeID, "
			+ "tagEnvironmentID, "
			+ "tagIndustrySchoolID, "
			+ "tagIndustrySchoolDescription, "
			+ "tagLearnerRoleID, "
			+ "tagLearnerRoleDescription, "
			+ "tagInstructorRoleID, "
			+ "tagInstructorRoleDescription)";
		query = Utilities.replaceStringTagInQuery(query, name, "TagName");
		query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
		query = Utilities.replaceIDTagInQuery(query, type, "tagTypeID");
		query = Utilities.replaceIDTagInQuery(query, environmentID, "tagEnvironmentID");
		query = Utilities.replaceIDTagInQuery(query, industrySchoolID, "tagIndustrySchoolID");
		query = Utilities.replaceStringTagInQuery(query, industrySchoolDescription, "tagIndustrySchoolDescription");
		query = Utilities.replaceIDTagInQuery(query, learnerRoleID, "tagLearnerRoleID");
		query = Utilities.replaceStringTagInQuery(query, learnerRoleDescription, "tagLearnerRoleDescription");
		query = Utilities.replaceIDTagInQuery(query, instructorRoleID, "tagInstructorRoleID");
		query = Utilities.replaceStringTagInQuery(query, instructorRoleDescription, "tagInstructorRoleDescription");

		return query;
	}

	public static String getContextID(int idSG, String name) {
		return "SELECT " + ModelTag.id
			+ " FROM " + DbTables.contexts
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG
			+ " AND " + ModelTag.name + "='" + Utilities.replaceSpecialCharacter(name) + "';";
	}

	public static String getComponentID(int idSG, String name) {
		return "SELECT " + ModelTag.id
			+ " FROM " + DbTables.components
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG
			+ " AND " + ModelTag.name + "='" + Utilities.replaceSpecialCharacter(name) + "';";
	}

	public static String getContextsForSG(int idSG) {
		return "SELECT " + DbTables.contexts + "." + ModelTag.id + ", "
			+ DbTables.contexts + "." + ModelTag.name + ", "
			+ DbTables.contexts + "." + ModelTag.description + ", "
			+ DbTables.contexts + "." + Context.tagTypeID + ", "
			+ DbTables.context_type + "." + ModelTag.name + " AS " + Context.tagType + ", "
			+ DbTables.contexts + "." + Context.tagEnvironmentID + ", "
			+ DbTables.context_environments + "." + ModelTag.name + " AS " + Context.tagEnvironment + ", "
			+ DbTables.contexts + "." + Context.tagIndustrySchoolID + " AS " + Context.tagIndustrySchoolSubTypeID + ", "
			+ DbTables.context_industry_school + "." + Context.tagIndustrySchoolID + " AS " + Context.tagIndustrySchoolID + ", "
			+ DbTables.contexts + "." + Context.tagIndustrySchoolDescription + ", "
			+ DbTables.contexts + "." + Context.tagLearnerRoleID + ", "
			+ DbTables.contexts + "." + Context.tagLearnerRoleDescription + ", "
			+ DbTables.contexts + "." + Context.tagInstructorRoleID + ", "
			+ DbTables.contexts + "." + Context.tagInstructorRoleDescription + " "
			+ " FROM " + DbTables.contexts
			+ " left join " + DbTables.context_type + " on " + DbTables.contexts + "." + Context.tagTypeID + " = " + DbTables.context_type + "." + ModelTag.id + " "
			+ " left join " + DbTables.context_environments + " on " + DbTables.contexts + "." + Context.tagEnvironmentID + " = " + DbTables.context_environments + "." + ModelTag.id + " "
			+ " left join " + DbTables.context_industry_school + " on " + DbTables.contexts + "." + Context.tagIndustrySchoolID + " = " + DbTables.context_industry_school + "." + ModelTag.id + " "
			+ " left join " + DbTables.context_industry_school_types + " on " + DbTables.context_industry_school + "." + Context.tagIndustrySchoolID + " = " + DbTables.context_industry_school_types + "." + ModelTag.id + " "
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG;
	}

	public static String insertLearningTopics(List<GenericModel> dataList, int contextId) {
		String query = "INSERT INTO " + DbTables.context_learning_topics + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.contextID + ", "
			+ DbTablesSpecialColums.learningTopicTypeID + ", "
			+ DbTablesSpecialColums.learningSubTopicID + ", "
			+ ModelTag.description + ") VALUES ";

		for (Iterator<GenericModel> it = dataList.iterator(); it.hasNext();) {
			GenericModel record = it.next();
			Integer learningTopicTypeID = record.getId();
			Integer learningSubTopicID = record.get(ModelTag.relationID.toString());
			String description = record.getDescription();
			query += "(NULL, "
				+ contextId + ", "
				+ learningTopicTypeID + ", "
				+ learningSubTopicID + ", "
				+ "TagDescription)";
			query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
			if (it.hasNext()) {
				query += ",";
			} else {
				query += ";";
			}
		}
		return query;
	}

	public static String getLearningTopicForContext(int condextID) {
		return "SELECT " + DbTables.context_learning_topic_types + "." + ModelTag.id + ", "
			+ DbTables.context_learning_topic_types + "." + ModelTag.name + ", "
			+ DbTables.context_learning_topics + "." + ModelTag.description + ", "
			+ DbTables.context_learning_topics + "." + DbTablesSpecialColums.learningSubTopicID + ", "
			+ DbTables.context_learning_subtopics + "." + ModelTag.name + " AS " + ModelTag.subType
			+ " FROM " + DbTables.context_learning_topics
			+ " inner join " + DbTables.context_learning_topic_types + " on " + DbTables.context_learning_topic_types + "." + ModelTag.id.toString() + " = " + DbTables.context_learning_topics + "." + DbTablesSpecialColums.learningTopicTypeID
			+ " inner join " + DbTables.context_learning_subtopics + " on " + DbTables.context_learning_topics + "." + DbTablesSpecialColums.learningSubTopicID.toString() + " = " + DbTables.context_learning_subtopics + "." + ModelTag.id
			+ " WHERE " + DbTablesSpecialColums.contextID.toString() + " = " + condextID;
	}

	public static String insertPedagogicalParadigmsForContext(List<GenericModel> dataList, int contextId) {
		String query = "INSERT INTO " + DbTables.context_pedagogical_paradigms + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.contextID + ", "
			+ DbTablesSpecialColums.pedagogicalParadigmTypeID + ", "
			+ ModelTag.description + ") VALUES ";

		for (Iterator<GenericModel> it = dataList.iterator(); it.hasNext();) {
			GenericModel record = it.next();
			Integer pedagogicalParadigmTypeID = record.getId();
			String description = record.getDescription();
			query += "(NULL, "
				+ contextId + ", "
				+ pedagogicalParadigmTypeID + ", "
				+ "TagDescription)";
			query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
			if (it.hasNext()) {
				query += ",";
			} else {
				query += ";";
			}
		}
		return query;
	}

	public static String getPedagogicalParadigmForContext(int condextID) {
		return "SELECT " + DbTables.context_pedagogical_paradigm_types + "." + ModelTag.id + ", "
			+ DbTables.context_pedagogical_paradigm_types + "." + ModelTag.name + ", "
			+ DbTables.context_pedagogical_paradigms + "." + ModelTag.description
			+ " FROM " + DbTables.context_pedagogical_paradigms
			+ " inner join " + DbTables.context_pedagogical_paradigm_types + " on " + DbTables.context_pedagogical_paradigm_types + "." + ModelTag.id.toString() + " = " + DbTables.context_pedagogical_paradigms + "." + DbTablesSpecialColums.pedagogicalParadigmTypeID + " "
			+ " WHERE " + DbTablesSpecialColums.contextID.toString() + " = " + condextID;
	}

	public static String insertLearningGoalsForContext(List<GenericModel> dataList, int contextId) {
		String query = "INSERT INTO " + DbTables.context_learning_goals + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.contextID + ", "
			+ DbTablesSpecialColums.learningGoalsTypeID + ", "
			+ DbTablesSpecialColums.learningGoalsSubTypeID + ", "
			+ DbTablesSpecialColums.learningGoalsSoftSkillsTypeID + ", "
			+ ModelTag.description + ") VALUES ";

		for (Iterator<GenericModel> it = dataList.iterator(); it.hasNext();) {
			GenericModel record = it.next();
			Integer learningGoalsTypeID = record.getId();
			Integer learningGoalsSubTypeID = record.get(ModelTag.relationID.toString());
			Integer learningGoalsSoftSkillsID = record.get(ModelTag.subSubTypeID.toString());
			String description = record.getDescription();
			query += "(NULL, "
				+ contextId + ", "
				+ learningGoalsTypeID + ", "
				+ learningGoalsSubTypeID + ", "
				+ "TagLearningGoalsSoftSkillsTypeID, "
				+ "TagDescription)";
			query = Utilities.replaceIDTagInQuery(query, learningGoalsSoftSkillsID, "TagLearningGoalsSoftSkillsTypeID");
			query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
			if (it.hasNext()) {
				query += ",";
			} else {
				query += ";";
			}
		}
		return query;
	}

	public static String getLearningGoalsForContext(int condextID) {
		return "SELECT " + DbTables.context_learning_goals_types + "." + ModelTag.id + ", "
			+ DbTables.context_learning_goals_types + "." + ModelTag.name + ", "
			+ DbTables.context_learning_goals + "." + ModelTag.description + ", "
			+ DbTables.context_learning_goals + "." + DbTablesSpecialColums.learningGoalsSubTypeID + ", "
			+ DbTables.context_learning_goals + "." + DbTablesSpecialColums.learningGoalsSoftSkillsTypeID + ", "
			+ DbTables.context_learning_goals_subtypes + "." + ModelTag.name + " AS " + ModelTag.subType
			+ " FROM " + DbTables.context_learning_goals
			+ " inner join " + DbTables.context_learning_goals_types + " on " + DbTables.context_learning_goals_types + "." + ModelTag.id.toString() + " = " + DbTables.context_learning_goals + "." + DbTablesSpecialColums.learningGoalsTypeID + " "
			+ " inner join " + DbTables.context_learning_goals_subtypes + " on " + DbTables.context_learning_goals_subtypes + "." + ModelTag.id.toString() + " = " + DbTables.context_learning_goals + "." + DbTablesSpecialColums.learningGoalsSubTypeID + " "
			+ " WHERE " + DbTablesSpecialColums.contextID.toString() + " = " + condextID;
	}

	public static String getContextIDsForSG(int idSG) {
		return "SELECT " + DbTables.contexts + "." + ModelTag.id
			+ " FROM " + DbTables.contexts
			+ " WHERE " + DbTablesSpecialColums.seriousGameID.toString() + " = " + idSG;
	}

	public static String insertAnalysis(Analysis analysis, int seriousGameId) {
		String query = "INSERT INTO " + DbTables.analysis + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.seriousGameID + ", "
			+ DbTablesSpecialColums.referenceContext + ", "
			+ DbTablesSpecialColums.methodology + ", "
			+ DbTablesSpecialColums.effectiveness + ", "
			+ DbTablesSpecialColums.effectivenessDescription + ", "
			+ DbTablesSpecialColums.efficiency + ", "
			+ DbTablesSpecialColums.efficiencyDescription + ", "
			+ DbTablesSpecialColums.usability + ", "
			+ DbTablesSpecialColums.usabilityDescription + ", "
			+ DbTablesSpecialColums.diffusion + ", "
			+ DbTablesSpecialColums.diffusionDescription + ", "
			+ DbTablesSpecialColums.feedbackAssessmentSupport + ", "
			+ DbTablesSpecialColums.feedbackAssessmentSupportDescription + ", "
			+ DbTablesSpecialColums.exploitabilityLearningContext + ", "
			+ DbTablesSpecialColums.exploitabilityLearningContextDescription + ", "
			+ DbTablesSpecialColums.reusabilityDifferentLearningContext + ", "
			+ DbTablesSpecialColums.reusabilityDifferentLearningContextDescription + ", "
			+ DbTablesSpecialColums.capabilityMotivatingUser + ", "
			+ DbTablesSpecialColums.capabilityMotivatingUserDescription + ", "
			+ DbTablesSpecialColums.capabilityEngagingUser + ", "
			+ DbTablesSpecialColums.capabilityEngagingUserDescription
			+ ") VALUES (NULL, "
			+ seriousGameId + ", "
			+ "TagReferenceContext, "
			+ "TagMethodology, "
			+ analysis.getEffectiveness() + ", "
			+ "TagEffectivenessDescription, "
			+ analysis.getEfficiency() + ", "
			+ "TagEfficiencyDescription, "
			+ analysis.getUsability() + ", "
			+ "TagUsabilityDescription, "
			+ analysis.getDiffusion() + ", "
			+ "TagDiffusionDescription, "
			+ analysis.getFeedbackAssessmentSupport() + ", "
			+ "TagFeedbackAssessmentSupportDescription, "
			+ analysis.getExploitabilityLearningContext() + ", "
			+ "TagExploitabilityLearningContextDescription, "
			+ analysis.getReusabilityDifferentLearningContext() + ", "
			+ "TagReusabilityDifferentLearningContextDescription, "
			+ analysis.getCapabilityMotivatingUser() + ", "
			+ "TagCapabilityMotivatingUserDescription, "
			+ analysis.getCapabilityEngagingUser() + ", "
			+ "TagCapabilityEngagingUserDescription)";
		query = Utilities.replaceStringTagInQuery(query, analysis.getReferenceContext(), "TagReferenceContext");
		query = Utilities.replaceStringTagInQuery(query, analysis.getMethodology(), "TagMethodology");
		query = Utilities.replaceStringTagInQuery(query, analysis.getEffectivenessDescription(), "TagEffectivenessDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getEfficiencyDescription(), "TagEfficiencyDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getUsabilityDescription(), "TagUsabilityDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getDiffusionDescription(), "TagDiffusionDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getFeedbackAssessmentSupportDescription(), "TagFeedbackAssessmentSupportDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getExploitabilityLearningContextDescription(), "TagExploitabilityLearningContextDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getReusabilityDifferentLearningContextDescription(), "TagReusabilityDifferentLearningContextDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getCapabilityMotivatingUserDescription(), "TagCapabilityMotivatingUserDescription");
		query = Utilities.replaceStringTagInQuery(query, analysis.getCapabilityEngagingUserDescription(), "TagCapabilityEngagingUserDescription");

		return query;
	}

	public static String insertPedagogicalParadigmsForComponent(List<GenericModel> dataList, int componentId) {
		String query = "INSERT INTO " + DbTables.components_pedagogical_paradigms + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.componentID + ", "
			+ DbTablesSpecialColums.pedagogicalParadigmTypeID + ", "
			+ ModelTag.description + ") VALUES ";

		for (Iterator<GenericModel> it = dataList.iterator(); it.hasNext();) {
			GenericModel record = it.next();
			Integer pedagogicalParadigmTypeID = record.getId();
			String description = record.getDescription();
			query += "(NULL, "
				+ componentId + ", "
				+ pedagogicalParadigmTypeID + ", "
				+ "TagDescription)";
			query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
			if (it.hasNext()) {
				query += ",";
			} else {
				query += ";";
			}
		}
		return query;
	}

	public static String getPedagogicalParadigmForComponent(int componentID) {
		return "SELECT " + DbTables.context_pedagogical_paradigm_types + "." + ModelTag.id + ", "
			+ DbTables.context_pedagogical_paradigm_types + "." + ModelTag.name + ", "
			+ DbTables.components_pedagogical_paradigms + "." + ModelTag.description
			+ " FROM " + DbTables.components_pedagogical_paradigms
			+ " inner join " + DbTables.context_pedagogical_paradigm_types + " on " + DbTables.context_pedagogical_paradigm_types + "." + ModelTag.id.toString() + " = " + DbTables.components_pedagogical_paradigms + "." + DbTablesSpecialColums.pedagogicalParadigmTypeID + " "
			+ " WHERE " + DbTablesSpecialColums.componentID.toString() + " = " + componentID;
	}

	public static String insertLearningGoalsForComponent(List<GenericModel> dataList, int componentId) {
		String query = "INSERT INTO " + DbTables.components_learning_goals + " (" + ModelTag.id + ", "
			+ DbTablesSpecialColums.componentID + ", "
			+ DbTablesSpecialColums.learningGoalsTypeID + ", "
			+ DbTablesSpecialColums.learningGoalsSubTypeID + ", "
			+ DbTablesSpecialColums.learningGoalsSoftSkillsTypeID + ", "
			+ ModelTag.description + ") VALUES ";

		for (Iterator<GenericModel> it = dataList.iterator(); it.hasNext();) {
			GenericModel record = it.next();
			Integer learningGoalsTypeID = record.getId();
			Integer learningGoalsSubTypeID = record.get(ModelTag.relationID.toString());
			Integer learningGoalsSoftSkillsID = record.get(ModelTag.subSubTypeID.toString());
			String description = record.getDescription();
			query += "(NULL, "
				+ componentId + ", "
				+ learningGoalsTypeID + ", "
				+ learningGoalsSubTypeID + ", "
				+ "TagLearningGoalsSoftSkillsTypeID, "
				+ "TagDescription)";
			query = Utilities.replaceIDTagInQuery(query, learningGoalsSoftSkillsID, "TagLearningGoalsSoftSkillsTypeID");
			query = Utilities.replaceStringTagInQuery(query, description, "TagDescription");
			if (it.hasNext()) {
				query += ",";
			} else {
				query += ";";
			}
		}
		return query;
	}

	public static String getLearningGoalsForComponent(int componentID) {
		return "SELECT " + DbTables.context_learning_goals_types + "." + ModelTag.id + ", "
			+ DbTables.context_learning_goals_types + "." + ModelTag.name + ", "
			+ DbTables.components_learning_goals + "." + ModelTag.description + ", "
			+ DbTables.components_learning_goals + "." + DbTablesSpecialColums.learningGoalsSubTypeID + ", "
			+ DbTables.components_learning_goals + "." + DbTablesSpecialColums.learningGoalsSoftSkillsTypeID + ", "
			+ DbTables.context_learning_goals_subtypes + "." + ModelTag.name + " AS " + ModelTag.subType
			+ " FROM " + DbTables.components_learning_goals
			+ " inner join " + DbTables.context_learning_goals_types + " on " + DbTables.context_learning_goals_types + "." + ModelTag.id.toString() + " = " + DbTables.components_learning_goals + "." + DbTablesSpecialColums.learningGoalsTypeID + " "
			+ " inner join " + DbTables.context_learning_goals_subtypes + " on " + DbTables.context_learning_goals_subtypes + "." + ModelTag.id.toString() + " = " + DbTables.components_learning_goals + "." + DbTablesSpecialColums.learningGoalsSubTypeID + " "
			+ " WHERE " + DbTablesSpecialColums.componentID.toString() + " = " + componentID;
	}
}
