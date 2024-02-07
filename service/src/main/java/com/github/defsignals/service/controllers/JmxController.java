package com.github.defsignals.service.controllers;

import com.github.defsignals.service.service.JmxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.lang.management.MemoryMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/jmx")
public class JmxController {

    private final JmxService jmxService;

    public JmxController(JmxService jmxService) {
        this.jmxService = jmxService;
    }


    @GetMapping("/memory")
    public Mono<Map<String, Long>> getMemoryUsage(@RequestParam Optional<String> host,
                                                  @RequestParam Optional<Integer> port) {
        Map<String, Long> res = new HashMap<>();
        MemoryMXBean memoryMxBean = jmxService.getMxBean(host, port, MemoryMXBean.class);
        final var heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
        final var nonHeapMemoryUsage = memoryMxBean.getNonHeapMemoryUsage();

        res.put("Max heap memory", heapMemoryUsage.getMax());
        res.put("Used heap memory", heapMemoryUsage.getUsed());
        res.put("Max non heap memory", nonHeapMemoryUsage.getMax());
        res.put("Used non heap memory", nonHeapMemoryUsage.getUsed());

        return Mono.just(res);
    }






}

