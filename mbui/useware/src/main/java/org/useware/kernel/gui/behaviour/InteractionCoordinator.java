package org.useware.kernel.gui.behaviour;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.useware.kernel.gui.behaviour.common.ActivationProcedure;
import org.useware.kernel.gui.behaviour.common.CommonQNames;
import org.useware.kernel.gui.behaviour.common.NavigationProcedure;
import org.useware.kernel.gui.behaviour.common.SelectStatementProcedure;
import org.useware.kernel.model.Dialog;
import org.useware.kernel.model.behaviour.Resource;
import org.useware.kernel.model.behaviour.ResourceType;
import org.useware.kernel.model.structure.InteractionUnit;
import org.useware.kernel.model.structure.QName;

/**
 * A coordinator acts as the middleman between a framework (i.e. GWTP), the structure (interface model)
 * and the behaviour (interaction model). <p/>
 *
 * It wires the {@link org.useware.kernel.model.behaviour.Resource} input/output of interaction units to certain behaviour and vice versa.
 * It's available at reification time to interaction units and provides an API to register {@link Procedure}'s.
 *
 *
 * @author Heiko Braun
 * @date 11/15/12
 */
public class InteractionCoordinator implements KernelContract,
        InteractionEvent.InteractionHandler, NavigationEvent.NavigationHandler,
        StatementEvent.StatementHandler, ProcedureExecution, ProcedureRuntimeAPI, StateCoordination {

    final static SystemEvent RESET = new SystemEvent(CommonQNames.RESET_ID);

    // a bus scoped to this coordinator and the associated models
    private EventBus bus;
    private BehaviourMap<Procedure> procedures = new BehaviourMap<Procedure>();
    private Dialog dialog;
    private DialogState dialogState;
    private final NavigationDelegate navigationDelegate;

    @Inject
    public InteractionCoordinator(Dialog dialog, StatementContext parentContext, NavigationDelegate navigationDelegate) {
        this.dialog = dialog;
        this.bus = new SimpleEventBus();
        this.navigationDelegate = navigationDelegate;
        this.dialogState = new DialogState(dialog, parentContext, this);

        // coordinator handles all events except presentation & system events
        bus.addHandler(InteractionEvent.TYPE, this);
        bus.addHandler(NavigationEvent.TYPE, this);
        bus.addHandler(StatementEvent.TYPE, this);

        // global procedures

        addProcedure(new SelectStatementProcedure(this));
        addProcedure(new ActivationProcedure(this));
        addProcedure(new NavigationProcedure(this));

    }

    public DialogState getDialogState() {
        return dialogState;
    }

    public EventBus getLocalBus()
    {
        return this.bus;
    }

    @Override
    public boolean isActive(QName interactionUnit) {
        return dialogState.isWithinActiveScope(interactionUnit);
    }

    @Override
    public boolean canBeActivated(QName interactionUnit) {
        return dialogState.canBeActivated(interactionUnit);
    }

    /**
     * Procedures of same kind (same ID) can coexist if they can be further distinguished.<br/>
     * A typical example stock procedures (save, load, etc) that are registered for different origins (interaction units).
     *
     * @param procedure
     */
    @Override
    public void addProcedure(Procedure procedure)
    {

        // TODO: verification of behaviour model
        // known behaviour -> accept
        // unknown behaviour -> issue warning

        // provide context
        procedure.setCoordinator(this);
        procedure.setStatementScope(dialogState);
        procedure.setRuntimeAPI(this);

        procedures.add(procedure);
    }

    @Override
    public Map<QName, Set<Procedure>> listProcedures() {
        return procedures.list();
    }

    /**
     * Event delegation. Uses the dialog ID as source.
     *
     * @param event
     */
    public void fireEvent(final Event<?> event)
    {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                bus.fireEventFromSource(event, dialog.getInterfaceModel().getId());
            }
        });

    }

    @Override
    public void activateUnit(QName unitId) {
        SystemEvent activationEvent = new SystemEvent(CommonQNames.ACTIVATION_ID);
        activationEvent.setPayload(unitId);
        fireEvent(activationEvent);

        // activate the units scope
        dialogState.activateScope(unitId);
    }

    @Override
    public void activate() {
        dialogState.reset();

        // navigate to root element
        fireEvent(new NavigationEvent( CommonQNames.NAVIGATION_ID, dialog.getInterfaceModel().getId()));
    }

    @Override
    public void reset() {
        bus.fireEvent(RESET);
    }

    @Override
    public void passivate() {

    }

    //  ----- Event handling ------

    @Override
    public boolean accepts(InteractionEvent event) {
        return true;
    }

    /**
     * Find the corresponding procedures and execute it.
     *
     * @param event
     */
    @Override
    public void onInteractionEvent(final InteractionEvent event) {
        QName id = event.getId();
        QName source = (QName)event.getSource();

        final Set<Procedure> collection = procedures.get(id);
        Procedure execution = null;

        if(collection!=null)
        {
            for(Procedure consumer : collection) {

                // TODO: This isn't optimal (creation of new resource with every comparison)
                Resource<ResourceType> resource = new Resource<ResourceType>(id, ResourceType.Interaction);
                resource.setSource(source);

                boolean justified = consumer.getJustification() == null
                        || source.equals(consumer.getJustification());

                if(consumer.doesConsume(resource) && justified)
                {
                    execution = consumer;
                    break;
                }
            }
        }

        if(null==execution)
        {
            Window.alert("No procedure for " + event);
            Log.warn("No procedure for " + event);
        }
        else if(execution.getPrecondition().isMet(getStatementContext(source)))   // guarded
        {
            try {
                execution.getCommand().execute(InteractionCoordinator.this.dialog, event.getPayload());
            } catch (Throwable e) {
                Log.error("Failed to execute procedure "+execution, e);
            }
        }

    }

    /**
     * Resolve the statement context for an interaction unit
     * @param interactionUnitId
     * @return
     */
    private StatementContext getStatementContext(QName interactionUnitId) {
        return dialogState.getContext(interactionUnitId);
    }

    @Override
    public boolean accepts(NavigationEvent event) {
        return true;
    }

    /**
     * Find and activate another IU.
     * Can delegate to another context (i.e. gwtp placemanager) or handle it internally (same dialog)
     *
     * @param event
     */
    @Override
    public void onNavigationEvent(NavigationEvent event) {

        QName source = (QName)event.getSource();
        QName target = event.getTarget();

        //System.out.println("Navigate to " + target);

        InteractionUnit targetUnit = dialog.findUnit(target);
        if(targetUnit!=null)  // local to dialog
        {
            Procedure activateProcedure = procedures.getSingle(CommonQNames.ACTIVATION_ID);
            activateProcedure.getCommand().execute(dialog, target);
        }
        else // absolute, external
        {
            navigationDelegate.onNavigation(dialog.getId(), target); // TODO: dialog || unit as source?
        }


        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                InteractionCoordinator.this.reset();
            }
        });

    }


    @Override
    public boolean accepts(StatementEvent event) {
        return true; // all statement are processed by the coordinator
    }

    @Override
    public void onStatementEvent(StatementEvent event) {

        Log.debug("StatementEvent " + event.getKey() + "=" + event.getValue());

        Procedure stmtProcedure = procedures.getSingle(CommonQNames.SELECT_ID);
        stmtProcedure.getCommand().execute(dialog, event);

        // TODO: Should this reset() ?

    }

    @Override
    public void setStatement(QName sourceId, String key, String value) {

        dialogState.flushChildScopes(sourceId);
        dialogState.setStatement(sourceId, key, value);
    }

    @Override
    public void clearStatement(QName sourceId, String key) {
        dialogState.flushChildScopes(sourceId);
        dialogState.clearStatement(sourceId, key);
    }
}
