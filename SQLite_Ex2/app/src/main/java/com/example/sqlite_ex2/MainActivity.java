package com.example.sqlite_ex2;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlite_ex2.Adapter.ProductAdapter;
import com.example.sqlite_ex2.Database.Database;
import com.example.sqlite_ex2.databinding.ActivityMainBinding;
import com.example.sqlite_ex2.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Database db;
    ProductAdapter productAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prepareDb();
        loadData();
    }

    private void loadData() {
        productAdapter = new ProductAdapter(this, R.layout.item, getDataFromDb());
        binding.lvProduct.setAdapter(productAdapter);
    }

    private List<Product> getDataFromDb() {
        products = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + Database.TBL_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            products.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public void openEditDialog(Product p){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit);

        EditText edName = dialog.findViewById(R.id.edName);
        edName.setText(p.getProductName());

        EditText edPrice = dialog.findViewById(R.id.edPrice);
        edPrice.setText(String.valueOf(p.getProductPrice()));

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                Double price = Double.valueOf(edPrice.getText().toString());
                db.execSql(" UPDATE " + Database.TBL_NAME + " SET " + Database.COL_NAME + " = '" + name + "', " + Database.COL_PRICE + " = " + price +
                        " WHERE " + Database.COL_CODE + " = " + p.getProductCode());
                //
                loadData();
                dialog.dismiss();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void prepareDb() {
        db = new Database(this);
        db.createSampleData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnAdd){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_add);

            EditText edName = dialog.findViewById(R.id.edName);
            EditText edPrice = dialog.findViewById(R.id.edPrice);

            Button btnSave = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = edName.getText().toString();
                    double price = Double.parseDouble(edPrice.getText().toString());
                    db.execSql("INSERT INTO " + Database.TBL_NAME + " VALUES(null, '" + name + "', " + price + ")");
                    loadData();
                    dialog.dismiss();
                }
            });

            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}