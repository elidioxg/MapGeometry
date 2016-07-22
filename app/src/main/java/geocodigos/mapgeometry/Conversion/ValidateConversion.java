package geocodigos.mapgeometry.Conversion;

public class ValidateConversion {
    private double minNorth = 0;
    private double maxNorth = 10000000;
    private double minEast = 160000;
    private double maxEast = 834000;

    /**
     * Validate Lat/Lon coordinates
     * @param latitude
     * @param longitude
     * @return
     */
    public int validate(double latitude, double longitude){
        if (latitude <= -90.0 || latitude >= 90.0) {
            return 1;
        }
        if(longitude <= -180.0 || longitude >= 180.0)
        {
            return 2;
        }
        return 0;
    }

    /**
     * Validate UTM Coordinates
     * @param setor
     * @param norte
     * @param leste
     * @return
     */
    public int validate (String setor, String norte, String leste){
        String sec[] = setor.split(" ");
        if(sec.length <2){
            return 1;
        }
        if(Double.parseDouble(norte) <minNorth || Double.parseDouble(norte) >maxNorth){
            return 2;
        }
        if(Double.parseDouble(leste) <minEast || Double.parseDouble(leste) >maxEast){
            return 3;
        }
        return 0;
    }

    /**
     * Validate Degrees, minutes and seconds Coordinates
     * @param latgrau
     * @param latmin
     * @param latseg
     * @param longrau
     * @param lonmin
     * @param lonseg
     * @return
     */
    public int validate(String latgrau, String latmin, String latseg,
                        String longrau, String lonmin, String lonseg ){
        //TODO
        if(Double.parseDouble(latgrau) >90 || Double.parseDouble(latgrau) <-90){
            return 1;
        }
        if (Double.parseDouble(longrau) > 180 || Double.parseDouble(longrau) < -180) {
            return 2;
        }
        if (Double.parseDouble(latmin) >= 60 || Double.parseDouble(latmin) < 0) {
            return 3;
        }
        if (Double.parseDouble(lonmin) >= 60 || Double.parseDouble(lonmin) < 0) {
            return 4;
        }
        if (Double.parseDouble(latseg) >= 60 || Double.parseDouble(latseg) < 0) {
            return 5;
        }
        if (Double.parseDouble(lonseg) >= 60 || Double.parseDouble(lonseg) < 0) {
            return 6;
        }
        return 0;
    }

}