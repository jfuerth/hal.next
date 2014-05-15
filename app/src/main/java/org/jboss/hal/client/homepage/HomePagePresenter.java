/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.hal.client.homepage;

import static org.jboss.hal.config.ProductInfo.Variant.COMMUNITY;
import static org.jboss.hal.config.ProductInfo.Variant.PRODUCT;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.hal.client.bootstrap.BootstrapContext;
import org.jboss.hal.client.resources.I18n;
import org.jboss.hal.client.resources.NameTokens;
import org.jboss.hal.config.ProductInfo;
import org.uberfire.client.annotations.DefaultPosition;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.workbench.model.Position;

/**
 * @author Harald Pehl
 */
@ApplicationScoped
@WorkbenchScreen(identifier = NameTokens.Homepage)
public class HomepagePresenter {

    public interface View extends IsWidget {

        void addInfoBoxes(List<InfoBox> infoBoxes);

        void addContentBoxes(List<ContentBox> contentBoxes);

        void addSidebarSections(List<SidebarSection> sidebarSections);
    }


    @Inject I18n i18n;
    @Inject View view;
    @Inject BootstrapContext bootstrapContext;
    @Inject Instance<InfoBox> infoBoxInstance;
    @Inject Instance<ContentBox> contentBoxInstance;

    @PostConstruct
    public void init() {
        view.addInfoBoxes(setupInfoBoxes(bootstrapContext.isStandalone()));
        view.addContentBoxes(setupContentBoxes(bootstrapContext.isStandalone()));
        view.addSidebarSections(setupSidebarSection(bootstrapContext.getProductInfo().getHalVariant()));
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Home";
    }

    @WorkbenchPartView
    public IsWidget getView() {
        return view;
    }

    @DefaultPosition
    public Position getDefaultPosition() {
        return Position.ROOT;
    }

    private List<InfoBox> setupInfoBoxes(final boolean standalone) {
        InfoBox infoBox;
        List<InfoBox> infoBoxes = new ArrayList<>();

        if (standalone) {
            infoBox = infoBoxInstance.get();
            infoBoxes.add(infoBox.using(NameTokens.ServerConfig, i18n.constants.common_label_configuration(),
                    i18n.constants.section_configuration_intro()));
            infoBox = infoBoxInstance.get();
            infoBoxes.add(infoBox
                    .using(NameTokens.StandaloneRuntime, "Runtime", i18n.constants.section_runtime_intro()));
        } else {
            infoBox = infoBoxInstance.get();
            infoBoxes.add(infoBox.using(NameTokens.ProfileManagement, i18n.constants.common_label_configuration(),
                    i18n.constants.section_configuration_intro()));
            infoBox = infoBoxInstance.get();
            infoBoxes.add(infoBox.using(NameTokens.HostManagement, "Domain", i18n.constants.section_domain_intro()));
            infoBox = infoBoxInstance.get();
            infoBoxes.add(infoBox.using(NameTokens.DomainRuntime, "Runtime", i18n.constants.section_runtime_intro()));
        }
        infoBox = infoBoxInstance.get();
        infoBoxes.add(infoBox
                .using(NameTokens.Administration, "Administration", i18n.constants.section_administration_intro()));

        return infoBoxes;
    }

