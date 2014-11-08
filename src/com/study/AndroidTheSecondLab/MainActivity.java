package com.study.AndroidTheSecondLab;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button button;
    private EditText editText;
    private ListView listView;
    private ArrayList<String> goods;
    private ArrayAdapter<String> adapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);

        goods = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, goods);
        listView.setAdapter(adapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        getGoods();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goods.add(editText.getText().toString());
                adapter.notifyDataSetChanged();
                setGoods();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                goods.remove(position);
                adapter.notifyDataSetChanged();
                setGoods();
                return true;
            }
        });
    }

    private void setGoods() {
        for (int i = 0; i < goods.size(); i++) {
            editor.putString("item" + i, goods.get(i));
        }

        editor.putInt("size", goods.size());
        editor.commit();
    }

    private void getGoods() {
        int size = preferences.getInt("size", 0);

        for (int i = 0; i < size; i++) {
            goods.add(preferences.getString("item" + i, null));
        }
    }
}
