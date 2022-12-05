/**
 * javac -cp lib\jade.jar;lib\CLIPSJNI.jar -d classes src\examples\protocols\*.java
 * java -cp lib\jade.jar;lib\CLIPSJNI.jar;classes jade.Boot -gui -agents r0:examples.protocols.ContractNetResponderAgent;r1:examples.protocols.ContractNetResponderAgent;r2:examples.protocols.ContractNetResponderAgent
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 * 
 * GNU Lesser General Public License
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */
package examples.protocols;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;


import net.sf.clipsrules.jni.*;

/**
   This example shows how to implement the responder role in 
   a FIPA-contract-net interaction protocol. In this case in particular 
   we use a 

   <code>ContractNetResponder</code> to participate into a negotiation 
   
   where an initiator needs to assign a task to an agent among a set of candidates.
   @author Giovanni Caire - TILAB
 */
public class ContractNetResponderAgent1 extends Agent {
	private Environment clips;

	protected void setup() {
		
        clips = new Environment(); 

		loadKnowledge();

		System.out.println("Agent "+getLocalName()+" waiting for CFP...");
		
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP) );
		addBehaviour(new ContractNetResponder(this, template) {
			
			@Override
			protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
				ACLMessage propose = cfp.createReply();
				//so my code do the diagnosis from the content of the proposing call that is the symptom
				try{
					clips.build("(defrule diagnosis (enfermedad (nombre ?n) (symptom $? "+cfp.getContent()+" $?)) => (assert (foundDisease (foundName ?n) ) ))");
					clips.run();
					FactAddressValue fv = clips.findFact("foundDisease");
					String enfermedad = fv.getSlotValue("foundName").toString();
					System.out.println("Agent "+getLocalName()+": Proposing "+enfermedad);
					
					propose.setPerformative(ACLMessage.PROPOSE);
					propose.setContent(String.valueOf(enfermedad));
					
				}catch (Exception e) {
					e.printStackTrace();
				  }
				return propose;
					

			}

			@Override
			protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
				System.out.println("Agent "+getLocalName()+": Proposal accepted");
					ACLMessage inform = accept.createReply();
				 	System.out.println("Agent "+getLocalName()+": Action successfully performed");
					 try{
						clips.build("(defrule tratamiento ?f <-(enfermedad (name  "+propose.getContent()+")(tratamiento $?b )) => (assert (foundtratamiento (fact-slot-value ?f tratamiento  ) )))");
						clips.run();
						FactAddressValue fv = clips.findFact("foundtratamiento");
						String tratamiento = fv.getSlotValue("implied").toString();
						System.out.println("Agent "+getLocalName()+": Proposing "+tratamiento);
						inform.setPerformative(ACLMessage.INFORM);
						inform.setContent(String.valueOf(tratamiento));
					}catch (Exception e) {
						e.printStackTrace();
				 	}

					return inform;
			}
			protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
				System.out.println("Agent "+getLocalName()+": Proposal rejected");
			}
		} );
	}
	public void loadKnowledge(){
		System.out.println("loading knowledge base"); 
                try{
                    clips.build("(deftemplate enfermedad(slot nombre)(multislot sintomas)(multislot tratamiento))");
                    clips.build("(deftemplate foundDisease (slot foundName))");
                    clips.build("(deffacts enfermedades"
                    +"(enfermedad (nombre sarampion) (sintomas \"rinitis\" \"conjuntivitis\" \"faringitis\" \"tos seca\")" 
                    +"(tratamiento \"vitamina a\"))"
                    clips.reset();
                } catch (Exception e) {
                  e.printStackTrace();
                }
	}
}