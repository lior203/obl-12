package Common;

public class LoanDetails {
	private String bookName;
	private String copyID;
	private String loanDate;

	public LoanDetails(String bookName, String copyID, String loanDate) {
		super();
		this.bookName = bookName;
		this.copyID = copyID;
		this.loanDate = loanDate;
	}
	public LoanDetails() {
		this.bookName="";
		this.copyID="";
		this.loanDate="";
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getCopyID() {
		return copyID;
	}
	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	
}