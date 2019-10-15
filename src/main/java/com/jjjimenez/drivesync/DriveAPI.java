package com.jjjimenez.drivesync;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

class DriveAPI {
    private final JsonFactory jsonFactory;
    private final HttpTransport httpTransport;
    private final InputStream credentialsResourceStream;
    private final String accessType;
    private final String tokensDirectory;
    private final String applicationName;
    private final String userId;
    private final int localServerPort;

    private final Credential credential;

    private DriveAPI(Builder builder) throws IOException {
        this.jsonFactory = builder.jsonFactory;
        this.httpTransport = builder.httpTransport;
        this.credentialsResourceStream = builder.credentialsResourceStream;
        this.accessType = builder.accessType;
        this.tokensDirectory = builder.tokensDirectory;
        this.applicationName = builder.applicationName;
        this.localServerPort = builder.localServerPort;
        this.userId = builder.userId;
        this.credential = getPreconfiguredCredential();
    }

    static final class Builder {
        private final InputStream credentialsResourceStream;
        private final String applicationName;
        private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        private int localServerPort = 8000;
        private String userId = "user";
        private String accessType = "offline";
        private String tokensDirectory = "tokens";

        private HttpTransport httpTransport;

        Builder(InputStream credentialsResourceStream, String applicationName) {
            this.credentialsResourceStream = credentialsResourceStream;
            this.applicationName = applicationName;
        }

        Builder jsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this;
        }

        Builder httpTransport(HttpTransport httpTransport) {
            this.httpTransport = httpTransport;
            return this;
        }

        Builder accessType(String accessType) {
            this.accessType = accessType;
            return this;
        }

        Builder tokensDirectory(String tokensDirectory) {
            this.tokensDirectory = tokensDirectory;
            return this;
        }

        Builder localServerPort(int localServerPort) {
            this.localServerPort = localServerPort;
            return this;
        }

        Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        DriveAPI build() throws IOException, GeneralSecurityException {
            if(httpTransport == null) {
                httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            }
            return new DriveAPI(this);
        }
    }

    private Credential getPreconfiguredCredential() throws IOException {
        GoogleClientSecrets client = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(credentialsResourceStream));
        GoogleAuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, client, DriveScopes.all())
                .setDataStoreFactory(new FileDataStoreFactory(new File(tokensDirectory)))
                .setAccessType(accessType)
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(localServerPort).build();

        return new AuthorizationCodeInstalledApp(authorizationCodeFlow, receiver).authorize(userId);
    }

    Drive getDriveAPI() {
        return new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(applicationName).build();
    }
}
