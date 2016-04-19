package geocodigos.mapgeometry.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import geocodigos.mapgeometry.Database.DatabaseHelper;
import geocodigos.mapgeometry.Models.PointModel;
import geocodigos.mapgeometry.R;

public class DialogAddPoint extends AlertDialog.Builder {

    private PointModel pointModel;
    private Context c;
    public DialogAddPoint(Context c, PointModel pm){
        super(c);
        this.c = c;
        pointModel = pm;
    }

    public AlertDialog.Builder createAlertAdd(final View v){

        final View view_marcar = View.inflate(c, R.layout.alert_add,
                null);
        final TextView tvLat = (TextView)view_marcar.findViewById(R.id.textviewLat);
        final TextView tvLon = (TextView)view_marcar.findViewById(R.id.textviewLon);
        final TextView tvSec = (TextView)view_marcar.findViewById(R.id.textviewSetor);
        final TextView tvNor = (TextView)view_marcar.findViewById(R.id.textviewNorte);
        final TextView tvLes = (TextView)view_marcar.findViewById(R.id.textviewLeste);
        final TextView tvLatDMS = (TextView)view_marcar.findViewById(R.id.textViewLatDMS);
        final TextView tvLonDMS = (TextView)view_marcar.findViewById(R.id.textViewLonDMS);
        final EditText etName = (EditText)view_marcar.findViewById(R.id.etName);
        final EditText etDesc = (EditText)view_marcar.findViewById(R.id.etDesc);

        tvLat.setText(pointModel.getlatitude());
        tvLon.setText(pointModel.getLongitude());
        tvSec.setText(pointModel.getSetor());
        tvLes.setText(pointModel.getLeste());
        tvNor.setText(pointModel.getNorte());
        tvLatDMS.setText(pointModel.getLatDms());
        tvLonDMS.setText(pointModel.getLonDms());

        etName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });
        etDesc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        AlertDialog.Builder alert= new AlertDialog.Builder(getContext());
        alert.setTitle("");
        alert.setView(view_marcar);
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ViewGroup parent = (ViewGroup) view_marcar.getParent();
                    parent.removeView(view_marcar);
                    dialog.dismiss();
                }
                return false;
            }
        });
        alert.setNegativeButton(R.string.cancelar,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViewGroup parent = (ViewGroup) view_marcar.getParent();
                        parent.removeView(view_marcar);
                        //dialog.dismiss();
                    }
                });
        alert.setPositiveButton(R.string.strMarcar,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final InputMethodManager imm;
                        DatabaseHelper database = new DatabaseHelper(getContext());
                        database.getWritableDatabase();

                        int id = 0;
                        String strAux;
                        do {
                            strAux = String.valueOf(id);
                            id++;
                        } while (database.pegarId(strAux));
                        pointModel.setId(strAux);
                        String strReccord = getContext().getResources().getString(R.string.strMarkerName);
                        if (etName.getText().toString().trim().isEmpty()) {
                            pointModel.setRegistro(strReccord);
                        } else {
                            pointModel.setRegistro(etName.getText().toString());
                        }
                        pointModel.setSelecao("1");
                        pointModel.setDescricao(etDesc.getText().toString());
                        pointModel.setOrder(String.valueOf(database.getSize()));

                        database.addPoint(pointModel);

                        if (pointModel.getlatitude().trim().isEmpty()) {
                            pointModel.setLatidude("0");
                        }
                        if (pointModel.getLongitude().trim().isEmpty()) {
                            pointModel.setLongitude("0");
                        }
                        database.close();

                        ViewGroup parent = (ViewGroup) view_marcar.getParent();
                        parent.removeView(view_marcar);
                        imm = (InputMethodManager) getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                        Toast.makeText(getContext(), R.string.ponto_marcado,
                                Toast.LENGTH_LONG).show();
                    }
                });
        return alert;
    }

}
