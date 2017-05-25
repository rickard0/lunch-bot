(ns lunch-bot.core
  (:require [lunch-bot.slack]))

(defn -main [& args]
  (println "Running...")
  (loop [a 1] (recur [a 1])))
