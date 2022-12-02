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

public class RunPersons extends Agent {
	private Environment clips;

	public void loadKnowledge(){
		System.out.println("**** Program to Load and Run Persons.clp ****"); 
                try{
                	// Cargamos la base de conocimiento
                    clips.build("(deftemplate person(slot name)(multislot gender)(slot age (type INTEGER))(slot partner))");
                    clips.build("(deffacts partnership(person (name Fred) (gender male) (age 26) (partner Susan)) (person (name Susan) (gender female) (age 24) (partner Fred))(person (name Andy) (gender male) (age 25) (partner Sara))(person (name Alice) (gender female) (age 23) (partner Bob)))");
                    // Se cargan las reglas 
                    clips.build("(defrule my-rule1 (person (name ?n)) => (printout t ?n  crlf ))");
                    clips.build("(defrule my-rule2 (person (age ?a)) => (printout t ?a  crlf ))");
                    clips.build("(defrule my-rule3 (person (gender female) (name ?x)) => (printout t ?x " is female" crlf ))");
                    clips.build("(defrule my-rule4 (person (partner ?p) (name ?n)) => (printout t ?p " is " ?n "'s partner" crlf ))");
                    clips.build("(defrule my-rule5 (person (gender female)) => (printout t ?p " is female" crlf))");
                    clips.reset();
                    // Lo corremos
                    clips.run();
                } catch (Exception e) {
                  e.printStackTrace();
                }
	}
}