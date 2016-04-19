package geocodigos.mapgeometry.implementation;

import java.util.Calendar;

public class getTime {

    public String returnTime() {
        Calendar c = Calendar.getInstance();

        int iHora = c.get(Calendar.HOUR_OF_DAY);
        int iMin = c.get(Calendar.MINUTE);
        int iSeg = c.get(Calendar.SECOND);
        String sqlHora = String.valueOf(iHora)+":"+
                String.valueOf(iMin)+":"+String.valueOf(iSeg);
        return sqlHora;
    }
}
