(ns com.chariotsolutions.rlusk.testing.output-capture-test
  (:require [com.chariotsolutions.rlusk.testing.output-capture :as sut]
            [com.chariotsolutions.rlusk.err-output :as eo]
            [clojure.test :as t]))

(eo/wrap-stderr print)

(t/deftest test-capture-stops
  (t/testing "Testing that capture stops before evaluation"
    (sut/capture-test-output
     [outer-err outer-str]
     (print "OUTSTD")
     (err-print "OUTERR")
     (sut/capture-test-output
      [inner-err inner-str]
      (print "IN-STD")
      (err-print "IN-ERR")
      :inner-eval
      (printf "inner-err: '%s'; inner-std: '%s'" inner-err inner-str))
     :outer-eval
     (t/is (re-matches #".*inner-err.*inner-std.*" outer-str)))))
