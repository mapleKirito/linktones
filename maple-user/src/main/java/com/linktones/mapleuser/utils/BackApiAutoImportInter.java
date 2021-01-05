package com.linktones.mapleuser.utils;
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

import org.springframework.context.ApplicationContext;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2020/10/28 01:06
 * @Description: # 后台接口自动导入
 */
public interface BackApiAutoImportInter {
    /**
     * 上下文赋值
     *
     * @param applicationContext 参数
     * @return
     * @author huangyutao
     * @date 2019-08-16 10:34:35
     */
    void setApplicationContext(ApplicationContext applicationContext);

    /**
     * 本地录入标识
     *
     * @param isLocal 参数
     * @return
     * @author huangyutao
     * @date 2019-09-05 18:01:24
     */
    void setIsLocal(boolean isLocal);

    /**
     * 开放标识
     *
     * @param isOpen 参数
     * @return
     * @author huangyutao
     * @date 2019-09-18 11:09:36
     */
    void setIsOpen(boolean isOpen);

    /**
     * 扫描包
     *
     * @param packageName 参数
     * @return
     * @author maple
     * @date 2020-11-18 11:32:48
     */
    void setScanPackage(String packageName);

    /**
     * 执行导入
     *
     * @return
     * @author huangyutao
     * @date 2019-08-20 10:13:28
     */
    void run();
}
