package com.cyecize.wakeonlan.api.ping;

import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
public class PingServiceImpl implements PingService {
    @Override
    public boolean ping(String ip, int wait) {
        try {
            final InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(wait);
        } catch (Exception ignored) {}

        return false;
    }
}
