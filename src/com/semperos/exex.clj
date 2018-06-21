(ns com.semperos.exex
  (:require [clojure.string :as str]))

(defn test-example
  [a b]
  (+ a b))

(defn test-string
  [s]
  (clojure.string/upper-case s))

(defn test-viz
  [n]
  (range n))

;; Could live in test ns
(add-viz #'com.semperos.exex/test-viz
         {:type :plot
          :args 7})

(defmacro add-example
  [v example]
  (list 'alter-meta! v `assoc :example example))

(defmacro add-viz
  [v example]
  (list 'alter-meta! v `assoc :viz example))

(defn find-meta
  [k ns]
  (let [all-interns (sort (ns-interns ns))
        all-vars (vals all-interns)]
    (filter (comp k meta) all-vars)))

(def find-examples (partial find-meta :example))
(def find-viz (partial find-meta :viz))

(defn eval-examples
  [example-vars]
  (map #(eval ((comp :example meta) %)) example-vars))

(defn render-viz
  [viz-vars]
  (map
   (fn [viz-var]
     (let [{:keys [type args]} (:viz (meta viz-var))
           sq "▪"]
       (cond
         (= :plot type) (str/join "\n"
                                  (map #(apply str (repeat % sq)) (viz-var args)))
         :else ::unsupported)))
   viz-vars))

(defn run-examples
  [example-vars]
  (doseq [example-var example-vars]
    (eval ((comp :example meta) example-var))))
