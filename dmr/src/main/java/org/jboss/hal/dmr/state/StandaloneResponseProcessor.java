package org.jboss.hal.dmr.state;

import java.util.List;
import java.util.Map;

import org.jboss.hal.dmr.ModelNode;
import org.jboss.hal.dmr.Property;

/**
 * @author Heiko Braun
 * @date 1/17/12
 */
public class StandaloneResponseProcessor implements ResponseProcessor {

    private static final String RESPONSE_HEADERS = "response-headers";
    private static final String PROCESS_STATE = "process-state";

    private static final String RESTART_REQUIRED = "restart-required";
    private static final String RELOAD_REQUIRED = "reload-required";
    private static final String STANDLONE_SERVER = "Standlone Server";


    @Override
    public boolean accepts(ModelNode response) {
        return response.hasDefined(RESPONSE_HEADERS);
    }

    @Override
    public void process(ModelNode response, Map<String, ServerState> serverStates) {

        boolean staleModel = parseServerState(response, serverStates);

        // if(!staleModel) reloadState.reset();

    }

    private static boolean parseServerState(ModelNode response, Map<String, ServerState> serverStates) {

        boolean staleModel = false;

        if(response.hasDefined(RESPONSE_HEADERS))
        {
            List<Property> headers = response.get(RESPONSE_HEADERS).asPropertyList();

            for(Property header : headers)
            {
                if(PROCESS_STATE.equals(header.getName()))
                {

                    String headerValue = header.getValue().asString();
                    if(RESTART_REQUIRED.equals(headerValue))
                    {
                        staleModel=true;

                        //reloadState.setRestartRequired(STANDLONE_SERVER, staleModel);

                        ServerState state = new ServerState(STANDLONE_SERVER);
                        state.setRestartRequired(true);
                        serverStates.put(STANDLONE_SERVER, state);

                    }
                    else if(RELOAD_REQUIRED.equals(headerValue))
                    {
                        staleModel=true;

                        ServerState state = new ServerState(STANDLONE_SERVER);
                        state.setRestartRequired(true);
                        serverStates.put(STANDLONE_SERVER, state);

                    }
                }
            }

        }
        return staleModel;
    }
}
