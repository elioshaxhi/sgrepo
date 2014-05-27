/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.models;

import com.customlib.seriousgame.client.DbTables;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeriousGame extends BaseModel implements IsSerializable {

	public static final String TableName = DbTables.serious_games.toString();
	public static final String TagId = "id";
	public static final String TagOwnerId = "OwnerID";
	public static final String TagOwner = "Owner";
	public static final String TagTitle = "Title";
	public static final String TagPublisherID = "PublisherID";
	public static final String TagWebSite = "WebSite";
	public static final String TagYearFirstRelease = "YearFirstRelease";
	public static final String TagYearLastRelease = "YearLastRelease";
	public static final String TagSourceID = "SourceID";
	public static final String TagStatusID = "StatusID";
	public static final String TagAvailabilityID = "AvailabilityID";
	public static final String TagDate = "Date";
	public static final String TagLastChange = "LastChange";
	public static final String TagVersion = "Version";
	public static final String TagFreeDescription = "FreeDescription";
	public static final String TagKeywords = "Keywords";
	public static final String TagAttachments = "Attachments";
	public static final String TagLearningCurveID = "LearningCurveID";
	public static final String TagLearningCurveNotes = "LearningCurveNotes";
	public static final String TagEffectiveLearningTimeID = "EffectiveLearningTimeID";
	public static final String TagEffectiveLearningTimeNotes = "EffectiveLearningTimeNotes";
	public static final String TagIsCurrentUserContributor = "isCurrentUserContributor";
	public static final String TagGeneralIfoChanges = "GeneralIfoChanges";
	public static final String TagRelationsChanges = "RelationsChanges";
	public static final String TagMarkets = "Markets";
	public static final String TagMarketsString = "MarketsString";
	public static final String TagAges = "Ages";
	public static final String TagGenres = "Genres";
	public static final String TagGenresString = "GenresString";
	public static final String TagTechnology = "Technology";
	public static final String TagLearningEnvironment = "LearningEnvironment";
	public static final String TagArchitecture = "Architecture";
	public static final String TagContextAndAnalysi = "ContextAndAnalysi";
	public static final String TagOthers = "Others";
	private Technology technology = null;
	private List<GenericModel> genericList = null;
	private LearningEnvironment le = null;
	private Architecture architecture = null;
	private ContextAndAnalysis contextAndAnalysi = null;
	private List<Attachment> attachments = null;

	public SeriousGame() {
	}

	public SeriousGame(int idOwner, String title, String freeDescription) {
		set(SeriousGame.TagOwnerId, idOwner);
		set(SeriousGame.TagTitle, title);
		if (freeDescription == null) {
			freeDescription = "";
		}
		set(SeriousGame.TagFreeDescription, freeDescription);
		set(SeriousGame.TagKeywords, "");
		set(SeriousGame.TagOthers, new ArrayList<GenericModel>());
	}

	public SeriousGame(int id, int idOwner, String owner, String title, int idPublisher, String webSite, int yearFirstRelease,
		int yearlastRelease, int idSource, int idStatus, int idAvailability, Date date, Date lastChange,
		int version, String freeDescription, String keywords, List<Attachment> attachments, int idLearningCurve, String learningCurveNotes, int learnintTimeId, String learningTimeNotes,
		List<GenericModel> markets, List<GenericModel> ages, List<GenericModel> genres, boolean isCurrentUserContributor, List<GenericModel> others,
		Technology technology, LearningEnvironment learningEnvironment, Architecture architecture, ContextAndAnalysis contextAndAnalysi) {
		set(SeriousGame.TagId, id);
		set(SeriousGame.TagOwnerId, idOwner);
		set(SeriousGame.TagOwner, owner);
		set(SeriousGame.TagTitle, title);
		set(SeriousGame.TagPublisherID, idPublisher);
		set(SeriousGame.TagWebSite, webSite);
		set(SeriousGame.TagYearFirstRelease, yearFirstRelease);
		set(SeriousGame.TagYearLastRelease, yearlastRelease);
		set(SeriousGame.TagSourceID, idSource);
		set(SeriousGame.TagStatusID, idStatus);
		set(SeriousGame.TagAvailabilityID, idAvailability);
		set(SeriousGame.TagDate, date);
		set(SeriousGame.TagLastChange, lastChange);
		set(SeriousGame.TagVersion, version);
		set(SeriousGame.TagFreeDescription, freeDescription);
		set(SeriousGame.TagKeywords, keywords);
		set(SeriousGame.TagAttachments, attachments);
		set(SeriousGame.TagLearningCurveID, idLearningCurve);
		set(SeriousGame.TagLearningCurveNotes, learningCurveNotes);
		set(SeriousGame.TagEffectiveLearningTimeID, learnintTimeId);
		set(SeriousGame.TagEffectiveLearningTimeNotes, learningTimeNotes);

		set(SeriousGame.TagMarkets, markets);
		set(SeriousGame.TagMarketsString, getNameString(markets));
		set(SeriousGame.TagAges, ages);
		set(SeriousGame.TagGenres, genres);
		set(SeriousGame.TagGenresString, getNameString(genres));
		set(SeriousGame.TagIsCurrentUserContributor, isCurrentUserContributor);

		if (others == null) {
			set(SeriousGame.TagOthers, new ArrayList<GenericModel>());
		} else {
			set(SeriousGame.TagOthers, others);
		}
		set(SeriousGame.TagTechnology, technology);
		set(SeriousGame.TagLearningEnvironment, learningEnvironment);
		set(SeriousGame.TagArchitecture, architecture);
		set(SeriousGame.TagContextAndAnalysi, contextAndAnalysi);
//        set(SeriousGame.TagContext, contextAndAnalysi.getContexts());
//        set(SeriousGame.TagContextAndAnalysi, contextAndAnalysi);
	}

	public Integer getId() {
		return (Integer) get(SeriousGame.TagId);
	}

	private String getNameString(List<GenericModel> list) {
		String retval = "";
		for (GenericModel genericModel : list) {
			if (retval.length() > 0) {
				retval += ", ";
			}
			retval += genericModel.getName();
		}
		return retval;
	}
}
