package locatorHelper;

import objectRepo.objectRepository_xml;
import org.openqa.selenium.By;

public class locatorHelper {    public static By getBy(String pageName , String elementName){
    String locatorData = objectRepository_xml.getLocator(pageName,elementName);
    String[] parts = locatorData.split("::");
    String value = parts[0];
    String type = parts[1];
    return switch (type) {
        case "id" -> By.id(value);
        case "xpath" -> By.xpath(value);
        case "className" -> By.className(value);
        case "css" -> By.cssSelector(value);
        case "tagName" -> By.tagName(value);
        case "linkText" ->By.linkText(value);
        case "name" -> By.name(value);
        default -> throw new RuntimeException("Unknown locator type: " + type);
    };
}
}
