/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author NERTIL
 */
public enum DbTables implements IsSerializable {

	ages("vre_ages"),
	ages_sgs("vre_ages_sgs"),
	attachments("vre_attachments"),
	availability("vre_availability"),
	contributors("vre_contributors"),
	contributors_messages("vre_contributors_messages"),
	game_genres("vre_game_genres"),
	genres_sgs("vre_genres_sgs"),
	learning_curve("vre_learning_curve"),
	markets("vre_markets"),
	markets_sgs("vre_markets_sgs"),
	publishers("vre_publishers"),
	serious_games("vre_serious_games"),
	sources("vre_sources"),
	status("vre_status"),
	users("jos_users"),
	others("vre_others"),
	effective_learning_time("vre_effective_learning_time"),
	game_platforms("vre_game_platforms"),
	bandwidth_types(" vre_bandwidth_types"),
	input_device_required("vre_input_device_required"),
	deployment_styles("vre_deployment_styles"),
	deployment_styles_sgs("vre_deployment_styles_sgs"),
	target_ranges("vre_target_ranges"),
	target_ranges_types("vre_target_ranges_types"),
	target_ranges_sgs("vre_target_ranges_sgs"),
	technology("vre_technology"),
	component_types("vre_component_types"),//vre_component_types
	algorithm_types("vre_algorithm_types"),//vre_component_types
	components("vre_components"),//da modificare nel vre
	components_learning_goals("vre_components_learning_goals"),
	components_pedagogical_paradigms("vre_components_pedagogical_paradigms"),
	context_environments("vre_context_environments"),
	context_industry_school("vre_context_industry_school"),
	context_industry_school_types("vre_context_industry_school_types"),
	context_learner_role_types("vre_context_learner_role_types"),
	context_learning_goals("vre_context_learning_goals"),
	context_learning_goals_types("vre_context_learning_goals_types"),
	context_learning_goals_soft_skills("vre_context_learning_goals_soft_skills"),
	context_learning_goals_subtypes("vre_context_learning_goals_subtypes"),
	context_learning_subtopics("vre_context_learning_subtopics"),
	context_learning_topic_types("vre_context_learning_topic_types"),
	context_teacher_role_types("vre_context_teacher_role_types"),
	context_type("vre_context_type"),
	contexts("vre_contexts"),
	context_learning_topics("vre_context_learning_topics"),
	context_pedagogical_paradigm_types("vre_context_pedagogical_paradigm_types"),
	context_pedagogical_paradigms("vre_context_pedagogical_paradigms"),
	learning_environment("vre_learning_environment"),
	algorithms("vre_algorithms"),
	game_engines("vre_game_engines"),
	interoperability_and_standards("vre_interoperability_and_standards"),
	psichological_aspects("vre_psichological_aspects"),
	neuroscientific_aspects("vre_neuroscientific_aspects"),
	analysis("vre_analysis");
	private String table;

	DbTables() {
	}

	DbTables(String table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return table;
	}
}
