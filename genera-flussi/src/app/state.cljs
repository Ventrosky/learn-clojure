(ns app.state
  (:require [reagent.core :refer [atom]]))

(defonce app-state (atom {:carta [{:pos 1
                                   :nome "Operazione"
                                   :descrizione "Codice operazione"
                                   :specifiche "Non sono ammesse operazioni diverse nello stesso file"
                                   :tipo :tchar
                                   :lungh 1
                                   :obbligatorio "O"
                                   :dominio ["E" "I" "U"]
                                   :code :cdoper
                                   :inputato true}
                                  {:pos 2
                                   :nome "Numero di Polizza collettiva"
                                   :descrizione "Numero di Polizza Collettiva"
                                   :specifiche "Numero di polizza collettiva per la quale viene trasmesso l’elenco dato costante all’interno dello stesso flusso."
                                   :tipo :tchar
                                   :lungh 20
                                   :obbligatorio "O"
                                   :dominio []
                                   :code :nupoliz
                                   :inputato true}
                                  {:pos 3
                                   :nome "Codice fiscale Contraente"
                                   :descrizione "Codice fiscale Contraente"
                                   :specifiche "Codice fiscale contraente collettiva. Dato costante per tutte le polizze collettive della contraente."
                                   :tipo :tchar
                                   :lungh 11
                                   :obbligatorio "O"
                                   :dominio []
                                   :code :cdfisccontr
                                   :inputato true}
                                  {:pos 4
                                   :nome "Sesso Aderente"
                                   :descrizione "Sesso aderente"
                                   :specifiche "M : Maschio; F : Femmina"
                                   :tipo :tchar
                                   :lungh 1
                                   :obbligatorio "O"
                                   :dominio ["M" "F"]
                                   :code :cdsesso
                                   :inputato false}
                                  {:pos 5
                                   :nome "Data di Nascita"
                                   :descrizione "Data di nascita dell’assicurato"
                                   :specifiche "Formato gg/MM/yyyy"
                                   :tipo :tdate
                                   :lungh 10
                                   :obbligatorio "O"
                                   :dominio []
                                   :code :dtnasc
                                   :inputato true}
                                  {:pos 6
                                   :nome "Progressivo Riga"
                                   :descrizione "Progressivo di riga all’interno del file"
                                   :specifiche "Valorizzare con il progressivo di riga."
                                   :tipo :tnumber
                                   :lungh 7
                                   :obbligatorio "O"
                                   :dominio []
                                   :code :prgriga
                                   :inputato false}
                                  {:pos 7
                                   :nome "Importo Prestazione TCM"
                                   :descrizione "Importo Prestazione Caso Morte"
                                   :specifiche "Valorizzare solo se la prestazione caso morte è prevista. Le ultime due cifre saranno utilizzate per i decimali"
                                   :tipo :tnumber
                                   :lungh 16
                                   :obbligatorio "R"
                                   :dominio []
                                   :code :imptcm
                                   :inputato true}
                                  {:pos 8
                                   :nome "Importo Prestazione CI"
                                   :descrizione "Importo Prestazione Caso Invalidità"
                                   :specifiche "Valorizzare solo se la prestazione caso invalidità è prevista. Le ultime due cifre saranno utilizzate per i decimali"
                                   :tipo :tnumber
                                   :lungh 16
                                   :obbligatorio "R"
                                   :dominio []
                                   :code :impci
                                   :inputato true}
                                  {:pos 9
                                   :nome "Importo Prestazione Rendita"
                                   :descrizione "Importo Prestazione Rendita"
                                   :specifiche "Valorizzare solo se la prestazione rendita è prevista. Le ultime due cifre saranno utilizzate per i decimali (intesa mensile)"
                                   :tipo :tnumber
                                   :lungh 16
                                   :obbligatorio "R"
                                   :dominio []
                                   :code :impre
                                   :inputato true}
                                  {:pos 10
                                   :nome "Importo Prestazione Critical Illness"
                                   :descrizione "Importo Prestazione Critical Illness"
                                   :specifiche "Valorizzare solo se la prestazione Critical Illness è prevista. Le ultime due cifre saranno utilizzate per i decimali"
                                   :tipo :tnumber
                                   :lungh 16
                                   :obbligatorio "R"
                                   :dominio []
                                   :code :impdd
                                   :inputato true}
                                  {:pos 11
                                   :nome "Data Entrata"
                                   :descrizione "Data di Ingresso in Assicurazione"
                                   :specifiche "Formato gg/MM/yyyy"
                                   :tipo :tdate
                                   :lungh 10
                                   :obbligatorio "O"
                                   :dominio []
                                   :code :dtentrata
                                   :inputato true}
                                  {:pos 12
                                   :nome "Data Uscita"
                                   :descrizione "Data di Uscita dall’Assicurazione"
                                   :specifiche "Valorizzare solo in caso di uscite. Formato gg/MM/yyyy"
                                   :tipo :tchar
                                   :lungh 10
                                   :obbligatorio "R"
                                   :dominio []
                                   :code :dtuscita
                                   :inputato true}
                                  {:pos 13
                                   :nome "Azienda Aderente"
                                   :descrizione "Denominazione Azienda Aderente"
                                   :specifiche "Denominazione della azienda aderente alla quale appartiene l’assicurato"
                                   :tipo :tchar
                                   :lungh 30
                                   :obbligatorio "F"
                                   :dominio []
                                   :code :dsazade
                                   :inputato true}
                                  {:pos 14
                                   :nome "Codice fiscale Azienda aderente"
                                   :descrizione "Codice fiscale Azienda aderente"
                                   :specifiche "Codice fiscale azienda aderente."
                                   :tipo :tchar
                                   :lungh 11
                                   :obbligatorio "F"
                                   :dominio []
                                   :code :cfazade
                                   :inputato true}
                                  {:pos 15
                                   :nome "Categoria Assicurato"
                                   :descrizione "Indica la categoria dell’assicurato "
                                   :specifiche "Valorizzare in base alle codifiche"
                                   :tipo :tchar
                                   :lungh 3
                                   :obbligatorio "R"
                                   :dominio ["A00" "A01" "A02" "P00" "P01" "P02" "P03"]
                                   :code :categoria
                                   :inputato false}
                                  {:pos 16
                                   :nome "Motivo scarto"
                                   :descrizione "Utilizzato nel file degli scarti"
                                   :specifiche "Utilizzato nel file degli scarti"
                                   :tipo :tchar
                                   :lungh 200
                                   :obbligatorio "F"
                                   :dominio []
                                   :code :dsmotivo
                                   :inputato false}]}))

(defonce app-generated (atom {:gen01 ["E","718","XXXXX","M","01/01/1900","1","100000","","","","01/01/1980","","","","A00",""]
                              :gen02 ["E","718","XXXXX","F","02/02/1902","2","100000","","","","02/01/1980","","","","A00",""]
                              :gen03 ["E","718","XXXXX","M","03/03/1903","3","100000","","","","03/01/1980","","","","A00",""]}))

(defonce censimento (atom {:carta {:name "CARTA"
                                   :code :carta
                                   :sort 5}}))


(defonce app-setting (atom {:num 100}))