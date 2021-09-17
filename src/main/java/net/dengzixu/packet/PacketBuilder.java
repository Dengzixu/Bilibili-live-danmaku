package net.dengzixu.packet;


import net.dengzixu.constant.PacketOperationEnum;
import net.dengzixu.constant.PacketProtocolVersionEnum;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class PacketBuilder {
    private final Packet packet;

    public PacketBuilder(short protocolVersion, int operation) {
        this(16, (short) 16,
                protocolVersion, operation,
                1, null);
    }

    public PacketBuilder(short protocolVersion, int operation, byte[] payload) {
        this(16 + payload.length, (short) 16, protocolVersion, operation, 1, payload);
    }

    public PacketBuilder(PacketProtocolVersionEnum packetProtocolVersion, PacketOperationEnum packetOperation,
                         String payloadString) {
        this(16 + payloadString.getBytes(StandardCharsets.UTF_8).length, (short) 16,
                packetProtocolVersion.version(), packetOperation.operation(),
                1, payloadString.getBytes(StandardCharsets.UTF_8));
    }

    public PacketBuilder(short protocolVersion, int operation,
                         String payloadString) {
        this(16 + payloadString.getBytes(StandardCharsets.UTF_8).length, (short) 16,
                protocolVersion, operation,
                1, payloadString.getBytes(StandardCharsets.UTF_8));
    }

    public PacketBuilder(int packetLength, short headerLength,
                         short protocolVersion, int operation,
                         int sequenceId, byte[] payload) {

        this.packet = new Packet() {{
            setPacketLength(packetLength);
            setHeaderLength(headerLength);
            setProtocolVersion(protocolVersion);
            setOperation(operation);
            setSequenceId(sequenceId);
            setPayload(payload);
        }};
    }

    public byte[] buildArrays() {
        ByteBuffer packetByteBuffer = ByteBuffer.allocate(packet.getPacketLength());
        try {
            // Packet Length
            packetByteBuffer.put(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.BIG_ENDIAN)
                    .putInt(packet.getPacketLength()).array());

            // Header Length
            packetByteBuffer.put(ByteBuffer.allocate(Short.BYTES).order(ByteOrder.BIG_ENDIAN)
                    .putShort(packet.getHeaderLength()).array());

            // Protocol Version
            packetByteBuffer.put(ByteBuffer.allocate(Short.BYTES).order(ByteOrder.BIG_ENDIAN)
                    .putShort(packet.getProtocolVersion()).array());

            // Operation
            packetByteBuffer.put(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.BIG_ENDIAN)
                    .putInt(packet.getOperation()).array());

            // Sequence Id
            packetByteBuffer.put(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.BIG_ENDIAN)
                    .putInt(packet.getProtocolVersion()).array());

            if (null != packet.getPayload()) {
                packetByteBuffer.put(packet.getPayload());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return packetByteBuffer.array();
    }
}
