package com.example.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Configuration
public class FirebaseConfig {
    private final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${fcm-key-path}")
    private String fcmKeyPath;

    /*@Value("${fcm.key.scope}")
    private String fireBaseScope;*/

    @PostConstruct
    public void initialize() {

        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();

        if(firebaseApps == null || firebaseApps.isEmpty()){

            log.info("Firebase application has been initialized");

            try{
                 ClassPathResource resource = new ClassPathResource(fcmKeyPath);

                 InputStream serviceAccount = resource.getInputStream();

                 GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                 //.createScoped(List.of(fireBaseScope));

                 FirebaseOptions options = FirebaseOptions.builder()
                                                            .setCredentials(credentials)
                                                          .build();
                 FirebaseApp.initializeApp(options);

            }catch(FileNotFoundException e){
                logger.error("Firebase ServiceAccountKey FileNotFoundException" + e.getMessage());

            }catch(IOException e) {
                logger.error("FirebaseOptions IOException" + e.getMessage());
            }
        }
    }
}