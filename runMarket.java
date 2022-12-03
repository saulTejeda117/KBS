public class RunProdcust extends Agent {
	private Environment clips;

	public void loadKnowledge(){
		System.out.println("**** Program to Load and Run RunProdcust.clp ****"); 
    try{
        clips.build("(deftemplate customer (slot customer-id) (multislot name) (multislot address)(slot phone))");
        clips.build("(deftemplate product (slot part-number) (slot name) (slot category) (slot price))");
        clips.build("(deftemplate order (slot order-number) (slot customer-id))");
        clips.build("(deftemplate line-item (slot order-number) (slot part-number) (slot customer-id)(slot quantity (default 1)))");

        clips.build("(deffacts products (product (name USBMem) (category storage) (part-number 1234) (price 199.99)) (product (name Amplifier) (category electronics) (part-number 2341) (price 399.99)) (product (name "Rubber duck") (category mechanics) (part-number 3412) (price 99.99)))");
        clips.build("(deffacts customers (customer (customer-id 101) (name joe) (address bla bla bla) (phone 3313073905)) (customer (customer-id 102) (name mary) (address bla bla bla) (phone 333222345))(customer (customer-id 103) (name bob) (address bla bla bla) (phone 331567890)) )");
        clips.build("(deffacts orders (order (order-number 300) (customer-id 102)) (order (order-number 301) (customer-id 103)))");
        clips.build("(deffacts items-list (line-item (order-number 300) (customer-id 102) (part-number 1234)) (line-item (order-number 301) (customer-id 103) (part-number 2341) (quantity 10)))");

        // Se cargan las reglas 
        clips.build("(defrule cust-not-buying (customer (customer-id ?id) (name ?name)) (not (order (order-number ?order) (customer-id ?id))) => (printout t ?name " no ha comprado... nada!" crlf))");
        clips.build("(defrule prods-bought (order (order-number ?order)) (line-item (order-number ?order) (part-number ?part)) (product (part-number ?part) (name ?pn)) => (printout t ?pn " was bought " crlf))");
        clips.build("(defrule prods-qty-bgt (order (order-number ?order)) (line-item (order-number ?order) (part-number ?part) (quantity ?q)) (product (part-number ?part) (name ?p) ) => (printout t ?q " " ?p " was/were bought " crlf))");
        clips.build("(defrule customer-shopping (customer (customer-id ?id) (name ?cn)) (order (order-number ?order) (customer-id ?id)) (line-item (order-number ?order) (part-number ?part)) (product (part-number ?part) (name ?pn)) => (printout t ?cn " bought  " ?pn crlf))");
        clips.build("(defrule cust-5-prods (customer (customer-id ?id) (name ?cn)) (order (order-number ?order) (customer-id ?id)) (line-item (order-number ?order) (part-number ?part) {quantity > 5}) (product (part-number ?part) (name ?pn)) => (printout t ?cn " bought more than 5 products (" ?pn ")" crlf))");
        clips.build("(defrule text-cust (customer (customer-id ?cid) (name ?name) (phone ?phone)) (not (order (order-number ?order) (customer-id ?cid))) => (assert (text-customer ?name ?phone "tienes 25% desc prox compra")) (printout t ?name " 3313073905 tienes 25% desc prox compra" ))");
        clips.build("(defrule call-cust (customer (customer-id ?cid) (name ?name) (phone ?phone)) (not (order (order-number ?order) (customer-id ?cid))) => (assert (call-customer ?name ?phone "tienes 25% desc prox compra")) (printout t ?name " 3313073905 tienes 25% desc prox compra" ))");

        // Lo corremos
        clips.reset();
        clips.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
	}
}