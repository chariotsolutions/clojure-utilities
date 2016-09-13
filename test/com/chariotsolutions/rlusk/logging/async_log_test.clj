(ns com.chariotsolutions.rlusk.logging.async-log-test
  (:require [com.chariotsolutions.rlusk.logging.async-log :as sut]
            [com.chariotsolutions.rlusk.logging.log :refer (log)]
            [clojure.test :as t :refer (is, testing, deftest)]
            [clojure.string :as str]))

(deftest test-async-log-asyncness
  "Create an asynclog, and pass it to multiple threads. It shouldn't lose anything."
  (testing "Log should not lose entries in parallel operation"
    (let [unit-under-test (sut/make-async-string-log "|")
          logger (fn [n] (log unit-under-test n))
          step 5
          driver-count 5
          driver (fn [start]
                   (doall (pmap logger (range start (+ step start)))))]
      (doall (pmap driver (range 1 (inc (* step driver-count)) step)))
      (let [log-string (str unit-under-test)
            results (str/split log-string #"\|")]
        (is (= "init" (first results)))
        (is (= (count (rest results))
               (* step driver-count)))
        (doseq [n (range 1 (inc (* step driver-count)))]
          (is (.contains log-string (str "|" n))))))))
