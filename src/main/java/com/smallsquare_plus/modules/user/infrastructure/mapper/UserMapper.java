package com.smallsquare_plus.modules.user.infrastructure.mapper;

import com.smallsquare_plus.modules.user.domain.entity.User;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import com.smallsquare_plus.modules.user.web.dto.request.UserUpdateReqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User getUserInfoByUserId(Long userId);

    Long createUser(User user);

    Boolean existsByUsername(String username);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);

    String getPasswordByUsername(String username);

    Long getUserIdByUsername(String username);

    Role getRoleByUserId(Long userId);

    int updateUserInfo(@Param("userId") Long userId, @Param("reqDTO") UserUpdateReqDTO reqDTO);

    int deleteMe(Long userId);
}
