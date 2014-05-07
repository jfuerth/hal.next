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
package org.jboss.hal.client.header;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.hal.client.sample.HelloWorldScreen;
import org.jboss.hal.client.sample.MoodScreen;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.widgets.menu.WorkbenchMenuBarPresenter;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;

public class Header extends Composite implements org.uberfire.client.workbench.Header {

    @Inject
    private WorkbenchMenuBarPresenter menuBarPresenter;

    @Inject
    private PlaceManager placeManager;

    @Override
    public Widget asWidget() {
        return menuBarPresenter.getView().asWidget();
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

    @PostConstruct
    private void initMenus() {
        Menus menus =
                MenuFactory.newTopLevelMenu("Screens")
                        .menus()
                        .menu("Hello Screen").respondsWith(makeGoToPlaceCommand(HelloWorldScreen.class)).endMenu()
                        .menu("Mood Screen").respondsWith(makeGoToPlaceCommand(MoodScreen.class)).endMenu()
                        .endMenus()
                        .endMenu()
                        .newTopLevelMenu("Other")
                        .menus()
                        .menu("Alert Box").respondsWith(new Command() {
                    @Override
                    public void execute() {
                        Window.alert("Hi. I'm an Alert Box.");
                    }
                }).endMenu()
                        .endMenus()
                        .endMenu()
                        .build();

        menuBarPresenter.addMenus(menus);
    }

    private Command makeGoToPlaceCommand(final Class<?> placeClass) {
        return new Command() {
            @Override
            public void execute() {
                placeManager.goTo(placeClass.getName());
            }
        };
    }
}