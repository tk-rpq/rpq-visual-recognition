package codeaction.eden.virecg.service;

import com.ibm.watson.developer_cloud.service.exception.RequestTooLargeException;
import com.ibm.watson.developer_cloud.service.exception.ServiceResponseException;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import codeaction.eden.virecg.config.WatsonVisualRecognitionConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class FoodRecognitionService {
  // 识别类型为food
  private static final String classify_id = "food";
  
  public ClassifiedImages recognizeFood(String filePath, String fileName) {
    try {
      IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
      VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
      service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);
      
      InputStream imagesStream = new FileInputStream(filePath);
      ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
              .imagesFile(imagesStream)
              .imagesFilename(fileName)
              .classifierIds(Arrays.asList(classify_id))
              .build();
      ClassifiedImages result = service.classify(classifyOptions).execute();
//      System.out.println(result);
      return result;
    } catch (FileNotFoundException e) {
    	
      // Handle Not Found (404) exception
      System.out.println("Handle Not Found (404) exception");
      e.printStackTrace();
    } catch (RequestTooLargeException e) {
    	
      // Handle Request Too Large (413) exception
      System.out.println("Handle Request Too Large (413) exception");
      e.printStackTrace();
    } catch (ServiceResponseException e) {
    	
      // Base class for all exceptions caused by error responses from the service
      System.out.println("Service returned status code " + e.getStatusCode() + ": " + e.getMessage());
    }
    return null;
  }
}
