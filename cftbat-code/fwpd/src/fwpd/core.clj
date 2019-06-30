(ns fwpd.core
  (:gen-class))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn list-names
  [col]
  (map :name col))

(def validations {:name string?
                  :glitter-index integer?})

(defn append
  [col s]
  (conj col s))

(defn validate
  [valid-map record]
  (every? identity
          (map 
           #(and 
             (contains? record %)
             ((get valid-map %) (% record)))
           (keys valid-map))))

(defn append-valid 
  [col rec]
  (if (validate validations rec) 
    (append col rec) 
    col))

(defn string-record
  [record]
  (let [{name :name
         index :glitter-index} record]
    (str name "," index)))

(defn csv-string
  [col]
  (clojure.string/join 
   "\n"
   (map string-record col)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (glitter-filter 3 (mapify (parse (slurp filename))))))
