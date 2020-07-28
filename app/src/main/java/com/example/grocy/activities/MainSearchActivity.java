package com.example.grocy.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.grocy.Adapters.SearchAdapter;
import com.example.grocy.Models.SearchShopModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainSearchActivity extends AppCompatActivity {

    EditText search_edit_text;
    ArrayList<SearchShopModel> search_data = new ArrayList();
    RecyclerView search_recycler;
    SearchAdapter searchAdapter;
    ArrayList<SearchShopModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        search_edit_text = findViewById(R.id.search_edit_text);

        search_data = (ArrayList<SearchShopModel>) getIntent().getSerializableExtra("search_data");

        search_recycler = findViewById(R.id.search_recycler);
        arrayList = new ArrayList();
        search_recycler.setHasFixedSize(false);
        search_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(this, arrayList);
        search_recycler.setAdapter(searchAdapter);
//        searchAdapter.notifyDataSetChanged();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String search_text) {

        ArrayList<SearchShopModel> filteredList = new ArrayList();

        for (SearchShopModel item : search_data) {
            if (item.getShopName().toLowerCase().contains(search_text)) {
                filteredList.add(item);
            }
        }

        searchAdapter.filteredList(filteredList);

    }
}