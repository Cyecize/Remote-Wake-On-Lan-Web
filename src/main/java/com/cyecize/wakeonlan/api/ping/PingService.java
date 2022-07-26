package com.cyecize.wakeonlan.api.ping;

public interface PingService {
    boolean ping(String ip, int wait);
}
