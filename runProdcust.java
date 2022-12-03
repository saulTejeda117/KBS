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
                    clips.build("(deftemplate product (slot part-number) (slot name) (slot category) (slot price))");
                    clips.build("(deffacts products (product (name "USB Memory") (category storage) (part-number 1234) (price 9.99)) (product (name Amplifier) (category electronics) (part-number 2341) (price 399.99)) (product (name Speakers) (category electronics) (part-number 23241) (price 19.99)) (product (name "iPhone 7") (category smartphone) (part-number 3412) (price 99.99)) (product (name "Samsung Edge 7") (category smartphone) (part-number 34412) (price 88.99)))");
                    
                    clips.build("(deftemplate customer (slot customer-id) (multislot name) (multislot address))")
                    clips.build("(deffacts customers (customer (customer-id 101) (name joe smith) (address bla bla bla)) (customer (customer-id 102) (name mary) (address bla bla bla))(customer (customer-id 103) (name bob) (address bla bla bla)))")

                    // Se cargan las reglas 
                    clips.build("(defrule my-rule1 (person (name ?n)) => (printout t ?n  crlf ))");
                    clips.build("(defrule my-rule2 (person (age ?a)) => (printout t ?a  crlf ))");
                    clips.build("(defrule my-rule3 (person (gender female) (name ?x)) => (printout t ?x " is female" crlf ))");
                    clips.build("(defrule my-rule4 (person (partner ?p) (name ?n)) => (printout t ?p " is " ?n "'s partner" crlf ))");
                    clips.build("(defrule my-rule5 (person (gender female)) => (printout t ?p " is female" crlf))");
                    clips.build("(defrule MAIN::my-rule11 (customer (name ?n)) => (printout t "Customer name found:" ?n  crlf ))");
                    clips.build("(defrule MAIN::my-rule12 ?c <- (customer (customer-id 101)) => (printout t "customer-id 101 belongs to:: " ?c.name " with address:: " ?c.address crlf))");
                    clips.build("(defrule MAIN::my-rule13 (product (category electronics) (name ?name)) => (printout t "Electronic product found: " ?name crlf))");
                    clips.build("(defrule MAIN::my-rule14 (not (product (category smartphone) {price < 50} (name ?n))) => (printout t "no smartphones cheaper than 50" crlf ))");
                    clips.build("(defrule MAIN::my-rule15 (product (category smartphone) {price < 100} (name ?n)) => (printout t ?n " is cheaper than 100 dlls" crlf ))");

                    // Lo corremos
                    clips.reset();
                    clips.run();
                } catch (Exception e) {
                  e.printStackTrace();
                }
	}
}
