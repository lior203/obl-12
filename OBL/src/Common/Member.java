package Common;

public class Member extends User{
private  Status status;
	
	public Member(String userName, String memberID, String phoneNumber, String email, String password, String firstName,
			String lastName) {
		super(userName,phoneNumber,email,password,firstName,lastName,memberID);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
