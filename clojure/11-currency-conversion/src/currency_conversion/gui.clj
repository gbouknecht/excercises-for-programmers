(ns currency-conversion.gui
  (:require [currency-conversion.exchange-rates-client :refer [get-currencies get-exchange-rates]])
  (:require [currency-conversion.converter :refer [exchange-rate convert-amount]])
  (:import (javax.swing SwingUtilities JFrame JComponent BorderFactory JPanel JLabel JFormattedTextField JComboBox)
           (java.awt BorderLayout GridLayout)
           (javax.swing.text NumberFormatter DefaultFormatterFactory)
           (java.text DecimalFormat DecimalFormatSymbols)
           (java.util Locale)
           (java.beans PropertyChangeListener)
           (java.awt.event ActionListener)))

(defn- double-text-field ^JFormattedTextField [pattern]
  (let [fmt (doto (NumberFormatter. (DecimalFormat. pattern (DecimalFormatSymbols. Locale/US)))
              (.setValueClass Double))]
    (JFormattedTextField. (DefaultFormatterFactory. fmt fmt fmt))))

(defn- read-only-double-text-field ^JFormattedTextField [pattern]
  (doto (double-text-field pattern)
    (.setEditable false)
    (.setFocusable false)))

(defn- currency-combo-box ^JComboBox [currency-codes default-currency-code]
  (let [combo-box (JComboBox.)]
    (doseq [currency-code currency-codes]
      (.addItem combo-box currency-code))
    (.setSelectedItem combo-box default-currency-code)
    combo-box))

(defn- set-border [^JComponent component top left bottom right]
  (.setBorder component (BorderFactory/createEmptyBorder top left bottom right)))

(defn- create-and-show-gui [exchange-rates-client]
  (let [currency-codes (-> exchange-rates-client get-currencies keys sort)
        base-currency-code "USD"
        exchange-rates (-> exchange-rates-client (get-exchange-rates base-currency-code nil))
        from-currency-combo-box (currency-combo-box currency-codes base-currency-code)
        from-amount-field (double-text-field "0.00")
        to-currency-combo-box (currency-combo-box currency-codes base-currency-code)
        to-amount-field (read-only-double-text-field "0.00")
        exchange-rate-field (read-only-double-text-field "0.000000")
        update (fn []
                 (let [from-currency-code (.getSelectedItem from-currency-combo-box)
                       from-amount (.getValue from-amount-field)
                       to-currency-code (.getSelectedItem to-currency-combo-box)]
                   (if (and from-currency-code to-currency-code)
                     (.setValue exchange-rate-field (exchange-rate base-currency-code exchange-rates from-currency-code to-currency-code)))
                   (if (and from-currency-code from-amount to-currency-code)
                     (.setValue to-amount-field (convert-amount base-currency-code exchange-rates from-currency-code to-currency-code from-amount)))))
        action-listener (reify ActionListener (actionPerformed [_ _] (update)))
        property-change-listener (reify PropertyChangeListener (propertyChange [_ _] (update)))]
    (.addActionListener from-currency-combo-box action-listener)
    (.addPropertyChangeListener from-amount-field property-change-listener)
    (.addActionListener to-currency-combo-box action-listener)
    (doto (JFrame. "Currency conversion")
      (.setContentPane (doto (JPanel. (BorderLayout.)) (set-border 12 12 12 12)))
      (.add (doto (JPanel. (GridLayout. 0 2 12 6))
              (.add (JLabel. "From currency")) (.add from-currency-combo-box)
              (.add (JLabel. "From amount")) (.add from-amount-field)
              (.add (JLabel. "To currency")) (.add to-currency-combo-box)
              (.add (JLabel. "To amount")) (.add to-amount-field)
              (.add (JLabel. "Exchange rate")) (.add exchange-rate-field))
            BorderLayout/PAGE_START)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setSize 500 250)
      (.setLocationRelativeTo nil)
      (.setVisible true))))

(defn show [exchange-rates-client]
  (SwingUtilities/invokeLater #(create-and-show-gui exchange-rates-client)))
