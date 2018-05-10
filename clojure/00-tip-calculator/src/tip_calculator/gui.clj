(ns tip-calculator.gui
  (:require [tip-calculator.calculator :refer :all])
  (:import (java.awt BorderLayout FlowLayout GridLayout)
           (java.beans PropertyChangeListener)
           (java.text DecimalFormat DecimalFormatSymbols)
           (java.util Locale)
           (javax.swing BorderFactory JFormattedTextField JFrame JLabel JPanel JSlider JTextField SwingConstants SwingUtilities)
           (javax.swing.event ChangeListener)
           (javax.swing.text DefaultFormatterFactory NumberFormatter)))

(defn- big-decimal-text-field ^JFormattedTextField [pattern]
  (let [fmt (doto (NumberFormatter. (DecimalFormat. pattern (DecimalFormatSymbols. Locale/US)))
              (.setValueClass BigDecimal))]
    (JFormattedTextField. (DefaultFormatterFactory. fmt fmt fmt))))

(defn- tip-rate-slider-label ^JLabel []
  (doto (JLabel.)
    (.setPreferredSize (.getPreferredSize (JLabel. "100%")))
    (.setHorizontalAlignment SwingConstants/RIGHT)))

(defn- read-only-text-field ^JTextField []
  (doto (JTextField.)
    (.setEditable false)
    (.setFocusable false)))

(defn- set-text-to-percentage [component percentage]
  (.setText component (format "%d%%" percentage)))

(defn- set-text-to-amount [component amount]
  (.setText component (format "$%s" (.toPlainString amount))))

(defn- create-and-show-gui []
  (let [bill-amount-field (big-decimal-text-field "0.00")
        tip-rate-slider (JSlider. 0 100 0)
        tip-rate-slider-label (tip-rate-slider-label)
        tip-field (read-only-text-field)
        total-field (read-only-text-field)
        update (fn []
                 (let [bill-amount (.getValue bill-amount-field) tip-rate (.getValue tip-rate-slider)]
                   (set-text-to-percentage tip-rate-slider-label tip-rate)
                   (if bill-amount
                     (let [{tip :tip total :total} (tip-and-total bill-amount tip-rate)]
                       (set-text-to-amount tip-field tip)
                       (set-text-to-amount total-field total)))))
        property-change-listener (reify PropertyChangeListener (propertyChange [_ _] (update)))
        change-listener (reify ChangeListener (stateChanged [_ _] (update)))]
    (.addPropertyChangeListener bill-amount-field property-change-listener)
    (.addChangeListener tip-rate-slider change-listener)
    (doto (JFrame. "Tip calculator")
      (.add (doto (JPanel. (GridLayout. 0 2 12 6))
              (.setBorder (BorderFactory/createEmptyBorder 12 12 12 12))
              (.add (JLabel. "What is the bill amount?")) (.add bill-amount-field)
              (.add (JLabel. "What is the tip rate?"))
              (.add (doto (JPanel. (FlowLayout.)) (.add tip-rate-slider) (.add tip-rate-slider-label)))
              (.add (JLabel. "Tip:")) (.add tip-field)
              (.add (JLabel. "Total:")) (.add total-field))
            BorderLayout/PAGE_START)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.pack)
      (.setLocationRelativeTo nil)
      (.setVisible true))))

(defn ask-and-calculate []
  (SwingUtilities/invokeLater create-and-show-gui))
