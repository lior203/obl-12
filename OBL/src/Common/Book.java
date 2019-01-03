package Common;

public class Book {
private String bookName;
private String bookID;
private String authorName;
private String editionNumber;
private String printDate;
private String theme;
private String description;
private String catalogNumber;
private String numberOfCopies;
private String purchaseDate;
private String tableOfContent;
private String closetDateOfReturn;

public String getClosetDateOfReturn() {
	return closetDateOfReturn;
}
public void setClosetDateOfReturn(String closetDateOfReturn) {
	this.closetDateOfReturn = closetDateOfReturn;
}
public void setId(String id) {
	this.bookID = id;
}
public String getBookName() {
	return bookName;
}
public void setBookName(String bookName) {
	this.bookName = bookName;
}
public String getId() {
	return bookID;
}
public String getAuthorName() {
	return authorName;
}
public void setAuthorName(String authorName) {
	this.authorName = authorName;
}
public String getEditionNumber() {
	return editionNumber;
}
public void setEditionNumber(String editionNumber) {
	this.editionNumber = editionNumber;
}
public String getPrintDate() {
	return printDate;
}
public void setPrintDate(String printDate) {
	this.printDate = printDate;
}
public String getTheme() {
	return theme;
}
public void setTheme(String theme) {
	this.theme = theme;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getCatalogNumber() {
	return catalogNumber;
}
public void setCatalogNumber(String catalogNumber) {
	this.catalogNumber = catalogNumber;
}
public String getNumberOfCopies() {
	return numberOfCopies;
}
public void setNumberOfCopies(String numberOfCopies) {
	this.numberOfCopies = numberOfCopies;
}
public String getPurchaseDate() {
	return purchaseDate;
}
public void setPurchaseDate(String purchaseDate) {
	this.purchaseDate = purchaseDate;
}

public String getTableOfContent() {
	return tableOfContent;
}
public void setTableOfContent(String tableOfContent) {
	this.tableOfContent = tableOfContent;
}
}
