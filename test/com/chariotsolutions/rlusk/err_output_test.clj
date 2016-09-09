(ns com.chariotsolutions.rlusk.err-output-test
  (:require [com.chariotsolutions.rlusk.err-output :as sut]
            [com.chariotsolutions.rlusk.testing.output-capture :refer (capture-test-output)]
            [clojure.test :refer :all])
  (:import [java.io PrintWriter StringWriter]))

(deftest test-err-println-experiment
  (testing "Test err-println"
    (capture-test-output
     [err-str out-str]
     (println "Out")
     (sut/err-println "Err")
     (println "Out")
     :evaluation
     (is (= err-str "Err\n"))
     (is (= out-str "Out\nOut\n"))))
  (testing "Test with-err-out"
    (capture-test-output
     [err-str out-str]
     (println "Out")
     (sut/with-stderr
       (println "Err"))
     (println "Out")
     :evaluation
     (is (= err-str "Err\n"))
     (is (= out-str "Out\nOut\n")))))

(deftest test-err-println
  (testing "Test err-println"
    (let [err-output (make-test-output)
          out-output (make-test-output)]
      (binding [*err* (:print-writer err-output)
                *out* (:print-writer out-output)]
        (println "Out")
        (sut/err-println "Err")
        (println "Out"))
      (is (= (str err-output) "Err\n"))
      (is (= (str out-output) "Out\nOut\n"))
      ))
  (testing "Test with-err-out"
    (let [err-output (make-test-output)
          out-output (make-test-output)]
      (binding [*err* (:print-writer err-output)
                *out* (:print-writer out-output)]
        (println "Out")
        (sut/with-stderr
          (println "Err"))
        (println "Out"))
      (is (= (str err-output) "Err\n"))
      (is (= (str out-output) "Out\nOut\n")))))
