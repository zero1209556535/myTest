package cn.itcast.ssm.po;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cn.itcast.ssm.controller.validation.ValidationGroup1;

public class Items {
    private Integer id;
    
    //校验名称在1到30字符之间
    //message的值是提示校验出错的显示信息
    //其中groups属性表示此校验属于哪个分组，groups中可以定义多个分组，表示此校验属于多个分组。
    @Size(min=1,max=30,message="{items.name.length.error}",groups={ValidationGroup1.class})
    //@Size(min=1,max=30,message="请您输入字符数在1至30之间的商品名称！")
    private String name;

    private Float price;

    private String pic;

    //非空校验
    @NotNull(message="{items.createtime.isNotNull}")
    private Date createtime;

    private Date effectivedate;

    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(Date effectivedate) {
        this.effectivedate = effectivedate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}