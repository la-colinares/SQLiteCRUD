package com.app.phonebook.AsyncTasks;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.phonebook.Adapters.ContactAdapter;
import com.app.phonebook.Constants.Constants;
import com.app.phonebook.Database.MyDatabase;
import com.app.phonebook.Database.MyDatabaseFunctions;
import com.app.phonebook.EditContact.EditContact;
import com.app.phonebook.Model.ContactModel;
import com.app.phonebook.R;
import com.app.phonebook.TableStructure.TableContacts;
import com.app.phonebook.Utils.OnTapListener;

import java.util.ArrayList;

/**
 * Created by Colinares on 4/6/2018.
 */
public class ContactsAsyncTask extends AsyncTask<Void, ContactModel, Void> {

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private LinearLayout noContacts;
    private Activity mActivity;

    private ContactAdapter mAdapter;
    private ArrayList<ContactModel> contactModels = new ArrayList<>();

    private int[] contact_id, contact_age;
    private String[] contact_name, contact_number, contact_gender, date_created;

    private SQLiteDatabase mDb;

    //for custom preview
    private TextView preview_name,
            preview_number,
            preview_age,
            preview_gender,
            preview_date;
    private ImageView preview_genderImage;

    private LinearLayout linearLayoutEdit, linearLayoutDelete, linearLayoutClose;

    private Animation goFadeOut;

    public ContactsAsyncTask(CoordinatorLayout coordinatorLayout, RecyclerView recyclerView, LinearLayout noRecords, Activity mActivity) {
        this.coordinatorLayout = coordinatorLayout;
        this.recyclerView = recyclerView;
        this.noContacts = noRecords;
        this.mActivity = mActivity;

        goFadeOut = AnimationUtils.loadAnimation(this.mActivity, R.anim.fade_out);
    }

    @Override
    protected void onPreExecute() {
        mAdapter = new ContactAdapter(contactModels);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        initDatabase();
        loadTrash();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(ContactModel... values) {
        contactModels.add(values[0]);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        mAdapter.setOnTapListener(new OnTapListener() {
            @Override
            public void onTapView(int position) {
                previewContact(position);
            }
        });
    }

    private void previewContact(final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity.getApplicationContext());
        final View custom_preview_view = layoutInflater.inflate(R.layout.custom_preview_contact, null);

        initCustomLayoutViews(custom_preview_view);

        final AlertDialog.Builder preview = new AlertDialog.Builder(this.mActivity);
        preview.setCancelable(true);
        preview.setView(custom_preview_view);

        displayPreview(position);

        final AlertDialog dialog = preview.create();

        dialog.show();

        initOnClickListener(dialog, position);

    }

