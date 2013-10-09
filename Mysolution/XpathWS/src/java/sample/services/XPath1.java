/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.services;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Massimiliano
 */
public class XPath1 {

    private XPath xpath;
    private Document doc;

    
    public XPath1() {
        initDocument();
    }

    private void initDocument() {
        System.out.println("init");
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        try {
            DocumentBuilder builder;
            builder = domFactory.newDocumentBuilder();
            doc = builder.parse(getClass().getClassLoader().getResourceAsStream("people.xml"));
            getXPAthObj();
        } catch (ParserConfigurationException ex) {
            System.out.println("pars");
        } catch (SAXException ex) {
            System.out.println("sax");
        } catch (IOException ex) {
            System.out.println("IO");
        }
    }

    private void getXPAthObj() {
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
    }
    
    private Node getPerson(String lastname){
          try {
            //get the person
              System.out.println("lastname:"+lastname);
            XPathExpression exp = xpath.compile("/people/person[lastname='"+lastname+"']");
            Node personNode = (Node)exp.evaluate(doc, XPathConstants.NODE);
            return personNode;
        } catch (XPathExpressionException ex) {
             System.out.println("problem");
            return null;
        }
    }
    /**
     * 
     * @param lastname -> ensure is unique
     * @return 
     * @throws javax.xml.xpath.XPathExpressionException 
     */
    public double getWeight(String lastname) throws XPathExpressionException{
        Double res = 0.0;
        Node person = getPerson(lastname);
        if(person!=null){
            System.out.println("not null");
            // non metto lo / iniziale perchè non parto + dalla root ma da un nodo
            XPathExpression exp = xpath.compile("healthprofile/weitgh");
            Node profile = (Node)exp.evaluate(person, XPathConstants.NODE);
            res = Double.valueOf(profile.getTextContent());
        }else{
            System.out.println("null person");
        }
       
        return res;
    }
    
    public double getHeight(String lastname) throws XPathExpressionException{
        Double res = 0.0;
         Node person = getPerson(lastname);
        if(person!=null){
            System.out.println("not null");
            XPathExpression exp = xpath.compile("healthprofile/height");
            Node profile = (Node)exp.evaluate(person, XPathConstants.NODE);
            res = Double.valueOf(profile.getTextContent());
        }else{
            System.out.println("null person");
        }
        return res;
    }
}
