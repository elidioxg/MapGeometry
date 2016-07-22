package geocodigos.mapgeometry.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import geocodigos.mapgeometry.Models.PointModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String dbId = "id_";
    private static String dbName = "database4090";
    private static String dbTable = "points";
    private static String tbName = "name";
    private static String dbDescription = "description";
    private static String dbLatitude = "latitude";
    private static String dbLongitude = "longitude";
    private static String dbNorth = "north";
    private static String dbEast = "east";
    private static String dbSector = "sector";
    private static String dbAltitude = "altitude";
    private static String dbPrecision = "precision";
    private static String dbData = "date";
    private static String dbTime = "time";
    private static String dbSel = "selected";
    private static String dbOrder = "position";

    private Context c;

    public DatabaseHelper(Context context) {
        super(context, dbName, null, 33);
        // TODO Auto-generated constructor stub
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE IF NOT EXISTS " + dbTable +
                //" ("+dbId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " (" + dbId + " INTEGER PRIMARY KEY , " +
                tbName + " TEXT, " + dbDescription + " TEXT, " +
                dbLatitude + " TEXT, " + dbLongitude + " TEXT, " +
                dbNorth + " TEXT, " + dbEast + " TEXT, " + dbSector +
                " TEXT, " + dbAltitude +
                " TEXT, " + dbTime + " TEXT, " + dbData + " TEXT, " +
                dbPrecision + " TEXT, " + dbSel + " TEXT," +
                dbOrder + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + dbTable + " ; ");
        onCreate(db);
    }

    /**
     * Add a Point in the Database
     * @param pointModel
     */
    public void addPoint(PointModel pointModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbId, pointModel.getId());
        contentValues.put(tbName, pointModel.getName());
        contentValues.put(dbDescription, pointModel.getDescription());
        contentValues.put(dbLatitude, pointModel.getlatitude());
        contentValues.put(dbLongitude, pointModel.getLongitude());
        contentValues.put(dbNorth, pointModel.getNorth());
        contentValues.put(dbEast, pointModel.getEast());
        contentValues.put(dbSector, pointModel.getSector());
        contentValues.put(dbAltitude, pointModel.getAltitude());

        contentValues.put(dbData, pointModel.getData());
        contentValues.put(dbTime, pointModel.getTime());

        contentValues.put(dbPrecision, pointModel.getPrecision());
        contentValues.put(dbSel, pointModel.getSelected());
        contentValues.put(dbOrder, pointModel.getOrder());

        db.insert(dbTable, null, contentValues);
        db.close();
    }

    /**
     * If True the point is Selected
     * @param pointModel
     */
    public void updateSelected(PointModel pointModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbSel, pointModel.getSelected());
        db.update(dbTable, contentValues, dbId + "=" + pointModel.getId(), null);
        db.close();
    }

    public void updatePoint(PointModel pointModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(dbId, pointModel.getId());
        contentValues.put(tbName, pointModel.getName());
        contentValues.put(dbDescription, pointModel.getDescription());
        contentValues.put(dbLatitude, pointModel.getlatitude());
        contentValues.put(dbLongitude, pointModel.getLongitude());
        contentValues.put(dbNorth, pointModel.getNorth());
        contentValues.put(dbEast, pointModel.getEast());
        contentValues.put(dbSector, pointModel.getSector());
        contentValues.put(dbAltitude, pointModel.getAltitude());
        contentValues.put(dbPrecision, pointModel.getPrecision());

        contentValues.put(dbData, pointModel.getTime());
        contentValues.put(dbTime, pointModel.getTime());

        contentValues.put(dbSel, pointModel.getSelected());
        contentValues.put(dbOrder, pointModel.getOrder());

        db.update(dbTable, contentValues, dbId + " = " + pointModel.getId(), null);
        db.close();
    }

        /*public void deleteAllFields() {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM "+dbTable+" ;");
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

    /**
     * Delete following ArrayList of Ids
     * @param array
     */
    public void removePoints(ArrayList<String> array) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i <= array.size() - 1; i++) {
                String[] args = {array.get(i)};
                db.delete(dbTable, dbId + " = ? ", args);
            }
            ContentValues contentValues = new ContentValues();
            Cursor cursor = db.rawQuery("SELECT " + dbId + ", " + dbOrder + " FROM "
                    + dbTable + " ORDER BY " + dbOrder + " ;", null);
            if (cursor.moveToFirst()) {
                int order = 0;
                while (cursor.moveToNext()) {
                    PointModel pm = new PointModel();
                    pm.setOrder(String.valueOf(order));
                    contentValues.put(dbOrder, pm.getOrder());
                    db.update(dbTable, contentValues, dbId + " = " +
                            cursor.getString(cursor.getColumnIndex(dbId)), null);
                    order++;
                }
            }
            cursor.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the Order of the Point in the list
     * @param pos
     */
    public void changeOrderUp(int pos) {
        if (pos >= 1) {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.query(false, dbTable, new String[]{dbId, tbName},
                        dbOrder + "=" + String.valueOf(pos), null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        String strId = cursor.getString(cursor.getColumnIndex(dbId));
                        ContentValues cv = new ContentValues();
                        PointModel pm = new PointModel();
                        pm.setOrder(String.valueOf(pos));
                        cv.put(dbOrder, pm.getOrder());
                        db.update(dbTable, cv, dbOrder + " = ? ", new String[]{String.valueOf(pos - 1)});
                        cv = new ContentValues();
                        pm.setOrder(String.valueOf(--pos));
                        cv.put(dbOrder, pm.getOrder());
                        db.update(dbTable, cv, dbId + "= ? ", new String[]{strId});
                    }
                }
                cursor.close();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param pos
     */
    public void changeOrderDown(int pos) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbTable + " ORDER BY " + dbOrder + " ;",
                    null);
            if (pos <= cursor.getCount() - 1) {
                cursor = db.rawQuery("SELECT " + dbId + " FROM " + dbTable + " WHERE " + dbOrder +
                        "= ? ", new String[]{String.valueOf(pos)});
                //   "="+String.valueOf(pos), null);
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        String strId = cursor.getString(cursor.getColumnIndex(dbId));
                        ContentValues cv = new ContentValues();
                        PointModel pm = new PointModel();
                        pm.setOrder(String.valueOf(pos));
                        cv.put(dbOrder, pm.getOrder());
                        db.update(dbTable, cv, dbOrder + "= ? ", new String[]{String.valueOf(pos + 1)});
                        cv = new ContentValues();
                        pm.setOrder(String.valueOf(++pos));
                        cv.put(dbOrder, pm.getOrder());
                        db.update(dbTable, cv, dbId + "= ? ", new String[]{strId});
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all Points in the Database
     * @return
     */
    public ArrayList<PointModel> getPoints() {
        ArrayList<PointModel> point = new ArrayList<PointModel>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbTable + " ORDER BY " + dbOrder + " ASC ;", null);

            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        PointModel pointModel = new PointModel();
                        pointModel.setId(cursor.getString(cursor.getColumnIndex(dbId)));
                        pointModel.setName(cursor.getString(cursor.getColumnIndex(tbName)));
                        pointModel.setDescription(cursor.getString(cursor.getColumnIndex(dbDescription)));
                        pointModel.setLatidude(cursor.getString(cursor.getColumnIndex(dbLatitude)));
                        pointModel.setLongitude(cursor.getString(cursor.getColumnIndex(dbLongitude)));
                        pointModel.setNorth(cursor.getString(cursor.getColumnIndex(dbNorth)));
                        pointModel.setEast(cursor.getString(cursor.getColumnIndex(dbEast)));
                        pointModel.setSector(cursor.getString(cursor.getColumnIndex(dbSector)));
                        pointModel.setAltitude(cursor.getString(cursor.getColumnIndex(dbAltitude)));
                        pointModel.setPrecision(cursor.getString(cursor.getColumnIndex(dbPrecision)));
                        pointModel.setTime(cursor.getString(cursor.getColumnIndex(dbTime)));
                        pointModel.setData(cursor.getString(cursor.getColumnIndex(dbData)));
                        pointModel.setSelected(cursor.getString(cursor.getColumnIndex(dbSel)));
                        pointModel.setOrder(cursor.getString(cursor.getColumnIndex(dbOrder)));
                        point.add(pointModel);
                        Log.i(pointModel.getName(), " Order: " + pointModel.getOrder());
                    } while (cursor.moveToNext());
                }

            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return point;
    }

    /**
     * Get a Point in Database
     * @param name
     * @return
     */
    public ArrayList<PointModel> getPoints(String name) {
        ArrayList<PointModel> point = new ArrayList<PointModel>();
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbTable + " WHERE " + tbName + " = ? ;",
                    new String[]{name});
            /*Cursor cursor = db.query(true, dbTable, new String[]{tbName, dbDescription,
                            dbLatitude, dbLongitude, dbNorth, dbEast, dbSector,
                            dbAltitude, dbTime, dbDatxa, dbSel},
                    tbName + " = ? ", new String[]{name}, null, null, null, null, null);
            */
            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        PointModel pointModel = new PointModel();
                        pointModel.setId(cursor.getString(cursor.getColumnIndex(dbId)));
                        pointModel.setName(cursor.getString(cursor.getColumnIndex(tbName)));
                        pointModel.setDescription(cursor.getString(cursor.getColumnIndex(dbDescription)));
                        pointModel.setLatidude(cursor.getString(cursor.getColumnIndex(dbLatitude)));
                        pointModel.setLongitude(cursor.getString(cursor.getColumnIndex(dbLongitude)));
                        pointModel.setNorth(cursor.getString(cursor.getColumnIndex(dbNorth)));
                        pointModel.setEast(cursor.getString(cursor.getColumnIndex(dbEast)));
                        pointModel.setSector(cursor.getString(cursor.getColumnIndex(dbSector)));
                        pointModel.setAltitude(cursor.getString(cursor.getColumnIndex(dbAltitude)));
                        pointModel.setPrecision(cursor.getString(cursor.getColumnIndex(dbPrecision)));
                        pointModel.setTime(cursor.getString(cursor.getColumnIndex(dbTime)));
                        pointModel.setData(cursor.getString(cursor.getColumnIndex(dbData)));
                        pointModel.setSelected(cursor.getString(cursor.getColumnIndex(dbSel)));
                        pointModel.setOrder(cursor.getString(cursor.getColumnIndex(dbOrder)));
                        point.add(pointModel);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return point;
    }

    /**
     * Check if Id is available
     * @param strId
     * @return
     */
    public boolean checkId(String strId) {
        boolean exists = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbId + " FROM " + dbTable + " WHERE " + dbId + " = ? ",
                new String[]{strId});
            /*Cursor cursor = db.query(true, dbTable, new String[] { dbId },
                    dbId+" = ? ", new String[] {strId}, null, null, null, null, null);
            */
        if (cursor.getCount() > 0) {
            exists = true;
        }
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Get the size of database
     * @return
     */
    public int getSize() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbId + " FROM " + dbTable, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}