# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]
### Added
- `testing.output-capture` for capturing STDERR and STDOUT. Notice especially the encapsulation in the `capture-test-output` macro:

```clojure
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
```

- `logging.log` and `logging.async-log` added to record an in-place
  logging tool that can be embedded in handlers, listeners, etc.
- `err_output.clj` to allow easy output to STDERR
- Files from the new template.


[Unreleased]: https://github.com/chariotsolutions/clojure-utilities/compare/0.1.0...HEAD
This link below will not yet work...
[0.1.0]: https://github.com/chariotsolutions/clojure-utilities/compare/0.1.0...0.1.0
