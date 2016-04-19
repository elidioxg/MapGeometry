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
    private ArrayList<PointModel> ponto = new ArrayList<PointModel>();
    private static String dbId = "id_";
    private static String dbName = "database4090";
    private static String dbTable = "pontos";
    private static String dbRegister = "registro";
    private static String dbDescription = "descricao";
    private static String dbLatitude = "latitude";
    private static String dbLongitude = "longitude";
    private static String dbNorte = "norte";
    private static String dbLeste = "leste";
    private static String dbSetor = "setor";
    private static String dbAltitude = "altitude";
    private static String dbPrecisao = "precisao";
    private static String dbData = "data";
    private static String dbHora = "hora";
    private static String dbSel = "selecionado";
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
                dbRegister + " TEXT, " + dbDescription + " TEXT, " +
                dbLatitude + " TEXT, " + dbLongitude + " TEXT, " +
                dbNorte + " TEXT, " + dbLeste + " TEXT, " + dbSetor +
                " TEXT, " + dbAltitude +
                " TEXT, " + dbHora + " TEXT, " + dbData + " TEXT, " +
                dbPrecisao + " TEXT, " + dbSel + " TEXT," +
                dbOrder + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + dbTable + " ; ");
        onCreate(db);
    }

    public void addPoint(PointModel pointModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbId, pointModel.getId());
        contentValues.put(dbRegister, pointModel.getRegistro());
        contentValues.put(dbDescription, pointModel.getDescricao());
        contentValues.put(dbLatitude, pointModel.getlatitude());
        contentValues.put(dbLongitude, pointModel.getLongitude());
        contentValues.put(dbNorte, pointModel.getNorte());
        contentValues.put(dbLeste, pointModel.getLeste());
        contentValues.put(dbSetor, pointModel.getSetor());
        contentValues.put(dbAltitude, pointModel.getAltitude());

        contentValues.put(dbData, pointModel.getData());
        contentValues.put(dbHora, pointModel.getHora());

        contentValues.put(dbPrecisao, pointModel.getPrecisao());
        contentValues.put(dbSel, pointModel.getSelecao());
        contentValues.put(dbOrder, pointModel.getOrder());

        db.insert(dbTable, null, contentValues);
        db.close();
    }

    public void updateSelecao(PointModel pointModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbSel, pointModel.getSelecao());
        db.update(dbTable, contentValues, dbId + "=" + pointModel.getId(), null);
        db.close();
    }

    public void updatePoint(PointModel pointModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(dbId, pointModel.getId());
        contentValues.put(dbRegister, pointModel.getRegistro());
        contentValues.put(dbDescription, pointModel.getDescricao());
        contentValues.put(dbLatitude, pointModel.getlatitude());
        contentValues.put(dbLongitude, pointModel.getLongitude());
        contentValues.put(dbNorte, pointModel.getNorte());
        contentValues.put(dbLeste, pointModel.getLeste());
        contentValues.put(dbSetor, pointModel.getSetor());
        contentValues.put(dbAltitude, pointModel.getAltitude());
        contentValues.put(dbPrecisao, pointModel.getPrecisao());

        contentValues.put(dbData, pointModel.getHora());
        contentValues.put(dbHora, pointModel.getHora());

        contentValues.put(dbSel, pointModel.getSelecao());
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

    public void changeOrderUp(int pos) {
        if (pos >= 1) {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.query(false, dbTable, new String[]{dbId, dbRegister},
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

    public ArrayList<PointModel> pegarPontos() {
        try {
            ponto.clear();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbTable + " ORDER BY " + dbOrder + " ASC ;", null);

            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        PointModel pointModel = new PointModel();
                        pointModel.setId(cursor.getString(cursor.getColumnIndex(dbId)));
                        pointModel.setRegistro(cursor.getString(cursor.getColumnIndex(dbRegister)));
                        pointModel.setDescricao(cursor.getString(cursor.getColumnIndex(dbDescription)));
                        pointModel.setLatidude(cursor.getString(cursor.getColumnIndex(dbLatitude)));
                        pointModel.setLongitude(cursor.getString(cursor.getColumnIndex(dbLongitude)));
                        pointModel.setNorte(cursor.getString(cursor.getColumnIndex(dbNorte)));
                        pointModel.setLeste(cursor.getString(cursor.getColumnIndex(dbLeste)));
                        pointModel.setSetor(cursor.getString(cursor.getColumnIndex(dbSetor)));
                        pointModel.setAltitude(cursor.getString(cursor.getColumnIndex(dbAltitude)));
                        pointModel.setPrecisao(cursor.getString(cursor.getColumnIndex(dbPrecisao)));
                        pointModel.setHora(cursor.getString(cursor.getColumnIndex(dbHora)));
                        pointModel.setData(cursor.getString(cursor.getColumnIndex(dbData)));
                        pointModel.setSelecao(cursor.getString(cursor.getColumnIndex(dbSel)));
                        pointModel.setOrder(cursor.getString(cursor.getColumnIndex(dbOrder)));
                        ponto.add(pointModel);
                        Log.i(pointModel.getRegistro(), " Order: " + pointModel.getOrder());
                    } while (cursor.moveToNext());
                }

            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ponto;
    }

    public ArrayList<PointModel> pegarPontos(String registro) {

        ponto.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbTable + " WHERE " + dbRegister + " = ? ;",
                new String[]{registro});
            /*Cursor cursor = db.query(true, dbTable, new String[]{dbRegister, dbDescription,
                            dbLatitude, dbLongitude, dbNorte, dbLeste, dbSetor,
                            dbAltitude, dbHora, dbDatxa, dbSel},
                    dbRegister + " = ? ", new String[]{registro}, null, null, null, null, null);
            */
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    PointModel pointModel = new PointModel();
                    pointModel.setId(cursor.getString(cursor.getColumnIndex(dbId)));
                    pointModel.setRegistro(cursor.getString(cursor.getColumnIndex(dbRegister)));
                    pointModel.setDescricao(cursor.getString(cursor.getColumnIndex(dbDescription)));
                    pointModel.setLatidude(cursor.getString(cursor.getColumnIndex(dbLatitude)));
                    pointModel.setLongitude(cursor.getString(cursor.getColumnIndex(dbLongitude)));
                    pointModel.setNorte(cursor.getString(cursor.getColumnIndex(dbNorte)));
                    pointModel.setLeste(cursor.getString(cursor.getColumnIndex(dbLeste)));
                    pointModel.setSetor(cursor.getString(cursor.getColumnIndex(dbSetor)));
                    pointModel.setAltitude(cursor.getString(cursor.getColumnIndex(dbAltitude)));
                    pointModel.setPrecisao(cursor.getString(cursor.getColumnIndex(dbPrecisao)));
                    pointModel.setHora(cursor.getString(cursor.getColumnIndex(dbHora)));
                    pointModel.setData(cursor.getString(cursor.getColumnIndex(dbData)));
                    pointModel.setSelecao(cursor.getString(cursor.getColumnIndex(dbSel)));
                    pointModel.setOrder(cursor.getString(cursor.getColumnIndex(dbOrder)));
                    ponto.add(pointModel);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return ponto;
    }

    public boolean pegarId(String strId) {
        //verifica se já existe o id, ja que id é unique
        boolean existe = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbId + " FROM " + dbTable + " WHERE " + dbId + " = ? ",
                new String[]{strId});
            /*Cursor cursor = db.query(true, dbTable, new String[] { dbId },
                    dbId+" = ? ", new String[] {strId}, null, null, null, null, null);
            */
        if (cursor.getCount() > 0) {
            existe = true;
        }
        cursor.close();
        db.close();
        return existe;
    }

    public int getSize() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbId + " FROM " + dbTable, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}