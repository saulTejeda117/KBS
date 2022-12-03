# Knowledge Based Systems Portfolio

| Contenido |
| ------------- |
| <a href='https://github.com/saulTejeda117/KBS/edit/main/README.md#hands-on-1-clips-setting-up'><p>Hands On 1: CLIPS Setting Up</p></a>|
| <a href='https://github.com/saulTejeda117/KBS/blob/main/README.md#hands-on-2-monkey-and-bananas'><p>Hands On 2: Monkey and Bananas</p></a>|
| <a href='https://github.com/saulTejeda117/KBS/edit/main/README.md#hands-on-3'><p>Hands On 3: CLIPS Scripts</p></a>|
| <a href='https://github.com/saulTejeda117/KBS/edit/main/README.md#hands-on-4-5'><p>Hands On 4-5: CLIPS Scripts</p></a>|
| <a href='https://github.com/saulTejeda117/KBS/edit/main/README.md#hands-on-6'><p>Hands On 6: CLIPS Scripts</p></a>|
| <a href='https://github.com/saulTejeda117/KBS/edit/main/README.md#kbs-project'><p>KBS Project</p></a>|

## Hands On 1: CLIPS Setting Up
<p align = 'justify'> Esta pirimera actividad de la clase consistió en llevar a cabo el proceso de instalación del lenguaje de programación basado en reglas <a href='https://www.clipsrules.net' target="_blank"><code>CLIPS</code></a> en su versión 6.24, para posteriormente realizar el setting up asociado:</p>
<p align="center">
<img width="700" src ="src/WhatsApp Image 2022-11-20 at 11.56.57 AM.jpeg"><br>Fig. 1 CLIPS Ejecutandose</img>
</p>
<br>

## Hands On 2: Monkey and Bananas

<p align = 'justify'>Este ejercicio consistió en completar el problema del mono y las banas, desarrollado durante la clase, por medio del lenguaje de programación basado en reglas <a href='https://www.clipsrules.net' target="_blank"><code>CLIPS</code></a>. Planteamiento del problema a resolver: Un mono está en una habitación, en el techo de dicha habitación se encuentra un racimo de plátanos, que está fuera del alcance del mono. Sin embargo, en la habitación también hay una silla, la cual puede ayudar al simio a alcanzar los plátanos, de manera que el mono debe de moverse y colocar la silla bajo los platanos para así poder alcanzarlos. Con esto en mente, desarrollamos la secuencia correcta de pasos a seguir para cumplir el objetivo tal y como se aprecia en el  código <a href='https://github.com/saulTejeda117/KBS/blob/main/monkeyAndBananas' target="_blank"><code>Monkey and Bananas</code></a>, obteniendo el siguiente resultados:</p>

<p align="center">
<img width="700" src ="src/WhatsApp Image 2022-10-02 at 8.44.48 PM (1).jpeg"><br>Fig. 2 Resultados Monkey and Bananas</img>
</p>

<br>

## Hands On 3:

<p>Para esta tercer actividad, fue necesario desarrollar tres clases de Java, a traves de las cuales fuera posible ejecutar los Scripts ubicados en la carpeta del repositorio público de GitHub "agent-repo", proporcionado por el profesor, todo esto empleando la API de CLIPSJNI, para lo cual propuse las siguientes soluciones a esos problemas:
</p>

### Persons
```
public class RunPersons extends Agent {
	private Environment clips;

	public void loadKnowledge(){
		System.out.println("**** Program to Load and Persons ****"); 
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

```

### Prodcust
```
public class RunProdcust extends Agent {
	private Environment clips;

	public void loadKnowledge(){
		System.out.println("**** Program to Load and Prodcust ****"); 
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
```

### Market

```
public class RunMarket extends Agent {
	private Environment clips;

	public void loadKnowledge(){
		System.out.println("**** Program to Load and Run Market****"); 
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
```

<br>

## Hands On 4-5:
<p align = 'justify'>Para las siguientes dos actividades se nos pidió implementar un Agente Emisor y un Agente Receptor, de los cuales uno de ellos (Agente Emisor) debería de enviar una serie de sintomas a manera de hechos con la sintaxis y semántica de CLIPS, mientas que el otro (Agente Receptor) contaría con una Base de Conocimientos, predefinida, con un conjunto de reglas condición-acción para diagnosticar al menos cinco enfermedades. Así mismo, se realizará un diagnostico a los sintomas de la enfermedad correspondiente. Por último, el diagnóstico se imprimirá en terminal; por el momento, no se regresa al Emisor. 
</p>
<br>

## Hands On 6:
<p align = 'justify'>Ahora, para esta última actividad era necesario implementar un sistema multiagentes, el cual pudiera soportar el diagnostico Distribuido de Patologías, todo esto basado en las últimas dos actividades.
</p>


<br>

## KBS Project:
<br>

