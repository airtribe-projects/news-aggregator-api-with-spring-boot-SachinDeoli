package com.airtribe.NewsAggregator.exceptions;

public class UserPreferenceNotFoundException extends Exception{
    public UserPreferenceNotFoundException(){
        super();
    }

    public UserPreferenceNotFoundException(String message){
        super(message);
    }
}
