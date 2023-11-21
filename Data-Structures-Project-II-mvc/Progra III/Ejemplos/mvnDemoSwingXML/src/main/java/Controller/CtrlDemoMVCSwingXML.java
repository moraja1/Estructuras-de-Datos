
package Controller;

import DataStructures.User;
import Model.UserManager;
import View.WindowManager;
import View.VLogin;
import javax.xml.transform.TransformerException;

/**
 *
 * @author 
 */
public class CtrlDemoMVCSwingXML {
    
    protected VLogin Login;
    protected User user;
    protected UserManager modelUserManager;
    
    public CtrlDemoMVCSwingXML() throws TransformerException{
        modelUserManager = new UserManager();
        modelUserManager.CreateUserFile();
        modelUserManager.insertUser(new User("9","Jose","Vindas","profe","qwefaqsdf@"));
        modelUserManager.insertUser(new User("11","Jose2","Vindas2","profe2","Aqwefaqsdf@"));
        modelUserManager.updateUser(new User("9","JoseUpdated","VindasUpdated","profeUpdated","qwefaqsdf@Updated"));
        Login = new VLogin(this);
        
        
    }
    
    public void InitGUI()
    {
        Login.setEnterButtonStatus(false);
        Login.Load();
        System.out.println("End of InitGUI");
        System.out.println("-----------------------------------------------");

}
    
    public void LoginEventCall() throws ClassNotFoundException{

        user = modelUserManager.CheckCredentials(Login.getUsuarioTextField(), Login.getPassTextField());

        /*if credential are valid*/
        if(!user.getID().equals("0"))
            WindowManager.LoadWelcomeView(user, Login);
        else
            WindowManager.showError(Login,"Error", "Credenciales inválidos");
        /*else*/
        
        
    }
}
