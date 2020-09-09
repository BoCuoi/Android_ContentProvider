package nguyenlexuantung15026121.finalexam.activity;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung15026121.finalexam.DatabaseHelper;
import nguyenlexuantung15026121.finalexam.R;
import nguyenlexuantung15026121.finalexam.models.Producer;
import nguyenlexuantung15026121.finalexam.models.Product;

public class AddActivity extends AppCompatActivity {
    // Declare public or declare local
    Button btnExitAdd, btnSave, btnAdd, btnDelete;
    EditText edtID, edtName, edtAmount;
    Spinner spnProducer;
    ListView lvProduct;
    RadioButton radSmall, radBig;
    RadioGroup radioGroup;

    DatabaseHelper databaseHelper;
    public List<Product> productList;
    List<Producer> producerList;
    ArrayAdapter<Product> lvAdapter;

    int positionPicked;
    String sizeProduct;

    private static final int RB1_ID = 1000; //first radio button id
    private static final int RB2_ID = 1001; //second radio button id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtID = (EditText) findViewById(R.id.edtProductID);
        edtName = (EditText) findViewById(R.id.edtProductName);
        edtAmount = (EditText) findViewById(R.id.edtAmountProduct);

        spnProducer = (Spinner) findViewById(R.id.spnProducer);

        radioGroup = (RadioGroup) findViewById(R.id.radGroupProduct);
        radSmall = (RadioButton) findViewById(R.id.radSmallProduct);
        radSmall.setId(RB1_ID);
        radSmall.setChecked(true);
        radBig = (RadioButton) findViewById(R.id.radBigProduct);
        radBig.setId(RB2_ID); // using private variable ?!

        btnExitAdd = (Button) findViewById(R.id.btnExitAdd);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSave = (Button) findViewById(R.id.btnSave);
        lvProduct = (ListView) findViewById(R.id.lvProduct);

        databaseHelper = new DatabaseHelper(AddActivity.this);

        producerList = databaseHelper.getAllProducer();
        // Use local cuz load only when create Activity
        ArrayAdapter<Producer> spnAdapter = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_spinner_dropdown_item, producerList);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spnProducer.setAdapter(spnAdapter);

        /// Method 1: productList is global variable |
        productList = new ArrayList<>();
        lvAdapter = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_list_item_1, productList);

        btnExitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radCheckedId = radioGroup.getCheckedRadioButtonId();
                if (radCheckedId == RB1_ID)
                    sizeProduct = "50 kg";
                else sizeProduct = "100 kg";
//                Within the loop, sizeProduct is just a local variable
//                switch (radCheckedId) {
//                    case RB1_ID:
//                        sizeProduct = "50 kg";
//                    case RB2_ID:
//                        sizeProduct = "100 kg";
//                    case -1: //Handle before
//            }
                Producer producerPicked = producerList.get(spnProducer.getSelectedItemPosition());
                // Notice mismatch foreign key
                Product product = new Product(
                        Integer.parseInt(edtID.getText().toString()),
                        edtName.getText().toString(),
                        sizeProduct,
                        Integer.parseInt(edtAmount.getText().toString()),
                        producerPicked.getProducer_id()
                );
                productList.add(product);
//                lvAdapter.notifyDataSetChanged();
                lvProduct.setAdapter(lvAdapter);

            }
        });

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                positionPicked = position;
                Product currentItem = (Product) parent.getItemAtPosition(position);
                edtID.setText(currentItem.getProductID() + "");
                edtName.setText(currentItem.getProductName());

                if (currentItem.getProductSize().contains("50 kg"))
                    radSmall.setChecked(true);
                else radBig.setChecked(true);
                spnProducer.setSelection(currentItem.getProducerID() - 1);
                edtAmount.setText(currentItem.getProductAmount() + "");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productList.size() > 0) {
                    productList.remove(positionPicked);
                    lvAdapter.notifyDataSetChanged();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Product product : productList) {
                    databaseHelper.addProduct(product);
                }
                productList.clear(); // Only clear here
                lvAdapter.notifyDataSetChanged();
            }
        });
    }

}