
/**
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
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import jade.domain.FIPANames;

import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;

import java.util.Scanner;

/**
   This example shows how to implement the initiator role in 
   a FIPA-contract-net interaction protocol. In this case in particular 
   we use a <code>ContractNetInitiator</code>  
   to assign a dummy task to the agent that provides the best offer
   among a set of agents (whose local
   names must be specified as arguments).
   @author Giovanni Caire - TILAB
 */
public class ContractNetInitiatorAgent extends Agent {
	private int nResponders;
	Scanner console = new Scanner(System.in);
	String name;

	protected void setup() { 
  	Object[] args = getArguments();
  	if (args != null && args.length > 0) {
  		nResponders = args.length;
  		System.out.println("Trying to delegate diagnosis to one out of "+nResponders+" responders.");

  		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
  		for (int i = 0; i < args.length; ++i) {
  			msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
  		}
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			System.out.println("Ingresar sintoma: ");
			name = console.nextLine();
			msg.setContent("\""+name+"\"")
			
			addBehaviour(new ContractNetInitiator(this, msg) {
				
				protected void handlePropose(ACLMessage propose, Vector v) {
					System.out.println("Agent "+propose.getSender().getName()+" proposed "+propose.getContent());
				}
				
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent "+refuse.getSender().getName()+" refused");
				}
				
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						System.out.println("Responder does not exist");
					}
					else {
						System.out.println("Agent "+failure.getSender().getName()+" failed");
					}
					nResponders--;
				}
				
				protected void handleAllResponses(Vector responses, Vector acceptances) {
					if (responses.size() < nResponders) {
						System.out.println("Timeout expired: missing "+(nResponders - responses.size())+" responses");
					}
					String bestProposal = "";
					AID bestProposer = null;
					ACLMessage accept = null;
					Enumeration e = responses.elements();
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);
							String proposal = msg.getContent();
							if (proposal != bestProposal) {
								bestProposal = proposal;
								bestProposer = msg.getSender();
								accept = reply;
							}
						}
					}
					if (accept != null) {
						System.out.println("Se acepto la proposicion "+bestProposal+" del responder "+bestProposer.getName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}						
				}
				protected void handleInform(ACLMessage inform) {
					System.out.println("El agente "+inform.getSender().getName()+" respondio correctamente"+inform.getContent());
					
					System.out.println("El tratamiento para usted es "+inform.getContent());
				}
			} );
  	}
  	else {
  		System.out.println("No se puedo conectar :c");
  	}
  } 
}

