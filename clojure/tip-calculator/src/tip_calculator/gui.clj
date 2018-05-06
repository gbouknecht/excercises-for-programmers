(ns tip-calculator.gui
  (:require [tip-calculator.calculator :refer :all])
  (:import (java.awt BorderLayout GridLayout)
           (java.beans PropertyChangeListener)
           (java.text DecimalFormat DecimalFormatSymbols)
           (java.util Locale)
           (javax.swing BorderFactory JFormattedTextField JFrame JLabel JPanel JTextField SwingUtilities)
           (javax.swing.text DefaultFormatterFactory NumberFormatter)))

(defn- big-decimal-text-field ^JFormattedTextField [pattern]
  (let [fmt (doto (NumberFormatter. (DecimalFormat. pattern (DecimalFormatSymbols. Locale/US)))
              (.setValueClass BigDecimal))]
    (JFormattedTextField. (DefaultFormatterFactory. fmt fmt fmt))))

(defn- read-only-text-field ^JTextField []
  (doto (JTextField.)
    (.setEditable false)
    (.setFocusable false)))

(defn- set-text [field amount]
  (.setText field (format "$%s" (.toPlainString amount))))

(defn- create-and-show-gui []
  (let [bill-amount-field (big-decimal-text-field "0.00")
        tip-rate-field (big-decimal-text-field "#")
        tip-field (read-only-text-field)
        total-field (read-only-text-field)
        update (fn []
                 (let [bill-amount (.getValue bill-amount-field) tip-rate (.getValue tip-rate-field)]
                   (if (and bill-amount tip-rate)
                     (let [{tip :tip total :total} (tip-and-total bill-amount tip-rate)]
                       (set-text tip-field tip)
                       (set-text total-field total)))))
        listener (reify PropertyChangeListener (propertyChange [_ _] (update)))]
    (.addPropertyChangeListener bill-amount-field listener)
    (.addPropertyChangeListener tip-rate-field listener)
    (doto (JFrame. "Tip calculator")
      (.setLayout (BorderLayout.))
      (.add (doto (JPanel. (GridLayout. 0 2 12 6))
              (.setBorder (BorderFactory/createEmptyBorder 12 12 12 12))
              (.add (JLabel. "What is the bill amount?")) (.add bill-amount-field)
              (.add (JLabel. "What is the tip rate?")) (.add tip-rate-field)
              (.add (JLabel. "Tip:")) (.add tip-field)
              (.add (JLabel. "Total:")) (.add total-field))
            BorderLayout/PAGE_START)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.pack)
      (.setLocationRelativeTo nil)
      (.setVisible true))))

(defn ask-and-calculate []
  (SwingUtilities/invokeLater create-and-show-gui))
