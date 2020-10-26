package fr.paris.lutece.plugins.appointment.modules.desk.util;

public enum IncrementingType {
	
	LACE( "module.appointment.desk.lace" , 1 ),
	
	HALFTIMEMORNING( "module.appointment.desk.halftimemorning", 2 ),
	
	HALFTIMEAFTERNOON( "module.appointment.desk.halftimeafternoon" , 3 ),
	
	FULLTIME( "module.appointment.desk.fulltime", 4 );
	
    private final String key;
    private final int value;

    IncrementingType(String key, Integer value) {
    	
        this.key = key;
        this.value = value;
    }

    public String getKey() {
    	
        return key;
    }
    public int getValue() {
    	
        return value;
    }
    
    public static IncrementingType valueOf(int value) {
    	
	    for (IncrementingType e : values()) {
	        if (e.getValue() == value ) {
	            return e;
	        }
	    }
	    return null;
	}

}
