package com.example.room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.room.database.PersonneEntity;
import com.example.room.database.TestData;

import java.util.ArrayList;
import java.util.List;

import viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private MainViewModel mViewModel;


// test
    private List<PersonneEntity> personsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // le viewModel delegue sa place au Provider
        listView=findViewById(R.id.listView);
        mViewModel=new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.mPersons.observe(
            this,persons->{
                personsData.clear();
                personsData.addAll(persons);
                if(arrayAdapter == null){
                    arrayAdapter=new ArrayAdapter<>(
                    getApplicationContext(),
                            android.R.layout.simple_list_item_1, persons);
                    listView.setAdapter(arrayAdapter);
                }
                else arrayAdapter.notifyDataSetChanged();
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
      //  return super.onCreateOptionsMenu(menu);
        return true;
    }

    public void onClickaddAllData(MenuItem item) {
        mViewModel.addAllPersons(TestData.getAll());
    }

    public void onClickdeleteAllData(MenuItem item) {
        mViewModel.deleteAllPersons();
    }
}