package geocodigos.mapgeometry.Kml;

import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;

import geocodigos.mapgeometry.Models.PointModel;

/**
 * Created by elidioxg on 05/05/16.
 */
public class KmlPolygon {

    /**
     *
     * @param layerName
     * @param array
     * @return
     */
    public  static String createLayer(String layerName, ArrayList<PointModel> array){
        XmlSerializer xml = Xml.newSerializer();
        StringWriter sw = new StringWriter();
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

            xml.startTag("", "name");
            xml.text("");//pegar nome do mapa
            xml.endTag("", "name");
            xml.startTag("", "Polygon");
            xml.startTag("", "extrude");
            xml.text("1");
            xml.endTag("", "extrude");
            xml.startTag("", "LinearRing");
            xml.startTag("", "coordinates");
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
            xml.endTag("", "coordinates");
            xml.endTag("", "LinearRing");
            xml.endTag("", "Polygon");

            xml.endTag("", "Document");
            xml.endTag("", "kml");
            xml.endDocument();
        } catch (Exception e){
            e.printStackTrace();
        }

        return sw.toString();
    }

}
