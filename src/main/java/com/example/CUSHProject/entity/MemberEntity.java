package com.example.CUSHProject.entity;

import com.example.CUSHProject.enums.Gender;
import com.example.CUSHProject.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="MEMBER")
@SequenceGenerator(
        name ="MEMBER_SEQ_GEN",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GEN")
    @Column(name="MEMBER_ID")
    private Long id;

    @NotNull
    @Column(name="EMAIL")
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="USERNAME")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name="GENDER")
    private Gender gender;

    @Column(name="BIRTH")
    private String birth;

    @Column(name="AGE")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Builder
    public MemberEntity(Long id, String username, String password, String nickname, Gender gender, String birth, int age, Role role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birth=birth;
        this.age = age;
        this.role = role;
    }

}