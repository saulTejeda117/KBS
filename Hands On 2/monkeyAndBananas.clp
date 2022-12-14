(defrule r1 (monkey near door) (monkey farfrom window) => (printout  t "monkey goes to the box" crlf) (assert (monkey near box)) (retract 1) (retract 2))

(defrule r2 (monkey near box) (monkey hands-free) => (printout t "mokey took the box" crlf) (retract 4) (assert (monkey hands-busy)) (assert (monkey grabbed-box)))

(defrule r3 (monkey near box) (monkey hands-busy) (monkey grabbed-box) => (printout t "monkey goes below the banana" crlf) (assert (monkey below-banana)))

(defrule r4 (monkey below-banana) (monkey near box) (monkey hands-busy) (monkey grabbed-box) => (printout t "monkey dropped the box below the banana" crlf) (retract 6) (retract 7) (assert (monkey hands-free)))

(defrule r5 (monkey below-banana) (monkey near box) (monkey hands-free) => (printout t "monkey climbed the box below the banana" crlf) (retract 6) (retract 7) (assert (monkey over the box)))

(defrule r6 (monkey below-banana) (monkey near box) (monkey hands-free) (monkey over the box) => (printout t "monkey can take the banana" crlf) (assert (monkey can take the banana)))

(defrule r6 (monkey below-banana) (monkey near box) (monkey hands-free) (monkey over the box) (monkey can take the banana) => (printout t "monkey took the banana" crlf)
