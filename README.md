# Emotion Detector Android App

An Android application that detects emotions from facial expressions using OpenAI Vision API.

## Features

- 📸 Real-time camera capture
- 😊 Emotion detection (Happy, Sad, Angry, Surprised, Neutral, Fearful, Disgusted)
- 🎯 Confidence scoring
- 🎨 Clean, intuitive UI
- 🔒 Privacy-focused (no data storage)

## Detected Emotions

- **Happy** 😊
- **Sad** 😢
- **Angry** 😠
- **Surprised** 😲
- **Neutral** 😐
- **Fearful** 😨
- **Disgusted** 🤢

## Setup Instructions

### Prerequisites

- Android Studio (latest version)
- Android device/emulator with camera
- OpenAI API key

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/janetelite3-png/emotion-detector-android.git
   cd emotion-detector-android
   ```

2. **Add your OpenAI API Key**

   Open `app/src/main/java/com/emotiondetector/app/EmotionDetector.java` and replace:
   ```java
   private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY_HERE";
   ```
   with your actual OpenAI API key.

3. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository folder
   - Wait for Gradle sync to complete

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon)
   - Grant camera permissions when prompted

## How to Use

1. Launch the app
2. Tap "Capture Photo" button
3. Take a selfie or photo of a face
4. Tap "Analyze Emotion" button
5. View the detected emotion and confidence score

## Play Store Submission Guide

### 1. Prepare App Assets

Create the following assets:

- **App Icon**: 512x512 PNG
- **Feature Graphic**: 1024x500 PNG
- **Screenshots**: At least 2 phone screenshots (1080x1920 recommended)
- **Privacy Policy**: Host a privacy policy URL (required)

### 2. Build Release APK/AAB

1. In Android Studio: Build > Generate Signed Bundle/APK
2. Choose "Android App Bundle" (recommended) or APK
3. Create a new keystore or use existing
4. Select release build variant
5. Sign and build

### 3. Create Play Console Account

1. Go to [Google Play Console](https://play.google.com/console)
2. Pay one-time $25 registration fee
3. Complete account verification

### 4. Create App Listing

Fill in these sections:

**App Details:**
- Title: "Emotion Detector - Face Analysis"
- Short description: "Detect emotions from facial expressions using AI"
- Full description: (Expand on features and benefits)
- App category: Tools or Health & Fitness
- Tags: emotion, face detection, AI, accessibility

**Store Listing:**
- Upload app icon, screenshots, feature graphic
- Add privacy policy URL
- Select content rating (Everyone)

**Content Rating:**
- Complete the questionnaire
- App should receive "Everyone" rating

**Pricing & Distribution:**
- Set as Free
- Select countries for distribution
- Agree to content guidelines

### 5. Upload Release

1. Go to "Production" in Release section
2. Create new release
3. Upload your signed AAB/APK
4. Add release notes
5. Review and rollout

### 6. Important Compliance

**Privacy Policy Requirements:**
- Clearly state: "We do not store any facial images or personal data"
- Explain OpenAI API usage for emotion detection
- Mention camera permission usage
- Include contact information

**Permissions Justification:**
- Camera: Required for capturing user's photo for emotion analysis
- Internet: Required to communicate with OpenAI API for emotion detection

### 7. Testing Before Release

- Test on multiple devices
- Check all emotions are detected correctly
- Verify camera permissions work
- Test error handling (no internet, API failures)
- Ensure UI is responsive

## Technical Details

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 33 (Android 13)
- **Language**: Java
- **Architecture**: Single Activity with emotion detection service
- **API**: OpenAI GPT-4o-mini with Vision

## Privacy & Security

- ✅ No image storage (images processed in memory only)
- ✅ No personal data collection
- ✅ Secure HTTPS API communication
- ✅ Camera permission requested at runtime
- ✅ Compliant with Play Store policies

## Troubleshooting

**Camera not working:**
- Check permissions in Settings > Apps > Emotion Detector > Permissions
- Restart the app

**API errors:**
- Verify your OpenAI API key is valid
- Check internet connection
- Ensure API key has sufficient credits

**Build errors:**
- Clean project: Build > Clean Project
- Invalidate caches: File > Invalidate Caches / Restart
- Sync Gradle: File > Sync Project with Gradle Files

## License

This project is open source and available under the MIT License.

## Support

For issues or questions:
- Open an issue on GitHub
- Contact: janetelite3@gmail.com

## Credits

Developed using OpenAI's Vision API for emotion detection.

---

**Note**: Remember to replace the placeholder API key with your actual OpenAI API key before building!
