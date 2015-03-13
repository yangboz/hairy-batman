/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package info.smartkit.hairy_batman.model;

import com.blogspot.na5cent.exom.annotation.Column;

/**
 * @author yangboz
 */
public class WxSubscriberExcelModel
{
    @Column(name = "序号")
    private Integer id;// 序号

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Column(name = "店代码")
    private String code;// 店代码

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    @Column(name = "店名")
    private String store;// 特约店名

    public String getStore()
    {
        return store;
    }

    public void setStore(String store)
    {
        this.store = store;
    }

    // @Column(name = "first name")
    // private String manager;// 区域经理
    //
    // public String getManager()
    // {
    // return manager;
    // }

    // public void setManager(String manager)
    // {
    // this.manager = manager;
    // }

    @Column(name = "办事处")
    private String agency;// 办事处

    public String getAgency()
    {
        return agency;
    }

    public void setAgency(String agency)
    {
        this.agency = agency;
    }

    @Column(name = "事业部")
    private String unit;// 事业部

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    @Column(name = "微信号类型")
    private String onSubscribe;// 是否开通订阅号

    public String getOnSubscribe()
    {
        return onSubscribe;
    }

    public void setOnSubscribe(String onSubscribe)
    {
        this.onSubscribe = onSubscribe;
    }

    @Column(name = "微信号全称")
    private String subscribeId;// 订阅号名全称,subscribeId VARCHAR(20),

    public String getSubscribeId()
    {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId)
    {
        this.subscribeId = subscribeId;
    }

    @Override
    public String toString()
    {
        return "id: " + id + ",code: " + code + ", store: " + store + ",agency: " + agency + ",unit: " + unit
            + ",onSubscribe: " + onSubscribe + ",subscribeId: " + subscribeId;
    }

}
