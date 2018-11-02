package no.nordicsemi.android.meshprovisioner.transport;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Wrapper class for network key
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class NetworkKey implements Parcelable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PHASE_0, PHASE_1, PHASE_2})
    public @interface KeyRefreshPhases {}

    // Key refresh phases
    public static final int PHASE_0 = 0; //Distribution of new keys
    public static final int PHASE_1 = 1; //Switching to the new keys
    public static final int PHASE_2 = 2; //Revoking the old keys

    @Expose
    private String name = "Network Key";
    @Expose
    final int keyIndex;
    @Expose
    final byte[] key;
    @Expose
    private int phase = PHASE_0;
    @Expose
    private boolean minSecurity;
    @Expose
    private byte[] oldKey;
    @Expose
    private long timestamp;

    /**
     * Constructs a NetworkKey object with a given key index and network key
     *
     * @param keyIndex 12-bit network key index
     * @param key      16-byte network key
     */
    public NetworkKey(final int keyIndex, final byte[] key) {
        this.key = key;
        this.keyIndex = keyIndex;
    }

    protected NetworkKey(Parcel in) {
        name = in.readString();
        keyIndex = in.readInt();
        key = in.createByteArray();
        phase = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(keyIndex);
        dest.writeByteArray(key);
        dest.writeInt(phase);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetworkKey> CREATOR = new Creator<NetworkKey>() {
        @Override
        public NetworkKey createFromParcel(Parcel in) {
            return new NetworkKey(in);
        }

        @Override
        public NetworkKey[] newArray(int size) {
            return new NetworkKey[size];
        }
    };

    /**
     * Returns the friendly name assigned to a network key
     *
     * @return String containing the a friendly name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a friendly name to a network key
     *
     * @param name friendly name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the network key
     *
     * @return 16-byte network key
     */
    public byte[] getKey() {
        return key;
    }

    /**
     * Returns the key refresh phase of the network key
     *
     * @return int phase
     */
    @KeyRefreshPhases
    public int getPhase() {
        return phase;
    }

    public void setPhase(@KeyRefreshPhases final int phase) {
        this.phase = phase;
    }

    /**
     * Returns the network key index
     *
     * @return key index
     */
    public int getKeyIndex() {
        return keyIndex;
    }

    /**
     * Uses min security
     * @return true if minimum security or false otherwise
     */
    public boolean isMinSecurity() {
        return minSecurity;
    }

    /**
     * Set security
     * @param minSecurity boolean security true if min false otherwise
     */
    public void setMinSecurity(final boolean minSecurity) {
        this.minSecurity = minSecurity;
    }

    /**
     * Returns the old network key
     * @return old key
     */
    public byte[] getOldKey() {
        return oldKey;
    }

    /**
     * Set the old key
     * @param oldKey old network key
     */
    public void setOldKey(final byte[] oldKey) {
        this.oldKey = oldKey;
    }

    /**
     * Returns the timestamp of the phase change
     * @return timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Set the timestamp when the the phase change happened
     * @param timestamp timestamp
     */
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }
}