package database;

import android.provider.BaseColumns;

public class DefineTabla {

    public DefineTabla(){}

    public static abstract class Alumnos implements BaseColumns{
        public static final String TABLE_NAME = "alumnos";
        public static final String COLUMN_TABLE_ID = "id";
        public static final String COLUMN_TABLE_MATRICULA = "matricula";
        public static final String COLUMN_TABLE_NOMBRE = "nombre";
        public static final String COLUMN_TABLE_CARRERA = "carrera";
        public static final String COLUMN_TABLE_FOTO = "foto";

        public static String[] REGISTRO = new String[]{
                Alumnos.COLUMN_TABLE_ID,
                Alumnos.COLUMN_TABLE_MATRICULA,
                Alumnos.COLUMN_TABLE_NOMBRE,
                Alumnos.COLUMN_TABLE_CARRERA,
                Alumnos.COLUMN_TABLE_FOTO
        };
    }
}
