package io.pivotal.pairwithme.domain;

public class PairingSessionInsert extends PairingSessionChange {
    private final PairingSession mNewPairingSession;

    public PairingSessionInsert(PairingSession newPairingSession) {
        mNewPairingSession = newPairingSession;
    }

    @Override
    public PairingSession getTarget() {
        return mNewPairingSession;
    }
}
