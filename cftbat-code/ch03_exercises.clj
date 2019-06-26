(ns testing)
(use 'clojure.test)




(defn add100
"takes a number and adds 100"
[n] 
(+ 100 n))

(defn dec-maker
"Write a function dec-maker"
[n]
#(- % n))

(defn btmapset
  "works like map except the return value is a set"
  [colle fu]
  (reduce (fn [final-seq item]
            (conj final-seq (fu item)))
          (set [])
          colle))

(def asym-alien-body-parts [{:name "head" :size 3}
                             {:name "first-eye" :size 1}
                             {:name "mouth" :size 1}
                             {:name "body" :size 10}
                             {:name "first-hand" :size 2}
                             {:name "first-leg" :size 3}])

(defn matching-part
  [part match]
     {:name (clojure.string/replace (:name part) #"^first-" match)
      :size (:size part)})

(defn alien-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (let [others ["second-" "third-" "fourth-" "fifth-"]]
    (reduce (fn [final-body-parts part]
              (into final-body-parts (conj (map #(matching-part part %) others) part)))
            (set [])
            asym-body-parts)))

(defn matching-part-gen
  [part pattern match]
     {:name (clojure.string/replace (:name part) pattern match)
      :size (:size part)})


(defn general-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts others pattern]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (conj (map #(matching-part-gen part pattern %) others) part)))
          (set [])
          asym-body-parts))

(deftest exercise2
  (is (= (add100 4) 104))
  (is (= (add100 7) 107)))

(deftest exercise3
  (is (= ((dec-maker 10) 40) 30))
  (is (= ((dec-maker 3) 12) 9)))
  
(deftest exercise4
  (is (= (btmapset [1 2 3 4] inc) (set [2 3 4 5])))
  (is (= ((btmapset [2 4 2 3 4] inc) (set [3 4 5])))))

(deftest exercise5
  (is (= (matching-part {:name "mouth" :size 1} "third-") {:name "mouth" :size 1}))
  (is (= (matching-part {:name "first-eye" :size 1} "second-") {:name "second-eye", :size 1}))
  (is (= (map #(matching-part {:name "first-eye" :size 1} %) ["second-" "third-" "fourth-" "fifth-"]) '({:name "second-eye", :size 1} {:name "third-eye", :size 1} {:name "fourth-eye", :size 1} {:name "fifth-eye", :size 1})))
  (is (= (alien-symmetrize-body-parts asym-alien-body-parts) #{{:name "fifth-leg", :size 3} {:name "first-eye", :size 1} {:name "fourth-leg", :size 3} {:name "third-leg", :size 3} {:name "mouth", :size 1} {:name "first-hand", :size 2} {:name "body", :size 10} {:name "second-hand", :size 2} {:name "second-leg", :size 3} {:name "third-hand", :size 2} {:name "second-eye", :size 1} {:name "head", :size 3} {:name "fourth-hand", :size 2} {:name "third-eye", :size 1} {:name "first-leg", :size 3} {:name "fifth-hand", :size 2} {:name "fourth-eye", :size 1} {:name "fifth-eye", :size 1}})))
  
(deftest exercise6
  (is (= (matching-part-gen {:name "mouth" :size 1} #"^one-" "two-") {:name "mouth" :size 1}))
  (is (= (matching-part-gen {:name "one-eye" :size 1} #"^one-" "two-") {:name "two-eye", :size 1}))
  (is (= (map #(matching-part-gen {:name "a-eye" :size 1} #"^a-" %) ["b-" "c-"]) '({:name "b-eye", :size 1} {:name "c-eye", :size 1})))
  (is (= (general-symmetrize-body-parts asym-alien-body-parts ["second-" "third-" "fourth-" "fifth-"] #"^first-") #{{:name "fifth-leg", :size 3} {:name "first-eye", :size 1} {:name "fourth-leg", :size 3} {:name "third-leg", :size 3} {:name "mouth", :size 1} {:name "first-hand", :size 2} {:name "body", :size 10} {:name "second-hand", :size 2} {:name "second-leg", :size 3} {:name "third-hand", :size 2} {:name "second-eye", :size 1} {:name "head", :size 3} {:name "fourth-hand", :size 2} {:name "third-eye", :size 1} {:name "first-leg", :size 3} {:name "fifth-hand", :size 2} {:name "fourth-eye", :size 1} {:name "fifth-eye", :size 1}})))
   
(run-tests 'testing)
