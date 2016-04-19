package geocodigos.mapgeometry.Utils;

public class CoordinatesArray {
    public String formatCoordinateToDMS(String letter, String deg, String min,
                                      String sec){
        return deg+"\u00B0 "+min+"' "+sec+"'' "+letter;
    }
}
