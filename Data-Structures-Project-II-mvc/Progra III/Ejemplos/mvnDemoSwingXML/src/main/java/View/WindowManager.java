
package View;

import DataStructures.User;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class WindowManager {
    
    public static void LoadWelcomeView(User user, VLogin vista){
        vista.dispose();
        new VWelcome().Load(user);
        
    }
    
    public static void showError(JFrame parent, String title, String message)
    {
        //JOptionPane.showMessageDialog(frame, "Welcome to Swing!");
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
