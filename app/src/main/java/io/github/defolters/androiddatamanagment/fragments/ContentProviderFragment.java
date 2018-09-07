package io.github.defolters.androiddatamanagment.fragments;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import io.github.defolters.androiddatamanagment.data.DataBaseOpenHelper;
import io.github.defolters.androiddatamanagment.Entry;
import io.github.defolters.androiddatamanagment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentProviderFragment extends Fragment {

    private static final int REQUEST_CONTACTS = 0;
    private DataBaseOpenHelper dataBaseOpenHelper;

    public ContentProviderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_provider, container, false);

        dataBaseOpenHelper = DataBaseOpenHelper.getDataBaseOpenHelper(getActivity());

        final Button loadContacts = view.findViewById(R.id.load_contacts);

        loadContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted

                    // Should I show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                        //show an explanation
                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "allow permission to read contacts",
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction("ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
                                    }
                                }).show();

                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
                    }
                } else {
                    getContacts();
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CONTACTS) {
            if (permissions[0].equals(Manifest.permission.READ_CONTACTS) &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getContacts();
            }
        }
    }

    private void getContacts() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        // РАЗОБРАТЬСЯ
        ArrayList<Entry> contacts = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{ id }, null);
                    while (pCur.moveToNext()) {
                        String name = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(new Entry(name, contactNumber));
                        break;
                    }
                    pCur.close();
                }
            } while (cursor.moveToNext()) ;
        }
        Log.d("CONTENT","numbers:"+contacts);

        dataBaseOpenHelper.addEntries(contacts);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Contacts loaded", Snackbar.LENGTH_LONG).show();
    }
}
