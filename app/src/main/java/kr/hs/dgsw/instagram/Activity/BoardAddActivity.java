package kr.hs.dgsw.instagram.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.ResponseFormat;
import kr.hs.dgsw.instagram.Network.Network;
import kr.hs.dgsw.instagram.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BoardAddActivity extends AppCompatActivity {

    ImageButton btn1, btn2, btn3, btn4, btn5;
    Button btnSend;
    EditText texttitle, textContent, textWriter;
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    ImageView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_add);
        getSupportActionBar().hide();

        btn1 = findViewById(R.id.ibtnHome);
        btn2 = findViewById(R.id.ibtnSearch);
        btn3 = findViewById(R.id.ibtnAdd);
        btn4 = findViewById(R.id.ibtnLike);
        btn5 = findViewById(R.id.ibtnUser);

        btnSend = (Button) findViewById(R.id.btnAdd);

        textWriter = (EditText) findViewById(R.id.etWriter);
        texttitle = (EditText) findViewById(R.id.etTitle);
        textContent = (EditText) findViewById(R.id.etContent);

        ivImage = (ImageView) findViewById(R.id.ivImage);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String writer = textWriter.getText().toString();
                String content = textContent.getText().toString();
                String title = texttitle.getText().toString();

                BoardModel bm = new BoardModel();
                bm.setTitle(title);
                bm.setContent(content);
                bm.setWriter(writer);

                Network network = Network.retrofit.create(Network.class);
                Call<ResponseFormat> call = network.post(bm);
                call.enqueue(new Callback<ResponseFormat>() {
                    @Override
                    public void onResponse(Response<ResponseFormat> response, Retrofit retrofit) {
                        Log.e("result", response.body().toString());

                        Intent intent = new Intent(BoardAddActivity.this, BoardListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("error", t.getMessage());
                    }
                });
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardAddActivity.this, BoardListActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectPhoto();
                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectGallery();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(new ContextThemeWrapper(BoardAddActivity.this, R.style.myDialog)).setTitle("Select Upload Image").setPositiveButton("camera", cameraListener).setNeutralButton("album",albumListener).setNegativeButton("cancel", cancelListener).show();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void SelectPhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, CAMERA_CODE);
                }
            }

        }
    }

    private void selectGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/path/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/path/"

                + mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;

    }

    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        ivImage.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    private Bitmap rotate(Bitmap src, float degree) {

// Matrix 객체 생성
        Matrix matrix = new Matrix();
// 회전 각도 셋팅
        matrix.postRotate(degree);
// 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GALLERY_CODE:
                    sendPicture(data.getData()); //갤러리에서 가져오기
                    break;
                case CAMERA_CODE:
                    getPictureForPhoto(); //카메라에서 가져오기
                    break;

                default:
                    break;
            }

        }

    }

    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        ivImage.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기

    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }
}
