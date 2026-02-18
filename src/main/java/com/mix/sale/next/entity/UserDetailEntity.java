package com.mix.sale.next.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "user_detail")
public class UserDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "prefix_id")
    private Long prefixId;

    @Column(name = "customer_no")
    private String customerNo;

    @Column(name = "frist_name")
    private String fristName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "village_no")
    private String villageNo;

    @Column(name = "alley")
    private String alley;

    @Column(name = "lane")
    private String lane;

    @Column(name = "road")
    private String road;

    @Column(name = "geographies_id")
    private Long geographiesId;

    @Column(name = "districts_id")
    private Long districtsId;

    @Column(name = "amphures_id")
    private Long amphuresId;

    @Column(name = "provinces_id")
    private Long provincesId;

    @Column(name = "user_image_name")
    private String userImageName;

    @Column(name = "user_image_path")
    private String userImagePath;

    @Basic
    @Column(name = "user_image_blob", columnDefinition = "LONGBLOB")
    private byte[] userImageBlob;

    @Column(name = "status")
    private String status;

    @Column(name = "create_by")
    private String createBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_date")
    private Timestamp updateDate;

    @OneToOne
    @JoinColumn(name = "prefix_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PrefixEntity prefix;

}
