package nguyenlexuantung15026121.finalexam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung15026121.finalexam.DatabaseHelper;
import nguyenlexuantung15026121.finalexam.R;
import nguyenlexuantung15026121.finalexam.models.Producer;
import nguyenlexuantung15026121.finalexam.models.Product;

public class ProductActivity extends AppCompatActivity {
    EditText edtID, edtName, edtAmount;
    Button btnClose, btnSave, btnShow, btnUpdate, btnDelete;
    Spinner spinnerProduct;
    RadioGroup radioGroup;
    RadioButton radioButtonSmall, radioButtonBig;
    ListView listViewProduct;
    DatabaseHelper databaseHelper;
    List<Product> productList;
    List<Producer> producerList;
    ArrayAdapter<Product> adapterListView;

    static final String PROVIDER_NAME = "nguyenlexuantung15026121.finalexam";
    static final String PRODUCTS_URL = "content://" + PROVIDER_NAME + "/products";
    static final Uri PRODUCTS_CONTENT_URI = Uri.parse(PRODUCTS_URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        btnClose = (Button) findViewById(R.id.btnCloseProduct);
        btnSave = (Button) findViewById(R.id.btnSaveProduct);
        btnShow = (Button) findViewById(R.id.btnShowProduct);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProduct);
        btnDelete = (Button) findViewById(R.id.btnDeleteProduct);

        edtID = (EditText) findViewById(R.id.edtID);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        spinnerProduct = (Spinner) findViewById(R.id.spinnerProducer);

        radioGroup = (RadioGroup) findViewById(R.id.radGroup);
        radioButtonSmall = (RadioButton) findViewById(R.id.radSmall);
        radioButtonSmall.setChecked(true);
        radioButtonBig = (RadioButton) findViewById(R.id.radBig);

        listViewProduct = (ListView) findViewById(R.id.listviewProduct);

        databaseHelper = new DatabaseHelper(ProductActivity.this);
        producerList = databaseHelper.getAllProducer();
        ArrayAdapter<Producer> adapterProducer = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_list_item_1, producerList);
        spinnerProduct.setAdapter(adapterProducer);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProducts();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
                showProducts();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
                showProducts();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
                showProducts();
            }
        });

        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product currentItem = (Product) parent.getItemAtPosition(position);
                edtID.setText(currentItem.getProductID() + "");
                edtName.setText(currentItem.getProductName());
                /// contains() to trigger change checked status successfully rather than ==
                if (currentItem.getProductSize().contains("50 kg")) {
                    radioButtonSmall.setChecked(true);
//                    radioButtonBig.setChecked(false);
                } else {
                    radioButtonBig.setChecked(true);
//                    radioButtonSmall.setChecked(false);
                }

                /// java.lang.IndexOutOfBoundsException: Index: 2, Size: 2 | getProducerID() not getProductID()
                spinnerProduct.setSelection(currentItem.getProducerID() - 1);
                edtAmount.setText(currentItem.getProductAmount() + "");
            }
        });
    }

    public void showProducts() {
        List<Product> productList = new ArrayList<>();
        productList = databaseHelper.getAllProduct();
        ArrayAdapter<Product> adapterListView = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_list_item_1, productList);
        listViewProduct.setAdapter(adapterListView);

    }

    public void saveProduct() {
        Producer producerPicked = producerList.get(spinnerProduct.getSelectedItemPosition());
        String tempValue;
        if (radioGroup.getCheckedRadioButtonId() == R.id.radSmall){
            tempValue = "50 kg";
        }
        else tempValue ="100 kg";
//        if (radioButtonSmall.isChecked()) {
//            tempValue = "50 kg";
//        } else
//            tempValue = "100 kg";
        Product postProduct = new Product(Integer.parseInt(edtID.getText().toString()),
                edtName.getText().toString(),
                tempValue,
                Integer.parseInt(edtAmount.getText().toString()),
                producerPicked.getProducer_id());
        databaseHelper.addProduct(postProduct);
    }

    public void updateProduct() {
        String tempValue;
        if (radioButtonSmall.isChecked()) {
            tempValue = "50 kg";
        } else
            tempValue = "100 kg";
        databaseHelper.updateProduct(new Product(Integer.parseInt(edtID.getText().toString()), edtName.getText().toString(), tempValue,
                Integer.parseInt(edtAmount.getText().toString()), spinnerProduct.getSelectedItemPosition() + 1));
    }

    public void deleteProduct() {
        databaseHelper.deleteProduct(Integer.parseInt(edtID.getText().toString()));
    }


    public void productCV() {
        String PROVIDER_NAME = "nguyenlexuantung15026121.finalexam"; // authority argm
        String PRODUCTS_URL = "content://" + PROVIDER_NAME + "/products";
        Uri PRODUCTS_CONTENT_URI = Uri.parse(PRODUCTS_URL);

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
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_list_item_1, productList);
        listViewProduct.setAdapter(adapter);
    }

    public void addProductCV() {
        Producer producerPicked = producerList.get(spinnerProduct.getSelectedItemPosition());
        String tempValue;
        if (radioButtonSmall.isChecked()) {
            tempValue = "50 kg";
        } else
            tempValue = "100 kg";
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("product_id", Integer.parseInt(edtID.getText().toString()));
        values.put("product_name", edtName.getText().toString());
        values.put("product_size", tempValue);
        values.put("product_amount", Integer.parseInt(edtAmount.getText().toString()));
        values.put("producer_id", spinnerProduct.getSelectedItemPosition() + 1);
        Uri postProductUri = contentResolver.insert(PRODUCTS_CONTENT_URI, values);
        Toast.makeText(ProductActivity.this, spinnerProduct.getSelectedItemPosition() + "", Toast.LENGTH_LONG).show();
    }
}