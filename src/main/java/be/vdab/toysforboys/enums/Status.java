package be.vdab.toysforboys.enums;

public enum Status {
	CANCELLED, DISPUTED, PROCESSING, RESOLVED, SHIPPED, WAITING;
	
	public String getPrintableName() {
		return name().substring(0,1) + name().substring(1).toLowerCase();
	}
}
