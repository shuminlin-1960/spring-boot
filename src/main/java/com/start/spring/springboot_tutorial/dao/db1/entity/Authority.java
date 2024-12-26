package com.start.spring.springboot_tutorial.dao.db1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="authority")
@NoArgsConstructor
@AllArgsConstructor
//@IdClass(AuthorityId.class)
public class Authority {
    @Id
    @Column(name = "authority", nullable=false, length=50)
    private  String authority;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", nullable=false)
    private User user;


}
