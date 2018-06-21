(ns com.semperos.exex-test
  (:require [clojure.test :refer :all]
            [com.semperos.exex :refer :all]))

(add-example #'com.semperos.exex/test-example `(test-example 2 3))
(add-example #'com.semperos.exex/test-string  `(test-string "wowza"))

(deftest eval-examples-test
  (is (= '(5 "WOWZA")
         (eval-examples (find-examples (the-ns 'com.semperos.exex))))))
