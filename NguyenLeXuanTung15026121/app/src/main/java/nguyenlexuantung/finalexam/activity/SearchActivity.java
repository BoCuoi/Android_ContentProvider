package nguyenlexuantung.finalexam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung.finalexam.DatabaseHelper;
import nguyenlexuantung.finalexam.R;
import nguyenlexuantung.finalexam.models.Product;

public class SearchActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch;
    GridView gridView;

    DatabaseHelper databaseHelper;
    List<Product> productList;
    ArrayAdapter<Product> gridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = (EditText) findViewById(R.id.edtSearch);
        gridView = (GridView) findViewById(R.id.gridProduct);

        databaseHelper = new DatabaseHelper(SearchActivity.this);
        productList = new ArrayList<>();

        btnSearch = (Button) findViewById(R.id.btnSearch);
        // Declare or: .NullPointerException: Attempt to invoke virtual method 'void android.widget.Button.setOnClickListener
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtSearch.getText().toString().isEmpty()) {
                    try {
                        productList.clear();
                        Product product = databaseHelper.getProductById(Integer.parseInt(edtSearch.getText().toString()));
                        productList.add(product);
                        gridAdapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, productList);
                        gridView.setAdapter(gridAdapter);

                    } catch (Exception e) {
                        System.out.println("Error " + e.getMessage());
                    }
                } else {
                    productList.clear();
                    productList = databaseHelper.getAllProduct();
                    gridAdapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, productList);
                    gridView.setAdapter(gridAdapter);
                }
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}