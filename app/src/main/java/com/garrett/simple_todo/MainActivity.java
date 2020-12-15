package com.garrett.simple_todo;
import com.garrett.todo_app.ItemAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_add;
    EditText et_item;
    RecyclerView rv_items;

    List<String> items;
    ItemAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.main_btn_add);
        et_item = findViewById(R.id.main_et_item);
        rv_items = findViewById(R.id.main_rv_items);

        loadItems();

        ItemAdapter.OnLongClickListener listener = new ItemAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked (int position) {
                items.remove(position);
                adapter.notifyDataSetChanged();
                saveItems();
                Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
            }
        };

        adapter = new ItemAdapter(items, listener);
        rv_items.setAdapter(adapter);
        rv_items.setLayoutManager(new LinearLayoutManager(this));

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String item = et_item.getText().toString();
                items.add(item);
                adapter.notifyDataSetChanged();
                et_item.setText("");
                saveItems();
                Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error getting items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items", e);
        }
    }
}