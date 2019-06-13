(ns nukr.application.suggestion
  (:require 
    [nukr.application.connection :as conn]))

(defn sort-suggestions [email sub-connections]
  (for [[key value]
    (sort-by val > 
      (frequencies sub-connections)) 
      :when (> value 1)] key))

(defn connection-suggestions [email]
  (def sub-connections
    (conn/get-sub-connections email))
  (def suggestions
    (sort-suggestions email sub-connections))
    suggestions)
