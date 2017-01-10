package com.phyder.paalan.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.phyder.paalan.R;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class FragmentMyProfile extends Fragment {

     final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    Intent intent;
    String[] FILE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_profile, container, false);


        imageView = (ImageView) view.findViewById(R.id.imageview_dp_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle(" Select Profile");





                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

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

            }
        });



        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}

/*
	    private static int IMG_RESULT = 1;
	    String ImageDecode;
	    ImageView imageViewLoad;
	    Button LoadImage;
	    Intent intent;
	    String[] FILE;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
			 final int CAMERA_REQUEST = 1888;
	        imageViewLoad = (ImageView) findViewById(R.id.imageView1);
	        LoadImage = (Button)findViewById(R.id.button1);

	        LoadImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {


					///////////////////////////

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

					// Setting Dialog Title
					alertDialog.setTitle(" Select Profile");





					// Setting Positive "Yes" Button
					alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int which) {

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

					// Showing Alert Message
					alertDialog.show();
					//////////////////////////


				}
			});

	    }
*/

