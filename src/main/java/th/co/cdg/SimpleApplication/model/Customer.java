package th.co.cdg.SimpleApplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "AGE")
    private Long age;
    @Column(name = "TEL")
    private String tel;
    @Column(name= "IMAGE")
    private byte[] image;

    public Customer() {}

    //Getter Zone
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public Long getAge() { return age; }
    public String getTel() { return tel; }
    public byte[] getImage() { return image; }

    //Setter Zone
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setAddress(String address) { this.address = address; }
    public void setAge(Long age) { this.age = age; }
    public void setTel(String tel) { this.tel = tel; }
    public void setImage(byte[] image) { this.image = image; }
}
