package kr.hs.dgsw.instagram.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.ResponseBoardFormat;
import kr.hs.dgsw.instagram.Model.ResponseFormat;
import kr.hs.dgsw.instagram.Network.Network;
import kr.hs.dgsw.instagram.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardAddActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    Button btnSend;
    EditText texttitle, textContent, textWriter;
    ImageView ivImage;
    private final int GET_GALLERY_IMAGE = 200;
    private View mLayout;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Uri mImageCaptureUri;
    private String absolutePath;
    Network network = Network.retrofit.create(Network.class);
    SharedPreferences sharedPreferences;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {


            boolean check_result = true;


            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                ;
            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {


                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                } else {


                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_add);
        getSupportActionBar().hide();

        mLayout = findViewById(R.id.layout_main);

        btnSend = (Button) findViewById(R.id.btnAdd);

        texttitle = (EditText) findViewById(R.id.etTitle);
        textContent = (EditText) findViewById(R.id.etContent);

        ivImage = (ImageView) findViewById(R.id.ivImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, GET_GALLERY_IMAGE);

                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(new ContextThemeWrapper(BoardAddActivity.this, R.style.myDialog)).setTitle("Select Upload Image").setPositiveButton("camera", cameraListener).setNeutralButton("album", albumListener).setNegativeButton("cancel", cancelListener).show();

            }
        });

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {


                Snackbar.make(mLayout, "이 앱을 실행하려면 카메라와 외부 저장소 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        ActivityCompat.requestPermissions(BoardAddActivity.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {

                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        sharedPreferences = getSharedPreferences("loginSetting", MODE_PRIVATE);

        if (requestCode == GET_GALLERY_IMAGE) {
            mImageCaptureUri = data.getData();
            final String imagePath = getRealPathFromURI(mImageCaptureUri);
            Log.e("data",data.getData().toString());
            Log.e("img Uri", imagePath);
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    ivImage.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            final File file = new File(imagePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            final RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            final MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String writer = sharedPreferences.getString("name","");
                    String title = texttitle.getText().toString();

                    BoardModel bm = new BoardModel();
                    bm.setTitle(title);
                    bm.setWriter(writer);

                    Call<ResponseBoardFormat> call = network.post(bm);
                    call.enqueue(new Callback<ResponseBoardFormat>() {
                        @Override
                        public void onResponse(Call<ResponseBoardFormat> call, Response<ResponseBoardFormat> response) {
                            Log.e("result", response.body().toString());
                            BoardModel b = response.body().getData();
                            Log.e("board",b.getId());

                            Log.e("upload file", uploadFile.toString());

                            Call<ResponseFormat> call2 = network.uploadImage(uploadFile,Integer.parseInt(b.getId()));
                            call2.enqueue(new Callback<ResponseFormat>() {
                                @Override
                                public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                                    Log.e("response", response.body().toString());
                                }

                                @Override
                                public void onFailure(Call<ResponseFormat> call, Throwable t) {
                                    Log.e("response", t.getMessage());
                                }
                            });

                            Intent intent = new Intent(BoardAddActivity.this, BoardListActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ResponseBoardFormat> call, Throwable t) {
                            Log.e("error", t.getMessage());
                        }
                    });
                }
            });
        }

    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if (!directory_SmartWheel.exists()) {
            directory_SmartWheel.mkdir();
        }
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendPicture(Uri imgUri) {
        String imagePath = getRealPathFromURI(imgUri);
        Log.e("img Uri", imagePath);

        File file = new File(imagePath);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        return cursor.getString(column_index);
    }

}
