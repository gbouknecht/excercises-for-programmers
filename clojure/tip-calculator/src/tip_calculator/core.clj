(ns tip-calculator.core
  (:require (tip-calculator [ui :as ui] [gui :as gui])))

(defn- ask-text-or-graphical []
  (do
    (print "Text (t) or Graphical (g): ") (flush)
    (or
      ({"t" :text "g" :graphical} (read-line))
      (recur))))

(defn -main []
  (case (ask-text-or-graphical)
    :text (ui/ask-and-calculate)
    :graphical (gui/ask-and-calculate)))
