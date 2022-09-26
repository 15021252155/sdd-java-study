package cn.com.sdd.jiguang;

/**
 * @author suidd
 * @name JiguangResult
 * @description 极光推送消息返回结构体
 * @date 2021/9/2 10:14
 * Version 1.0
 **/
public class JiguangResult {
    private String sendno;
    private String msg_id;

    public String getSendno() {
        return sendno;
    }

    public void setSendno(String sendno) {
        this.sendno = sendno;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }
}
