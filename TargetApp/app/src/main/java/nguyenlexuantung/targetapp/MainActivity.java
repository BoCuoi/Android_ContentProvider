package nguyenlexuantung.targetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtID, edtName, edtAmount;
    Button btnClose, btnShow, btnInsert, btnDelete, btnUpdate;
    CheckBox ckbSmall, ckbBig;
    Spinner spinnerProducer;
    ListView listView;
    ArrayList<Producer> producerArrayList;

    final String PROVIDER_NAME = "nguyenlexuantung15026121.finalexam";
    final String PRODUCTS_URL = "content://" + PROVIDER_NAME + "/products";
    final Uri PRODUCTS_CONTENT_URI = Uri.parse(PRODUCTS_URL);

    final String PRODUCERS_URL = "content://" + PROVIDER_NAME + "/producers";
    final Uri PRODUCERS_CONTENT_URI = Uri.parse(PRODUCERS_URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtID = (EditText) findViewById(R.id.edtProductID);
        edtName = (EditText) findViewById(R.id.edtProductName);
        edtAmount = (EditText) findViewById(R.id.edtAmountProduct);

        spinnerProducer = (Spinner) findViewById(R.id.spnProducer);
        ckbSmall = (CheckBox) findViewById(R.id.ckbSmall);
        ckbBig = (CheckBox) findViewById(R.id.ckbBig);
        ckbBig.setChecked(true);

        btnClose = (Button) findViewById(R.id.btnExitAdd);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnInsert = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        producerArrayList = new ArrayList<>();
        loadProducer();
        listView = (ListView) findViewById(R.id.lvProduct);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = (Product) parent.getItemAtPosition(position);
                edtID.setText(selectedProduct.getProductID() + "");
                edtName.setText(selectedProduct.getProductName());
                if (selectedProduct.getProductSize().contains("50 kg")) {
                    ckbSmall.setChecked(true);
                    ckbBig.setChecked(false);
                } else {
                    ckbBig.setChecked(true);
                    ckbSmall.setChecked(false);
                }
                spinnerProducer.setSelection(selectedProduct.getProducerID() - 1);
                edtAmount.setText(selectedProduct.getProductAmount() + "");
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productCV();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
                productCV();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
                productCV();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
                productCV();
            }
        });
    }

    public void loadProducer() {

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(PRODUCERS_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Producer producer = new Producer(cursor.getInt(0),
                        cursor.getString(1)
                );
                producerArrayList.add(producer);
                cursor.moveToNext();
            }
            cursor.close();
        }
        ArrayAdapter<Producer> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, producerArrayList);
        spinnerProducer.setAdapter(adapter);
    }

    public void productCV() {

        List<Product> productList = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(PRODUCTS_CONTENT_URI, null, null, null, null);
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
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, productList);
        listView.setAdapter(adapter);
    }

    public void addProduct() {
        String tempValue;
        if (ckbSmall.isChecked()) {
            tempValue = "50 kg";
        } else
            tempValue = "100 kg";
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("product_id", Integer.parseInt(edtID.getText().toString()));
        values.put("product_name", edtName.getText().toString());
        values.put("product_size", tempValue);
        values.put("product_amount", Integer.parseInt(edtAmount.getText().toString()));
        values.put("producer_id", spinnerProducer.getSelectedItemPosition() + 1);
        contentResolver.insert(PRODUCTS_CONTENT_URI, values);
//        Toast.makeText(MainActivity.this, postProductUri + "", Toast.LENGTH_LONG).show(); ///CONTENT_URI/ID
    }

    public void updateProduct() {
        String tempValue;
        if (ckbSmall.isChecked()) {
            tempValue = "50 kg";
        } else
            tempValue = "100 kg";
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("product_id", Integer.parseInt(edtID.getText().toString()));
        values.put("product_name", edtName.getText().toString());
        values.put("product_size", tempValue);
        values.put("product_amount", Integer.parseInt(edtAmount.getText().toString())); // Can not be null
        values.put("producer_id", spinnerProducer.getSelectedItemPosition() + 1);
        int updateCount = contentResolver.update(PRODUCTS_CONTENT_URI, values, "product_id=?", new String[]{String.valueOf(Integer.parseInt(edtID.getText().toString()))});
        if (updateCount > 0) {
            Toast.makeText(MainActivity.this, "UPDATE SUCCESSFULLY" + updateCount, Toast.LENGTH_LONG).show();
        }
    }

    public void deleteProduct() {
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(PRODUCTS_CONTENT_URI, "product_id=?", new String[]{String.valueOf(Integer.parseInt(edtID.getText().toString()))});
    }
}