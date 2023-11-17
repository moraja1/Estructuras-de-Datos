
package DataStructures;

/**
 *
 * @author josev
 */
public class User {
    
    private String ID;
    
    private String Firstname;
    
    private String LastName;
    
    private String Username;
    
    private String Password;
    
    
    public User(){
        this.ID = "0";
    }
    
    public User(String ID, String Firstname, String Lastname, String Username, String Password){
        
        this.ID = ID;
        this.Firstname = Firstname;
        this.LastName = Lastname;
        this.Username = Username;
        this.Password = Password;
    
    }
    
    public String getFirstname()
    {
        return this.Firstname;
    }
    public String getLastname()
    {
        return this.LastName;
    }
    
    public String getUsername()
    {
        return this.Username;
    }
    public String getPassword()
    {
        return this.Password;
    }
    public String getID()
    {
        return this.ID;
    }
    
    
    public void setUserName(String Username)
    {
        this.Username= Username;
    }
    public void getPassword(String Password)
    {
        this.Password= Password;
    }
    
    


        
    @Override
    public String toString() {
        return"";
    }

    public void setUsername(String userTextField) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setPassword(String passTextField) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
