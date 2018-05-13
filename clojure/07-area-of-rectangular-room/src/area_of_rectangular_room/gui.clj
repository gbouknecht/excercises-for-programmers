(ns area-of-rectangular-room.gui
  (:require [clojure.string :refer [join]]
            [area-of-rectangular-room.calculator :refer :all])
  (:import (javax.swing SwingUtilities JFrame JPanel BorderFactory JLabel JTextField JTextArea JComponent JRadioButton ButtonGroup)
           (java.awt GridLayout BorderLayout FlowLayout)
           (java.awt.event KeyAdapter ActionListener)
           (java.util Locale)
           (java.text DecimalFormat DecimalFormatSymbols)))

(defn- set-border [^JComponent component top left bottom right]
  (.setBorder component (BorderFactory/createEmptyBorder top left bottom right)))

(defn- parse [field]
  (if-let [x (try (Double/parseDouble (.getText field)) (catch Exception _ nil))]
    (if (pos? x) x)))

(defn- format-double [x]
  (.format (DecimalFormat. "0.###" (DecimalFormatSymbols. Locale/US)) x))

(defn- unit->text ^String [unit]
  (unit {:feet "feet" :meters "meters"}))

(defn- ^JRadioButton radio-button
  ([unit] (radio-button unit false))
  ([unit selected] (doto (JRadioButton. (unit->text unit)) (.setSelected selected))))

(defn- result-text [length width from-to-unit]
  (let [area-from (area length width)
        area-to (convert-area area-from from-to-unit)
        from-unit-as-text (unit->text (:from from-to-unit))
        to-unit-as-text (unit->text (:to from-to-unit))]
    (join "\n"
          [(format "You entered dimensions of %1$s %3$s by %2$s %3$s." (format-double length) (format-double width) from-unit-as-text)
           (format "The area is")
           (format "%s square %s" (format-double area-from) from-unit-as-text)
           (format "%s square %s" (format-double area-to) to-unit-as-text)])))

(defn- create-and-show-gui []
  (let [feet-radio-button (radio-button :feet true)
        meters-radio-button (radio-button :meters)
        length-label (JLabel.)
        length-field (JTextField.)
        width-label (JLabel.)
        width-field (JTextField.)
        result-area (doto (JTextArea.) (.setEditable false) (.setFocusable false) (set-border 12 12 12 12))
        from-to-unit #(cond
                        (.isSelected feet-radio-button) {:from :feet :to :meters}
                        (.isSelected meters-radio-button) {:from :meters :to :feet})
        update-labels #(let [from-unit-as-text (unit->text (:from (from-to-unit)))]
                         (.setText length-label (format "What is the length of the room in %s?" from-unit-as-text))
                         (.setText width-label (format "What is the width of the room in %s?" from-unit-as-text)))
        update #(let [length (parse length-field) width (parse width-field)]
                  (update-labels)
                  (if (and length width)
                    (.setText result-area (result-text length width (from-to-unit)))))
        action-listener (reify ActionListener (actionPerformed [_ _] (update)))
        key-listener (proxy [KeyAdapter] [] (keyReleased [_] (update)))]
    (.addActionListener feet-radio-button action-listener)
    (.addActionListener meters-radio-button action-listener)
    (doto (ButtonGroup.) (.add feet-radio-button) (.add meters-radio-button))
    (.addKeyListener length-field key-listener)
    (.addKeyListener width-field key-listener)
    (update-labels)
    (doto (JFrame. "Area of a rectangular room")
      (.setContentPane (doto (JPanel. (BorderLayout.)) (set-border 12 12 12 12)))
      (.add (doto (JPanel. (GridLayout. 0 2 12 6))
              (.add (JLabel. "Which unit?")) (.add (doto (JPanel. (FlowLayout. FlowLayout/LEFT))
                                                     (.add feet-radio-button)
                                                     (.add meters-radio-button)))
              (.add length-label) (.add length-field)
              (.add width-label) (.add width-field))
            BorderLayout/PAGE_START)
      (.add (doto (JPanel. (BorderLayout.))
              (set-border 12 0 0 0)
              (.add result-area)))
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setSize 600 300)
      (.setLocationRelativeTo nil)
      (.setVisible true))))

(defn start []
  (SwingUtilities/invokeLater create-and-show-gui))
