package me.yarosbug.scnf.main;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "my-feign")
public interface MyFeignClient {

    @GetMapping("/anything/{anything}")
    String remoteCall(@PathVariable("anything") String data);
}
