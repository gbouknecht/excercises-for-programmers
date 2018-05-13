(ns paint-calculator.core
  (:import (java.text DecimalFormat DecimalFormatSymbols)
           (java.util Locale)))

(defn- ask-shape-of-room []
  (print "Rectangle (r) or circle (c)? ") (flush)
  (or ({"r" :rectangle "c" :circle} (read-line)) (recur)))

(defn- parse-double [s]
  (if-let [x (try (Double/parseDouble s) (catch Exception _ nil))]
    (if (pos? x) x)))

(defn- ask [question]
  (print question) (flush)
  (or (parse-double (read-line)) (recur question)))

(defn- area-of-rectangle [length width]
  (* length width))

(defn- area-of-circle [diameter]
  (* Math/PI (Math/pow (/ diameter 2) 2)))

(defmulti ^:private ask-and-calculate-area (fn [shape] shape))

(defmethod ^:private ask-and-calculate-area :rectangle [_]
  (let [length (ask "Length in feet? ")
        width (ask "Width in feet? ")]
    (area-of-rectangle length width)))

(defmethod ^:private ask-and-calculate-area :circle [_]
  (let [diameter (ask "Diameter in feet? ")]
    (area-of-circle diameter)))

(defn- gallons-of-paint [area]
  (let [square-feet-per-gallon 350]
    (int (Math/ceil (/ area square-feet-per-gallon)))))

(defn- quantity-and-gallons-text [quantity]
  [quantity (if (= quantity 1) "gallon" "gallons")])

(defn- format-double [x]
  (.format (DecimalFormat. "0.###" (DecimalFormatSymbols. Locale/US)) x))

(defn -main []
  (let [shape (ask-shape-of-room)
        area (ask-and-calculate-area shape)
        gallons-of-paint (gallons-of-paint area)]
    (println (apply format "You will need to purchase %d %s of" (quantity-and-gallons-text gallons-of-paint)))
    (println (format "paint to cover %s square feet." (format-double area)))))
