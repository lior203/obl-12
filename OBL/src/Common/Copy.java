package Common;

public class Copy {
	private String copyName;
	private String copyID;
	private String shelfLocation;
	private boolean onLoan;

	public boolean isOnLoan() {
		return onLoan;
	}

	public void setOnLoan(boolean onLoan) {
		this.onLoan = onLoan;
	}

	public String getShelfLocation() {
		return shelfLocation;
	}

	public void setShelfLocation(String shelfLocation) {
		this.shelfLocation = shelfLocation;
	}
	public String getId() {
		return copyID;
	}
	public void setId(String id) {
		this.copyID = id;
	}

}
