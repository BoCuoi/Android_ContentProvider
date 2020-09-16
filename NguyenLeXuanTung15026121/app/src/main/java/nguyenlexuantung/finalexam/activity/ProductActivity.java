package nguyenlexuantung.finalexam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung.finalexam.DatabaseHelper;
import nguyenlexuantung.finalexam.R;
import nguyenlexuantung.finalexam.models.Producer;
import nguyenlexuantung.finalexam.models.Product;

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
                if (currentItem.getProductSize().contains("50 kg")) {
                    radioButtonSmall.setChecked(true);
                } else {
                    radioButtonBig.setChecked(true);
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
}