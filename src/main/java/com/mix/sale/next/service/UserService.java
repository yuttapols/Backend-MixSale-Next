package com.mix.sale.next.service;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import com.mix.sale.next.dto.res.PrefixResDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.mix.sale.next.dto.req.UserDetailReqDTO;
import com.mix.sale.next.dto.res.UserDetailResDTO;
import com.mix.sale.next.payload.CustomerUserAttr;

public interface UserService {

    UserDetailsService userDetailsService();

    public Long updateImageProfile(CustomerUserAttr userAttr, MultipartFile file, Long userId) throws IOException, Exception;

    public byte[] getImageByte(Long userDetailId) throws IOException, DataFormatException;

    public Long updateProfile(CustomerUserAttr userAttr, UserDetailReqDTO userDetailReqDTO, Long userId) throws Exception;

    public UserDetailResDTO getById(Long userId) throws Exception;

    public List<UserDetailResDTO> getCustomerAll() throws Exception;

    public void delete(Long userId) throws IOException;

    public Long save(CustomerUserAttr userAttr, UserDetailReqDTO userDetailReqDTO) throws Exception;

    public String checkUsreName(String userName) throws Exception;

    public List<PrefixResDTO> getPrefixAll() throws Exception;

    // save Image V2
    public Long updateImageProfileV2(CustomerUserAttr userAttr, MultipartFile file, Long userId) throws IOException, Exception;

    public byte[] getImageByteV2(Long userDetailId) throws IOException, DataFormatException;
}
