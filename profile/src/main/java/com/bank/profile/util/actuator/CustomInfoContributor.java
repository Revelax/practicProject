package com.bank.profile.util.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class CustomInfoContributor implements InfoContributor {

    private final ServletContext servletContext;
    private final long startTime = System.currentTimeMillis();

    @Override
    public void contribute(Info.Builder builder) {

        LocalDateTime startApp = LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.UTC).plusHours(3);

        builder
                .withDetail("время запуска микросервиса", startApp)
                .withDetail("context path", servletContext.getContextPath());
    }

}
