package project;


import java.io.Serializable;


/*	Admin of the RMOS 
 */
public class Admin implements Serializable{
	String userId;			// admin user id
	String password;		// admin pw
	Rmos rmos;				// RMOS admin belongs to
	
	
	/*	constructor 
	 */
	public Admin(String userId, 
			String password, Rmos rmos) {
		this.userId = userId;
		this.password = password;
		this.rmos = rmos;
	}
	
	
	/*	returns admin id 
	 */
	public String getUserId() {
		return userId;
	}
	
	
	/*	returns admin password 
	 */
	public String getPassword() {
		return password;
	}
	
	
	/*	returns RMOS that admin belongs to 
	 */
	public Rmos getRmos() {
		return rmos;
	}	
}




