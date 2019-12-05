package com.aaa.lee.app.base;

import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import org.springframework.stereotype.Controller;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/21 18:56
 * @Description
 **/
@Controller
public class BaseController {
    private ResultData resultData = new ResultData();
    /**
     * @Author Lee
     * @Description
     *  登录成功，使用系统消息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/21
     */

    public ResultData loginSuccess(){
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(LoginStatus.LOGIN_SUCCESS.getMsg());
        return resultData;

    }
   /**
    * @Author Lee
    * @Description
    * 登录成功，自定义返回消息
    * @Param  * @param msg
    * @Return com.aaa.lee.app.base.ResultData
    * @Date 2019/11/21
    */
    protected ResultData loginSuccess(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

   /**
    * @Author Lee
    * @Description
    *
    * 登录成功，使用系统消息，自定义返回值
    * @Param  * @param data
    * @Return com.aaa.lee.app.base.ResultData
    * @Date 2019/11/21
    */
    protected ResultData loginSuccess(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(LoginStatus.LOGIN_SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

  /**
   * @Author Lee
   * @Description
   * 登录成功，自定义消息，自定义返回值
   * @Param  * @param msg
 * @param data
   * @Return com.aaa.lee.app.base.ResultData
   * @Date 2019/11/21
   */
    protected ResultData loginSuccess(String msg, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

 /**
  * @Author Lee
  * @Description
  * 登录失败，返回系统消息
  * @Param  * @param
  * @Return com.aaa.lee.app.base.ResultData
  * @Date 2019/11/21
  */

    protected ResultData loginFailed() {
        resultData.setCode(LoginStatus.LOGIN_FAILED.getCode());
        resultData.setMsg(LoginStatus.LOGIN_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author Zhang Lee
     * @Description
     *      操作成功，返回系统信息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(StatusEnum.SUCCESS.getMsg());
        return resultData;
    }
    /**
     * @Author Zhang Lee
     * @Description
     *      操作成功返回自定义信息
     * @Param  * @param msg
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(String msg){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }
    /**
     * @Author Zhang Lee
     * @Description
     *      操作成功，返回系统信息，自定义数据
     * @Param  * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(Object data){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(StatusEnum.SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author Zhang Lee
     * @Description
     *      操作成功，返回自定义信息，自定义数据
     * @Param  * @param msg
     * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(String msg,Object data){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author Lee
     * @Description
     *      操作失败，返回系统消息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/21
     */
    protected ResultData operateFailed(){
        resultData.setCode(StatusEnum.FAILED.getCode());
        resultData.setMsg(StatusEnum.FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author Lee
     * @Description
     *      信息存在，返回自定义信息，自定义数据
     * @Param  * @param msg
     * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData exist(String msg,Object data){
        resultData.setCode(StatusEnum.EXIST.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author Lee
     * @Description
     *      信息存在，返回系统信息，自定义数据
     * @Param  * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData exist(Object data){
        resultData.setCode(StatusEnum.EXIST.getCode());
        resultData.setMsg(StatusEnum.EXIST.getMsg());
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author Lee
     * @Description
     *      信息存在，返回系统信息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    public ResultData exist(){
        resultData.setCode(StatusEnum.EXIST.getCode());
        resultData.setMsg(StatusEnum.EXIST.getMsg());
        return resultData;
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      信息不存在，返回系统信息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    public ResultData notExist(){
        resultData.setCode(StatusEnum.NOT_EXIST.getCode());
        resultData.setMsg(StatusEnum.NOT_EXIST.getMsg());
        return resultData;
    }


}
