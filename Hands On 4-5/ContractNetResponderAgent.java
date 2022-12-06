
/*
 * * javac -cp lib\jade.jar;lib\CLIPSJNI.jar -d classes src\examples\protocols\*.java
 * java -cp lib\jade.jar;lib\CLIPSJNI.jar;classes jade.Boot -gui -agents r0:examples.protocols.ContractNetResponderAgent;r1:examples.protocols.ContractNetResponderAgent;r2:examples.protocols.ContractNetResponderAgent
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
public class ContractNetResponderAgent extends Agent {
	private Environment clips;

	protected void setup() {
		
        clips = new Environment(); 

		loadKnowledge();

		System.out.println("Agent "+getLocalName()+" waiting for CFP...");
		
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP) );
		//this isnt the dispatchers guy of a parallel responder
		addBehaviour(new ContractNetResponder(this, template) {
			
			@Override
			protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
				ACLMessage propose = cfp.createReply();
				//so my code do the diagnosis from the content of the proposing call that is the symptom
				try{
					clips.build("(defrule diagnosis (disease (name ?n) (symptom $? "+cfp.getContent()+" $?)) => (assert (foundDisease (foundName ?n) ) ))");
					clips.run();
					FactAddressValue fv = clips.findFact("foundDisease");
					String disease = fv.getSlotValue("foundName").toString();
					System.out.println("Agent "+getLocalName()+": Proposing "+disease);
					
					propose.setPerformative(ACLMessage.PROPOSE);
					propose.setContent(String.valueOf(disease));//setting the found disease as the proposed diagnosis
					
				}catch (Exception e) {
					e.printStackTrace();
				  }
				return propose;
				
			}

			@Override
			protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
				System.out.println("Agent "+getLocalName()+": Proposal accepted");
					//int t; variable dummy
					
					ACLMessage inform = accept.createReply();
				 	System.out.println("Agent "+getLocalName()+": Action successfully performed");
					//this give a treatment from the previous diagnosis, getting the diagnosis from the propose content
					 try{
						//clips.build("(defrule treatment (disease (name  "+propose.getContent()+")(treatment $?b )) => (printout t \"treatment \" ?b crlf))");
						clips.build("(defrule treatment ?f <-(disease (name  "+propose.getContent()+")(treatment $?b )) => (assert (foundTreatment (fact-slot-value ?f treatment  ) )))");
						clips.run();
						//t=1;
						FactAddressValue fv = clips.findFact("foundTreatment");
						String treatment = fv.getSlotValue("implied").toString();//implied see docs basic programming
						System.out.println("Agent "+getLocalName()+": Proposing "+treatment);
						inform.setPerformative(ACLMessage.INFORM);
						inform.setContent(String.valueOf(treatment));//setting found treatment to inform patient
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
//metodo para cargar la base de conocimiento
	public void loadKnowledge(){
		System.out.println("loading knowledge base"); 
                try{
                    clips.build("(deftemplate disease(slot name)(multislot symptom)(multislot treatment))");
                    clips.build("(deftemplate foundDisease (slot foundName))");
					
					//clips.build("(deftemplate foundTreatment (multislot foundT))");
                   
                    clips.build("(deffacts diseases"
					+"(disease (name rubeola) (symptom \"febricula\" \"malestar general\" \"linfadenopatias\")"
                    +"(treatment \"reposo\"))"
                    +"(disease (name neumonia) (symptom \"cefalea\" \"hiporexia\" \"dolor abdominal\")"
                    +"(treatment \"paracetamol\"))"
                    +"(disease (name sarampion) (symptom \"rinitis\" \"conjuntivitis\" \"faringitis\" \"tos seca\")" 
                    +"(treatment \"vitamina a\"))"
                    +"(disease (name asma) (symptom \"anorexia\" \"odinofagia\")" 
                    +"(treatment \"no comer comida picante \"))"
                    +"(disease (name edema) (symptom \"esputo con sangre\" \"Dificultad respiratoria\" \"Sibilancias\" \"Sencacion de asfixia\" \"Disminucion de nivel de conciencia\" \"Sudoracion\") "
                    +"(treatment \"oxigenoterapia\" \"Ventilacion mecanica\" \"Diuretico\" \"Inotropos\")))"
					);
                    clips.reset();
                } catch (Exception e) {
                  e.printStackTrace();
                }
	}
}

