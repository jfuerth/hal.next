package org.jboss.hal.dmr.client.shared.state;

import java.util.HashMap;
import java.util.Map;

import org.jboss.hal.dmr.client.ModelNode;
import org.jboss.hal.dmr.client.notify.Notifications;

/**
 * @author Heiko Braun
 * @date 8/22/13
 */
public class ResponseProcessorDelegate {

    static ResponseProcessor[] processors = {
            new DomainResponseProcessor(),
            new StandaloneResponseProcessor()
    };


    public ResponseProcessorDelegate() {

    }

    public void process(ModelNode response) {

        Map<String, ServerState> serverStates = new HashMap<String, ServerState>();

        for(ResponseProcessor proc : processors)
        {
            if(proc.accepts(response))
            {
                proc.process(response, serverStates);
                break;
            }
        }

        if(serverStates.size()>0)
            Notifications.fireReloadNotification(new ReloadNotification(serverStates));

    }
}
