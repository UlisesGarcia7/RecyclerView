package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_alumno_alta extends AppCompatActivity{
    private TextView lblMatricula, lblNombre, lblFoto;
    private EditText txtMatricula, txtNombre, txtGrado;
    private Button btnGuardar, btnRegresar;
    private ImageView imgAlumno;
    private Alumno alumno;
    private String carrera = "Ing. Tec. Informacion";
    private int posicion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_alta);
        lblMatricula = (TextView) findViewById(R.id.lblMatricula);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblFoto = (TextView) findViewById(R.id.lblFoto);
        txtMatricula = (EditText) findViewById(R.id.txtMatricula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtGrado = (EditText) findViewById(R.id.txtGrado);
        btnGuardar = (Button) findViewById(R.id.btnSalir);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        imgAlumno = (ImageView) findViewById(R.id.imgAlumno);

        Bundle bundle = getIntent().getExtras();
        alumno = (Alumno) bundle.getSerializable("alumno");
        posicion = bundle.getInt("posicion", posicion);

        if(posicion >= 0){
            txtMatricula.setText(alumno.getMatricula());
            txtNombre.setText(alumno.getNombre());
            txtGrado.setText(alumno.getGrados());
            imgAlumno.setImageResource(alumno.getImg());
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alumno == null){
                    // Agregar un nuevo alumno
                    alumno = new Alumno();
                    alumno.setGrados(carrera);
                    alumno.setMatricula(txtMatricula.getText().toString());
                    alumno.setNombre(txtNombre.getText().toString());
                    alumno.setImg(R.drawable.us01);

                    if(validar()){
                        Aplicacion.alumnos.add(alumno);
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

                    Aplicacion.alumnos.get(posicion).setMatricula(alumno.getMatricula());
                    Aplicacion.alumnos.get(posicion).setNombre(alumno.getNombre());
                    Aplicacion.alumnos.get(posicion).setGrado(alumno.getGrados());
                    Toast.makeText(getApplicationContext(), "Se modifico con exito", Toast.LENGTH_SHORT).show();
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