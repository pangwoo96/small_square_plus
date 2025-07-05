package com.smallsquare_plus.modules.user.infrastructure.mapper;

import com.smallsquare_plus.modules.user.domain.entity.User;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Long createUser(User user);

    Boolean existsByUsername(String username);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);

    String getPasswordByUsername(String username);

    Long getUserIdByUsername(String username);

    Role getRoleByUserId(Long userId);

    User getUserInfoByUserId(Long userId);
}
