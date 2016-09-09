(ns com.chariotsolutions.rlusk.logging.log
  "Convenient definition for a log of events")

(defprotocol Log
  "Recorder of events, separating them"
  (log [this entry] "Add entry to the log"))
