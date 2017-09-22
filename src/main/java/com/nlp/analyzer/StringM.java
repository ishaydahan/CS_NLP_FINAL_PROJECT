package com.nlp.analyzer;

import java.util.Objects;

public final class StringM {
	    public final String underlyingString;

	    public StringM(String underlyingString) {
	        this.underlyingString = underlyingString;
	    }
	    public String toString() {
	        return underlyingString;
	    }
	    // Overriding equals() to compare two Complex objects
	    
	    public int hashCode() {
	    	return 8;
	    }

	    public boolean equals(Object o) {
	    	if (this == o) {
	            return true;
	        }
	        if (o instanceof StringM) {
	            StringM anotherString = (StringM)o;
	            int a = LevenshteinDistance.computeLevenshteinDistance(underlyingString, anotherString.underlyingString);
	            if( a <= 3 ) {
	            	return true;
	            }
	            else
	            	return false;
	        }
			return false;
	    }
	}


