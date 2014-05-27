/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.server;

import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.DbTablesSpecialColums;
import com.customlib.seriousgame.client.models.DataBase;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.customlib.seriousgame.server.DataBaseInteraction;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;

import org.seriousgame.repository.client.server;

/**
 *
 * @author NERTIL
 */
public class serverImpl extends RemoteServiceServlet implements server {

	@Override
	public void init() {

		//String servletContext = getServletContext().getRealPath("");
		DataBaseInteraction.init();
	}

	@Override
	public DataBase getData() {
		DataBase db = new DataBase(DataBaseInteraction.getGeneric(DbTables.ages.toString()),
			DataBaseInteraction.getGeneric(DbTables.availability.toString()),
			DataBaseInteraction.getGeneric(DbTables.game_genres.toString()),
			DataBaseInteraction.getGeneric(DbTables.learning_curve.toString()),
			DataBaseInteraction.getGeneric(DbTables.markets.toString()),
			DataBaseInteraction.getPublishers(),
			DataBaseInteraction.getLastSeriousGames(20),
			DataBaseInteraction.getGeneric(DbTables.status.toString()),
			null,
			null,
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
	public SeriousGame getSeriousGame(int id) {
		return DataBaseInteraction.getSeriousGame(0, id);
	}


	@Override
	public List<SeriousGame> getSeriousGames(String searchText) {
		return DataBaseInteraction.getSeriousGames(searchText);
	}

	@Override
	public PagingLoadResult<SeriousGame> getSeriousGames(FilterPagingLoadConfig filters, String contentSearch) {
		List<SeriousGame> tempList = DataBaseInteraction.getSeriousGames(contentSearch);
		int start = filters.getOffset();
		int limit = tempList.size();
		if (filters.getLimit() > 0) {
			limit = Math.min(start + filters.getLimit(), limit);
		}
		List<SeriousGame> retvalList = new ArrayList<SeriousGame>();
		for (int i = filters.getOffset(); i < limit; i++) {
			retvalList.add(tempList.get(i));
		}
		return new BasePagingLoadResult<SeriousGame>(retvalList,
			filters.getOffset(), tempList.size());
	}
}
