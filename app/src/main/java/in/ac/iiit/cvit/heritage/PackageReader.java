package in.ac.iiit.cvit.heritage;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PackageReader {
    /**
     * This method is called from ActivityMain
     * This method gets all the interest points from the download package of particular site
     *
     */


    public String _packageName;
    InterestPoint interestPoint;
    ArrayList<InterestPoint> InterestPoints;

    public static final String LOGTAG = "Heritage";

    public PackageReader(String packageName){
        _packageName = packageName;
        InterestPoints = new ArrayList<InterestPoint>();
        readFromFile();
    }

    /**
     * This is the method which is accessible from outside
     * @return List of interest points
     */
    public ArrayList<InterestPoint> getContents(){
        return InterestPoints;
    }

    /**
     * This method is called from readFromFile
     * This method extracts information from xml file according to their tags in xml file
     * This method calls another method InterestPoint to set the interest points
     * It stores obtained interest points in InterestPoint array
     * @param xml It is the string containing all the data from the d.xml file
     */
    private void readContentsFromString(String xml){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(xml);
            ByteArrayInputStream xmlfile = null;
            try {
                xmlfile = new ByteArrayInputStream(
                        xmlStringBuilder.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Document doc = null;
            try {
                doc = builder.parse(xmlfile);

                //get the first element
                Element root = doc.getDocumentElement();

                //get all the child elements
                NodeList ips = root.getChildNodes();

                for(int i=0; i<ips.getLength(); i++){

                    if(ips.item(i).getNodeType() == Node.ELEMENT_NODE){

                        //We are creating an interest point object to store all the relevant data available
                        interestPoint = new InterestPoint();

                        //getting a list of all the child elements
                        NodeList keys = ips.item(i).getChildNodes();

                        for(int j=0; j<keys.getLength(); j++){
                            if(keys.item(j).getNodeType() == Node.ELEMENT_NODE){
                                Element key = (Element)keys.item(j);
                                interestPoint.set(key.getNodeName(), key.getTextContent());
                            }
                        }
                        //Here we are storing each InterestPoint object in an InterestPoint array
                        InterestPoints.add(interestPoint);
                    }
                }
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when this class is initialised.
     * It calls readTextFile and readContentsFromString to get the contents from heritage storage folder
     */
    private void readFromFile(){
        File baseLocal = Environment.getExternalStorageDirectory();

        File xmlfile = new File(baseLocal, "heritage/extracted/" + _packageName + "/d.xml");
        try {
            FileInputStream xmlStream = new FileInputStream(xmlfile);
            String contents = readTextFile(xmlStream);
            readContentsFromString(contents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is called from readFromFile
     *This class reads the entire d.xml file into a string. THis string is used as input to readContentsFromString
     * @param inputStream It is an InputStream file containing location of the file to be read into string
     * @return String containing the contents of the d.xml file
     */
    private String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
