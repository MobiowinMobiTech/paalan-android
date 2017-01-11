package com.phyder.paalan.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.phyder.paalan.R;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class FragmentMyProfile extends Fragment {
    final static int IMG_RESULT = 1;
    final int CAMERA_REQUEST = 1888;
    private ImageView profileImage;
    String ImageDecode;

    Intent intent;
    String[] FILE;

    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_profile, container, false);


        profileImage = (ImageView) view.findViewById(R.id.imageview_dp_image);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity());
                // AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle(" Select Profile");


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                        // Write your code here to invoke YES event
                        //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Gallary", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent, IMG_RESULT);
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            profileImage.setImageBitmap(photo);

//            try {
//
//                if (requestCode == IMG_RESULT && resultCode == RESULT_OK
//                        && null != data) {
//
//                    Log.d(TAG, "onActivityResult: " + IMG_RESULT + "\n" + RESULT_OK);
//
//                    Uri URI = data.getData();
//                    String[] FILE = {MediaStore.Images.Media.DATA};
//
//
//                    Cursor cursor = getActivity().getContentResolver().query(URI,
//                            FILE, null, null, null);
//
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(FILE[0]);
//                    Log.d(TAG, "Photopath" + ImageDecode);
//                    ImageDecode = cursor.getString(columnIndex);
//
//                    Log.d(TAG, "Photopath" + ImageDecode);
//                    profileImage.setImageBitmap(BitmapFactory
//                            .decodeFile(ImageDecode));
//                    cursor.close();
//                }
//            } catch (Exception e) {
//                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG)
//                        .show();
//            }
        } else if (requestCode == IMG_RESULT) {
            try {

                if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                        && null != data) {

                    Log.d(TAG, "onActivityResult: " + IMG_RESULT + "\n" + RESULT_OK);

                    Uri URI = data.getData();
                    String[] FILE = {MediaStore.Images.Media.DATA};


                    Cursor cursor = getActivity().getContentResolver().query(URI,
                            FILE, null, null, null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(FILE[0]);
                    Log.d(TAG, "Photopath" + ImageDecode);
                    ImageDecode = cursor.getString(columnIndex);

                    Log.d(TAG, "Photopath" + ImageDecode);
                    profileImage.setImageBitmap(BitmapFactory
                            .decodeFile(ImageDecode));
                    cursor.close();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}


//

