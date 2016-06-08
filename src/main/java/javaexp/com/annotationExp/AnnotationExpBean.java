package javaexp.com.annotationExp;

public class AnnotationExpBean {

	@AnnotationExpForField(fastDb=true)
	private String strValue;
	
	@AnnotationExpForField(fastDb=false)
	private int intValue;
	
	private double dValue;

	public String getStrValue() {
		return "str dönüldü";
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public int getIntValue() {
		return 6;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public double getDoubleValue() {
		return 12d;
	}

	public void setdValue(double doubleValue) {
		this.dValue = doubleValue;
	}

	
	
	
}
