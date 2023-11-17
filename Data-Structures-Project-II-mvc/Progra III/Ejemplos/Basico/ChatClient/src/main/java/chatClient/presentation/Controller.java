package chatClient.presentation;

import chatClient.logic.ITarget;
import chatClient.logic.Service;
import chatProtocol.Message;
import chatProtocol.User;

import java.util.ArrayList;

public class Controller implements ITarget {
    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        Service.instanceListener().addTarget(this);
        view.setController(this);
        view.setModel(model);
    }

    public void login(User u) throws Exception{
        User logged= Service.instance().login(u);
        model.setCurrentUser(logged);
        model.commit(Model.USER);
    }

    public void post(String text){
        Message message = new Message();
        message.setMessage(text);
        message.setSender(model.getCurrentUser());
        Service.instance().post(message);
        model.commit(Model.CHAT);
    }

    public void logout(){
        try {
            Service.instance().logout(model.getCurrentUser());
            model.setMessages(new ArrayList<>());
            model.commit(Model.CHAT);
        } catch (Exception ex) {
        }
        model.setCurrentUser(null);
        model.commit(Model.USER+Model.CHAT);
    }
        
    public void deliver(Message message){
        model.messages.add(message);
        model.commit(Model.CHAT);       
    }    
}
