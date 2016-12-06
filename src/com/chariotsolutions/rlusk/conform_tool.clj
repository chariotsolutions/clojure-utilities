(ns com.chariotsolutions.rlusk.conform-tool
  "Stuart Halloway's conformance tool"
  (:require [clojure.spec :as s]))

(defn conform!
  "Like s/conform, but throws an error with s/explain-data on failure.

  Taken from Stuart Halloway's video on ETL:
  https://www.youtube.com/watch?v=oOON--g1PyU"
  ([spec x]
   (conform! spec x ""))
  ([spec x msg]
   (let [conformed (s/conform spec x)]
     (if (= ::s/invalid conformed)
       (throw (ex-info (str "Failed to conform " spec ", see ex-data")
                       {:data (s/explain-data spec x)
                        :value x
                        :message msg}))
       conformed))))

