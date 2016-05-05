package geocodigos.mapgeometry.Kml;

import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;

import geocodigos.mapgeometry.Models.PointModel;

/**
 * Created by elidioxg on 05/05/16.
 */
public class KmlLine {
    /**
     *
     * @param layerName
     * @param array
     * @return
     */
    public static String createLayer(String layerName, ArrayList<PointModel> array){
        StringWriter sw = new StringWriter();
        XmlSerializer xml = Xml.newSerializer();
        String altitude = "0";
        try{
            xml.setOutput(sw);
            xml.startDocument("UTF-8", true);
            xml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            xml.startTag("", "kml").attribute("", "xmlns",
                    "http://www.opengis.net/kml/2.2");
            xml.startTag("", "Document");
            xml.startTag("", "name");
            xml.text(layerName);
            xml.endTag("", "name");

            xml.startTag("", "Placemark");
            xml.startTag("", "LineString");
            xml.startTag("", "Coordinates");
            for (int i = 0; i < array.size(); i++) {
                altitude = array.get(i).getAltitude();
                if (altitude.toString().isEmpty()) {
                    altitude = "0";
                }
                if (Integer.parseInt(array.get(i).getSelected().trim()) == 1) {
                    xml.text(array.get(i).getlatitude() + "," +
                            array.get(i).getLongitude() + "," + altitude +
                            System.getProperty("line.separator"));
                }
            }
            xml.endTag("", "Coordinates");
            xml.endTag("", "LineString");
            xml.endTag("", "Placemark");

            xml.endTag("", "Document");
            xml.endTag("", "kml");
            xml.endDocument();

        }catch (Exception e){
            e.printStackTrace();
        }

        return xml.toString();
    }

}
