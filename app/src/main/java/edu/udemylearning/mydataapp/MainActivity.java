package edu.udemylearning.mydataapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MainActivity extends Activity {
    TextView myTextView;
    EditText myEditText;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         myTextView =  findViewById(R.id.myTextView);
         myEditText =  findViewById(R.id.myEditText);
         dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();
    }

    public void printDatabase() {
        String dbstring = dbHandler.databaseToString();
        myTextView.setText(dbstring);
        myEditText.setText("");
    }

    public void onClickAdd(View v) {;
        Products products = new Products(myEditText.getText().toString());
            dbHandler.addProducts(products);
            printDatabase();

    }

    public void onCLickDelete(View v) {
        String inputText = myEditText.getText().toString();
        dbHandler.deleteProducts(inputText);
        printDatabase();
    }
}
