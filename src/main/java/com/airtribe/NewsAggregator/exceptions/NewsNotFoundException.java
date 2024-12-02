package com.airtribe.NewsAggregator.exceptions;

public class NewsNotFoundException extends Exception{
    public NewsNotFoundException(){
        super();
    }

    public NewsNotFoundException(String message){
        super(message);
    }
}
