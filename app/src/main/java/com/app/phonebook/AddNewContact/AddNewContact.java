package com.app.phonebook.AddNewContact;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.phonebook.Database.MyDatabase;
import com.app.phonebook.Database.MyDatabaseFunctions;
import com.app.phonebook.Home.Home;
import com.app.phonebook.R;
import com.app.phonebook.TableStructure.TableContacts;
import com.app.phonebook.Utils.DateTimeUtils;

public class AddNewContact extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText txtName, txtNumber, txtAge, txtGender;
    private Button btnSave;

    private SQLiteDatabase mDb;

    private final String MALE = "Male";
    private final String FEMALE = "Female";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        initViews();
        initDatabase();
        initOnClickListener();

    }

    private void initOnClickListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            txtGender.setShowSoftInputOnFocus(false);
        }
        txtGender.setFocusableInTouchMode(false);
        txtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGenderDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyFields()) {
                    validationDialog();
                } else {
                    saveNewContact();
                }
            }
        });
    }

    private void saveNewContact() {
        String age = txtAge.getText().toString();


        String saveName = txtName.getText().toString();
        String saveNumber = txtNumber.getText().toString();
        int saveAge = Integer.parseInt(age);
        String saveGender = txtGender.getText().toString();

        ContentValues values = new ContentValues();

        values.put(TableContacts.COLUMN_NAME, saveName);
        values.put(TableContacts.COLUMN_NUMBER, saveNumber);
        values.put(TableContacts.COLUMN_AGE, saveAge);
        values.put(TableContacts.COLUMN_GENDER, saveGender);
        values.put(TableContacts.COLUMN_DATE_CREATED, DateTimeUtils.getDateTime());

        MyDatabaseFunctions.insert(TableContacts.TABLE_NAME, values);

        saveSuccessfulDialog();

    }

    private void saveSuccessfulDialog() {
        final AlertDialog.Builder save = new AlertDialog.Builder(this);

        save.setTitle("Message");
        save.setMessage("New Contact Added.");
        save.setCancelable(true);
        save.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
            }
        });

        save.create().show();

    }

    private void validationDialog() {
        final AlertDialog.Builder errorMessage = new AlertDialog.Builder(this);

        errorMessage.setTitle("Message");
        errorMessage.setMessage("Please Complete All Fields.");
        errorMessage.setCancelable(true);
        errorMessage.setPositiveButton("OK", null);

        errorMessage.create().show();

    }

    private boolean isEmptyFields() {
        if (txtName.getText().toString().isEmpty()
                || txtNumber.getText().toString().isEmpty()
                || txtAge.getText().toString().isEmpty()
                || txtGender.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void selectGenderDialog() {

        final String[] gender = new String[]{MALE, FEMALE};

        final AlertDialog.Builder selectGender = new AlertDialog.Builder(this);

        selectGender.setTitle("Select Gender");
        selectGender.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    txtGender.setText(gender[0]);
                } else {
                    txtGender.setText(gender[1]);
                }
            }
        });
        selectGender.setCancelable(false);
        selectGender.create().show();

    }

    private void initDatabase() {
        mDb = MyDatabase.getInstance(getApplicationContext()).getReadableDatabase();
        MyDatabaseFunctions.init(getApplicationContext());
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = findViewById(R.id.add_name);
        txtNumber = findViewById(R.id.add_number);
        txtAge = findViewById(R.id.add_age);
        txtGender = findViewById(R.id.add_gender);
        btnSave = findViewById(R.id.btnSave);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Home.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.default_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
