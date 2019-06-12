(ns nukr.models.user
  (:require [com.stuartsierra.mapgraph :as mg]
            [nano-id.core :refer [nano-id]]))

(def db (atom (mg/new-db)))

(defn add-profile [name email]
  (swap! db mg/add-id-attr :user/id :user/email)
  (swap! db mg/add
         {:user:id (nano-id 5)
          :user/name name
          :user/email email
          :user/hidden false
          :user/connections (vector)}))

(defn create-connection [email new-connection]
  (swap! db mg/add
         {:user/email email
          :user/connections new-connection}))

(defn get-connections [email]
  (def attributes
    (get @db [:user/email email]))
  (get-in attributes [:user/connections]))

(defn connect-profiles [guest host]
  (def old-guest-connections
    (get-connections guest))
  (def old-host-connections
    (get-connections host))
  (def new-guest-connections
    (cons host old-guest-connections))
  (def new-host-connections
    (cons guest old-host-connections))
  (create-connection guest new-guest-connections)
  (create-connection host new-host-connections))

(defn get-profile-details [email]
  (get @db [:user/email email]))

(defn build-sub-connections [email connections]
  (def sub-connections
    (for [email connections]
      (get-connections email)))
  (def details 
    (for [email (apply concat sub-connections)]
      (get-profile-details email)))
  (def valid-profiles 
    (filter #(= (:user/hidden %) false) details))
  (def get-emails-only 
    (map #(select-keys % [:user/email]) valid-profiles))
   (apply concat get-emails-only))

(defn get-sub-connections [email]
  (def connections
    (get-connections email))
  (def sub-connections
    (build-sub-connections email connections))
  (remove (fn [x]
            (= x email)) (apply concat sub-connections)))

(defn change-hidden-status [email new-status]
  (swap! db
        mg/add
        {:user/email email
          :user/hidden new-status}))

(defn get-hidden-status [email]
  (def attributes
    (get @db [:user/email email]))
  (def status
    (get-in attributes [:user/hidden]))
    status)

(defn sort-suggestions [email sub-connections]
  (for [[key value]
    (sort-by val > 
      (frequencies sub-connections)) 
      :when (> value 1)] key))

(defn connection-suggestions [email]
  (def sub-connections
    (get-sub-connections email))
  (def suggestions
    (sort-suggestions email sub-connections))
    suggestions)

(defn show [email]
  (get @db [:user/email email]))