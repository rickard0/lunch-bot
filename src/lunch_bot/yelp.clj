(ns lunch-bot.yelp
	(:require 
    [gws.yelp.client    :as client]
    [gws.yelp.api       :as api]
    [clojure.data.json  :as json]))

(def yelp-client (client/create "CONSUMER KEY" 
                                "CONSUMER SECRET" 
                                "TOKEN"
                                "TOKEN SECRET"))
(defn find-first-result [resp]
  #_(clojure.pprint/pprint resp)
  (first (:businesses resp)))

(defn find-random 
  ([] (find-random "restaurants"))
  ([category] 
   (let [resp (api/search yelp-client {:category_filter category :location "91362" :radius_filter 10000})]
    (println (str "Found " (count (:businesses resp)) " locations."))
     (if (> (count (:businesses resp)) 0)
       (rand-nth (:businesses resp))))))


(defn get-address [resp]
  (apply str (interleave (:dispaly_address (:location resp)) "\n")))

"Wishful thinking: Make this look really slick when sent to slack. No documentation? No problem!" 
(defn format-attachment [resp user chan]
  #_(clojure.pprint/pprint resp)
  (let [pretext       (str "Go To _" (:name resp) "_")
        author-link   "www.yelp.com" ;; Hardcode author for now
        author-icon   "https://s3-media1.fl.yelpcdn.com/assets/srv0/yelp_styleguide/86ec59fef415/assets/img/logos/burst_tiny.png" ;; Yelp Starburst
        title (:name resp)
        title-link (:url resp)
        text-address (get-address resp)
        image-url (:rating_img_url resp)
        review-count (:review_count resp)]
    {:type "message" 
     :user user
     :channel chan
     :attachments 
         [{:fallback title
            :color "#36a64f"
            :pretext pretext
            :author_name "Yelp"
            :author_link author-link
            :author_icon author-icon
            :title title
            :title_link title-link
            :text text-address
            :fields [{:title "Reviews"
                        :value review-count
                        :short false}]
            :image_url image-url}]}))

(defn find-spot [term]
  (let [resp (api/search yelp-client {:term term :location "westlake village, ca"})]
   (format-attachment (find-first-result resp)))) 


