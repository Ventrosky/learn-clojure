(ns giggin.fb.init
  (:require ["firebase/app" :as firebase]
            ["firebase/database"]
            ["firebase/auth"]
            [giggin.fb.auth :refer [on-auth-state-change]]))

(defn firebase-init
  []
  (if (zero? (alength firebase/apps))
    (firebase/initializeApp
     #js {:apiKey "apiKey"
          :authDomain "authDomain"
          :databaseURL "databaseURL"
          :projectId "projectId"})
    (firebase/app))
  (on-auth-state-change))