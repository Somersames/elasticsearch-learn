package xyz.somersames.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.somersames.service.EsClientService;

import java.io.IOException;

/**
 * @author szh
 * @create 2019-04-10 23:35
 **/
@RestController
public class EsServiceController {

    @Autowired
    EsClientService esClientService;


    @RequestMapping(value = "test",method = RequestMethod.GET)
    public void test() throws IOException {
        esClientService.save();
    }
}
