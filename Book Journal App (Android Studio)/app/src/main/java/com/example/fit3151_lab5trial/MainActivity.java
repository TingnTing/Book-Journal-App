package com.example.fit3151_lab5trial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



import com.example.fit3151_lab5trial.provider.BookItem;
import com.example.fit3151_lab5trial.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    // Random class
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase();

    public static final String digits = "0123456789";

    public static final String alphaNummeric = upper + lower + digits;
    DrawerLayout drawer;

    // Week 7 - Database
    private BookViewModel mItemViewModel;
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    GestureDetector gestureDetector;

    DatabaseReference myRef;

    // reading from firebase
    DatabaseReference mTable;

    int x_down;
    int y_down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        // Week 7
        recyclerView = findViewById(R.id.rv);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        mItemViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");
        mTable = myRef.child("Books");


        // bind fab button with id
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText myTitle = findViewById(R.id.inputTextTitle);
                EditText myISBN = findViewById(R.id.inputTextISBN);
                EditText myAuthor = findViewById(R.id.inputTextAuthor);
                EditText myDescription = findViewById(R.id.inputTextDescription);
                EditText myPrice = findViewById(R.id.inputTextPrice);
                String TitleData = myTitle.getText().toString();
                String ISBNData = myISBN.getText().toString();
                String AuthorData = myAuthor.getText().toString();
                String DescriptionData = myDescription.getText().toString();
                String PriceData = myPrice.getText().toString();


                Formatter formatter = new Formatter();   // utility class to format stream output
                formatter.format("%.2f", Double.valueOf(myPrice.getText().toString()));

                Toast.makeText(getApplicationContext(),"Book (" + myTitle.getText() + ") " +
                        "and the price (" + formatter.toString() + ")", Toast.LENGTH_SHORT).show();

                BookItem book = new BookItem(TitleData, ISBNData, AuthorData, DescriptionData, Integer.parseInt(PriceData));
                mItemViewModel.insert(book);
                myRef.push().setValue(book);
            }
        });

