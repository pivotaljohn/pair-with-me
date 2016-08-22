package io.pivotal.pairwithme.integrations.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import io.pivotal.pairwithme.domain.PairingSession;
import io.pivotal.pairwithme.domain.PairingSessionChange;
import io.pivotal.pairwithme.domain.PairingSessionInsert;
import io.pivotal.pairwithme.domain.Repository;
import io.pivotal.pairwithme.integrations.firebase.schema.FirebasePairingSession;
import rx.Observable;
import rx.subjects.PublishSubject;

public class FirebaseRepository implements Repository {
    private static final String TAG = FirebaseRepository.class.getSimpleName();
    private PublishSubject<PairingSessionChange> subject;
    private final DatabaseReference mSessions;
    private final FirebaseDatabase mDatabase;

    public FirebaseRepository() {
        subject = PublishSubject.create();
        mDatabase = FirebaseDatabase.getInstance();
        mSessions = mDatabase.getReference("sessions");
        mSessions.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot newSessionNode, String s) {
                subject.onNext(new PairingSessionInsert(new FirebasePairingSession(newSessionNode).asDomainObject()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public Observable<PairingSessionChange> asObservable() {
        return subject;
    }

}
