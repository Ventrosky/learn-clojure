(ns app.tracciato
  (:require [cljsjs.semantic-ui-react :as ui]
            [goog.string :as gstring]
            [goog.string.format]
            [clojure.walk]))

;;"This function returns js/Date from a clj String"
(defn string-to-date-obj
  [date-str]
  (js/Date. (clj->js (reverse (clojure.string/split date-str #"[^\d]")))))

(defn left-pad
  [d]
  (gstring/format "%02d" d))

;;"This function returns formatted dd/MM/yyy string from js/Date"
(defn date-obj-to-str
  [date-obj]
  (str (left-pad (.getDate date-obj)) "/" (left-pad (inc (.getMonth date-obj))) "/" (.getFullYear date-obj)))

;;"This function returns new random js/Date from intervals js/Date"
(defn random-date 
  [start end] 
  (let [time-start (.getTime start)
        time-end (.getTime end)
        time-new (+ time-start (* (Math/random) (- time-end time-start)))]
    (js/Date. time-new)))

;;"This function returns new random   from intervals clj String"

(defn rng-date-str-interval
  ([date-str-from date-str-to]
   (date-obj-to-str (random-date (string-to-date-obj date-str-from) (string-to-date-obj date-str-to))))
  ([date-str]
     (rng-date-str-interval date-str date-str)))

(defn rng-date-in
  [date-str]
  (if (clojure.string/blank? date-str)
    ""
    (apply rng-date-str-interval (clojure.string/split date-str #";"))))

(defn positive-numbers
  ([] (positive-numbers 1))
  ([n] (lazy-seq (cons n (positive-numbers (inc n))))))

(defn rng-importo
  ([imp-min imp-max]
   (let [n-min (js/parseInt imp-min)
         n-max (js/parseInt imp-max)]
     (str (+ n-min (rand-int (- n-max n-min))))))
  ([imp]
   (rng-importo imp imp)))

(defn rng-importo-in
  [str-imp]
  (if (clojure.string/blank? str-imp)
    ""
    (apply rng-importo (clojure.string/split str-imp #";"))))

(defn no-nill-str
  [str]
  (if (clojure.string/blank? str)
    ""
    str))

(def fu-gen-record
  {:cdoper #(repeat (no-nill-str %))
   :nupoliz #(repeat (no-nill-str %))
   :cdfisccontr #(repeat (no-nill-str %))
   :cdsesso (fn [seq-sex] (repeatedly #(rand-nth seq-sex)));["M", "F"]
   :dtnasc (fn [dt-str] (repeatedly #(rng-date-in dt-str)))
   :prgriga #(positive-numbers)
   :imptcm  (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :impci (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :impre (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :impdd (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :dtentrata (fn [dt-str] (repeatedly #(rng-date-in dt-str)))
   :dtuscita (fn [dt-str] (repeatedly #(rng-date-in dt-str)))
   :dsazade #(repeat (no-nill-str %))
   :cfazade #(repeat (no-nill-str %))
   :categoria (fn [categorie] (repeatedly #(rand-nth categorie)))
   :dsmotivo #(repeat " ")
   })

(defn genera-carta
  [dati-form]
  (let [{:keys [cdoper nupoliz cdfisccontr cdsesso dtnasc prgriga imptcm impci impre impdd dtentrata dtuscita dsazade cfazade categoria dsmotivo]
         :or {cdoper "E" dsmotivo "" cdsesso ["M" "F"] categoria ["A00" "A01" "A02" "P00" "P01" "P02" "P03"]}} dati-form
        gen-single-seq (fn [nkey nval] ((nkey fu-gen-record) nval))]
    (map vector
         (gen-single-seq :cdoper cdoper)
         (gen-single-seq :nupoliz nupoliz)
         (gen-single-seq :cdfisccontr cdfisccontr)
         (gen-single-seq :cdsesso ["M" "F"])
         (gen-single-seq :dtnasc dtnasc)
         ((:prgriga fu-gen-record))
         (gen-single-seq :imptcm imptcm)
         (gen-single-seq :impci impci)
         (gen-single-seq :impre impre)
         (gen-single-seq :impdd impdd)
         (gen-single-seq :dtentrata dtentrata)
         (gen-single-seq :dtuscita dtuscita)
         (gen-single-seq :dsazade dsazade)
         (gen-single-seq :cfazade cfazade)
         (gen-single-seq :categoria ["A00" "A01" "A02" "P00" "P01" "P02" "P03"])
         (gen-single-seq :dtnasc dsmotivo))))

(def fu-gen-record-carbp
  {:cdoper #(repeat (no-nill-str %))
   :nupoliz #(repeat (no-nill-str %))
   :dsnome #(repeat "")
   :dscognome #(repeat "")
   :cdfisc #(repeat "")
   :dtnasc (fn [dt-str] (repeatedly #(rng-date-in dt-str)))
   :dtentrata (fn [dt-str] (repeatedly #(rng-date-in dt-str)))
   :dtuscita (fn [dt-str] (repeatedly #(rng-date-in dt-str)))
   :imptcm  (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :impci (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :impre (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :impdd (fn [imp-str] (repeatedly #(rng-importo-in imp-str)))
   :categoria (fn [categorie] (repeatedly #(rand-nth categorie)))
   :domanda1 (fn [answers] (repeatedly #(rand-nth answers))) 
   :domanda2 (fn [answers] (repeatedly #(rand-nth answers)))
   :domanda3 (fn [answers] (repeatedly #(rand-nth answers)))
   :domanda4 (fn [answers] (repeatedly #(rand-nth answers)))
   :aws (fn [answers] (repeatedly #(rand-nth answers)))})

(defn genera-carbp
  [dati-form]
  (let [{:keys [cdoper nupoliz dsnome dscognome cdfisc dtnasc dtentrata dtuscita imptcm impci impre impdd categoria domanda1 domanda2 domanda3 domanda4 aaw]
         :or {cdoper "E" categoria ["A00" "A01" "A02" "P00" "P01" "P02" "P03"]}} dati-form
        gen-single-seq (fn [nkey nval] ((nkey fu-gen-record-carbp) nval))]
    (map vector
         (gen-single-seq :cdoper cdoper)
         (gen-single-seq :nupoliz nupoliz)
         (gen-single-seq :dsnome dsnome)
         (gen-single-seq :dscognome dscognome)
         (gen-single-seq :cdfisc cdfisc)
         (gen-single-seq :dtnasc dtnasc)
         (gen-single-seq :dtentrata dtentrata)
         (gen-single-seq :dtuscita dtuscita)
         (gen-single-seq :imptcm imptcm)
         (gen-single-seq :impci impci)
         (gen-single-seq :impre impre)
         (gen-single-seq :impdd impdd)
         (gen-single-seq :categoria ["A00" "A01" "A02" "P00" "P01" "P02" "P03"])
         (gen-single-seq :domanda1 ["S" "N"])
         (gen-single-seq :domanda2 ["S" "N"])
         (gen-single-seq :domanda3 ["S" "N"])
         (gen-single-seq :domanda4 ["S" "N"])
         (gen-single-seq :aws ["S" "N"]))))

(def numbers (iterate inc 1))

(def titles (map #(str "gen-" %) numbers))

(defn genera-carta-n
  [n spec-in key]
  (let [fu-key (case key
                 :carta genera-carta
                 :carbp genera-carbp)]
    (clojure.walk/keywordize-keys (zipmap titles (take n (fu-key spec-in))))))

(defn section
  [cname title elem]
  [:section {:class (str "section-wrapper" cname)}
   [:div.container
    [:div.row
     [:div.col-md-3
      [:div.section-title
       [:h2 title]]]
     [:div.col-md-9
      elem]]]])

(defn download-trc
  [name record-text]
  (let [link (.createElement js/document "a")
        text (clojure.string/join "\n" (map #(clojure.string/join "|" %) (sort-by #(nth % 5) record-text)))
        text-enc (str "data:text/plain;charset=utf-8," (.encodeURIComponent js/window text))]
    (set! (.-href link) text-enc)
    (.setAttribute link "download" name)
    (.appendChild (.-body js/document) link)
    (.click link)
    (.removeChild (.-body js/document) link)))
