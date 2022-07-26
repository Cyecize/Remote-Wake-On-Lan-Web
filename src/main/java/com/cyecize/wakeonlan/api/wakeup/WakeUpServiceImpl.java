package com.cyecize.wakeonlan.api.wakeup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
@Service
@RequiredArgsConstructor
public class WakeUpServiceImpl implements WakeUpService {

    @Value("${magic.packet.port}")
    private final int port;

    @Value("${target.address.strategy}")
    private final WakeUpStrategy wakeUpStrategy;

    @Override
    public void sendMagicPacket(String ipStr, String macStr) {
        try {
            final byte[] macBytes = getMacBytes(macStr);
            final byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

            final InetAddress address = InetAddress.getByName(this.wakeUpStrategy.processIp(ipStr));
            final DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, this.port);
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setBroadcast(this.wakeUpStrategy.isBroadcast());
                socket.send(packet);
            }

            log.info("Wake-on-LAN packet sent.");
        } catch (Exception e) {
            log.error("Failed to send Wake-on-LAN packet:", e);
        }
    }

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        final byte[] bytes = new byte[6];
        final String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
}
