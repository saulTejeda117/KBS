(deftemplate product
   (slot name)
  (slot id)
  (slot marca)
  (slot price)
  (slot stock))

(deftemplate cards
   (slot bank) (slot id))

(deftemplate msis
 (slot id)
 (slot bank)
   (slot msi))

(deftemplate deal
  (slot id)
 (slot concept))
(deffacts products
(product (name P30)(id 1001) (marca Huawei) (price 300) (stock 19))(product (name iPhone7)(id 1002) (marca apple) (price 199) (stock 12))(product (name iPhone13)(id 1003) (marca apple) (price 359) (stock 5))(product (name MacBook)(id 1004) (marca apple) (price 499) (stock 1))(product (name S21)(id 1005) (marca Samsung) (price 200) (stock 1))

)(deffacts card
(cards (bank Citibanamex)(id 1))(cards (bank BBVA)(id 2))(cards (bank HSBC)(id 3))(cards (bank Santander)(id 4))

)