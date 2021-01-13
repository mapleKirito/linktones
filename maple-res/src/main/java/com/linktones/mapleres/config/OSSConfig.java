package com.linktones.mapleres.config;
/***
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                    O\ = /O
 *                ____/`---'\____
 *              .   ' \\| |// `.
 *               / \\||| : |||// \
 *             / _||||| -:- |||||- \
 *               | | \\\ - /// | |
 *             | \_| ''\---/'' | |
 *              \ .-\__ `-` ___/-. /
 *           ___`. .' /--.--\ `. . __
 *        ."" '< `.___\_<|>_/___.' >'"".
 *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *         \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                    `=---='
 *
 * .............................................
 *          佛祖保佑             永无BUG
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/5 23:43
 * @Description: # 描述
 */
@Configuration
@Getter
public class OSSConfig {

    //Endpoint（地域节点）
    @Value("${maple.oss.endpoint}")
    private String endpoint;

    // 授权信息
    @Value("${maple.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${maple.oss.accessKeySecret}")
    private String accessKeySecret;

    //其他信息
    @Value("${maple.oss.bucketName}")
    private String bucketName;



    @Bean
    public OSS ossClient(){
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
