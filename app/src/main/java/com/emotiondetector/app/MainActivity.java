package com.emotiondetector.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView imageView;
    private TextView resultTextView;
    private Button captureButton;
    private Button analyzeButton;
    private ProgressBar progressBar;
    private Bitmap capturedImage;
    private EmotionDetector emotionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        imageView = findViewById(R.id.imageView);
        resultTextView = findViewById(R.id.resultTextView);
        captureButton = findViewById(R.id.captureButton);
        analyzeButton = findViewById(R.id.analyzeButton);
        progressBar = findViewById(R.id.progressBar);

        // Initialize emotion detector
        emotionDetector = new EmotionDetector(this);

        // Set up capture button
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermissionAndCapture();
            }
        });

        // Set up analyze button
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capturedImage != null) {
                    analyzeEmotion();
                } else {
                    Toast.makeText(MainActivity.this, "Please capture an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        analyzeButton.setEnabled(false);
    }

    private void checkCameraPermissionAndCapture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            capturedImage = (Bitmap) extras.get("data");
            imageView.setImageBitmap(capturedImage);
            analyzeButton.setEnabled(true);
            resultTextView.setText("Image captured! Tap 'Analyze Emotion' to detect.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void analyzeEmotion() {
        progressBar.setVisibility(View.VISIBLE);
        resultTextView.setText("Analyzing...");

        emotionDetector.detectEmotion(capturedImage, new EmotionDetector.EmotionCallback() {
            @Override
            public void onSuccess(String emotion, String confidence) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        String emoji = getEmotionEmoji(emotion);
                        resultTextView.setText("Emotion: " + emotion + " " + emoji + "\n" + 
                                             "Confidence: " + confidence);
                    }
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        resultTextView.setText("Error: " + error);
                    }
                });
            }
        });
    }

    private String getEmotionEmoji(String emotion) {
        switch (emotion.toLowerCase()) {
            case "happy": return "😊";
            case "sad": return "😢";
            case "angry": return "😠";
            case "surprised": return "😲";
            case "fearful": return "😨";
            case "disgusted": return "🤢";
            case "neutral": return "😐";
            default: return "🙂";
        }
    }
}
