package org.jboss.hal.dmr.state;

import java.util.Map;

import org.jboss.hal.dmr.ModelNode;

/**
 * @author Heiko Braun
 * @date 1/17/12
 */
public interface ResponseProcessor {

    boolean accepts(ModelNode response);
    void process(ModelNode response, Map<String, ServerState> serverStates);
}
