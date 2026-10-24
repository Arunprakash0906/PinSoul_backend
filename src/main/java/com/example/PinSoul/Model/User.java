package com.example.PinSoul.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String UserName;
    private String MobileNo;
    private String MailId;
    private String address;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] profileimage;

    @OneToOne
    @JoinColumn(name = "sign_id", unique = true)
    private Sign sign;

    public User(Integer userId, String UserName, String MobileNo, String MailId, String address, byte[] profileimage,
            Sign sign) {
        this.userId = userId;
        this.UserName = UserName;
        this.MobileNo = MobileNo;
        this.MailId = MailId;
        this.address = address;
        this.profileimage = profileimage;
        this.sign = sign;
    }
}
