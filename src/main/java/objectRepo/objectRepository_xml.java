package objectRepo;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.*;

public class objectRepository_xml {
    public static String getLocator(String pageName,String elementName){
        try{
            File objectRepo = new File("C:\\Users\\ALEKHYA\\IdeaProjects\\Selenium_Automation\\Object Repository\\Locators.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(objectRepo);
            doc.getDocumentElement().normalize();

            NodeList pages = doc.getElementsByTagName("page");
            for(int i=0;i<pages.getLength();i++){
                Element page = (Element) pages.item(i);
                if(page.getAttribute("name").equals(pageName)){
                    NodeList elements = page.getElementsByTagName("Element");
                    for(int j=0;j<elements.getLength();j++){
                        Element element = (Element) elements.item(j);
                        if(element.getAttribute("name").equals(elementName)){
                            Element locator = (Element) element.getElementsByTagName("Locator").item(0);
                            return locator.getTextContent() + "::" + locator.getAttribute("type");
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Locator not found for page: " + pageName + ", element: " + elementName);
    }
}
