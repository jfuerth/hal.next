/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.jboss.hal.client.resources;

import com.google.gwt.i18n.client.Constants;


/**
 * @author Heiko Braun
 * @author David Bosschaert
 * @date 5/2/11
 */
public interface UIConstants extends Constants {

    String common_error_contentStillAssignedToGroup();

    String common_error_unexpectedHttpResponse();

    String common_error_detailsMissing();

    String common_error_failedToDecode();

    String common_error_deploymentFailed();

    String common_error_unknownError();

    String common_error_failure();

    String common_label_addItem();

    String common_label_addProperty();

    String common_label_noRecords();

    String common_label_edit();

    String common_label_save();

    String common_label_delete();

    String common_label_createNewServerConfig();

    String common_label_attributes();

    String common_label_details();

    String common_label_name();

    String common_label_autoStart();

    String common_label_configuration();

    String common_label_noRecentMessages();

    String common_label_messageDetailTitle();

    String common_label_messageDetail();

    String common_label_messages();

    String common_label_newServerGroup();

    String common_label_add();

    String common_label_value();

    String common_label_key();

    String common_label_option();

    String common_label_cancel();

    String common_label_verifyDeploymentNames();

    String common_label_settings();

    String common_label_disable();

    String common_label_enable();

    String common_label_enOrDisable();

    String common_label_areYouSure();

    String common_label_addToGroup();

    String common_label_addToGroups();

    String common_label_updateContent();

    String common_label_useFileSystem();

    String common_label_date();

    String common_label_operations();

    String common_label_exclude();

    String common_label_upload();

    String common_label_deploymentSelection();

    String common_label_step();

    String common_label_chooseFile();

    String common_label_chooseServerGroup();

    String common_label_finish();

    String common_label_addContent();

    String common_label_contentRepository();

    String common_label_enabled();

    String common_label_lazyActivation();

    String common_label_changeActivation();

    String common_label_success();

    String common_label_reset();

    String common_label_reload();

    String common_label_selectedGroups();

    String subsys_jca_newDataSource();

    String subsys_jca_existingDataSources();

    String subsys_logging_addHandler();

    String subsys_logging_removeHandler();

    String subsys_logging_invalidByteSpec();

    String subsys_logging_newHandlerProperty();

    String subsys_infinispan_cache_container_desc();

    String subsys_infinispan_local_cache_desc();

    String subsys_infinispan_invalidation_cache_desc();

    String subsys_infinispan_replicated_cache_desc();

    String subsys_infinispan_distributed_cache_desc();

    String subsys_osgi_capabilityAdd();

    String subsys_osgi_capabilityEdit();

    String subsys_osgi_frameworkPropertyAdd();

    String subsys_jca_dataSource_registered();

    String subsys_jca_dataSource_xaprop_help();

    String subsys_jca_ra_registered();

    String subsys_jca_dataSource_select_driver();

    String common_label_refresh();

    String common_label_clear();

    String common_label_action();

    String common_label_start();

    String common_label_stop();

    String common_label_type();

    String subsys_jca_dataSource_verify();

    String common_label_item();

    String subsys_logging_customHandlers_desc();

    String subsys_logging_rootLogger_desc();

    String subsys_logging_fileHandlers_desc();

    String subsys_logging_periodicRotatingFileHandlers_desc();

    String subsys_logging_sizeRotatingFileHandlers_desc();

    String subsys_logging_asyncHandlers_desc();

    String subsys_logging_consoleHandlers_desc();

    String subsys_logging_loggers_desc();

    String subsys_threads_factory_desc();

    String subsys_threads_queueless_desc();

    String subsys_threads_blocking_queueless_desc();

    String subsys_threads_bounded_desc();

    String subsys_threads_blocking_bounded_desc();

    String subsys_threads_unbounded_desc();

    String subsys_threads_scheduled_desc();

    String subsys_jmx_desc();

    String subsys_jca_common_config_desc();

    String subsys_jca_boostrap_config_desc();

    String subsys_jca_workmanager_config_desc();

    String common_label_back();

    String common_label_view();

    String common_label_selection();

    String subsys_jca_threadpool_config_desc();

    String subsys_jca_error_default_workmanager_deletion();

    String subsys_jca_error_pool_removal();

    String subsys_jca_error_pool_removal_desc();

    String subsys_jca_error_pool_exist();

    String subsys_jca_error_pool_exist_desc();

    String subsys_jca_error_context_removal();

    String subsys_jca_error_context_removal_desc();

    String subsys_jca_dataSources_desc();

    String subsys_jca_xadataSources_desc();

    String subsys_jca_datasource_error_loadDriver();

