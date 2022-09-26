package cn.com.sdd.jiguang;

/**
 * @author suidd
 * @name JiguangPushEntity
 * @description TODO
 * @date 2021/9/1 13:37
 * Version 1.0
 **/
public class JiguangPushEntity {
    private String[] platform;
    private AudienceEntity audience;
    private NotificationEntity notification;

    public String[] getPlatform() {
        return platform;
    }

    public void setPlatform(String[] platform) {
        this.platform = platform;
    }

    public AudienceEntity getAudience() {
        return audience;
    }

    public void setAudience(AudienceEntity audience) {
        this.audience = audience;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public void setNotification(NotificationEntity notification) {
        this.notification = notification;
    }
}
