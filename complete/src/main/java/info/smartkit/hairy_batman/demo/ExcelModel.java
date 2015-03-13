/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package info.smartkit.hairy_batman.demo;

import java.util.Date;

import com.blogspot.na5cent.exom.annotation.Column;

/**
 * @author redcrow
 */
public class ExcelModel
{

    @Column(name = "first name")
    private String fistName;

    @Column(name = "last name")
    private String lastName;

    private Integer age;

    @Column(name = "birth date", pattern = "dd/MM/yyyy")
    private Date birthdate;

    public String getFistName()
    {
        return fistName;
    }

    public void setFistName(String fistName)
    {
        this.fistName = fistName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Date getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }

}
