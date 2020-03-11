package ru.socshared.template.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "test")
public class TestFeignClient {
}
