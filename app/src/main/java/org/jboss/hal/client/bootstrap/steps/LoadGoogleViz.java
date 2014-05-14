package org.jboss.hal.client.bootstrap.steps;

import javax.enterprise.context.Dependent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import org.jboss.gwt.flow.Control;
import org.jboss.gwt.flow.Function;
import org.jboss.hal.client.bootstrap.BootstrapContext;

/**
 * @author Heiko Braun
 * @date 12/7/11
 */
@Dependent
public class LoadGoogleViz implements Function<BootstrapContext> {

    @Override
    public void execute(Control<BootstrapContext> control) {

        // GWT vis is only used by MBUI tools. These are available in dev mode only
        if (!GWT.isScript()) {
            VisualizationUtils.loadVisualizationApi(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Loaded Google Vizualization API");
                        }
                    }, LineChart.PACKAGE, OrgChart.PACKAGE
            );
        }
        // viz can be loaded in background ...
        control.proceed();
    }
}
