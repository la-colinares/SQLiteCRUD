package com.app.phonebook.EditContact;

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

import com.app.phonebook.Constants.Constants;
import com.app.phonebook.Database.MyDatabase;
import com.app.phonebook.Database.MyDatabaseFunctions;
import com.app.phonebook.Home.Home;
import com.app.phonebook.R;
import com.app.phonebook.TableStructure.TableContacts;
import com.app.phonebook.Utils.DateTimeUtils;

public class EditContact extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText txtName, txtNumber, txtAge, txtGender;
    private Button btnUpdate;

    private SQLiteDatabase mDb;

    private final String MALE = "Male";
    private final String FEMALE = "Female";

    //for getting intent extras
    private String extra_name, extra_number, extra_gender;
    private int extra_id, extra_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        initDatabase();
        getAllIntents();
        initSetTexts();
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyFields()) {
                    validationDialog();
                } else {
                    updateContact();
                }
            }
        });

    }

    private void updateContact() {

        int updateId = extra_id;

        String age = txtAge.getText().toString();

        String updateName = txtName.getText().toString();
        String updateNumber = txtNumber.getText().toString();
        int updateAge = Integer.parseInt(age);
        String updateGender = txtGender.getText().toString();

        ContentValues values = new ContentValues();

        values.put(TableContacts.COLUMN_NAME, updateName);
        values.put(TableContacts.COLUMN_NUMBER, updateNumber);
        values.put(TableContacts.COLUMN_AGE, updateAge);
        values.put(TableContacts.COLUMN_GENDER, updateGender);
        values.put(TableContacts.COLUMN_DATE_CREATED, DateTimeUtils.getDateTime());

        boolean result = MyDatabaseFunctions.update(updateId, values);

        if (result) {
            updateSuccessDialog();
        } else {
            updateFailedDialog();
        }

    }

    private void updateFailedDialog() {
        final AlertDialog.Builder failed = new AlertDialog.Builder(this);

        failed.setTitle("Updating Contact");
        failed.setMessage("Failed to update contact.");
        failed.setCancelable(true);
        failed.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();

            }
        });

        failed.create().show();

    }

    private void updateSuccessDialog() {
        final AlertDialog.Builder success = new AlertDialog.Builder(this);

        success.setTitle("Updating Contact");
        success.setMessage("Update successful.");
        success.setCancelable(true);
        success.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();

            }
        });

        success.create().show();

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

    private void initSetTexts() {
        txtName.setText(extra_name);
        txtNumber.setText(extra_number);
        txtAge.setText("" + extra_age);
        txtGender.setText(extra_gender);
    }

    private void initDatabase() {
        mDb = MyDatabase.getInstance(getApplicationContext()).getReadableDatabase();
        MyDatabaseFunctions.init(getApplicationContext());
    }

    private void getAllIntents() {
        extra_id = getIntent().getExtras().getInt(Constants.EXTRA_ID);
        extra_name = getIntent().getExtras().getString(Constants.EXTRA_NAME);
        extra_number = getIntent().getExtras().getString(Constants.EXTRA_NUMBER);
        extra_age = getIntent().getExtras().getInt(Constants.EXTRA_AGE);
        extra_gender = getIntent().getExtras().getString(Constants.EXTRA_GENDER);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = findViewById(R.id.edit_name);
        txtNumber = findViewById(R.id.edit_number);
        txtAge = findViewById(R.id.edit_age);
        txtGender = findViewById(R.id.edit_gender);
        btnUpdate = findViewById(R.id.btnUpdate);
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
