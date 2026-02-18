package com.mix.sale.next.dto.req;

import lombok.Data;

@Data
public class UserDetailReqDTO {

    private String userName;
    private String password;
    private Long prefixId;

    private String fristName;
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
    private Long amphuresId;
    private Long provincesId;
}
