(ns com.chariotsolutions.rlusk.logging.async-log
  "Log for tracking asynchronous events

  Use as follows:

    (defn make-entry-listener
      [label]
      (let [record-of-call (make-async-string-log \"\\n\")
            aslog (fn [entry]
                  \"Append entry to the 'log'\"
                  (log record-of-call entry))]
        (reify
          Object
          (toString [this]
            (format \"EntryListener '%s': %s\" label record-of-call))
          EntryListener
          (^void entryAdded [this ^EntryEvent entry-event]
            (aslog \"Added\"))
          (^void entryUpdated [this ^EntryEvent entry-event]
            (aslog \"Updated\"))
          (^void entryRemoved [this ^EntryEvent entry-event]
            (aslog \"Removed\"))
          (^void entryEvicted [this ^EntryEvent entry-event]
            (aslog \"Evicted\"))
          (^void mapCleared [this ^MapEvent map-event]
            (aslog \"Map Cleared\"))
          (^void mapEvicted [this ^MapEvent map-event]
            (aslog \"Map Evicted\")))))     

  "
  (:require [com.chariotsolutions.rlusk.logging.log :refer (Log)]))

(deftype AsyncStringLog
    [state sep]
  Object
  (toString [this]
    @state)
  Log
  (log [this entry]
    (swap! state str sep entry)))

(defn make-async-string-log
  ([]
   (make-async-string-log ">"))
  ([sep]
   (->AsyncStringLog (atom "init") sep)))

