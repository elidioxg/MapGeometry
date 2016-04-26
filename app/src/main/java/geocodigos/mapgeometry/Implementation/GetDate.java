package geocodigos.mapgeometry.implementation;

import java.util.Calendar;

public class GetDate {

    /**
     *
     * @return Formated Date YYYY-MM-DD
     */
    public String returnDate() {
        Calendar c = Calendar.getInstance();
        int iAno = c.get(Calendar.YEAR);
        int iMes = c.get(Calendar.MONTH);
        int iDia = c.get(Calendar.DAY_OF_MONTH);
        String sqlData =
                String.valueOf(iAno)+"-"+String.valueOf(iMes+1)+
                        "-"+String.valueOf(iDia);
        return sqlData;
    }
}