//        // Week 10 contents
//        View view=findViewById(R.id.touchFrame);
//        // registering onTouchListener
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int action = event.getActionMasked();
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        x_down = (int)event.getX();
//                        y_down = (int)event.getY();
//                        // EXTRA LAB - author caps
//                        if(x_down <= 100 && y_down <= 100){
//                            EditText myAuthor = findViewById(R.id.inputTextAuthor);
//                            String AuthorData = myAuthor.getText().toString();
//                            myAuthor.setText(AuthorData.toUpperCase());
//                        }
//
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
//                        // price increase ->
//                        if(x_down-event.getX()<0 && Math.abs(y_down-event.getY())<40){
//                            EditText myPrice = findViewById(R.id.inputTextPrice);
//                            String PriceData = myPrice.getText().toString();
//                            int priceint = Integer.parseInt(PriceData) + 1;
//                            PriceData = Integer.toString(priceint);
//                            myPrice.setText(PriceData);
//                        }
//                        return true;
//                    case (MotionEvent.ACTION_UP) :
//                        if(Math.abs(x_down-event.getX())>40){
//                            if(x_down-event.getX()>0){
//                                // add book <-
//                                EditText myTitle = findViewById(R.id.inputTextTitle);
//                                EditText myISBN = findViewById(R.id.inputTextISBN);
//                                EditText myAuthor = findViewById(R.id.inputTextAuthor);
//                                EditText myDescription = findViewById(R.id.inputTextDescription);
//                                EditText myPrice = findViewById(R.id.inputTextPrice);
//                                String TitleData = myTitle.getText().toString();
//                                String ISBNData = myISBN.getText().toString();
//                                String AuthorData = myAuthor.getText().toString();
//                                String DescriptionData = myDescription.getText().toString();
//                                String PriceData = myPrice.getText().toString();
//
//
//                                Formatter formatter = new Formatter();   // utility class to format stream output
//                                formatter.format("%.2f", Double.valueOf(myPrice.getText().toString()));
//
//                                Toast.makeText(getApplicationContext(),"Book (" + myTitle.getText() + ") " +
//                                        "and the price (" + formatter.toString() + ")", Toast.LENGTH_SHORT).show();
//
//                                BookItem book = new BookItem(TitleData, ISBNData, AuthorData, DescriptionData, Integer.parseInt(PriceData));
//                                mItemViewModel.insert(book);
//                                myRef.push().setValue(book);
//                            }
//                        }
//                        else if( Math.abs(y_down-event.getY())>40){
//                            if((y_down-event.getY())<0){
//                                finish();
//                            }
//                            else if ((y_down-event.getY())>0){
//                                Toast.makeText(getApplicationContext(),"up", Toast.LENGTH_SHORT).show();
//                                EditText myID = findViewById(R.id.inputTextID);
//                                EditText myTitle = findViewById(R.id.inputTextTitle);
//                                EditText myISBN = findViewById(R.id.inputTextISBN);
//                                EditText myAuthor = findViewById(R.id.inputTextAuthor);
//                                EditText myDescription = findViewById(R.id.inputTextDescription);
//                                EditText myPrice = findViewById(R.id.inputTextPrice);
//
//                                myID.setText("");
//                                myTitle.setText("");
//                                myISBN.setText("");
//                                myAuthor.setText("");
//                                myDescription.setText("");
//                                myPrice.setText("");
//                            }
//                        }
//
//                        return true;
//                    default :
//                        return false;
//                }
//            }
//        });

        // Week 11 Contents
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        // scaleGestureDetector = new ScaleGestureDetector(this, new MyScaleDetector());
        // Week 11 contents
        View view=findViewById(R.id.touchFrame);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // override the onTouch callback method and forward the MotionEvent object to the detectors
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(MotionEvent e){
            EditText myID = findViewById(R.id.inputTextID);
            EditText myTitle = findViewById(R.id.inputTextTitle);
            EditText myISBN = findViewById(R.id.inputTextISBN);
            EditText myAuthor = findViewById(R.id.inputTextAuthor);
            EditText myDescription = findViewById(R.id.inputTextDescription);
            EditText myPrice = findViewById(R.id.inputTextPrice);

            myID.setText("");
            myTitle.setText("");
            myISBN.setText("");
            myAuthor.setText("");
            myDescription.setText("");
            myPrice.setText("");
            return super.onDoubleTap(e);
        }


        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            String random = generateNewRandomString(7);
            EditText myISBN = findViewById(R.id.inputTextISBN);
            myISBN.setText(random);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            EditText myPrice = findViewById(R.id.inputTextPrice);
            EditText myTitle = findViewById(R.id.inputTextTitle);
            String PriceData = myPrice.getText().toString();
            float newPrice =Float.parseFloat(PriceData);


            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                // When it moves to the right, distanceX gives a negative value
                // so we have to - so it increases the price when moved right ->
                newPrice -= distanceX;

                if (newPrice < 0){
                    newPrice = 0;
                }

                Formatter formatter = new Formatter();   // utility class to format stream output
                formatter.format("%.2f", Double.valueOf(String.valueOf(newPrice)));


                myPrice.setText(formatter.toString());
            }

            else if (Math.abs(distanceX) < Math.abs(distanceY)){

                myTitle.setText("Untitled");

            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {

            if (Math.abs(velocityX) > 1000 || Math.abs(velocityY) > 1000) {
                moveTaskToBack(true);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            EditText myID = findViewById(R.id.inputTextID);
            EditText myTitle = findViewById(R.id.inputTextTitle);
            EditText myISBN = findViewById(R.id.inputTextISBN);
            EditText myAuthor = findViewById(R.id.inputTextAuthor);
            EditText myDescription = findViewById(R.id.inputTextDescription);
            EditText myPrice = findViewById(R.id.inputTextPrice);

            myID.setText("0");
            myTitle.setText("Kite Runner");
            myISBN.setText("123");
            myAuthor.setText("Kim");
            myDescription.setText("Good");
            myPrice.setText("5");
            super.onLongPress(e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lab 3","onStart");

        SharedPreferences myData = getPreferences(0);
        String IDData = myData.getString("key2","");
        String TitleData = myData.getString("key3","");
        String ISBNData = myData.getString("key4","");
        String AuthorData = myData.getString("key5","");
        String DescriptionData = myData.getString("key6","");
        // String PriceData = myData.getString("key7","");

        EditText myID = findViewById(R.id.inputTextID);
        EditText myTitle = findViewById(R.id.inputTextTitle);
        EditText myISBN = findViewById(R.id.inputTextISBN);
        EditText myAuthor = findViewById(R.id.inputTextAuthor);
        EditText myDescription = findViewById(R.id.inputTextDescription);
        EditText myPrice = findViewById(R.id.inputTextPrice);
        myID.setText(IDData);
        myTitle.setText(TitleData);
        myISBN.setText(ISBNData);
        myAuthor.setText(AuthorData);
        myDescription.setText(DescriptionData);
        // myPrice.setText(PriceData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lab 3","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lab 3","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lab 3","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lab 3","onDestroy");}

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        EditText myTitle = findViewById(R.id.inputTextTitle);
        EditText myISBN = findViewById(R.id.inputTextISBN);
        outState.putString("titleKey", myTitle.getText().toString());
        outState.putString("isbnKey", myISBN.getText().toString());

        Log.i("lab 3","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("lab 3", "onRestoreInstanceState");

        String sTitle = savedInstanceState.getString("titleKey");
        EditText myTitle = findViewById(R.id.inputTextTitle);
        myTitle.setText(sTitle);
        String sISBN = savedInstanceState.getString("isbnKey");
        EditText myISBN = findViewById(R.id.inputTextISBN);
        myISBN.setText(sISBN);

        EditText myID = findViewById(R.id.inputTextID);
        EditText myAuthor = findViewById(R.id.inputTextAuthor);
        EditText myDescription = findViewById(R.id.inputTextDescription);
        // EditText myPrice = findViewById(R.id.inputTextPrice);
        myID.setText("");
        myAuthor.setText("");
        myDescription.setText("");
        // myPrice.setText(0);
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.add_item) {
                EditText myTitle = findViewById(R.id.inputTextTitle);
                EditText myISBN = findViewById(R.id.inputTextISBN);
                EditText myAuthor = findViewById(R.id.inputTextAuthor);
                EditText myDescription = findViewById(R.id.inputTextDescription);
                EditText myPrice = findViewById(R.id.inputTextPrice);
                String TitleData = myTitle.getText().toString();
                String ISBNData = myISBN.getText().toString();
                String AuthorData = myAuthor.getText().toString();
                String DescriptionData = myDescription.getText().toString();
                String PriceData = myPrice.getText().toString();


                Formatter formatter = new Formatter();   // utility class to format stream output
                formatter.format("%.2f", Double.valueOf(myPrice.getText().toString()));

                Toast.makeText(getApplicationContext(),"Book (" + myTitle.getText() + ") " +
                        "and the price (" + formatter.toString() + ")", Toast.LENGTH_SHORT).show();

                BookItem book = new BookItem(TitleData, ISBNData, AuthorData, DescriptionData, Integer.parseInt(PriceData));
                mItemViewModel.insert(book);
                myRef.push().setValue(book);

            } else if (id == R.id.removeLast_item) {
                mItemViewModel.deleteLastItem();

            } else if (id == R.id.removeAll_item){
                mItemViewModel.deleteAll();
                myRef.removeValue();
            }

            else if (id == R.id.closeApp) {
                finish();
            }
            else if (id == R.id.listAll) {
                Intent i = new Intent(getApplicationContext(), MainActivityList.class);
                startActivity(i);

            }else if (id == R.id.deleteSomeItems) {
                mItemViewModel.deleteSomeItem();
            }
            // close the drawer
            drawer.closeDrawers();
            // tell the OS
            return true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.clearFields) {
            EditText myID = findViewById(R.id.inputTextID);
            EditText myTitle = findViewById(R.id.inputTextTitle);
            EditText myISBN = findViewById(R.id.inputTextISBN);
            EditText myAuthor = findViewById(R.id.inputTextAuthor);
            EditText myDescription = findViewById(R.id.inputTextDescription);
            EditText myPrice = findViewById(R.id.inputTextPrice);

            myID.setText("");
            myTitle.setText("");
            myISBN.setText("");
            myAuthor.setText("");
            myDescription.setText("");
            myPrice.setText("");

        } else if (id == R.id.Load_item) {
            // loads the old stuff
            EditText myID = findViewById(R.id.inputTextID);
            EditText myTitle = findViewById(R.id.inputTextTitle);
            EditText myISBN = findViewById(R.id.inputTextISBN);
            EditText myAuthor = findViewById(R.id.inputTextAuthor);
            EditText myDescription = findViewById(R.id.inputTextDescription);
            EditText myPrice = findViewById(R.id.inputTextPrice);

            SharedPreferences myData = getPreferences(0);
            String IDData = myData.getString("key2","");
            String TitleData = myData.getString("key3","");
            String ISBNData = myData.getString("key4","");
            String AuthorData = myData.getString("key5","");
            String DescriptionData = myData.getString("key6","");
            String PriceData = myData.getString("key7","");

            myID.setText(IDData);
            myTitle.setText(TitleData);
            myISBN.setText(ISBNData);
            myAuthor.setText(AuthorData);
            myDescription.setText(DescriptionData);
            myPrice.setText(PriceData);
        }
        else if (id == R.id.showTotalBooks) {
        }
        return super.onOptionsItemSelected(item);
    }

    public static String generateNewRandomString(int length) {
        char[] buf;
        Random random=new Random();
        if (length < 1) throw new IllegalArgumentException();
        buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = alphaNummeric.charAt(random.nextInt(alphaNummeric.length()));
        return new String(buf);
    }
}