package kejuntong.com.samplemoduleapp.UtilClasses;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;


public class LoadFBDataSynchronously {

    public static <T> T loadSynchronous(DatabaseReference databaseReference, Class<T> clazz) {
        final DataSnapshotWrapper snapshotWrapper = new DataSnapshotWrapper();
        final CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Location loaded");
                snapshotWrapper.snapshot = dataSnapshot;
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError dbError) {
                System.out.println("Error loading location");
                latch.countDown();
            }
        });
        try {
            System.out.println("Prelatch");
            latch.await();
            System.out.println("Returning from latch");
            return snapshotWrapper.snapshot.getValue(clazz);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DataSnapshot loadSynchronous(Query databaseReference) {
        final DataSnapshotWrapper snapshotWrapper = new DataSnapshotWrapper();
        final CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Location loaded");
                snapshotWrapper.snapshot = dataSnapshot;
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError dbError) {
                System.out.println("Error loading location");
                latch.countDown();
            }
        });
        try {
            System.out.println("Prelatch");
            latch.await();
            System.out.println("Returning from latch");
            return snapshotWrapper.snapshot;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DataSnapshot loadSynchronous(DatabaseReference databaseReference) {
        final DataSnapshotWrapper snapshotWrapper = new DataSnapshotWrapper();
        final CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Location loaded");
                snapshotWrapper.snapshot = dataSnapshot;
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError dbError) {
                System.out.println("Error loading location");
                latch.countDown();
            }
        });
        try {
            System.out.println("Prelatch");
            latch.await();
            System.out.println("Returning from latch");
            return snapshotWrapper.snapshot;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class DataSnapshotWrapper {
        private DataSnapshot snapshot;
    }
}