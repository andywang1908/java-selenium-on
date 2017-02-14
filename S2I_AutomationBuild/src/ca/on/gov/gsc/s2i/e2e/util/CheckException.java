package ca.on.gov.gsc.s2i.e2e.util;

public class CheckException extends RuntimeException {
	private static final long serialVersionUID = -8478318217892785981L;
	//TODO why?
	//private String messageRuntime;

	public CheckException() {
		super();
	}

	public CheckException(String s) {
		super(s);
		System.out.println(s);
		//this.messageRuntime = s;
	}

	public CheckException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public CheckException(Throwable throwable) {
		super(throwable);
	}

	@Override
	public String getMessage() {
		return "selenium check:"+super.getMessage();
	}
}
