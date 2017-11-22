package util;

import org.jbpm.workflow.instance.node.RuleSetNodeInstance;
import org.kie.api.KieServices; 
import org.kie.api.runtime.KieContainer; 
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.event.rule.*;
import org.kie.api.event.process.*;

public class KnowledgeSessionHelper {
	public static KieContainer createRuleBase(){
		KieServices ks = KieServices.Factory.get(); 
		KieContainer kieContainer = ks.getKieClasspathContainer(); 
		return kieContainer; 
	}

	public static StatelessKieSession getStatelessKnowledgeSesion(KieContainer kieContainer, String sessionName)
	{
		StatelessKieSession kSession = kieContainer.newStatelessKieSession(sessionName); 

		return kSession; 
	}

	public static KieSession getStatefullKnowledgeSesion(KieContainer kieContainer, String sessionName)
	{
		KieSession kSession = kieContainer.newKieSession(sessionName); 

		return kSession; 
	}
	public static KieSession getStatefulKnowledgeSessionWithCallback(KieContainer kieContainer, String sessionName)
	{
		KieSession session = getStatefullKnowledgeSesion(kieContainer, sessionName);
		session.addEventListener(new RuleRuntimeEventListener() 
		{
			public void objectInserted(ObjectInsertedEvent event) {
				System.out.println("Object inserted \n"
						+ event.getObject().toString());
			}
			public void objectUpdated(ObjectUpdatedEvent event) {
				System.out.println("Object was updated \n"
						+ "new Content \n" + event.getObject().toString());
			}
			public void objectDeleted(ObjectDeletedEvent event) {
				System.out.println("Object retracted \n"
						+ event.getOldObject().toString());
			}
		}); 

		session.addEventListener(new AgendaEventListener() {
			public void matchCreated(MatchCreatedEvent event) {
				//System.out.println("The rule "
				//	+ event.getMatch().getRule().getName()
				//+ " can be fired in agenda");
			}
			public void matchCancelled(MatchCancelledEvent event) {
				//System.out.println("The rule "
				//	+ event.getMatch().getRule().getName()
				//+ " cannot b in agenda");
			}
			public void beforeMatchFired(BeforeMatchFiredEvent event) {
				//System.out.println("The rule "
				//	+ event.getMatch().getRule().getName()
				//+ " will be fired");
			}
			public void afterMatchFired(AfterMatchFiredEvent event) {
				System.out.println("The rule "
						+ event.getMatch().getRule().getName()
						+ " has been fired");
			}
			public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
			}
			public void agendaGroupPushed(AgendaGroupPushedEvent event) {
			}			
			public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {
				// TODO Auto-generated method stub

			}
			public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {
				// TODO Auto-generated method stub

			}
			public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {
				// TODO Auto-generated method stub

			}
			public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		return session; 
	}

	public static KieSession getStatefulKnowledgeSessionForJBPM(
			KieContainer kieContainer, String sessionName) {
		KieSession session = getStatefulKnowledgeSessionWithCallback(kieContainer,sessionName);
		session.addEventListener(new ProcessEventListener() {

			public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void beforeProcessStarted(ProcessStartedEvent arg0) {
				System.out.println("Process Name "+arg0.getProcessInstance().getProcessName()+" has been started");


			}

			public void beforeProcessCompleted(ProcessCompletedEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
				if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
					System.out.println("Node Name "+ arg0.getNodeInstance().getNodeName()+" has been left");        
				}

			}

			public void afterVariableChanged(ProcessVariableChangedEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void afterProcessStarted(ProcessStartedEvent arg0) {

			}

			public void afterProcessCompleted(ProcessCompletedEvent arg0) {
				System.out.println("Process Name "+arg0.getProcessInstance().getProcessName()+" has stopped");


			}

			public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
				if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
					System.out.println("Node Name "+ arg0.getNodeInstance().getNodeName()+" has been entered");        
				}
			}

			public void afterNodeLeft(ProcessNodeLeftEvent arg0) {
			}
		});
		return session;
	}
}
