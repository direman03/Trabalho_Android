package br.edu.unifcv.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private final String TABELA = "Contatos";
    private static final String DATABASE = "DadosAgenda";

    public ContatoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + " nome TEXT NOT NULL, "
                + " ref TEXT, "
                + " email TEXT, "
                + " endereco TEXT, "
                + " telefone TEXT, "
                + " foto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<ContatoInfo> getList(String order){
        List<ContatoInfo> contatos = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + " ORDER BY nome " +
                order + ";", null);

        while(cursor.moveToNext()){
            ContatoInfo c = new ContatoInfo();

            c.setId(cursor.getLong(cursor.getColumnIndex("id")));
            c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            c.setRef(cursor.getString(cursor.getColumnIndex("ref")));
            c.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            c.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            c.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            c.setFoto(cursor.getString(cursor.getColumnIndex("foto")));

            contatos.add(c);
        }

        cursor.close();

        return contatos;
    }

    public void inserirContato(ContatoInfo c){
        ContentValues values = new ContentValues();

        values.put("nome", c.getNome());
        values.put("ref", c.getRef());
        values.put("email", c.getEmail());
        values.put("telefone", c.getTelefone());
        values.put("endereco", c.getEndereco());
        values.put("foto", c.getFoto());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public void alteraContato(ContatoInfo c){
        ContentValues values = new ContentValues();

        values.put("id", c.getId());
        values.put("nome", c.getNome());
        values.put("ref", c.getRef());
        values.put("email", c.getEmail());
        values.put("telefone", c.getTelefone());
        values.put("endereco", c.getEndereco());
        values.put("foto", c.getFoto());

        String[] idParaSerAlterado = {String.valueOf(c.getId())};
        getWritableDatabase().update(TABELA, values, "id=?", idParaSerAlterado);
    }

    public void apagarContato(ContatoInfo c){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(c.getId())};
        db.delete(TABELA, "id=?", args);
    }
}
