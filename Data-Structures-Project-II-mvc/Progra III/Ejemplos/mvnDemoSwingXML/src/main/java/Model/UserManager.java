
package Model;


import DataStructures.User;
import XML.DOM.UserDom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * @author 
 */
public class UserManager {
    
    private  UserDom UserFile;

    public void CreateUserFile()
    {
        UserFile = new UserDom("usuarios.xml");
    }
    
    public boolean insertUser(User user) throws TransformerException
    {
        return UserFile.AddUser(user);
    }

    public User getUserbyID()
    {
        User user = new User();
    
        return user;
    }
    
    public boolean updateUser(User user) throws TransformerException
    {
        return UserFile.UpdateUser(user);
    }
    
    public User CheckCredentials(String User, String Password)
    {
        try {
            return UserFile.CheckCredentials(User, Password);
        } catch (SAXException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new User();
    }
    
    
    
    
/*    
    

  */  
}
