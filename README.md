# Hands-on
| Contenido: Actividades |
| ------------- |
| <a href='https://github.com/saulTejeda117/Hands-on/blob/main/README.md#monkey-and-bananas'><p>Monkey and Bananas</p></a>|

## Monkey and Bananas
<p> Este pirimer ejercicio de la clase consistió en resolver el problema del mono y las banas por medio del lenguaje de programación basado en reglas <a href='https://www.clipsrules.net' target="_blank"><code>CLIPS</code></a>.

### Planteamiento del problema
Planteamiento del problema a resolver: Un mono está en una habitación, en el techo de dicha habitación se encuentra un racimo de plátanos, que está fuera del alcance del mono. Sin embargo, en la habitación también hay una silla, la cual puede ayudar al simio a alcanzar los plátanos, de manera que el mono debe de moverse y colocar la silla bajo los platanos para así poder alcanzarlos. Con esto en mente, desarrollamos la secuencia correcta de pasos a seguir para cumplir el objetivo  el código fuente de este programa se puede encontrar a continuación:</p>

```
(defrule r1 (monkey near door) (monkey farfrom window) => 
(printout  t "monkey goes to the box" crlf) 
(assert (monkey near box)) 
(retract 1) 
(retract 2))

(defrule r2 (monkey near box) (monkey hands-free) => 
(printout t "mokey took the box" crlf) (retract 4) 
(assert (monkey hands-busy)) 
(assert (monkey grabbed-box)))

(defrule r3 (monkey near box) (monkey hands-busy) (monkey grabbed-box) => 
(printout t "monkey goes below the banana" crlf) 
(assert (monkey below-banana)))

(defrule r4 (monkey below-banana) (monkey near box) (monkey hands-busy) (monkey grabbed-box) => 
(printout t "monkey dropped the box below the banana" crlf) 
(retract 6) 
(retract 7) 
(assert (monkey hands-free)))

(defrule r5 (monkey below-banana) (monkey near box) (monkey hands-free) => 
(printout t "monkey climbed the box below the banana" crlf) 
(assert (monkey over the box)))

(defrule r6 (monkey below-banana) (monkey near box) (monkey hands-free) (monkey over the box) => 
(printout t "monkey can take the banana" crlf) 
(assert (monkey can take the banana)))

(defrule r6 (monkey below-banana) (monkey near box) (monkey hands-free) (monkey over the box) (monkey can take the banana) => 
(printout t "monkey took the banana" crlf)
```
### Resultados
<p>Lo que como resultado nos muestra las siguientes impresiones de pantalla:</p>
<img width="700" src ="src/WhatsApp Image 2022-10-02 at 8.44.48 PM (1).jpeg"><br>Fig. 1 Resultados Obtenidos</img><br>
