package com.spring.javaGroupS.dao;

import org.apache.ibatis.annotations.Param;

public interface AdminDAO {

	int setMemberLevelChange(@Param("idx") int idx, @Param("level") int level);

}
