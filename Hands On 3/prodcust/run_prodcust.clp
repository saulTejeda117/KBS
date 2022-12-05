(load "C:/Users/sault/Downloads/agent-repo-main/clips/prodcust/load-prod-cust.clp") 

(load "C:/Users/sault/Downloads/agent-repo-main/clips/prodcust/load-prodcust-rules.clp")

(printout t "Current stored facts in CLIPS Working Memory:" crlf)

(facts)
(run)

(clear)  

