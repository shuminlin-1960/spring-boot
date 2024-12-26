package com.start.spring.springboot_tutorial.dao.db1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityId implements Serializable {
    private String username;
    private String authority;

//    public boolean equals (AuthorityId aid) {
//        return aid.getUsername().equals(this.username) &&
//               aid.getAuthority().equals(this.authority);
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }


}
