package io.pivotal.pairwithme.integrations.firebase.schema;

import com.google.firebase.database.DataSnapshot;

import org.joda.time.DateTime;

import io.pivotal.pairwithme.domain.PairingSession;

public class FirebasePairingSession {
    private final DataSnapshot mPairingSessionRecord;

    public FirebasePairingSession(DataSnapshot pairingSessionRecord) {
        mPairingSessionRecord = pairingSessionRecord;
    }

    public String getURL() {
        return mPairingSessionRecord.getRef().toString();
    }

    public PairingSession asDomainObject() {
        return new PairingSession(getURL(),
                (String) get("firstName"),
                (String) get("lastName"),
                DateTime.parse((String) get("startTime")),
                (String) get("description"));
    }

    private Object get(String propertyName) {
        return mPairingSessionRecord.child(propertyName).getValue();
    }
}
