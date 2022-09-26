package cn.com.sdd.study.temp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author suidd
 * @name DatatRoomTempDTO
 * @description 室温生产数据DTO
 * @date 2020/5/25 10:37
 * Version 1.0
 **/
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class DatatRoomTempDTO implements Serializable {
    private static final long serialVersionUID = -4252100908817240830L;
    /**
     * 主键id
     */
    private long id;
    /**
     * 原始数据主键id
     */
    private String oriId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 小区id
     */
    private String uptownId;
    /**
     * 楼号
     */
    private String buildingNum;
    /**
     * 单元号
     */
    private String buildingUnit;
    /**
     * 房间号
     */
    private String roomNum;
    /**
     * 室内温度，单位：℃
     */
    private double temp;
    /**
     * 换热站室温达标率，单位：%
     */
    private double stationTempStandardRate;
    /**
     * 采集点位置
     */
    private String pointPos;
    /**
     * 剩余电量，单位：%
     */
    private double pointSurplusElectric;
    /**
     * 采集时间
     */
    private Date collectDate;
    /**
     * 填报时间
     */
    private Date fillTime;
    /**
     * 数据来源
     */
    private int dataResource;
    /**
     * 企业主用户id
     */
    private long masterUserId;
    /**
     * 是否删除。0否，1是
     */
    private int isDeletel;
    /**
     * 数据状态 。1 初始化接入
     */
    private int dataStatus;
    /**
     * 推送时间
     */
    private Date pushTime;
    /**
     * 接收时间
     */
    private Date receiveTime;
}