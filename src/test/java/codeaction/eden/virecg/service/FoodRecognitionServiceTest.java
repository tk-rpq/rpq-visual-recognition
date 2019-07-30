package codeaction.eden.virecg.service;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import codeaction.eden.virecg.common.SpringJUnit4Base;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class FoodRecognitionServiceTest extends SpringJUnit4Base {

    @Autowired private FoodRecognitionService foodImgRecognitionService;

    @Test
    public void testRecognizeFood(){
        String filePath = "E:\\ibm\\foods\\fruitbowl.jpg";
        String fileName = "fruitbowl.jpg";
        ClassifiedImages result = foodImgRecognitionService.recognizeFood(filePath, fileName);
        assertNotNull(result);
        System.out.println(result);
    }
    
    @Test
    public void testRecognizeFood1(){
        String filePath = "E:\\ibm\\foods\\food1.jpg";
        String fileName = "food1.jpg";
        ClassifiedImages result = foodImgRecognitionService.recognizeFood(filePath, fileName);
        assertNotNull(result);
        System.out.println(result);
    }
}