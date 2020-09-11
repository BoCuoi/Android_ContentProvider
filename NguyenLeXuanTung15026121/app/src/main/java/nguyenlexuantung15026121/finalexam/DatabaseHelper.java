package nguyenlexuantung15026121.finalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung15026121.finalexam.models.Producer;
import nguyenlexuantung15026121.finalexam.models.Product;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "STORE.sql";
    private static final int VERSION = 3;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Database Schema
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS products(product_id INTEGER PRIMARY KEY, " +
            "product_name TEXT, " +
            "product_size TEXT, " +
            "product_amount INTEGER, " +
            "producer_id INTEGER NOT NULL CONSTRAINT producer_id REFERENCES producers(producer_id) ON DELETE CASCADE)";

    /// android.database.sqlite.SQLiteException: foreign key mismatch || MAKE ID IS PRIMARY KEY
    private static final String CREATE_TABLE_PRODUCER = "CREATE TABLE IF NOT EXISTS producers(producer_id INTEGER PRIMARY KEY, producer_name TEXT)";

    private static final String CREATE_DEFAULT_PRODUCER_1 = "INSERT INTO producers(producer_id,producer_name) VALUES( 1 , 'Food')";
    private static final String CREATE_DEFAULT_PRODUCER_2 = "INSERT INTO producers(producer_id,producer_name) VALUES( 2 , 'Toy')";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCER);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_DEFAULT_PRODUCER_1);
    }

    // Begin with version 2 when delete database is the same ver 1
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL(CREATE_DEFAULT_PRODUCER_2);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = new Product(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                );
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return productList;
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("product_id", product.getProductID());
        cv.put("product_name", product.getProductName());
        cv.put("product_size", product.getProductSize());
        cv.put("product_amount", product.getProductAmount());
        cv.put("producer_id", product.getProducerID());
        db.insert("products", null, cv);
        db.close();
    }

    public void deleteProduct(int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("products", "product_id=?", new String[]{String.valueOf(productID)});
        db.close();
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", product.getProductID());
        values.put("product_name", product.getProductName());
        values.put("product_size", product.getProductSize());
        values.put("product_amount", product.getProductAmount());
        values.put("producer_id", product.getProducerID());
        db.update("products", values, "product_id=?", new String[]{String.valueOf(product.getProductID())});
    }

    public Product getProductById(int productID) {
        Product product = new Product();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE product_id=?", new String[]{String.valueOf(productID)});

        if (cursor != null) {
            cursor.moveToFirst(); // android.database.CursorIndexOutOfBoundsException
            while (!cursor.isAfterLast()) {
                product = new Product(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                );
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return product;
    }


    public List<Producer> getAllProducer() {
        List<Producer> producerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM producers", null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Producer producer = new Producer(cursor.getInt(0),
                        cursor.getString(1)
                );
                producerList.add(producer);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return producerList;
    }

    public void addProducer(Producer producer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("producer_id", producer.getProducer_id());
        cv.put("producer_name", producer.getProducer_name());
        db.insert("producers", null, cv);
        db.close();
    }
}
