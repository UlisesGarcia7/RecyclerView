package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import database.AlumnoDbHelper;
import database.AlumnosDb;

public class activity_alumno_alta extends AppCompatActivity{
    private TextView lblMatricula, lblNombre, lblFoto, txtId;
    private EditText txtMatricula, txtNombre, txtGrado;
    private Button btnGuardar, btnRegresar, btnCargarImagen, btnBorrar;
    private ImageView imgAlumno;
    private Uri img;
    private Alumno alumno;
    private String carrera = "Ing. Tec. Informacion";
    private int posicion;
    private int seleccionarImagen;

    private AlumnoDbHelper helper = new AlumnoDbHelper(this);
    private AlumnosDb alumnoDb = new AlumnosDb(this, helper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_alta);
        lblMatricula = (TextView) findViewById(R.id.lblMatricula);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblFoto = (TextView) findViewById(R.id.lblFoto);
        txtId = (TextView) findViewById(R.id.txtId);
        txtMatricula = (EditText) findViewById(R.id.txtMatricula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtGrado = (EditText) findViewById(R.id.txtGrado);
        btnGuardar = (Button) findViewById(R.id.btnSalir);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btnCargarImagen = (Button) findViewById(R.id.btnCargarImagen);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        imgAlumno= (ImageView) findViewById(R.id.imgAlumno);
        Bundle bundle = getIntent().getExtras();
        alumno = (Alumno) bundle.getSerializable("alumno");
        posicion = bundle.getInt("posicion", posicion);

        if(posicion >= 0){
            txtMatricula.setText(alumno.getMatricula());
            txtNombre.setText(alumno.getNombre());
            txtGrado.setText(alumno.getGrados());
            imgAlumno.setImageURI(Uri.parse(alumno.getImg()));
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alumno == null){
                    // Agregar un nuevo alumno
                    alumno = new Alumno();
                    alumno.setGrados(txtGrado.getText().toString());
                    alumno.setMatricula(txtMatricula.getText().toString());
                    alumno.setNombre(txtNombre.getText().toString());
                    alumno.setImg(img.toString());
                    //alumno.setImg(R.drawable.us01);

                    if(validar()){
                        Aplicacion.getAlumnos().add(alumno);
                        alumnoDb.openDataBase();
                        alumnoDb.insertAlumno(alumno);
                        alumnoDb.closeDataBase();
                        setResult(Activity.RESULT_OK);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Faltan capturar datos", Toast.LENGTH_SHORT).show();
                        txtMatricula.requestFocus();
                    }
                }

                if(posicion >= 0){
                    alumno.setMatricula(txtMatricula.getText().toString());
                    alumno.setNombre(txtNombre.getText().toString());
                    alumno.setGrados(txtGrado.getText().toString());

                    if(seleccionarImagen == 1){
                        alumno.setImg(img.toString());
                    }

                    Aplicacion.alumnos.get(posicion).setMatricula(alumno.getMatricula());
                    Aplicacion.alumnos.get(posicion).setNombre(alumno.getNombre());
                    Aplicacion.alumnos.get(posicion).setGrados(alumno.getGrados());
                    Aplicacion.getAlumnos().get(posicion).setImg(alumno.getImg());

                    alumnoDb.openDatabase();
                    alumnoDb.updateAlumno(alumno);
                    alumnoDb.closeDatabase();

                    Toast.makeText(getApplicationContext(), "Se modifico con exito", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicion >= 0){
                    Aplicacion.alumnos.remove(posicion);
                    alumnoDb.openDataBase();
                    alumnoDb.deleteAlumno(alumno.getId());
                    alumnoDb.closeDataBase();
                    finish();
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        btnCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
                seleccionarImagen = 1;
            }
        });
    }
    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), 200);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                Uri path = data.getData();
                if (null != path) {
                    imgAlumno.setImageURI(path);
                    img = path;
                    ContentResolver cr = getApplicationContext().getContentResolver();
                    cr.takePersistableUriPermission(img, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
        }
    }

    private boolean validar(){
        boolean exito = true;
        Log.d("nombre", "validar: " + txtNombre.getText());
        if(txtNombre.getText().toString().equals("")) exito = false;
        if(txtMatricula.getText().toString().equals("")) exito = false;
        if(txtGrado.getText().toString().equals("")) exito = false;

        return exito;
    }


}