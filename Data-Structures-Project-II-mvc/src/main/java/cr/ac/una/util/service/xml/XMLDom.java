// Capa Data
package cr.ac.una.util.service.xml;

// Logic
import cr.ac.una.util.graphs.*;

// Clases para funcionamiento
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import cr.ac.una.util.graphs.exceptions.VertexNotFoundException;
import cr.ac.una.util.trees.exceptions.RootNotNullException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLDom {

    public void saveData(Set<MGraph> mazes) {

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element root = doc.createElement("Mazes");
            doc.appendChild(root);

            for (MGraph maze : mazes) {
                Element mazeElement = doc.createElement("Maze");
                root.appendChild(mazeElement);

                int sizeX = maze.getSizeX();
                int sizeY = maze.getSizeY();
                Element sizeElement = doc.createElement("Size");
                sizeElement.setAttribute("x", String.valueOf(sizeX));
                sizeElement.setAttribute("y", String.valueOf(sizeY));
                mazeElement.appendChild(sizeElement);

                String label = maze.getLabel();
                Element labelElement = doc.createElement("Label");
                labelElement.setAttribute("name",label);
                mazeElement.appendChild(labelElement);

                String creationDate = maze.getFormattedCreationDate();
                Element dateElement = doc.createElement("Date");
                dateElement.setAttribute("creation",creationDate);
                mazeElement.appendChild(dateElement);

                Set<Edge<VInfo<Character>>> list = maze.getMATRIX_EDGES();
                Iterator<Edge<VInfo<Character>>> iterator = list.iterator();
                Element edgesList = doc.createElement("Edges");
                mazeElement.appendChild(edgesList);
                for(int i = 0; i < list.size(); i++){
                    Edge<VInfo<Character>> edge = iterator.next();
                    Element edgeElement = doc.createElement("edge");
                    edgesList.appendChild(edgeElement);

                    Element weightElement = doc.createElement("weight");
                    double weight = edge.getWeight();
                    weightElement.setAttribute("amount", String.valueOf(weight));
                    edgeElement.appendChild(weightElement);

                }

            }

            // Guardar el archivo XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StreamResult result = new StreamResult(new File("mazes.xml"));
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public Set<MGraph> loadData() {
        Set<MGraph> mazes = new HashSet<>();

        try {
            File xmlFile = new File("mazes.xml");
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList mazeNodes = doc.getElementsByTagName("Maze");
            for (int i = 0; i < mazeNodes.getLength(); i++) {
                Element mazeElement = (Element) mazeNodes.item(i);
                int sizeX = Integer.parseInt(mazeElement.getElementsByTagName("Size").item(0).getAttributes().getNamedItem("x").getTextContent());
                int sizeY = Integer.parseInt(mazeElement.getElementsByTagName("Size").item(0).getAttributes().getNamedItem("y").getTextContent());
                String label = mazeElement.getElementsByTagName("Label").item(0).getAttributes().getNamedItem("name").getTextContent();
                String date = mazeElement.getElementsByTagName("Date").item(0).getAttributes().getNamedItem("creation").getTextContent();

                MGraph maze = new MGraph(label,sizeX,sizeY,false);
                maze.setCreationDate(date);

                NodeList edgeNodes = mazeElement.getElementsByTagName("edge");
                List<Double> weightsList = new ArrayList<>();
                for(int j = 0; j < edgeNodes.getLength(); j++){
                    double weight = Double.parseDouble(mazeElement.getElementsByTagName("weight").item(j).getAttributes().getNamedItem("amount").getTextContent());
                    weightsList.add(weight);
                }

                Iterator<Double> iterator = weightsList.iterator();
                Vertex<VInfo<Character>>[][] MATRIX = maze.getMatrix();
                for (int x = 0; x < sizeX; x++) {
                    for (int y = 0; y < sizeY; y++) {
                        if (x != sizeX - 1) {
                            maze.addEdge(MATRIX[x][y], MATRIX[x + 1][y], iterator.next());
                        }
                        if (y != sizeY - 1) {
                            maze.addEdge(MATRIX[x][y], MATRIX[x][y + 1], iterator.next());
                        }
                    }
                };

                maze.generateMaze();
                mazes.add(maze);
            }
        } catch (ParserConfigurationException |IOException | SAXException | VertexNotFoundException e) {
            throw new RuntimeException(e);} catch (RootNotNullException e) {
            throw new RuntimeException(e);
        } catch (cr.ac.una.util.trees.exceptions.VertexNotFoundException e) {
            throw new RuntimeException(e);
        }
        return mazes;
    }}