    private void initOnClickListener(final AlertDialog alertDialog, final int position) {
        linearLayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutEdit.startAnimation(goFadeOut);

                Intent goEdit = new Intent(mActivity.getApplicationContext(), EditContact.class);

                goEdit.putExtra(Constants.EXTRA_ID, contact_id[position]);
                goEdit.putExtra(Constants.EXTRA_NAME, contact_name[position]);
                goEdit.putExtra(Constants.EXTRA_NUMBER, contact_number[position]);
                goEdit.putExtra(Constants.EXTRA_AGE, contact_age[position]);
                goEdit.putExtra(Constants.EXTRA_GENDER, contact_gender[position]);

                mActivity.startActivity(goEdit);
                alertDialog.dismiss();
                mActivity.finish();

            }
        });

        linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutDelete.startAnimation(goFadeOut);
                deleteWarningDialog(alertDialog, position);
            }
        });

        linearLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutClose.startAnimation(goFadeOut);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteWarningDialog(final AlertDialog alertDialog, final int position) {
        final AlertDialog.Builder delete = new AlertDialog.Builder(this.mActivity);

        delete.setTitle("Warning");
        delete.setMessage("Are you sure you want to delete " + contact_name[position].toUpperCase() + " from your contacts?");
        delete.setCancelable(true);
        delete.setPositiveButton("No", null);
        delete.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goDelete(position);
                alertDialog.dismiss();
            }
        });

        delete.create().show();

    }

    private void goDelete(final int position) {
        int id = contact_id[position];

        Log.e("contact_id", id + "");

        MyDatabaseFunctions.delete(id);

        mAdapter.refreshList(contactModels);
        initDatabase();
        loadTrash();

        deleteSuccessfulDialog();


    }

    private void deleteSuccessfulDialog() {
        final AlertDialog.Builder delete = new AlertDialog.Builder(this.mActivity);

        delete.setTitle("Message");
        delete.setMessage("Contact Deleted.");
        delete.setCancelable(true);
        delete.setPositiveButton("OK", null);

        delete.create().show();

    }

    private void displayPreview(final int position) {
        preview_name.setText("Name : " + contact_name[position]);
        preview_number.setText("Number : " + contact_number[position]);
        preview_age.setText("Age : " + contact_age[position]);
        preview_gender.setText("Gender : " + contact_gender[position]);
        preview_date.setText("Date Created : " + date_created[position]);

        if (contact_gender[position].equals("Male")) {
            preview_genderImage.setImageResource(R.drawable.male);
        } else {
            preview_genderImage.setImageResource(R.drawable.female);
        }
    }

    private void initCustomLayoutViews(final View custom_preview_view) {
        preview_name = custom_preview_view.findViewById(R.id.preview_name);
        preview_number = custom_preview_view.findViewById(R.id.preview_number);
        preview_age = custom_preview_view.findViewById(R.id.preview_age);
        preview_gender = custom_preview_view.findViewById(R.id.preview_gender);
        preview_date = custom_preview_view.findViewById(R.id.preview_date);

        preview_genderImage = custom_preview_view.findViewById(R.id.preview_image);

        linearLayoutEdit = custom_preview_view.findViewById(R.id.linearEdit);
        linearLayoutDelete = custom_preview_view.findViewById(R.id.linearDelete);
        linearLayoutClose = custom_preview_view.findViewById(R.id.linearClose);
    }

    private void loadTrash() {
        int index = 0;

        String query = "SELECT * FROM " + TableContacts.TABLE_NAME +
                " ORDER BY " + TableContacts.COLUMN_NAME +
                " ASC";

        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();

        contact_id = new int[cursor.getCount()];
        contact_name = new String[cursor.getCount()];
        contact_number = new String[cursor.getCount()];
        contact_age = new int[cursor.getCount()];
        contact_gender = new String[cursor.getCount()];
        date_created = new String[cursor.getCount()];

        if (cursor.getCount() == 0) {
            noContacts.setVisibility(View.VISIBLE);
        } else {
            noContacts.setVisibility(View.INVISIBLE);

            if (cursor.moveToFirst()) {
                do {

                    contact_id[index] = cursor.getInt(cursor.getColumnIndex(TableContacts.COLUMN_ID));
                    contact_name[index] = cursor.getString(cursor.getColumnIndex(TableContacts.COLUMN_NAME));
                    contact_number[index] = cursor.getString(cursor.getColumnIndex(TableContacts.COLUMN_NUMBER));
                    contact_age[index] = cursor.getInt(cursor.getColumnIndex(TableContacts.COLUMN_AGE));
                    contact_gender[index] = cursor.getString(cursor.getColumnIndex(TableContacts.COLUMN_GENDER));
                    date_created[index] = cursor.getString(cursor.getColumnIndex(TableContacts.COLUMN_DATE_CREATED));

                    publishProgress(new ContactModel(contact_id[index], contact_age[index], contact_name[index], contact_number[index],
                            contact_gender[index], date_created[index]));

                    index++;
                } while (cursor.moveToNext());
            }
        }
    }

    private void initDatabase() {
        mDb = MyDatabase.getInstance(mActivity).getReadableDatabase();
        MyDatabaseFunctions.init(mActivity.getApplicationContext());
    }
}
