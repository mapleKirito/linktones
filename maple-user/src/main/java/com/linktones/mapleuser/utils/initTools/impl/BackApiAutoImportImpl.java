package com.linktones.mapleuser.utils.initTools.impl;
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

import com.linktones.mapleuser.entity.SysPermation;
import com.linktones.mapleuser.model.BackApiAnnoDTO;
import com.linktones.mapleuser.service.ISysPermationService;
import com.linktones.mapleuser.utils.initTools.BackApiAutoImportInter;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2020/10/28 01:16
 * @Description: # 描述
 */
@Slf4j
@Service
public class BackApiAutoImportImpl implements BackApiAutoImportInter {

    @Autowired
    private ISysPermationService sysPermationService;

    @Value("${maple.moduleName}")
    private String moduleName;

    /**Spring框架的上下文*/
    private ApplicationContext applicationContext;
    /**本地录入标识*/
    private boolean isLocal;
    /**开放标识*/
    private boolean isOpen;
    /**扫描包名*/
    private String packageName;

    /**权限列表*/
    private List<SysPermation> permationList=new ArrayList<>();



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    @Override
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public void setScanPackage(String packageName) {
        this.packageName=packageName;
    }

    @Override
    public void run() {
        List<String> invalidControllers = new ArrayList<>();//没有注解RequestMapping或RequestMapping注解没有设置name的所有的类的集合
        List<String> invalidMethods = new ArrayList<>();
        List<String> invalidMethodAnnos = new ArrayList<>();

        // 获取代码中所有的controller
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);//获取使用RestController注解的所有controller层类

