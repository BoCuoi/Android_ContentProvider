package quangnguyen.finaltestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    Button btnClose, btnAdd, btnShow, btnEdit, btnDelete;
    EditText edtID, edtName;
    Spinner spinner;
    ListView listView;

    DatabaseHelper databaseHelper;

    ArrayList<Book> bookArrayList;
    ArrayList<Author> authorArrayList;

    ArrayAdapter<Author> authorArrayAdapter;
    ArrayAdapter<Book> bookArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        btnClose = findViewById(R.id.btnClose1);
        btnAdd = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        edtID = findViewById(R.id.edtIDBook);
        edtName = findViewById(R.id.edtName);

        spinner = findViewById(R.id.spinAuthor);
        listView = findViewById(R.id.lvBook);

        databaseHelper = new DatabaseHelper(BookActivity.this);

        loadSpinner();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showBook();

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
                showBook();
            }
        });


        //============Chọn item trong list để lên Edittext===============
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = (Book) adapterView.getItemAtPosition(i);
                edtID.setText(book.getId() + "");
                edtName.setText(book.getName());
                spinner.setSelection(book.getAuthor_id() - 1);
            }
        });

        //==============Edit========================
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBook();
                showBook();
            }
        });

        //================Delete=====================
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
                showBook();
            }
        });
    }

    //===============load author dạng list==================
    public void loadSpinner() {
        authorArrayList = databaseHelper.getAllAuthor();
        authorArrayAdapter = new ArrayAdapter<>(BookActivity.this, android.R.layout.simple_list_item_1, authorArrayList);
        spinner.setAdapter(authorArrayAdapter);
    }

    //=================Them======================
    public void addBook() {
        databaseHelper.addBook(new Book(Integer.parseInt(edtID.getText().toString()),
                edtName.getText().toString(),
                spinner.getSelectedItemPosition() + 1));
    }

    //=================Xem=======================
    public void showBook() {
        bookArrayList = new ArrayList<>();
        if(edtID.getText().toString().equals(""))
            {
            bookArrayList = databaseHelper.getAllBook();
            bookArrayAdapter = new ArrayAdapter<>(BookActivity.this, android.R.layout.simple_list_item_1, bookArrayList);
            listView.setAdapter(bookArrayAdapter);
            }
        else{
            bookArrayList = databaseHelper.getBooktById(Integer.parseInt(edtID.getText().toString()));
            bookArrayAdapter = new ArrayAdapter<>(BookActivity.this, android.R.layout.simple_list_item_1, bookArrayList);
            listView.setAdapter(bookArrayAdapter);

        }
    }

    //=================Update====================
    public void updateBook() {
        databaseHelper.updateBook(new Book(Integer.parseInt(edtID.getText().toString()),
                edtName.getText().toString(),
                spinner.getSelectedItemPosition() + 1
        ));
    }


    //==================Delete==============
    public void deleteBook() {
        databaseHelper.deleteBook(new Book(Integer.parseInt(edtID.getText().toString()),
                edtName.getText().toString(),
                spinner.getSelectedItemPosition() + 1
        ));
    }
}