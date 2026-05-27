package com.example.security_shashin;

import android.content.Context;
import android.util.Log;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

public class BiometricsHelper {
    Context context;
    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.AuthenticationCallback callback;
    BiometricPrompt.PromptInfo promptInfo;
    public BiometricsHelper(MainActivity activity, BiometricPrompt.AuthenticationCallback callback) {
        this.context = activity.getApplicationContext();
        this.callback = callback;
        this.executor = ContextCompat.getMainExecutor(activity);

        biometricPrompt = new BiometricPrompt(activity, this.executor, this.callback);
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация по отпечатку")
                .setSubtitle("Приложите палец к сканеру")
                .setDescription("Подтвердите личность для входа в приложение")
                .setNegativeButtonText("Отмена")
                .build();
    }
    public void show() {
        if(isBiometricAvailable()) {
            biometricPrompt.authenticate(promptInfo);
        } else {
            Log.d("BiometricHelper", "Биометрия недоступна на устройстве");
        }
    }
    boolean isBiometricAvailable() {
        BiometricManager biometricManager = BiometricManager.from(context);
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS;
    }
}
