/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package info.smartkit.hairy_batman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yangboz
 */
@Entity
@Table(name = "wxSubscribers")
public class WxSubscriberJPAModel
{
    // An auto-generated id (unique for each user in the db)
    @Id
    // 序号
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId()
    {
        return id;
    }

    public void setId(long value)
    {
        this.id = value;
    }

    private String code;// 店代码

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

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

    private String agency;// 办事处

    public String getAgency()
    {
        return agency;
    }

    public void setAgency(String agency)
    {
        this.agency = agency;
    }

    private String unit;// 事业部

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    private String onSubscribe;// 是否开通订阅号

    public String getOnSubscribe()
    {
        return onSubscribe;
    }

    public void setOnSubscribe(String onSubscribe)
    {
        this.onSubscribe = onSubscribe;
    }

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
