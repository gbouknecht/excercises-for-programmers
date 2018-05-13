(ns pizza-party.core)

(defn- parse [s]
  (if-let [x (try (Integer/parseInt s) (catch Exception _ nil))]
    (if (pos? x) x)))

(defn- ask [question]
  (print question) (flush)
  (or (parse (read-line)) (recur question)))

(defn- number-and-people-text [n]
  [n (if (= n 1) "person" "people")])

(defn- number-and-pizzas-text [n]
  [n (if (= n 1) "pizza" "pizzas")])

(defn- number-and-pieces-text [n]
  [n (if (= n 1) "piece" "pieces")])

(defn -main []
  (let [number-of-people (ask "How many people? ")
        number-of-pizzas (ask "How many pizzas do you have? ")
        number-of-slices-each-pizza (ask "How many slices each pizza? ")
        total-number-of-slices (* number-of-pizzas number-of-slices-each-pizza)
        number-of-slices-each-person (int (/ total-number-of-slices number-of-people))
        number-of-slices-leftover (rem total-number-of-slices number-of-people)]
    (println (apply format "%d %s with %d %s" (concat (number-and-people-text number-of-people) (number-and-pizzas-text number-of-pizzas))))
    (println (apply format "Each person gets %d %s of pizza." (number-and-pieces-text number-of-slices-each-person)))
    (println (apply format "There are %d leftover %s." (number-and-pieces-text number-of-slices-leftover)))))
