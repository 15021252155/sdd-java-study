package cn.com.sdd.jiguang;

/**
 * @author suidd
 * @name NotificationEntity
 * @description TODO
 * @date 2021/9/1 13:41
 * Version 1.0
 **/
public class NotificationEntity {
    private AndroidEntity android;
    private IosEntity ios;

    public AndroidEntity getAndroid() {
        return android;
    }

    public void setAndroid(AndroidEntity android) {
        this.android = android;
    }

    public IosEntity getIos() {
        return ios;
    }

    public void setIos(IosEntity ios) {
        this.ios = ios;
    }
}
