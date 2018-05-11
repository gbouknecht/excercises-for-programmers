(ns simple-math.core
  (:require [clojure.string :refer [join]])
  (:import (javax.swing JPanel BorderFactory JLabel JFrame SwingUtilities JTextArea JTextField JComponent)
           (java.awt GridLayout BorderLayout Font)
           (java.awt.event KeyAdapter)))

(defn- set-border [^JComponent component top left bottom right]
  (.setBorder component (BorderFactory/createEmptyBorder top left bottom right)))

(defn- parse [number-field]
  (let [number (try (Integer/parseInt (.getText number-field)) (catch Exception _ nil))]
    (if (and number (> number 0)) number)))

(defn- result-text [first-number second-number]
  (letfn [(calculate-and-format [[operator-string operator]]
            (format "%d %s %d = %d" first-number operator-string second-number (int (operator first-number second-number))))]
    (join "\n" (map calculate-and-format [["+" +] ["-" -] ["*" *] ["/" /]]))))

(defn- create-and-show-gui []
  (let [first-number-field (JTextField.)
        second-number-field (JTextField.)
        result-area (doto (JTextArea. 4 0)
                      (.setEditable false)
                      (.setFocusable false)
                      (.setFont (Font. Font/MONOSPACED Font/PLAIN 14))
                      (set-border 12 12 12 12))
        update (fn []
                 (let [first-number (parse first-number-field) second-number (parse second-number-field)]
                   (if (and first-number second-number)
                     (.setText result-area (result-text first-number second-number)))))
        listener (proxy [KeyAdapter] [] (keyReleased [_] (update)))]
    (.addKeyListener first-number-field listener)
    (.addKeyListener second-number-field listener)
    (doto (JFrame. "Simple math")
      (.setContentPane (doto (JPanel. (BorderLayout.)) (set-border 12 12 12 12)))
      (.add (doto (JPanel. (GridLayout. 0 2 12 6))
              (.add (JLabel. "What is the first number?")) (.add first-number-field)
              (.add (JLabel. "What is the second number?")) (.add second-number-field)))
      (.add (doto (JPanel. (BorderLayout.))
              (set-border 12 0 0 0)
              (.add result-area))
            BorderLayout/PAGE_END)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.pack)
      (.setLocationRelativeTo nil)
      (.setVisible true))))

(defn -main []
  (SwingUtilities/invokeLater create-and-show-gui))
