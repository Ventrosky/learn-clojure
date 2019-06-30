(ns giggin.api
  (:require [ajax.core :refer [GET]]
            [giggin.state :as state]))

(defn index-by
  [key coll]
  "Transfomr a coll to a map with a given key as a lookup value"
  (->> coll
       (map (juxt key identity))
       (into {})))

(defn handler [response]
  (reset! state/gigs (index-by :id response)))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn fetch-gigs
  []
  (GET "https://gist.githubusercontent.com/Ventrosky/9287e0d7ce138cd31ff5c13bf40b983e/raw/c2e5de9f1e0880fae425ae9093ce05e50909c746/gigs.json"
    {:handler handler
     :error-handler error-handler
     :response-format :json
     :keywords? true}))
