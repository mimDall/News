package com.mimdal.news.service;

public final class Endpoints {

    private static String API_KEY = "2df13c905138b34cfc99c50f181c40e5";

    private String selectedTopic;
    private String selectedCountry;
    private String selectedLanguage;

    private Endpoints(Builder builder) {
        this.selectedTopic = builder.selectedTopic;
        this.selectedCountry = builder.selectedCountry;
        this.selectedLanguage = builder.selectedLanguage;
    }


    public String getNewsRequestURL() {
        return "https://gnews.io/api/v4/top-headlines?"+
                "topic="+selectedTopic+
                "&country="+selectedCountry+
                "&lang="+selectedLanguage+
                "&token="+API_KEY;
    }

    public static class Builder{

        private String selectedTopic="breaking-news";
        private String selectedCountry="";
        private String selectedLanguage="";

        public Builder setSelectedTopic(String selectedTopic) {
            this.selectedTopic=selectedTopic;
            return this;
        }

        public Builder setSelectedCountry(String selectedCountry) {
            this.selectedCountry=selectedCountry;
            return this;
        }

        public Builder setSelectedLanguage(String selectedLanguage) {
            this.selectedLanguage=selectedLanguage;
            return this;
        }

        public Endpoints build(){
            return new Endpoints(this);
        }

    }
}
