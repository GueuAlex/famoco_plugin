package com.example.famoco_finger_print;
import com.morpho.morphosmart.sdk.MorphoDevice;
import com.morpho.morphosmart.sdk.FingerNumber;
import com.morpho.morphosmart.sdk.TemplateType;
import com.morpho.morphosmart.sdk.TemplateFVPType;
import com.morpho.morphosmart.sdk.EnrollmentType;
import com.morpho.morphosmart.sdk.LatentDetection;
import com.morpho.morphosmart.sdk.Coder;
import com.morpho.morphosmart.sdk.DetectionMode;
import com.morpho.morphosmart.sdk.CompressionAlgorithm;
import com.morpho.morphosmart.sdk.TemplateList;
import com.morpho.morphosmart.sdk.Template;
import com.morpho.morphosmart.sdk.ErrorCodes;


import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;

public class FamocoFingerPrintPlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;

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
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("captureFinger")) {
            String message = captureFinger();
            result.success(message);
        } else {
            result.notImplemented();
        }
    }
    private void initializeFingerprints() {
        
    }

    private String getStringFromPlugin() {
        return "Ceci est un message provenant du plugin FamocoFingerPrintPlugin";
    }

    private  String captureFinger() {
        MorphoDevice m = new MorphoDevice();
        TemplateType templateType = TemplateType.MORPHO_PK_COMP;
        TemplateFVPType templateFVPType = TemplateFVPType.MORPHO_PK_FVP;
        EnrollmentType enrollmentType = EnrollmentType.THREE_ACQUISITIONS;
        LatentDetection latentDetection = LatentDetection.LATENT_DETECT_ENABLE;
        Coder coderChoice = Coder.MORPHO_MSO_V9_CODER;
        Template template = new Template();
        template.setTemplateFVPType(TemplateFVPType.MORPHO_PK_FVP);
        template.setTemplateType(TemplateType.MORPHO_PK_COMP);

        DetectionMode detectionMode = DetectionMode.MORPHO_DEFAULT_DETECT_MODE;
        CompressionAlgorithm compressionAlgorithm = CompressionAlgorithm.MORPHO_COMPRESS_V1;
        TemplateList templateList = new TemplateList();
        templateList.putTemplate(template);


      int resutl =   m.capture(
                0,
                0,
                0,
                1,
                template.getTemplateType(),
                template.getTemplateFVPType(),
                1,
                enrollmentType,
                latentDetection,
                coderChoice,
                2,
                compressionAlgorithm,
                0,
                templateList,
                0,
                null
                );
      if (resutl == ErrorCodes.MORPHO_OK || resutl == 0){
          return    templateList.getTemplate(0).getData().toString();

      }else {
          String message = ErrorCodes.getError(resutl, resutl);
          return "Le code d'erreur est: " + resutl + "\n Le message associé est : " + message;

      }

       //return m.getUserAreaData();
    }

    
    
}
