package com.linktones.mapledemo.bean;
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

import java.util.List;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/5/29 15:41
 * @Description: # 描述
 */
@Data
public class PageBean {
    /*当前页*/
    private int current;
    /*每页条数*/
    private int size;
    /*总条数*/
    private long total;
    /*内容*/
    private List aaData;

    /*兼容运管前端分页*/
    /*开始条数*/
    private int iDisplayStart;
    /*每页条数*/
    private int iDisplayLength;
    /*总条数*/
    private long iTotalRecords;
    private long iTotalDisplayRecords;


}