        for (Map.Entry<String, Object> entry : controllers.entrySet()) {//遍历每个controller层
            //过滤不需要扫描的包路径
            String className=AopUtils.getTargetClass(entry.getValue()).getName();
            if(!className.startsWith(packageName)){
                continue;
            }
            log.info("=======>扫描到的接口类：{}",className);
            //AopUtils.getTargetClass(entry.getValue())  获取controller的真实的class 比如  com.demo1.controller.Demo1Controller
            //isAnnotationPresent 判断class的类注解中有没有这个注解
            //此处为判断这个controller类有没有RequestMapping注解(否则是普通的方法)
            if (!AopUtils.getTargetClass(entry.getValue()).isAnnotationPresent(RequestMapping.class)
                    //getAnnotation 返回这个class的这个注解的所有信息
                    //此处判断的是,给这个controller类设置了name
                    // || StringUtils.isBlank(AopUtils.getTargetClass(entry.getValue()).getAnnotation(RequestMapping.class).name())) {
            ) {
                invalidControllers.add(entry.getKey());
            } else {
                RequestMapping controllerAnno = AopUtils.getTargetClass(entry.getValue()).getAnnotation(RequestMapping.class);//获取这个controller的RequestMapping注解的信息

                for (String controllerUrl : controllerAnno.value()) {//遍历controller的接口(value是数组,一个RequestMapping可以设置多个url)
                    // 根据需要过滤特定开头的接口
                    //if (StringUtils.isNotBlank(controllerUrl) && !procPath(controllerUrl).toLowerCase().startsWith("/back/")) {
                    if (StringUtils.isNotBlank(controllerUrl) ) {
                        List<String> methodUrls = new ArrayList<>();//这个类的所有的url接口
                        List<Method> methods = Arrays.stream(AopUtils.getTargetClass(entry.getValue()).getDeclaredMethods())//获取本类的所有的方法(包括public、private、protected、默认)
                                .filter(p -> Modifier.isPublic(p.getModifiers()))//过滤掉public的
                                .collect(Collectors.toList());
                        for (Method method : methods) {
                            //若是这个方法没有RequestMapping及相关注解,或者注解没有设置name值
                            if ((!method.isAnnotationPresent(RequestMapping.class) || StringUtils.isBlank(method.getDeclaredAnnotation(RequestMapping.class).name()))
                                    && (!method.isAnnotationPresent(GetMapping.class) || StringUtils.isBlank(method.getDeclaredAnnotation(GetMapping.class).name()))
                                    && (!method.isAnnotationPresent(PostMapping.class) || StringUtils.isBlank(method.getDeclaredAnnotation(PostMapping.class).name()))
                                    && (!method.isAnnotationPresent(PutMapping.class) || StringUtils.isBlank(method.getDeclaredAnnotation(PutMapping.class).name()))
                                    && (!method.isAnnotationPresent(DeleteMapping.class) || StringUtils.isBlank(method.getDeclaredAnnotation(DeleteMapping.class).name()))
                                    && (!method.isAnnotationPresent(PatchMapping.class) || StringUtils.isBlank(method.getDeclaredAnnotation(PatchMapping.class).name()))
                            ) {
                                invalidMethods.add(method.getDeclaringClass().getName() + "." + method.getName());//不需要解析的方法
                            } else {
                                List<BackApiAnnoDTO> backApiAnnoDTOS = getAnnos(method);
                                if (CollectionUtils.isNotEmpty(backApiAnnoDTOS)) {
                                    methodUrls.addAll(backApiAnnoDTOS.stream()
                                            .map(p -> p.getValue())
                                            .collect(Collectors.toList()));
                                }
                            }
                        }
                        List<String> dupliUrls = getDuplicateElements(methodUrls);//获取url重复的元素列表
                        if (CollectionUtils.isNotEmpty(dupliUrls)) {
                            invalidMethodAnnos.addAll(dupliUrls.stream()
                                    .map(p -> "url:\"" + p + "\"[" + AopUtils.getTargetClass(entry.getValue()).getName() + "]")
                                    .collect(Collectors.toList()));//记录重复的url
                        }
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(RequestMapping.class)
                                    || method.isAnnotationPresent(GetMapping.class)
                                    || method.isAnnotationPresent(PostMapping.class)
                                    || method.isAnnotationPresent(PutMapping.class)
                                    || method.isAnnotationPresent(DeleteMapping.class)
                                    || method.isAnnotationPresent(PatchMapping.class)
                            ) {//若是这个方法有接口的注解
                                List<BackApiAnnoDTO> backApiAnnoDTOS = getAnnos(method);//获取所有的接口信息
                                if (CollectionUtils.isNotEmpty(backApiAnnoDTOS)) {
                                    backApiAnnoDTOS.forEach(backApiAnnoDTO -> {
                                        if (!dupliUrls.contains(backApiAnnoDTO.getValue())) {//不是重复的url(spring不允许重复的url,有重复的就该报异常了)
                                            String apiUrl = new StringBuilder() //拼接controller和方法的url
                                                    .append(procPath(controllerUrl))
                                                    .append(procPath(backApiAnnoDTO.getValue()))
                                                    .toString();
                                            if (StringUtils.isNotBlank(apiUrl)) {
                                                log.info("扫描到的接口[{}]：{}",backApiAnnoDTO.getName(),apiUrl);
                                                //TODO 初始化接口权限
                                                permationList.add(SysPermation.builder()
                                                        .permissionModule(moduleName)
                                                        .permissionUrl(apiUrl)
                                                        .permissionDesc(backApiAnnoDTO.getNotes())
                                                        .permissionEnable(1)
                                                        .build()) ;
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        // controller value is empty
                        log.info("没找到接口方法");
                    }
                }
            }
        }
        //TODO 接口初始化,请求接口/permations/init
        /**
        * 报文格式：
         * moduleName:String
         * list:SysPermation.class
        * */
        sysPermationService.initPermation(permationList,moduleName);

    }

    /**
     * 获取注解信息列表
     *
     * @param method 方法
     * @return
     * @author huangyutao
     * @date 2019-08-15 17:26:35
     */
    private List<BackApiAnnoDTO> getAnnos(Method method) {
        List<BackApiAnnoDTO> backApiAnnoDTOs = new ArrayList<>();
        String name = "";
        List<String> values = new ArrayList<>();
        String notes="";

        if (method.isAnnotationPresent(ApiOperation.class)) {//判断方法有没有这个注解
            ApiOperation methodAnno = method.getDeclaredAnnotation(ApiOperation.class);
            notes = methodAnno.notes();//获取接口描述
        }

        if (method.isAnnotationPresent(RequestMapping.class)) {//判断方法有没有这个注解
            RequestMapping methodAnno = method.getDeclaredAnnotation(RequestMapping.class);
            name = methodAnno.name();//获取方法名
            values = Arrays.asList(methodAnno.value());//获取这个方法的所有的路由url
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping methodAnno = method.getDeclaredAnnotation(GetMapping.class);
            name = methodAnno.name();
            values = Arrays.asList(methodAnno.value());
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping methodAnno = method.getDeclaredAnnotation(PostMapping.class);
            name = methodAnno.name();
            values = Arrays.asList(methodAnno.value());
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping methodAnno = method.getDeclaredAnnotation(PutMapping.class);
            name = methodAnno.name();
            values = Arrays.asList(methodAnno.value());
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping methodAnno = method.getDeclaredAnnotation(DeleteMapping.class);
            name = methodAnno.name();
            values = Arrays.asList(methodAnno.value());
        } else if (method.isAnnotationPresent(PatchMapping.class)) {
            PatchMapping methodAnno = method.getDeclaredAnnotation(PatchMapping.class);
            name = methodAnno.name();
            values = Arrays.asList(methodAnno.value());
        }

        //根据swagger获取更多信息
        if (method.isAnnotationPresent(ApiOperation.class)) {//判断方法有没有这个注解
            ApiOperation methodAnno = method.getDeclaredAnnotation(ApiOperation.class);
            name = methodAnno.value();//如果有则代替接口名称
            notes = methodAnno.notes();//获取接口描述
            if(StringUtils.isBlank(notes)){
                notes=name;//如果没有描述则用名称代替
            }
        }

        for (String value : values) {//遍历每一个路由,创建详情
            BackApiAnnoDTO backApiAnnoDTO = new BackApiAnnoDTO();
            backApiAnnoDTO.setName(name);
            backApiAnnoDTO.setValue(value);
            backApiAnnoDTO.setNotes(notes);
            backApiAnnoDTOs.add(backApiAnnoDTO);
        }
        return backApiAnnoDTOs;
    }

    /**
     * 处理path
     *
     * @param path path
     * @return path
     * @author huangyutao
     * @date 2019-08-12 11:22:59
     */
    private String procPath(String path) {
        path = path.trim();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 获取重复的元素列表
     *
     * @param list 数据源
     * @return
     * @author huangyutao
     * @date 2019-08-19 10:59:29
     */
    private <T> List<T> getDuplicateElements(List<T> list) {
        return list.stream()
                // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                // 所有 entry 对应的 Stream
                .entrySet().stream()
                // 过滤出元素出现次数大于 1 的 entry
                .filter(entry -> entry.getValue() > 1)
                // 获得 entry 的键（重复元素）对应的 Stream
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
    }


}
