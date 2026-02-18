package com.mix.sale.next.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import com.mix.sale.next.dto.res.PrefixResDTO;
import com.mix.sale.next.entity.PrefixEntity;
import com.mix.sale.next.repository.PrefixRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mix.sale.next.dto.req.UserDetailReqDTO;
import com.mix.sale.next.dto.res.UserDetailResDTO;
import com.mix.sale.next.entity.AuthenticationEntity;
import com.mix.sale.next.entity.UserDetailEntity;
import com.mix.sale.next.payload.CustomerUserAttr;
import com.mix.sale.next.repository.AuthenticationRepository;
import com.mix.sale.next.repository.UserDetailRepository;
import com.mix.sale.next.service.UserService;
import com.mix.sale.next.util.Constants;
import com.mix.sale.next.util.DateUtil;
import com.mix.sale.next.util.FunctionUtil;
import com.mix.sale.next.util.ImgUtils;
import com.mix.sale.next.util.Md5Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    UserDetailRepository userDeatilRepository;

    @Autowired
    PrefixRepository prefixRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {

                return authenticationRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long updateImageProfile(CustomerUserAttr userAttr, MultipartFile file, Long userId) throws IOException, Exception {
        Long response = null;
        if (null != file && null != userId) {

            UserDetailEntity userDtEntity = userDeatilRepository.findByUserId(userId);

            if (ObjectUtils.isNotEmpty(userDtEntity)) {

                if (StringUtils.isNotBlank(userDtEntity.getUserImageName())) {
                    ImgUtils.deleteFileV2(userDtEntity.getUserImageName(), userDtEntity.getUserImagePath());
                }

                String fileName = ImgUtils.getFileNameImages(file);
                // userDtEntity.setUserImage(ImgUtils.compressImage(file.getBytes()));
                // File fileProfile = new File(file.getOriginalFilename());
                ImgUtils.saveFileV2(file, fileName, Constants.PATH_IMAGES.PATH_FOLDER_PROFILE);

                userDtEntity.setUserImageName(fileName);
                userDtEntity.setUserImagePath(ImgUtils.getPathInput(Constants.PATH_IMAGES.PATH_FOLDER_PROFILE));

                userDeatilRepository.save(userDtEntity);
                response = userDtEntity.getId();
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getImageByte(Long userDetailId) throws IOException, DataFormatException {

        UserDetailEntity userImage = userDeatilRepository.findById(userDetailId).get();

        if (ObjectUtils.isNotEmpty(userImage)) {
            if (StringUtils.isNotBlank(userImage.getUserImageName())) {

                String path = userImage.getUserImagePath();
                Path imagePath = Paths.get(path, userImage.getUserImageName());

                return Files.readAllBytes(imagePath);
            }
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long updateProfile(CustomerUserAttr userAttr, UserDetailReqDTO userDetailReqDTO, Long userId) throws Exception {
        Long response = null;
        if (ObjectUtils.isNotEmpty(userDetailReqDTO) && null != userId) {

            UserDetailEntity userDtEntity = userDeatilRepository.findByUserId(userId);

            if (ObjectUtils.isNotEmpty(userDtEntity)) {
                userDtEntity.setPrefixId(userDetailReqDTO.getPrefixId());
                userDtEntity.setFristName(userDetailReqDTO.getFristName());
                userDtEntity.setLastName(userDetailReqDTO.getLastName());
                userDtEntity.setNickName(userDetailReqDTO.getNickName());
                userDtEntity.setEmail(userDetailReqDTO.getEmail());
                userDtEntity.setTelephone(userDetailReqDTO.getTelephone());
                userDtEntity.setHouseNo(userDetailReqDTO.getHouseNo());
                userDtEntity.setVillageNo(userDetailReqDTO.getVillageNo());
                // userDtEntity.setAlley(userDetailReqDTO.getAlley());
                // userDtEntity.setLane(userDetailReqDTO.getLane());
                userDtEntity.setRoad(userDetailReqDTO.getRoad());
                userDtEntity.setGeographiesId(userDetailReqDTO.getGeographiesId());
                userDtEntity.setDistrictsId(userDetailReqDTO.getDistrictsId());
                userDtEntity.setAmphuresId(userDetailReqDTO.getAmphuresId());
                userDtEntity.setProvincesId(userDetailReqDTO.getProvincesId());

                userDtEntity.setUpdateBy(userAttr.getCustomerNo());
                userDtEntity.setUpdateDate(DateUtil.createTimestmapNow());
                userDeatilRepository.save(userDtEntity);

                response = userDtEntity.getUserId();
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailResDTO getById(Long userId) throws Exception {
        UserDetailResDTO resp = null;
        if (null != userId) {
            UserDetailEntity userDtEntity = userDeatilRepository.findByUserId(userId);
            resp = mapper.map(userDtEntity, new TypeToken<UserDetailResDTO>() {
            }.getType());

            resp.setAddress(genAddress(resp));
        }

        return resp;
    }

    public String genAddress(UserDetailResDTO data) {

        StringBuilder result = new StringBuilder();

        if (StringUtils.isNotBlank(data.getHouseNo())) {
            result.append("บ้านเลขที่ ");
            result.append(data.getHouseNo());
        }

        if (StringUtils.isNotBlank(data.getVillageNo())) {
            result.append(" หมู่ที่ ");
            result.append(data.getVillageNo());
        }

        if (StringUtils.isNotBlank(data.getRoad())) {
            result.append(" ถนน ");
            result.append(data.getRoad());
        }

        if (StringUtils.isNotBlank(data.getDistrictsName())) {
            result.append(" ตำบล ");
            result.append(data.getDistrictsName());
        }

        if (StringUtils.isNotBlank(data.getAmphuresName())) {
            result.append(" อำเภอ ");
            result.append(data.getAmphuresName());
        }

        if (StringUtils.isNotBlank(data.getProvincesName())) {
            result.append(" จังหวัด ");
            result.append(data.getProvincesName());
        }

        if (null != data.getZipCode()) {
            result.append(" รหัสไปรษณีย์ ");
            result.append(data.getZipCode());
        }

        return result.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailResDTO> getCustomerAll() throws Exception {
        List<UserDetailResDTO> resp = null;

        List<AuthenticationEntity> authenEntityList = authenticationRepository.findAllByCustomer();

        if (CollectionUtils.isNotEmpty(authenEntityList)) {
            resp = new ArrayList<>();
            for (AuthenticationEntity authen : authenEntityList) {

                UserDetailResDTO userDt = mapper.map(userDeatilRepository.findByUserId(authen.getId()), new TypeToken<UserDetailResDTO>() {
                }.getType());

                resp.add(userDt);
            }
        }
        return resp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long userId) throws IOException {

        // UserDetailEntity userDtEntity = userDeatilRepository.findByUserId(userId);
        // if (StringUtils.isNotBlank(userDtEntity.getUserImageName())) {
        //     ImgUtils.deleteFileV2(userDtEntity.getUserImageName(), userDtEntity.getUserImagePath());
        // }
        authenticationRepository.deleteByUserId(userId);
        userDeatilRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long save(CustomerUserAttr userAttr, UserDetailReqDTO userDetailReqDTO) throws Exception {

        Long response = null;
        if (ObjectUtils.isNotEmpty(userDetailReqDTO)) {

            if ("99".equals(checkUsreName(userDetailReqDTO.getUserName()))) {
                return null;
            }

            AuthenticationEntity authenEntity = new AuthenticationEntity();

            authenEntity.setUserName(userDetailReqDTO.getUserName());
            authenEntity.setPassword(Md5Util.genarateMd5(userDetailReqDTO.getPassword()));
            authenEntity.setRoleId(Constants.USER.ROLE_CUSTOMER);
            authenEntity.setStatus(Constants.STATUS_NORMAL);
            authenEntity.setCreateBy(userAttr.getCustomerNo());
            authenEntity.setCreateDate(DateUtil.createTimestmapNow());

            authenEntity = authenticationRepository.save(authenEntity);

            if (ObjectUtils.isNotEmpty(authenEntity)) {
                UserDetailEntity userDtEntity = new UserDetailEntity();

                userDtEntity.setUserId(authenEntity.getId());
                userDtEntity.setCustomerNo(FunctionUtil.genarateCustomerNo(authenticationRepository.findAllByCustomer().size(), Constants.USER.USER_PREFIX));
                userDtEntity.setPrefixId(userDetailReqDTO.getPrefixId());
                userDtEntity.setFristName(userDetailReqDTO.getFristName());
                userDtEntity.setLastName(userDetailReqDTO.getLastName());
                userDtEntity.setNickName(userDetailReqDTO.getNickName());
                userDtEntity.setEmail(userDetailReqDTO.getEmail());
                userDtEntity.setTelephone(userDetailReqDTO.getTelephone());
                userDtEntity.setHouseNo(userDetailReqDTO.getHouseNo());
                userDtEntity.setVillageNo(userDetailReqDTO.getVillageNo());
                userDtEntity.setRoad(userDetailReqDTO.getRoad());
                userDtEntity.setDistrictsId(userDetailReqDTO.getDistrictsId());
                userDtEntity.setAmphuresId(userDetailReqDTO.getAmphuresId());
                userDtEntity.setProvincesId(userDetailReqDTO.getProvincesId());
                userDtEntity.setStatus(Constants.STATUS_NORMAL);
                userDtEntity.setCreateBy(userAttr.getCustomerNo());
                userDtEntity.setCreateDate(DateUtil.createTimestmapNow());
                userDeatilRepository.save(userDtEntity);
            }

            response = authenEntity.getId();
        }
        return response;
    }

    @Override
    public String checkUsreName(String userName) throws Exception {
        String resp = "0";

        Optional<AuthenticationEntity> authen = authenticationRepository.findByUserName(userName);
        if (authen.isPresent()) {
            resp = "99";
        }

        return resp;
    }

    @Override
    public List<PrefixResDTO> getPrefixAll() throws Exception {
        List<PrefixResDTO> resp = null;

        List<PrefixEntity> prefixEntityList = prefixRepository.findAll();
        if (CollectionUtils.isNotEmpty(prefixEntityList)) {
            resp = mapper.map(prefixEntityList, new TypeToken<List<PrefixResDTO>>() {
            }.getType());
        }

        return resp;
    }

    @Override
    public Long updateImageProfileV2(CustomerUserAttr userAttr, MultipartFile file, Long userId) throws IOException, Exception {
        Long response = null;
        if (null != file && null != userId) {

            UserDetailEntity userDtEntity = userDeatilRepository.findByUserId(userId);

            if (ObjectUtils.isNotEmpty(userDtEntity)) {

                String fileName = ImgUtils.getFileNameImages(file);
                userDtEntity.setUserImageBlob(ImgUtils.compressImage(file.getBytes()));
                userDtEntity.setUserImageName(fileName);
                userDtEntity.setUserImagePath(ImgUtils.getPathInput(Constants.PATH_IMAGES.PATH_FOLDER_PROFILE));

                userDeatilRepository.save(userDtEntity);
                response = userDtEntity.getId();
            }
        }
        return response;
    }

    @Override
    public byte[] getImageByteV2(Long userDetailId) throws IOException, DataFormatException {
        UserDetailEntity userImage = userDeatilRepository.findById(userDetailId).get();

        if (ObjectUtils.isNotEmpty(userImage)) {
            if (StringUtils.isNotBlank(userImage.getUserImageName())) {

                return ImgUtils.decompressImage(userImage.getUserImageBlob());
            }
        }

        return null;
    }
}
