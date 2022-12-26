package com.qzx.user.mapper;

import com.qzx.user.entity.VoRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author qc
* @description 针对表【vo_role】的数据库操作Mapper
* @createDate 2022-11-25 17:35:02
* @Entity generator.domain.VoRole
*/
@Mapper
public interface VoRoleMapper extends BaseMapper<VoRoleEntity> {

    List<VoRoleEntity> queryRoleList(Long userId);
}




