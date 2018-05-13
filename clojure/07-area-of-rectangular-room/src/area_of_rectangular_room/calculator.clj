(ns area-of-rectangular-room.calculator)

(defn area [length width]
  (* length width))

(def ^:private conversion-factor 0.09290304)

(defmulti convert-area (fn [_ {from-unit :from to-unit :to}] [from-unit to-unit]))
(defmethod convert-area [:feet :meters] [area _] (* area conversion-factor))
(defmethod convert-area [:meters :feet] [area _] (/ area conversion-factor))
