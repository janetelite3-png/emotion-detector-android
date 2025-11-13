package com.emotiondetector.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EmotionDetector {

    private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY_HERE";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private OkHttpClient client;
    private Context context;

    public interface EmotionCallback {
        void onSuccess(String emotion, String confidence);
        void onError(String error);
    }

    public EmotionDetector(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public void detectEmotion(Bitmap bitmap, EmotionCallback callback) {
        try {
            String base64Image = bitmapToBase64(bitmap);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-4o-mini");

            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");

            JSONArray content = new JSONArray();

            // Text prompt
            JSONObject textPart = new JSONObject();
            textPart.put("type", "text");
            textPart.put("text", "Analyze this face image and detect the primary emotion. " +
                    "Respond ONLY in this exact JSON format: {\"emotion\": \"<emotion_name>\", \"confidence\": \"<percentage>%\"}. " +
                    "Possible emotions: happy, sad, angry, surprised, neutral, fearful, disgusted. " +
                    "Be concise and accurate.");
            content.put(textPart);

            // Image part
            JSONObject imagePart = new JSONObject();
            imagePart.put("type", "image_url");
            JSONObject imageUrl = new JSONObject();
            imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
            imagePart.put("image_url", imageUrl);
            content.put(imagePart);

            message.put("content", content);
            messages.put(message);

            jsonBody.put("messages", messages);
            jsonBody.put("max_tokens", 300);

            RequestBody body = RequestBody.create(
                    jsonBody.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError("Network error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            JSONObject jsonResponse = new JSONObject(responseBody);

                            String messageContent = jsonResponse
                                    .getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");

                            // Parse emotion and confidence from response
                            JSONObject emotionData = new JSONObject(messageContent);
                            String emotion = emotionData.getString("emotion");
                            String confidence = emotionData.getString("confidence");

                            callback.onSuccess(emotion, confidence);
                        } catch (Exception e) {
                            callback.onError("Error parsing response: " + e.getMessage());
                        }
                    } else {
                        callback.onError("API error: " + response.code() + " - " + response.message());
                    }
                }
            });

        } catch (Exception e) {
            callback.onError("Error: " + e.getMessage());
        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
}
