package analyzer;

/**
 * @author Moshiko
 * class that override the String class
 */
public final class StringM {
	    public final String underlyingString;

	    public StringM(String underlyingString) {
	        this.underlyingString = underlyingString;
	    }
	    
	    public String toString() {
	        return underlyingString;
	    }
	    
	    public int hashCode() {
	    	return 8;
	    }
	    
	    // Overriding equals() to compare two Complex objects	    
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


