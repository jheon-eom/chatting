package com.api.chatting.domain.user.repository;

import com.api.chatting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWriteRepository extends JpaRepository<User, Long> {

}
