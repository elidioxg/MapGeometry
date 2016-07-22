package geocodigos.mapgeometry.Conversion;

import java.util.ArrayList;

public class DMSConversion {
    String formatPrecision = "%.5f";
    public void DMSConversion(){

    }

    /**
     * Convert Decimal Degrees to Degrees, Minutes and Seconds
     * @param degrees
     * @return
     */
    public String convertFromDegrees(double degrees) {
        double aux, aux2, deg, min, sec;
        deg = (long) degrees;
        aux =  ((degrees - deg)*60);
        min = (long) aux;
        aux2 =  (aux - min)*60;
        sec = (long) aux2;
        if(sec>=60){
            min=min+1;
            sec=sec-60.;
        }
        if(min>=60){
            deg=deg+1;
            min=min-60.;
        }
        return doubleToStr(deg, min, sec);
    }

    /**
     * Convert
     * @param positive If North or East then is positive
     * @param degrees
     * @param min
     * @param seg
     * @return Decimal Degrees Coordinates
     */
    public String convertToDegrees(boolean positive, String degrees, String min, String seg) {
        double deg = Double.parseDouble(degrees);
        double minutes = Double.parseDouble(min);
        double seconds = Double.parseDouble(seg);
        minutes = minutes + (seconds/60);
        deg = deg + (minutes/60);
        if(!positive) {
            deg *=-1;
        }
        return doubleToStr(deg);
    }

    /**
     *
     * @param positive If North or East then is Positive
     * @param degrees
     * @param min
     * @param seg
     * @return Cordinates in Decimal Degrees
     */
    public String convertToDegrees(boolean positive, double degrees, double min, double seg) {
        min = min + (seg/60);
        degrees = degrees + (min/60);
        if(!positive) {
            degrees *=-1;
        }
        return doubleToStr(degrees);
    }

    /**
     * Format coordinates to String
     * @param degrees
     * @return
     */
    public String doubleToStr(double degrees) {
        String result = String.format(formatPrecision, degrees);
        result = result.replace(",", ".");
        return result;
    }

    /**
     * Get Double values and convert to String
     * @param deg
     * @param min
     * @param sec
     * @return String with values of Degrees, Minutes and Seconds
     */
    public String doubleToStr(double deg, double min, double sec){
        String aux1,aux2,aux3;
        if(deg<0){deg*=-1;}
        if(min<0){min*=-1;}
        if(sec<0){sec*=-1;}
        aux1 = String.format("%.0f",deg);
        aux2 = String.format("%.0f",min);
        aux3 = String.format("%.0f",sec);
        return (aux1+" "+aux2+ " "+aux3);
    }

    /**
     * Convert Lat/Lon to Degrees, Minutes and Seconds
     * @param lat
     * @param lon
     * @return
     */
    public ArrayList<String> DegreesConversion(double lat, double lon) {
        ArrayList<String> arrayList = new ArrayList<>();
        String strLat = convertFromDegrees(lat);
        String strLon = convertFromDegrees(lon);
        String coordLat[] = strLat.split(" ");
        String coordLon[] = strLon.split(" ");
        arrayList.add(coordLat[0]);
        arrayList.add(coordLat[1]);
        arrayList.add(coordLat[2]);
        arrayList.add(coordLon[0]);
        arrayList.add(coordLon[1]);
        arrayList.add(coordLon[2]);
        return arrayList;
    }

}