package com.airtribe.NewsAggregator.exceptions;

public class PreferenceNotFoundException extends Exception{
    public PreferenceNotFoundException(){
        super();
    }

    public PreferenceNotFoundException(String message){
        super(message);
    }
}
