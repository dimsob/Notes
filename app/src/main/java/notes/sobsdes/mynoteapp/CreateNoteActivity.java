package notes.sobsdes.mynoteapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.VideoView;

public class CreateNoteActivity extends AppCompatActivity {
    GPSTracker gps;

    Button save;
    Button update;
    String note_text;
    String note_date;
    EditText et;


    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "MyNotesApp";

    private Uri fileUri; // переменная для адреса директории изображений

    private ImageView imgPreview;

    private Button btnCapturePicture, btnRecordVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        save = (Button) findViewById(R.id.button);
        update = (Button) findViewById(R.id.button2);

        int flag = 0;// = Integer.valueOf(getIntent().getStringExtra("flag_place"));


        if (flag == 0) {
            update.setEnabled(false);

        } else if (flag == 1) {
            save.setEnabled(false);
            note_text = getIntent().getStringExtra("note_text");
            note_date = getIntent().getStringExtra("note_date");
            et = (EditText) findViewById(R.id.editText);
            et.setText(note_text);
        }

        imgPreview = (ImageView) findViewById(R.id.imageView);

        btnCapturePicture = (Button) findViewById(R.id.button3);

        /**
         * Обработка события при нажатии на кнопку Сделать фото
         */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Вызов функции? которая сделает фото
                captureImage();
            }
        });

        // Проверка доступности камеры
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Ваше устройство не поддерживает камеру",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }
    /**
     * Функция для проверки доступности камеры
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Запуск втроенного интерфейса камеры в телефон
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // начало фотографирования
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    /**
     * Теперь нужно сохранить сделанное фото при возвращении в приложение из интерфейса камеры
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // сохранение картинки по ссылке
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // установка изображения из ссылки
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    /**
     * Получение результата работы камеры - возможные ошибки например если карта памяти переполнена или батарея имеет низкий заряд
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ПЛящем от результата работы активити камеры
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Изображение сделанно успешно
                // Вызываем функцию? которая устанавливает это изображение в наше ImageView
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // пользователь отменил фото
                Toast.makeText(getApplicationContext(),
                        "Пользователь отменил обработку", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // другие ошибки при попытке сделать фото
                Toast.makeText(getApplicationContext(),
                        "Что то пошло не так!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Отображение изображения в ImageView
     */
    private void previewCapturedImage() {
        try {

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            //
            // Загрузка изображдения если позволяет память устройства
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    /**
     * Дополнительные методы
     * */

    /**
     * Создание ссылки на фото на диске
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Функция для отдачи сделанного фото
     */
    private static File getOutputMediaFile(int type) {

        // Объявление хранилища
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Создание директории приложения в галерее
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Ошибка создания "
                        + IMAGE_DIRECTORY_NAME + " директории");
                return null;
            }
        }

        // Создание названия медиафайла
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
    public void onSaveClick(View view) {
        EditText someEditText = (EditText) findViewById(R.id.editText);
        String str = someEditText.getText().toString();
        String noteText = someEditText.getText().toString();
        NoteDatabase db = new NoteDatabase(CreateNoteActivity.this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatedDate = df.format(c.getTime());


        double lat = 1.0;//широта
        double longT = 1.0;//долгота


        GPSTracker gp = new GPSTracker(CreateNoteActivity.this);

        if (gp.canGetLocation()) {
            lat = gp.getLatitude();
            longT = gp.getLongitude();

            Log.d("Широта", "Lat " + String.valueOf(lat));
            Log.d("Долгота", "Long " + String.valueOf(longT));
        } else {
            gp.showSettingsAlert();
        }
/*Сделать проверку на определение координат - если широтаи долгота равны 0,0 - то вывести сообщение "Координаты не определены/ Попробуйте еще раз/"
И? соответственно, если координаты опеределены  - только тогда создовать объект и сохранять заметку в БД
 */
        //if (lat != 0.0 && longT != 0.0){
        Note note = new Note(str, formatedDate, lat, longT);
        db.addNote(note);
        Toast t = Toast.makeText(this, "Заметка создана", Toast.LENGTH_LONG);
        t.show();


/*****<Блок для уведомлений в статус бар о создании заметки******/
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, AboutActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Заметка создана ")
                .setContentText(str).setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .addAction(R.mipmap.ic_launcher, "Просмотреть", pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
/******Окончание блока создания заметки*********/

        finish();
       /* }
        else {
            Toast t = Toast.makeText(this, "Коордитнат не определены. Попробуйте позже.", Toast.LENGTH_LONG);
            t.show();
        }*/

    }


    public void onRefreshNote(View view) {
        String refreshedNoteText = et.getText().toString();
        NoteDatabase db = new NoteDatabase(CreateNoteActivity.this);
        db.updateNoteByDate(note_date, refreshedNoteText);
        Toast t = Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_LONG);
        t.show();
        finish();
    }
}
