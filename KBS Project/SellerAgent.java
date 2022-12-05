package df.agents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\
import net.sf.clipsrules.jni.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.io.FileWriter;


public class SellerAgent extends Agent {
	///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\
	Environment clips;
	String facts = "src/clips/";
	String rules = "src/clips/";

	protected void setup() {
		//Create new enviroment clips
		try{
			clips = new Environment();
		}catch(Exception e) 
		{
			System.out.println("ERROR CREATING NEW ENVIROMENT");
		}

		//read name files of facts and rules
		Object[] args = getArguments();
		facts=facts+(String) args[0]+".clp";
		rules=rules+(String) args[1]+".clp";
		//Charge files
		setData();

		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("smartphones-computadoras");
		sd.setName("JADE-product-trading");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Add the behaviour serving queries from buyer agents
		addBehaviour(new OfferRequestsServer());

		// Add the behaviour serving purchase orders from buyer agents
		addBehaviour(new PurchaseOrdersServer());
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		try{
			clips.clear();
		}
		catch(Exception e){}
		System.out.println("Seller-agent "+getAID().getName()+" terminating.");
	}

	/**
     This is invoked by the GUI when the user adds a new book for sale
	 */
	 ///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\
	public void updateCatalogue(final int idn) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				//catalogue.put(title, new Integer(price));
				//System.out.println(title+" inserted into catalogue. Price = "+price);
				try
		{
			List<FactAddressValue> techniqueFacts = clips.findAllFacts("product");
            int tNum = techniqueFacts.size();
            String messageStr="";
            String factsbk = "";
            tNum=tNum+1000;
            for (int i = 1001; i <= tNum; i++)
            {   int j=0;
                FactAddressValue fv = clips.findFact("?f","product","(eq ?f:id " + i + ")");
                //if (fv == null) continue;
                int id = ((NumberValue) fv.getSlotValue("id")).intValue();
                if(id==idn)
                {
                    int newStock = ((NumberValue) fv.getSlotValue("stock")).intValue()-1;
                    factsbk = factsbk+"(product (name "+((LexemeValue) fv.getSlotValue("name")).getValue()+")(id "+((NumberValue) fv.getSlotValue("id")).intValue()+") (marca "+((LexemeValue) fv.getSlotValue("marca")).getValue()+") (price "+((NumberValue) fv.getSlotValue("price")).intValue()+") (stock "+newStock+"))";
                }
                else
                {
                    factsbk = factsbk+"(product (name "+((LexemeValue) fv.getSlotValue("name")).getValue()+")(id "+((NumberValue) fv.getSlotValue("id")).intValue()+") (marca "+((LexemeValue) fv.getSlotValue("marca")).getValue()+") (price "+((NumberValue) fv.getSlotValue("price")).intValue()+") (stock "+((NumberValue) fv.getSlotValue("stock")).intValue()+"))";
                }
                j++;
            }
			///////////////////////////////////////////////////////////////////////////////////////////////////////
			List<FactAddressValue> bankFacts = clips.findAllFacts("cards");
            tNum = bankFacts.size();
			//System.out.println("(((Tenemos size de "+tNum);
            messageStr="";
            //String[] factsbn = new String[tNum];
			String factsbn="";
            tNum=tNum;
            for (int i = 1; i <= tNum; i++)
            {   int j=0;
                FactAddressValue fv = clips.findFact("?f","cards","(eq ?f:id " + i + ")");
                //if (fv == null) continue;
                //int id = ((NumberValue) fv.getSlotValue("id")).intValue();
                //factsbn[j] = "(cards (bank "+((LexemeValue) fv.getSlotValue("bank")).getValue()+")(id "+((NumberValue) fv.getSlotValue("id")).intValue()+"))\n";

				factsbn = factsbn+"(cards (bank "+((LexemeValue) fv.getSlotValue("bank")).getValue()+")(id "+((NumberValue) fv.getSlotValue("id")).intValue()+"))";

				j++;
			}
			//System.out.println("proded to dell");
			dell();
			//System.out.println("dell");
			saveData(factsbk,factsbn);
			//System.out.println("save");
			setData();
			//System.out.println("chargedddd");
		}
		catch(Exception e)
		{
			
		}
			}
		} );
	}

	/**
	   Inner class OfferRequestsServer.
	   This is the behaviour used by Book-seller agents to serve incoming requests 
	   for offer from buyer agents.
	   If the requested book is in the local catalogue the seller agent replies 
	   with a PROPOSE message specifying the price. Otherwise a REFUSE message is
	   sent back.
	 */
	private class OfferRequestsServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String product = msg.getContent();
				ACLMessage reply = msg.createReply();

				String[] proposal = tell(product);
				if(proposal[0]!="error")
				{
					System.out.println("Seller "+getLocalName()+": Proposing "+product+" A tan solo "+proposal[0]);
					reply.setPerformative(ACLMessage.PROPOSE);
					String proposals=proposal[0]+"+"+proposal[1]+"+"+proposal[2];
					reply.setContent(proposals);
				}
				else {
					// The requested book is NOT available for sale.
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class OfferRequestsServer

	/**
	   Inner class PurchaseOrdersServer.
	   This is the behaviour used by Book-seller agents to serve incoming 
	   offer acceptances (i.e. purchase orders) from buyer agents.
	   The seller agent removes the purchased book from its catalogue 
	   and replies with an INFORM message to notify the buyer that the
	   purchase has been sucesfully completed.
	 */
	private class PurchaseOrdersServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// ACCEPT_PROPOSAL Message received. Process it
				String product = msg.getContent();
				ACLMessage reply = msg.createReply();

				String consult[] = ask(product).split("\\+");
				int stock = Integer.parseInt(consult[4]);
				int id = Integer.parseInt(consult[1]);
				String promo = consult[5];
				if (stock>0) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent(promo);
					System.out.println(product+" sold to agent "+msg.getSender().getName());
					updateCatalogue(id);
				}
				else {
					// The requested book has been sold to another buyer in the meanwhile .
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class OfferRequestsServer
	private void setData()
	{
		try{
			clips.clear();
			clips.load(facts);
			clips.load(rules);
			clips.reset();
			System.out.println("**************"+getLocalName()+"***Facts charged*****************");
			System.out.println("*****************************************************************");
			clips.eval("(facts)");
			System.out.println("*****************************************************************");
			System.out.println("*****************************************************************");
		}catch(Exception e) 
		{
			System.out.println("ERROR CHARGIN FROM FILES");
		}
		
	}

	private void saveData(String addPr, String addBn)
	{

		//clips.eval("(save "+facts+")");
		try
        {
            String filePath = facts;
            FileWriter fw = new FileWriter(filePath, true);    
            //String lineToAppend = "\r\nThe quick brown fox jumps over the lazy dog";  
			String addPr2=addPr;
			String addBn2=addBn;
			/*for(int i=0; i<addPr.length; i++)
				addPr2=addPr2+addPr[i];*/
			/*for(int i=0; i<addBn.length; i++)
				addBn2=addBn2+addBn[i];*/
			String factsTemplate = "(deftemplate product\n   (slot name)\n  (slot id)\n  (slot marca)\n  (slot price)\n  (slot stock))\n\n(deftemplate cards\n   (slot bank) (slot id))\n\n(deftemplate msis\n (slot id)\n (slot bank)\n   (slot msi))\n\n(deftemplate deal\n  (slot id)\n (slot concept))\n";
			String defFactTemplate = "(deffacts products\n"+addPr2+"\n\n)";
			String bankTemplate  = "(deffacts card\n"+addBn2+"\n\n)";  

			/*System.out.println("=== FACTSTEMPLATE ==="+factsTemplate);
			System.out.println("=== DEFACTS==="+defFactTemplate);
			System.out.println("=== BANKS==="+bankTemplate);*/
			fw.write(factsTemplate);
			fw.write(defFactTemplate);
			fw.write(bankTemplate);
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}

	private void dell()
	{	
		try
		{
					File archivo = new File(facts);
            if(archivo.delete())
            {
                System.out.println("eliminado");
               // clips.eval("(save src/clips/amazon2-facts.clp)");
				clips.eval("(clear)");
            }
            else
            {
                System.out.println("No se pudo eliminar");
            }
		
		}
		catch(Exception e)
		{
			
		}

		//int newStock = Integer.parseInt(product[4])-1;
		//clips.reset();

		/*final MultifieldValue backup = (MultifieldValue) this.clips.eval("(find-all-facts ((?f product)) (name ?g))");

		clips.eval("(defrule remove
   						?f <- (product (name ?g))
   						=>
   						(retract ?f)
					)");
		for (int i = 0; i < backup.size(); i++) {
			final PrimitiveValue bv1 = pv1.get(i);
			final PrimitiveValue bv2 = pv2.get(j);
			if (backup.name==product) {
				
			}
		}*/

	}

	private String[] tell(String art) {
		String[] proposal;
		try
				{
					clips.run();
					//String proposal;
					System.out.println("Searching.....");
					//clips.eval("(facts)");
					FactAddressValue fv;
					fv = (FactAddressValue)((MultifieldValue) clips.eval("(find-fact ((?f product)) (eq ?f:name "+art+"))")).get(0);
					System.out.println("Encontramos lo que buscamos");
					int precio = ((NumberValue) fv.getSlotValue("price")).intValue();
					int id = ((NumberValue) fv.getSlotValue("id")).intValue();
					String bank = "na";
					int msi = 1;
					try
					{
						FactAddressValue fv2 = (FactAddressValue)((MultifieldValue) clips.eval("(find-fact ((?f msis)) (eq ?f:id "+id+"))")).get(0);
						bank=((LexemeValue) fv2.getSlotValue("bank")).getValue();
						msi=((NumberValue) fv2.getSlotValue("msi")).intValue();
					}
					catch(Exception e){}
					//proposal = String.valueOf(precio)+"Z"+String.valueOf(msi)+"Z"+bank;
					proposal = new String[]{String.valueOf(precio),String.valueOf(msi),bank};
					return proposal;
				}
				catch(Exception e)
				{
					proposal = new String[]{"error"};
					return proposal;
				}
	}

	private String ask(String c) {
		try
				{
					clips.run();
					String result,promo="na";
					System.out.println("Searching.....");
					FactAddressValue fv;
					fv = (FactAddressValue)((MultifieldValue) clips.eval("(find-fact ((?f product)) (eq ?f:name "+c+"))")).get(0);
					String name = ((LexemeValue) fv.getSlotValue("name")).getValue();
					int id = ((NumberValue) fv.getSlotValue("id")).intValue();
					String marca = ((LexemeValue) fv.getSlotValue("marca")).getValue();
					int precio = ((NumberValue) fv.getSlotValue("price")).intValue();
					int stock = ((NumberValue) fv.getSlotValue("stock")).intValue();
					try
					{
						FactAddressValue fv2 = (FactAddressValue)((MultifieldValue) clips.eval("(find-fact ((?f deal)) (eq ?f:id "+id+"))")).get(0);
						promo=" Y tiene la siguiente promocion Vigente: "+((LexemeValue) fv2.getSlotValue("concept")).getValue();
					}
					catch(Exception e){}
					result=name+"+"+id+"+"+marca+"+"+precio+"+"+stock+"+"+promo;
					return result;
				}
				catch(Exception e){return "error";}
	}
}
