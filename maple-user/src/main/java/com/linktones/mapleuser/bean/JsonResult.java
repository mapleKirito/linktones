package com.linktones.mapleuser.bean;
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

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2020/10/13 14:34
 * @Description: # 描述
 */
@Data
@ToString
public class JsonResult {

    public static JsonResult successList(List list,String msg){
        JsonResult jsonResult=new JsonResult(0,msg);
        jsonResult.setList(list);
        return jsonResult;
    }


    private Object data;
    private List list;
    //0表示成功，其余表示异常
    private Integer code;
    private String msg;

    public JsonResult() {
        this.code = 0;
        this.msg = "操作成功";
    }

    /**
     * 没有数据返回，可以人为指定状态码和提示消息
     * @param code
     * @param msg
     */
    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为0，默认提示消息为：操作成功!
     * @param data
     */
    public JsonResult(Object data){
        this.data = data;
        this.code = 0;
        this.msg = "操作成功";
    }

    /**
     * 有数据返回，状态码为0，认位指定提示信息
     * @param data
     * @param msg
     */
    public JsonResult(Object data,String msg){
        this.data = data;
        this.msg = msg;
        this.code = 0;
    }


    /**
     * 返回list
     * @param list
     */
    public void setList(List list){
        this.list=list;
    }

    /**
     * 返回失败
     * @param msg
     */
    public void setFail(String msg){
        setCode(-1);
        setMsg(msg);
    }

    /**
     * 返回成功
     * @param msg
     */
    public void setSuccess(String msg){
        setCode(0);
        setMsg(msg);
    }

}
