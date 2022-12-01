# Knowledge Based Systems Portfolio

| Contenido |
| ------------- |
| <a href='https://github.com/saulTejeda117/KBS/edit/main/README.md#hands-on-1-clips-setting-up'><p>Hands On 1: CLIPS Setting Up</p></a>|
| <a href='https://github.com/saulTejeda117/Hands-on/blob/main/README.md#monkey-and-bananas'><p>Hands On 2: Monkey and Bananas</p></a>|

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
<p>Lo que como resultado nos muestra las siguientes impresiones de pantalla:</p>

```
(defrule r1 (monkey near door) (monkey farfrom window) => 
(printout  t "monkey goes to the box" crlf) 
(assert (monkey near box)) 
(retract 1) 
(retract 2))

```
<p align="center">
<img width="700" src ="src/WhatsApp Image 2022-12-01 at 12.06.49 PM.jpeg"><br>Fig. 3 Ejecución de Persons y Sus Reglas</img>
</p>


```
(defrule r1 (monkey near door) (monkey farfrom window) => 
(printout  t "monkey goes to the box" crlf) 
(assert (monkey near box)) 
(retract 1) 
(retract 2))

```


```
(defrule r1 (monkey near door) (monkey farfrom window) => 
(printout  t "monkey goes to the box" crlf) 
(assert (monkey near box)) 
(retract 1) 
(retract 2))

```


<br>

## Hands On 4-5:
<p>Lo que como resultado nos muestra las siguientes impresiones de pantalla:</p>
<br>

## Hands On 6:
<br>

## KBS Project:
<br>