    String subsys_jca_datasource_error_loadDriver_desc();

    String subsys_jca_error_datasource_notenabled();

    String subsys_jca_resource_adapter_desc();

    String subsys_jca_ra_connection_desc();

    String subsys_jca_adminobject_desc();

    String common_label_advanced();

    String subsys_messaging_jms_provider_desc();

    String subsys_messaging_jms_desc();

    String subsys_ejb3_container_desc();

    String subsys_ejb3_services_desc();

    String subsys_ejb3_beanpools_desc();

    String subsys_ejb3_threadpools_desc();

    String subsys_ee_desc();

    String subys_tx_desc();

    String subsys_jpa_desc();

    String subsys_jacorb_desc();

    String subsys_security_desc();

    String subsys_security_domains_desc();

    String subsys_security_authentication_desc();

    String subsys_security_authorization_desc();

    String subsys_security_audit_desc();

    String subsys_security_mapping_desc();

    String subsys_web_desc();

    String subsys_ws_desc();

    String subsys_ws_endpoint_desc();

    String common_label_setDefault();

    String common_serverGroups_desc();

    String interfaces_err_inetAddress_set();

    String interfaces_err_wildcard_nor_address_set();

    String interfaces_err_wildcard_set();

    String interfaces_err_loopback_set();

    String interfaces_err_loopback_nor_address_set();

    String interfaces_err_loopback_address_set();

    String interfaces_err_nic_set();

    String interfaces_err_nicmatch_set();

    String interfaces_err_not_set();

    String interfaces_desc();

    String common_socket_bindings_desc();

    String common_label_newGroup();

    String properties_global_desc();

    String common_label_active();

    String server_instance_reloadRequired();

    String server_instance_servers_needRestart();

    String subsys_jpa_deployment_desc();

    String subsys_jpa_basicMetric_desc();

    String subsys_jpa_puList_desc();

    String subsys_web_socketInUse();

    String hosts_jvm_desc();

    String hosts_jvm_err_deleteDefault();

    String common_serverConfig_desc();

    String host_interfaces_desc();

    String host_properties_desc();

    String server_instances_desc();

    String common_label_plaseWait();

    String common_label_requestProcessed();

    String common_err_server_not_active();

    String subsys_jca_dataSource_metric_desc();

    String subsys_messaging_queue_metric_desc();

    String subys_tx_metric_desc();

    String subsys_messaging_topic_metric_desc();

    String subys_web_metric_desc();

    String server_reload_desc();

    String server_config_uptodate();

    String server_config_desc();

    String server_instance_pleaseSelect();

    String common_label_logout();

    String logout_confirm();

    String subsys_mail_session_desc();

    String subsys_mail_server_desc();

    String common_label_append();

    String subsys_jgroups_err_protocols_required();

    String subsys_modcluster_desc();

    String help_need_help();

    String help_close_help();

    String common_label_copy();

    String common_label_export();

    String common_label_replace();

    String common_label_assign();

    String common_label_done();

    String commom_label_filter();

    String common_label_probe();

    String common_label_recover();

    String administration_assignment_user_group_desc();

    String administration_assignment_realm_desc();

    String administration_assignment_type_desc();

    String administration_assignment_roles_desc();

    String administration_audit_log_desc();

    String administration_add_scoped_role();

    String administration_standard_roles_desc();

    String administration_scoped_roles_desc();

    String administration_scoped_role_base_role_desc();

    String administration_scoped_role_scope_desc();

    String administration_role_include_all_desc();

    String administration_group_assignment();

    String administration_user_assignment();

    String administration_members();

    String administration_add_user_assignment();

    String administration_add_group_assignment();

    String administration_available_roles();

    String administration_assigned_roles();

    String administration_excluded_roles();

    String common_label_basedOn();

    String unauthorized();

    String unauthorized_desc();

    String duplicate_mail_server_type();

    String duplicate_data_source_name();

    String duplicate_data_source_jndi();

    String subsys_jca_datasource_error_load();

    String verify_datasource_failed_header();

    String verify_datasource_successful_header();

    String verify_datasource_internal_error();

    String verify_datasource_disabled();

    String verify_datasource_no_running_servers();

    String verify_datasource_dependent_error();

    String patch_manager_latest();

    String patch_manager_apply_new();

    String patch_manager_rollback();

    String patch_manager_recently();

    String patch_manager_error();

    String patch_manager_stop_server_title();

    String patch_manager_stop_server_yes();

    String patch_manager_stop_server_no();

    String patch_manager_select_patch_title();

    String patch_manager_select_patch_body();

    String patch_manager_stopping_servers_body();

