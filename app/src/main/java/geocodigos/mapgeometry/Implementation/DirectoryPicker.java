package geocodigos.mapgeometry.Implementation;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import geocodigos.mapgeometry.R;

/**
 Modified by elidioxg
    August,2015
 */

/**
 Copyright (C) 2011 by Brad Greco <brad@bgreco.net>

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

public class DirectoryPicker extends ListActivity {

    public static final String START_DIR = "startDir";
    public static final String ONLY_DIRS = "onlyDirs";
    public static final String SHOW_HIDDEN = "showHidden";
    public static final String CHOSEN_DIRECTORY = "chosenDir";
    public static final int PICK_DIRECTORY = 10;
    private File dir;
    private boolean showHidden = false;
    private boolean onlyDirs = true ;
    public String param_camada, nome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        nome = extras.getString("nome_camada");
        //Log.i("nome_camada: ", nome);
        param_camada = extras.getString("param");

        dir = Environment.getExternalStorageDirectory();
        if (extras != null) {
            String preferredStartDir = extras.getString(START_DIR);
            showHidden = extras.getBoolean(SHOW_HIDDEN, false);
            onlyDirs = extras.getBoolean(ONLY_DIRS, true);
            if(preferredStartDir != null) {
                File startDir = new File(preferredStartDir);
                if(startDir.isDirectory()) {
                    dir = startDir;
                }
            }
        }

        setContentView(R.layout.chooser_list);
        setTitle(dir.getAbsolutePath());
        Button btnChoose = (Button) findViewById(R.id.btnChoose);
        String name = dir.getName();
        if(name.length() == 0)
            name = "/";

        btnChoose.setText(getResources().getString(R.string.escolher) + " '" + name + "'?");
        btnChoose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnDir(dir.getAbsolutePath());
            }
        });

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        if(!dir.canRead()) {
            Context context = getApplicationContext();
            String msg = "Could not read folder contents.";
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        final ArrayList<File> files = filter(dir.listFiles(), onlyDirs, showHidden);
        String[] names = names(files);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, names));


        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!files.get(position).isDirectory())
                    return;
                String path = files.get(position).getAbsolutePath();
                Intent intent = new Intent(DirectoryPicker.this, DirectoryPicker.class);
                intent.putExtra(DirectoryPicker.START_DIR, path);
                intent.putExtra(DirectoryPicker.SHOW_HIDDEN, showHidden);
                intent.putExtra(DirectoryPicker.ONLY_DIRS, onlyDirs);
                startActivityForResult(intent, PICK_DIRECTORY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path=null;
        File file;
        if(requestCode == PICK_DIRECTORY && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            path = (String) extras.get(DirectoryPicker.CHOSEN_DIRECTORY);
            file = new File(path+"/"+nome+".kml");

            try {
                file.createNewFile();
                FileOutputStream outputStream;
                outputStream = new FileOutputStream(file);
                outputStream.write(param_camada.getBytes());
                outputStream.close();
                if(file.exists()) {
                    Toast.makeText(getApplicationContext(), R.string.arquivo_salvo,
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), R.string.file_not_saved,
                            Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Log.i("OnActivityResult", path);
        returnDir(path);
    }

    private void returnDir(String path) {
        Intent result = new Intent();
        result.putExtra(CHOSEN_DIRECTORY, path);
        setResult(RESULT_OK, result);
        finish();
    }

    public ArrayList<File> filter(File[] file_list, boolean onlyDirs, boolean showHidden) {
        ArrayList<File> files = new ArrayList<File>();
        for(File file: file_list) {
            if(onlyDirs && !file.isDirectory())
                continue;
            if(!showHidden && file.isHidden())
                continue;
            files.add(file);
        }
        Collections.sort(files);
        return files;
    }

    public String[] names(ArrayList<File> files) {
        String[] names = new String[files.size()];
        int i = 0;
        for(File file: files) {
            names[i] = file.getName();
            i++;
        }
        return names;
    }
}
