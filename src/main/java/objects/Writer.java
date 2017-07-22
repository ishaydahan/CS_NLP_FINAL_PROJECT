package objects;

public enum Writer {

	STUDENT("STUDENT"),
	TEACHER("TEACHER"),
	COMPUTER("COMPUTER");
	
    @SuppressWarnings("unused")
	private final String name;

    private Writer(String s) {
        name = s;
    }
}