    String patch_manager_possible_actions();

    String patch_manager_conflict_title();

    String patch_manager_conflict_body();

    String patch_manager_conflict_cancel_title();

    String patch_manager_conflict_cancel_body();

    String patch_manager_conflict_override_body();

    String patch_manager_conflict_override_check();

    String patch_manager_error_title();

    String patch_manager_apply_error_body();

    String patch_manager_apply_error_cancel_title();

    String patch_manager_apply_error_cancel_body();

    String patch_manager_apply_error_select_title();

    String patch_manager_apply_error_select_body();

    String patch_manager_show_details();

    String patch_manager_hide_details();

    String patch_manager_stop_server_error();

    String patch_manager_stop_server_timeout();

    String patch_manager_stop_server_unknown_error();

    String patch_manager_stop_server_error_continue_title();

    String patch_manager_stop_server_error_continue_body();

    String patch_manager_stop_server_error_cancel_title();

    String patch_manager_stop_server_error_cancel_body();

    String patch_manager_select_file();

    String patch_manager_restart_pending();

    String patch_manager_restart_timeout();

    String patch_manager_restart_error();

    String patch_manager_desc_community();

    String patch_manager_toolstrip_desc();

    String patch_manager_patch_information();

    String patch_manager_applied_at();

    String patch_manager_apply_patch();

    String patch_manager_apply_new_wizard_error();

    String patch_manager_rollback_wizard_error();

    String patch_manager_stop_server_question_for_apply();

    String patch_manager_stop_server_question_for_rollback();

    String patch_manager_servers_shutdown();

    String patch_manager_select_patch_upload();

    String patch_manager_update();

    String patch_manager_restart_now();

    String patch_manager_restart_no();

    String patch_manager_applied_success();

    String patch_manager_rollback_options_title();

    String patch_manager_rollback_options_body();

    String patch_manager_rollback_options_reset_configuration();

    String patch_manager_rollback_options_reset_configuration_desc();

    String patch_manager_rollback_options_override_all();

    String patch_manager_rollback_options_override_all_desc();

    String patch_manager_rollback_confirm_title();

    String patch_manager_rollback_confirm_body();

    String patch_manager_rolled_back_success_title();

    String patch_manager_rolled_back_success_body();

    String patch_manager_rollback_error_body();

    String patch_manager_rollback_error_cancel_title();

    String patch_manager_rollback_error_cancel_body();

    String patch_manager_rollback_error_select_title();

    String patch_manager_rollback_error_select_body();

    String homepage_header_community();

    String homepage_header_product();

    String homepage_sidebar_header();

    String section_configuration_intro();

    String section_runtime_intro();

    String section_domain_intro();

    String section_administration_intro();

    String content_box_create_datasource_title();

    String content_box_new_deployment_title();

    String content_box_new_deployment_link();

    String content_box_apply_patch_title();

    String content_box_role_assignment_title();

    String content_box_role_assignment_link();

    String content_box_create_server_group_title();

    String content_box_create_server_group_link();

    String content_box_topology_title();

    String content_box_topology_link();

    String sidebar_general_resources();

    String sidebar_eap_documentation_text();

    String sidebar_eap_documentation_link();

    String sidebar_learn_more_eap_text();

    String sidebar_learn_more_eap_link();

    String sidebar_trouble_ticket_text();

    String sidebar_trouble_ticket_link();

    String sidebar_training_text();

    String sidebar_training_link();

    String sidebar_developer_resources();

    String sidebar_tutorials_link();

    String sidebar_tutorials_text();

    String sidebar_eap_community_link();

    String sidebar_eap_community_text();

    String sidebar_operational_resources();

    String sidebar_eap_configurations_link();

    String sidebar_eap_configurations_text();

    String sidebar_knowledgebase_link();

    String sidebar_knowledgebase_text();

    String sidebar_consulting_link();

    String sidebar_consulting_text();

    String sidebar_wilfdfly_documentation_text();

    String sidebar_admin_guide_text();

    String sidebar_wildfly_issues_text();

    String sidebar_get_help();

    String sidebar_irc_text();

    String sidebar_user_forums_text();

    String sidebar_developers_mailing_list_text();

    String sidebar_wilfdfly_home_text();

    String sidebar_model_reference_text();

    String sidebar_latest_news();

    String patch_manager_servers_still_running_warning();

    String search_placeholder();

    String search_tooltip_osx();

    String search_tooltip_other();

    String common_label_restart();

    String patch_manager_restart_verify();

    String patch_manager_restart_required();

    String homepage_view_and_manage();

    String homepage_common_tasks();
}
