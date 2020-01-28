package com.pawpaw.stock.post;

import com.pawpaw.framework.web.PawpawWebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootStrap {

    public static void main(String[] args) {
        PawpawWebApplication application = new PawpawWebApplication();
        application.run(BootStrap.class);


    }
}
