(deftemplate product
   	(slot name)
   	(slot id)
   	(slot marca)
   	(slot price)
	(slot stock)
)

(deffacts products 
	(product (name P30) (id 1001) (marca Huawei) (price 310.00) (stock 3))
 	(product (name iPhone7) (id 1002) (marca apple) (price 169.99) (stock 6))
 	(product (name iPhone13) (id 1003) (marca apple) (price 399.99) (stock 9))
 	(product (name MacBook) (id 1004) (marca apple) (price 499.99) (stock 12))
	(product (name S21)	(id 1005) (marca Samsung) (price 200.00) (stock 15))
)

(deftemplate cards
	(slot bank)
	(slot id)
)

(deffacts card
	(cards (bank Citibanamex) (id 1))
	(cards (bank BBVA) (id 2))
	(cards (bank HSBC) (id 3))
	(cards (bank Santander) (id 4))
)

(deftemplate msis
	(slot id)
	(slot bank)
	(slot msi)
)

(deftemplate deal
   	(slot id)
   	(slot concept)
)
