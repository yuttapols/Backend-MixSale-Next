package com.mix.sale.next.dto.res;

import java.sql.Timestamp;

import com.mix.sale.next.entity.PrefixEntity;
import lombok.Data;

@Data
public class UserDetailResDTO {

    private Long id;
    private Long userId;
    private String customerNo;
    private String fristName;
    private String middleName;
    private String lastName;
    private String nickName;
    private String email;
    private String telephone;
    private String houseNo;
    private String villageNo;
    private String alley;
    private String lane;
    private String road;
    private Long geographiesId;
    private Long districtsId;
    private String districtsName;
    private Long amphuresId;
    private String amphuresName;
    private Long provincesId;
    private String provincesName;
    private byte[] userImage;
    private String status;
    private String createBy;
    private Timestamp createDate;
    private String updateBy;
    private Timestamp updateDate;
    private PrefixEntity prefix;
    private Long zipCode;

    private String address;

    // address
}
