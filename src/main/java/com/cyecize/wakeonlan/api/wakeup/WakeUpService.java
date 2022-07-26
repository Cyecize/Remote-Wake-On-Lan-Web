package com.cyecize.wakeonlan.api.wakeup;

public interface WakeUpService {
    void sendMagicPacket(String ip, String mac);
}