    private List<ContentBox> setupContentBoxes(final boolean standalone) {
        ContentBox contentBox;
        List<ContentBox> list = new LinkedList<>();

        if (standalone) {
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("NewDeployment",
                    i18n.constants.content_box_new_deployment_title(),
                    i18n.messages.content_box_new_deployment_body_standalone(),
                    i18n.constants.content_box_new_deployment_link(), NameTokens.DeploymentBrowser));
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("Datasources",
                    i18n.constants.content_box_create_datasource_title(),
                    i18n.messages.content_box_create_datasource_body_standalone(),
                    "Datasources", NameTokens.DataSource));
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("ApplyPath",
                    i18n.constants.content_box_apply_patch_title(),
                    i18n.messages.content_box_apply_patch_body_standalone(),
                    "Patch Management", NameTokens.Patching));
        } else {
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("NewDeployment",
                    i18n.constants.content_box_new_deployment_title(),
                    i18n.messages.content_box_new_deployment_body_domain(),
                    i18n.constants.content_box_new_deployment_link(), NameTokens.Deployments));
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("Datasources",
                    i18n.constants.content_box_create_datasource_title(),
                    i18n.messages.content_box_create_datasource_body_domain(),
                    "Datasources", NameTokens.DataSource));
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("Topology",
                    i18n.constants.content_box_topology_title(),
                    i18n.messages.content_box_topology_body(),
                    i18n.constants.content_box_topology_link(), NameTokens.Topology));
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("CreateServerGroup",
                    i18n.constants.content_box_create_server_group_title(),
                    i18n.messages.content_box_create_server_group_body(),
                    i18n.constants.content_box_create_server_group_link(), NameTokens.ServerGroup));
            contentBox = contentBoxInstance.get();
            list.add(contentBox.using("ApplyPath",
                    i18n.constants.content_box_apply_patch_title(),
                    i18n.messages.content_box_apply_patch_body_domain(),
                    "Patch Management", NameTokens.Patching));
        }
        contentBox = contentBoxInstance.get();
        list.add(contentBox.using("Administration",
                i18n.constants.content_box_role_assignment_title(),
                i18n.messages.content_box_role_assignment_body(),
                i18n.constants.content_box_role_assignment_link(), NameTokens.RoleAssignment));

        return list;
    }

    private List<SidebarSection> setupSidebarSection(ProductInfo.Variant variant) {
        List<SidebarSection> sections = new LinkedList<>();

        if (variant == COMMUNITY) {
            SidebarSection general = new SidebarSection(i18n.constants.sidebar_general_resources());
            general.addLink("http://wildfly.org/", i18n.constants.sidebar_wilfdfly_home_text());
            general.addLink("https://docs.jboss.org/author/display/WFLY8/Documentation",
                    i18n.constants.sidebar_wilfdfly_documentation_text());
            general.addLink("https://docs.jboss.org/author/display/WFLY8/Admin+Guide",
                    i18n.constants.sidebar_admin_guide_text());
            general.addLink("http://wildscribe.github.io/index.html", i18n.constants.sidebar_model_reference_text());
            general.addLink("https://issues.jboss.org/browse/WFLY", i18n.constants.sidebar_wildfly_issues_text());
            general.addLink("http://wildfly.org/news/", i18n.constants.sidebar_latest_news());
            sections.add(general);

            SidebarSection help = new SidebarSection(i18n.constants.sidebar_get_help());
            help.addLink("http://www.jboss.org/jdf/", i18n.constants.sidebar_tutorials_text());
            help.addLink("https://community.jboss.org/en/wildfly?view=discussions",
                    i18n.constants.sidebar_user_forums_text());
            help.addLink("irc://freenode.org/#wildfly", i18n.constants.sidebar_irc_text());
            help.addLink("https://lists.jboss.org/mailman/listinfo/wildfly-dev",
                    i18n.constants.sidebar_developers_mailing_list_text());
            sections.add(help);

        } else if (variant == PRODUCT) {
            SidebarSection general = new SidebarSection(i18n.constants.sidebar_general_resources());
            general.addLink(i18n.constants.sidebar_eap_documentation_link(),
                    i18n.constants.sidebar_eap_documentation_text());
            general.addLink(i18n.constants.sidebar_learn_more_eap_link(),
                    i18n.constants.sidebar_learn_more_eap_text());
            general.addLink(i18n.constants.sidebar_trouble_ticket_link(),
                    i18n.constants.sidebar_trouble_ticket_text());
            general.addLink(i18n.constants.sidebar_training_link(), i18n.constants.sidebar_training_text());
            sections.add(general);

            SidebarSection developer = new SidebarSection(i18n.constants.sidebar_developer_resources());
            developer.addLink(i18n.constants.sidebar_tutorials_link(), i18n.constants.sidebar_tutorials_text());
            developer.addLink(i18n.constants.sidebar_eap_community_link(),
                    i18n.constants.sidebar_eap_community_text());
            sections.add(developer);

            SidebarSection operational = new SidebarSection(i18n.constants.sidebar_operational_resources());
            operational.addLink(i18n.constants.sidebar_eap_configurations_link(),
                    i18n.constants.sidebar_eap_configurations_text());
            operational.addLink(i18n.constants.sidebar_knowledgebase_link(),
                    i18n.constants.sidebar_knowledgebase_text());
            operational
                    .addLink(i18n.constants.sidebar_consulting_link(), i18n.constants.sidebar_consulting_text());
            sections.add(operational);
        }
        return sections;
    }
}
