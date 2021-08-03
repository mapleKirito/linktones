package com.linktones.mapleuser;

import com.linktones.mapleuser.utils.initTools.BackApiAutoImportInter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j
@SpringBootApplication
@EnableOpenApi
@MapperScan("com.linktones.mapleuser.mapper")
public class MapleUserApplication implements CommandLineRunner, ApplicationContextAware {

    @Value("${maple.init.permation:true}")
    private boolean initPermation;

    /**
     * 2.获取Spring框架的上下文
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     后台接口自动导入
     */
    @Autowired
    private BackApiAutoImportInter backApiAutoImportInter;

    public static void main(String[] args) {
        //SpringApplication.run(DemoApplication.class, args);
        SpringApplication app = new SpringApplication(MapleUserApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("权限初始化:{}",initPermation?"开启":"关闭");
            if(initPermation){
                if (backApiAutoImportInter != null) {
                    backApiAutoImportInter.setApplicationContext(applicationContext);
                    backApiAutoImportInter.setIsLocal(false);
                    backApiAutoImportInter.setIsOpen(false);
                    backApiAutoImportInter.setScanPackage("com.linktones.mapleuser.controller");

                    backApiAutoImportInter.run();
                } else {
                    log.error("backApiAutoImportInter is empty", "backApiAutoImportInter inject failed");
                }
            }
        } catch (Exception ex) {
            log.error("error:", ex);
        }
    }
}
