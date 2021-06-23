import com.google.api.gax.paging.Page;


import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;
import com.google.common.collect.Lists;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Bucket;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HelloGCP {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello GCP");
        bqExample();
        //authExplicit("/USERS/joeletsdoit/Downloads/joe-try-gcp1-c24ba57b0d02.json");
    }

    static void authExplicit(String jsonPath) throws IOException {
        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
   }

    static void bqExample() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/USERS/joeletsdoit/Downloads/joe-try-gcp1-c24ba57b0d02.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        BigQuery bq = BigQueryOptions.newBuilder().setCredentials(credentials).build().getService();

        String datasetName = "joefirstbqdataset";

        Dataset data = null;
        DatasetInfo info = DatasetInfo.newBuilder(datasetName).build();

        data=bq.create(info);
        System.out.printf("Dataset %s created.%n", data.getDatasetId().getDataset());

    }
}
