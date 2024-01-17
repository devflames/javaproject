package common;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

public class Uuid {

    private final Encoder encoder;

    public Uuid() {
        this.encoder = Base64.getUrlEncoder();
    }

    public 
    String randomId() {

        // Create random UUID
        UUID uuid = UUID.randomUUID();

        // Create byte[] for base64 from uuid
        byte[] src = ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();

        // Encode to Base64 and remove trailing ==
        return encoder.encodeToString(src).substring(0, 22);
    }

}