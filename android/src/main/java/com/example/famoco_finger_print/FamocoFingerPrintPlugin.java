package com.example.famoco_finger_print;


import static com.morpho.morphosmart.sdk.CompressionAlgorithm.MORPHO_NO_COMPRESS;
import static com.morpho.morphosmart.sdk.TemplateFVPType.MORPHO_NO_PK_FVP;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.morpho.morphosmart.sdk.Coder;
import com.morpho.morphosmart.sdk.DetectionMode;
import com.morpho.morphosmart.sdk.EnrollmentType;
import com.morpho.morphosmart.sdk.ErrorCodes;
import com.morpho.morphosmart.sdk.LatentDetection;
import com.morpho.morphosmart.sdk.MorphoDevice;
import com.morpho.morphosmart.sdk.MorphoWakeUpMode;
import com.morpho.morphosmart.sdk.TemplateFVPType;
import com.morpho.morphosmart.sdk.TemplateList;
import com.morpho.morphosmart.sdk.TemplateType;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;

public class FamocoFingerPrintPlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;


    /**
     * Handler to update UI Thread
     */
    private Handler mHandler;

   /**
         * Morpho Device Capture Configuration
         */
        private final String ID_USER = "test";
        private final TemplateType TEMPLATE_TYPE = TemplateType.MORPHO_PK_ISO_FMC_CS;
        private final TemplateFVPType TEMPLATE_FVP_TYPE = MORPHO_NO_PK_FVP;
        private final EnrollmentType ENROLL_TYPE = EnrollmentType.ONE_ACQUISITIONS;
        private final int MAX_SIZE_TEMPLATE = 255;
        private final LatentDetection LATENT_DETECTION = LatentDetection.LATENT_DETECT_ENABLE;
        private final int NB_FINGER = 1;

    // Vous pouvez également ajouter d'autres membres de la classe et des méthodes ici.

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        channel = new MethodChannel(binding.getBinaryMessenger(), "famoco_finger_print");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        // Nettoyez les ressources ou effectuez des actions de nettoyage ici si nécessaire.
    }


    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {

       

        if (call.method.equals("captureFingerprint")) {
            mHandler = new Handler();
            MorphoDevice morphoDevice = new MorphoDevice();

            // Add the code snippet to capture the fingerprint here
            // Replace 'morphoDevice' with Famoco-specific objects

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ProcessInfo processInfo = ProcessInfo.getInstance();
                    final int timeout = processInfo.getTimeout();
                    TemplateList templateList = new TemplateList();

                    int acquisitionThreshold = (processInfo.isFingerprintQualityThreshold()) ?
                            processInfo.getFingerprintQualityThresholdvalue() : 0;
                    int advancedSecurityLevelsRequired = (processInfo.isAdvancedSecLevCompReq()) ?
                            1 : 0xFF;

                    int callbackCmd = processInfo.getCallbackCmd();
                    Coder coderChoice = processInfo.getCoder();

                    int detectModeChoice = DetectionMode.MORPHO_ENROLL_DETECT_MODE.getValue();
                    if (processInfo.isForceFingerPlacementOnTop())
                        detectModeChoice |= DetectionMode.MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE.getValue();
                    if (processInfo.isWakeUpWithLedOff())
                        detectModeChoice |= MorphoWakeUpMode.MORPHO_WAKEUP_LED_OFF.getCode();

                    int ret = morphoDevice.setStrategyAcquisitionMode(processInfo.getStrategyAcquisitionMode());
                    if (ret == ErrorCodes.MORPHO_OK) {

                        final Observer observer = null;

                        ret = morphoDevice.capture(timeout, acquisitionThreshold, advancedSecurityLevelsRequired,
                                NB_FINGER,
                                TEMPLATE_TYPE, TEMPLATE_FVP_TYPE, MAX_SIZE_TEMPLATE, ENROLL_TYPE,
                                LATENT_DETECTION, coderChoice, detectModeChoice,
                                MORPHO_NO_COMPRESS, 0, templateList, callbackCmd, null);
                    }

                    processInfo.setCommandBioStart(false);

                    //MorphoUtils.storeFFDLogs(morphoDevice);

                    if (ret == ErrorCodes.MORPHO_OK) {
                        //exportFVP(templateList);
                        //exportFP(templateList);

                        // Assuming fingerprint data is stored in 'templateList' or a similar object
                        // Convert the fingerprint data to a format suitable for Flutter
                        String fingerprintData = convertFingerprintDataToString(templateList);

                        // Send the captured fingerprint data back to the Flutter app
                        mHandler.post(new Runnable() {
                            @Override
                            public synchronized void run() {
                                result.success(fingerprintData);
                            }
                        });
                    } else {
                        // Handle errors and return an error message
                        mHandler.post(new Runnable() {
                            @Override
                            public synchronized void run() {
                                result.error("FINGERPRINT_CAPTURE_ERROR", "Failed to capture fingerprint", null);
                            }
                        });
                    }
                }
            }).start();
        } else {
            result.notImplemented();
        }
    }

    // Function to convert fingerprint data to a format suitable for Flutter
    private String convertFingerprintDataToString(TemplateList templateList) {
        // Convert the fingerprint data to a string, format it as needed
        // Return the formatted fingerprint data as a string
        return templateList.toString(); // Modify this to return the actual data
    }
   





    
    
}
