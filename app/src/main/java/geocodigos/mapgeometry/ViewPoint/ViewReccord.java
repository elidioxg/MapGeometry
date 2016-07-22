package geocodigos.mapgeometry.ViewPoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import geocodigos.mapgeometry.Database.DatabaseHelper;
import geocodigos.mapgeometry.Models.PointModel;
import geocodigos.mapgeometry.R;


public class ViewReccord extends Activity {

    private DatabaseHelper database;
    private ArrayList<PointModel> arrayList = new ArrayList<PointModel>();
    private EditText etLatitude, etLongitude, etPrecisao, etAltitude, etSetor,
            etNorte, etLeste, etDescricao, etHora, etData;
    private TextView tvRegistro;
    private ImageButton ibAnterior, ibProximo, ibSalvar;
    private boolean modificado = false;
    private int posicao, total;
    private TextWatcher tw;

    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_registro);

        Bundle args = getIntent().getExtras();
        posicao = (int) args.get("posicao");
        total = (int) args.get("total_registros");

        ibAnterior = (ImageButton) findViewById(R.id.ib_anterior);
        ibProximo = (ImageButton) findViewById(R.id.ib_proximo);
        ibSalvar = (ImageButton) findViewById(R.id.ib_salvar);

        ibAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posicao > 0) {
                    posicao--;
                    preencheCampos(posicao);
                    modificado=false;
                }
            }
        });

        ibProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posicao < (total - 1)) {
                    posicao++;
                    preencheCampos(posicao);
                    modificado=false;
                }
            }
        });

        ibSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPonto(posicao);
            }
        });

        etLatitude = (EditText) findViewById(R.id.etlatitude);
        etLongitude = (EditText)findViewById(R.id.etlongitude);
        tvRegistro = (TextView) findViewById(R.id.tvRegistro);
        etDescricao = (EditText)findViewById(R.id.etdescricao);
        etPrecisao = (EditText) findViewById(R.id.etPrecisao);
        etAltitude = (EditText) findViewById(R.id.etAltitude);
        etHora = (EditText) findViewById(R.id.etHora);
        etData = (EditText) findViewById(R.id.etData);
        etSetor = (EditText)findViewById(R.id.etSetor);
        etNorte = (EditText)findViewById(R.id.etNorte);
        etLeste = (EditText)findViewById(R.id.etLeste);

        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!modificado){
                    modificado=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etLatitude.addTextChangedListener(tw);
        etLongitude.addTextChangedListener(tw);
        etDescricao.addTextChangedListener(tw);
        etPrecisao.addTextChangedListener(tw);
        etAltitude.addTextChangedListener(tw);
        etHora.addTextChangedListener(tw);
        etData.addTextChangedListener(tw);
        etSetor.addTextChangedListener(tw);
        etNorte.addTextChangedListener(tw);
        etLeste.addTextChangedListener(tw);

        preencheCampos(posicao);
    }

    private void salvarPonto(int posicao) {
        if(modificado) {
            PointModel pm = new PointModel();
            database = new DatabaseHelper(this);
            database.getWritableDatabase();
            arrayList.clear();
            arrayList = database.getPoints();
            pm.setId(arrayList.get(posicao).getId());
            pm.setName(arrayList.get(posicao).getName());
            pm.setDescription(etDescricao.getText().toString());
            pm.setLatidude(etLatitude.getText().toString().trim());
            pm.setLongitude(etLongitude.getText().toString().trim());
            pm.setData(etData.getText().toString().trim());
            pm.setTime(etHora.getText().toString().trim());
            pm.setAltitude(etAltitude.getText().toString().trim());
            pm.setPrecision(etPrecisao.getText().toString().trim());
            pm.setSector(etSetor.getText().toString());
            pm.setNorth(etNorte.getText().toString().trim());
            pm.setEast(etLeste.getText().toString().trim());
            pm.setSelected(arrayList.get(posicao).getSelected());
            database.updatePoint(pm);
            database.close();
            Toast.makeText(this, R.string.alteracoes_salvas, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.nenhuma_modificacao, Toast.LENGTH_SHORT).show();
        }
    }

    public void preencheCampos(int posicao) {
        database = new DatabaseHelper(this);
        database.getWritableDatabase();
        arrayList.clear();
        arrayList = database.getPoints();

        etLatitude.setText(arrayList.get(Integer.valueOf(posicao)).getlatitude());
        etLongitude.setText(arrayList.get(Integer.valueOf(posicao)).getLongitude());
        tvRegistro.setText(arrayList.get(Integer.valueOf(posicao)).getName());
        etDescricao.setText(arrayList.get(Integer.valueOf(posicao)).getDescription());
        etData.setText(arrayList.get(Integer.valueOf(posicao)).getData());
        etHora.setText(arrayList.get(Integer.valueOf(posicao)).getTime());
        etPrecisao.setText(arrayList.get(Integer.valueOf(posicao)).getPrecision());
        etAltitude.setText(arrayList.get(Integer.valueOf(posicao)).getAltitude());
        etSetor.setText(arrayList.get(Integer.valueOf(posicao)).getSector());
        etNorte.setText(arrayList.get(Integer.valueOf(posicao)).getNorth());
        etLeste.setText(arrayList.get(Integer.valueOf(posicao)).getEast());

        database.close();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}

