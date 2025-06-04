package th.co.cdg.SimpleApplication.model;

public class Customer {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private Long age;
    private String tel;

    //Getter Zone
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public Long getAge() { return age; }
    public String getTel() { return tel; }

    //Setter Zone
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setAddress(String address) { this.address = address; }
    public void setAge(Long age) { this.age = age; }
    public void setTel(String tel) { this.tel = tel; }
}
