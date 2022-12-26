package com.qzx.user.mapper;

import com.qzx.user.entity.VoRoleEntity;
import com.qzx.user.entity.VoUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserNormalMapper {


    VoUserEntity queryUserByName(VoUserEntity user);


}
