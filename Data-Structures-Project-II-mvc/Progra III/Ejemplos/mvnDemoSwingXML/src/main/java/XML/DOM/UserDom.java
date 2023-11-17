
package XML.DOM;

import DataStructures.User;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author josev
 */
public class UserDom {

    
    private static String xmlFilePath = "";
    
    public UserDom(String filepath)
    {
        xmlFilePath = filepath;
        CreateFile();
    }
    
    private void CreateFile()
    {
        try {
        
            File xmlFile = new File(xmlFilePath);
        
            if(!xmlFile.exists())
            {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();

                // root element
                Element root = document.createElement("users"); //users
                document.appendChild(root);

                // user element
                Element user = document.createElement("user");

                root.appendChild(user);

                // set an attribute to staff element
                Attr attr = document.createAttribute("id");
                attr.setValue("1");
                user.setAttributeNode(attr);

                // firstname element
                Element firstName = document.createElement("firstname");
                firstName.appendChild(document.createTextNode("John"));
                user.appendChild(firstName);

                // lastname element
                Element lastname = document.createElement("lastname");
                lastname.appendChild(document.createTextNode("Doe"));
                user.appendChild(lastname);

                // email element
                Element username = document.createElement("username");
                username.appendChild(document.createTextNode("johndoe"));
                user.appendChild(username);

                // password elements
                Element password = document.createElement("password");
                password.appendChild(document.createTextNode("mypass"));
                user.appendChild(password);

                // create the xml file
                //transform the DOM Object to an XML File
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(xmlFilePath));

                // If you use
                // StreamResult result = new StreamResult(System.out);
                // the output will be pushed to the standard output ...
                // You can use that for debugging

                transformer.transform(domSource, streamResult);

                System.out.println("Done adding new user to XML File");
            }
        } catch (ParserConfigurationException | TransformerException pce) {
            System.out.println(pce.getMessage());
            
        }
    }

    @SuppressWarnings("empty-statement")
     public User CheckCredentials(String Username, String Password) throws SAXException
     {
        User user = new User();
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            if(!xmlFile.exists())
                return user;
            ;
            Document doc = builder.parse(xmlFile);
            NodeList userNodes = doc.getElementsByTagName("user");
            for(int i = 0; i < userNodes.getLength(); i++) {            
                Node userNode = userNodes.item(i);
                if(userNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element userElement = (Element) userNode;
                     
                     String username = userElement.getElementsByTagName("username").item(0).getTextContent();
                     String password = userElement.getElementsByTagName("password").item(0).getTextContent();
                     
                     if(username.equals(Username) && password.equals(Password))
                     {
                        //int id = Integer.parseInt(userElement.getAttribute("id"));
                        String firstname = userElement.getElementsByTagName("firstname").item(0).getTextContent();
                        String lastname = userElement.getElementsByTagName("lastname").item(0).getTextContent();
                        user = new User(userElement.getAttribute("id"), firstname, lastname,Username,Password);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException pce) {
        }
         return user;
     }
    
    public boolean AddUser(User user) throws TransformerConfigurationException, TransformerException
    {
        boolean result = false;
        boolean idexist = false;
      
        //----------------Check if user id already exists--------------
        
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            if(!xmlFile.exists())
                return result;
            Document doc = builder.parse(xmlFile);
            NodeList userNodes = doc.getElementsByTagName("user");
            for(int i = 0; i < userNodes.getLength(); i++) {            
                Node userNode = userNodes.item(i);
                if(userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    //int id = Integer.parseInt(userElement.getAttribute("id"));

                    if(user.getID().equals(userElement.getAttribute("id")))
                    {
                        idexist = true;
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException pce) {}
        
        
        
        //---------------If user id does not exits---------------------
        try {
            if(!idexist)
            {
                File xmlFile = new File(xmlFilePath);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                if(!xmlFile.exists())
                    return result;
                Document document = builder.parse(xmlFile);
                //NodeList userNodes = document.getElementsByTagName("users");

                Node root = document.getElementsByTagName("users").item(0);

                //Element root = document.getElementById("users");
                // user element
                Element newuser = document.createElement("user");

                root.appendChild(newuser);

                // set an attribute to staff element
                Attr attr = document.createAttribute("id");
                attr.setValue(user.getID());
                newuser.setAttributeNode(attr);


                // firstname element
                Element firstName = document.createElement("firstname");
                firstName.appendChild(document.createTextNode(user.getFirstname()));
                newuser.appendChild(firstName);

                // lastname element
                Element lastname = document.createElement("lastname");
                lastname.appendChild(document.createTextNode(user.getLastname()));
                newuser.appendChild(lastname);

                // email element
                Element username = document.createElement("username");
                username.appendChild(document.createTextNode(user.getUsername()));
                newuser.appendChild(username);

                // department elements
                Element password = document.createElement("password");
                password.appendChild(document.createTextNode(user.getPassword()));
                newuser.appendChild(password);

                 // create the xml file
                //transform the DOM Object to an XML File
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(xmlFilePath));

                // If you use
                // StreamResult result = new StreamResult(System.out);
                // the output will be pushed to the standard output ...
                // You can use that for debugging

                transformer.transform(domSource, streamResult);

                result = true;
            }

        } catch (ParserConfigurationException | IOException | SAXException pce) {
        }
        
        return result;
    }
     
    public boolean UpdateUser(User user) throws TransformerConfigurationException, TransformerException
    {
        boolean result = false;
        
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            if(!xmlFile.exists())
                return result;
            Document doc = builder.parse(xmlFile);
            NodeList userNodes = doc.getElementsByTagName("user");
            for(int i = 0; i < userNodes.getLength(); i++) {            
                Node userNode = userNodes.item(i);
                if(userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    //int id = Integer.parseInt(userElement.getAttribute("id"));
                     
                    if(user.getID().equals(userElement.getAttribute("id")))
                    {
                        userElement.getElementsByTagName("firstname").item(0).setTextContent(user.getFirstname());
                        userElement.getElementsByTagName("lastname").item(0).setTextContent(user.getLastname()); 
                        userElement.getElementsByTagName("password").item(0).setTextContent(user.getPassword()); 
                        
                        // write the DOM object to the file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();

                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource domSource = new DOMSource(doc);

                        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
                        transformer.transform(domSource, streamResult);
                        
                        System.out.println("Done updating the user to XML File");
                        
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException pce) {
        }
        
        return result;
    }
    
    
    
}
