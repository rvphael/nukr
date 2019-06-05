(ns nukr.user)

(defn create [name email password]
  (with-open [wrtr (clojure.java.io/writer "data.clj" :append true)]
    (doseq [i [{:id 1, :data {:name name, :email email, :password password}}]]
      (.write wrtr (str i "\n")))))

