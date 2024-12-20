package greenart.festival.member.entity;

import greenart.festival.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

    @Entity
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public class Member extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "member_id")
        private Long id;

        @Column(nullable = false, unique = true)
        private String email;

        private String name;

        @Column(nullable = false)
        private String password;

//        private String phoneNumber;

//        private String birthDate;

        @Enumerated(EnumType.STRING)
        @Builder.Default
        private Social provider = Social.NONE;

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @Builder.Default
        private Set<MemberRole> roles = new HashSet<>();


        public void addMemberRole(MemberRole role) {
            roles.add(role);
        }


//        //추가된부분 241203
//        public void setPassword(String password) {
//        }

//        public void setPhoneNumber(String newPhoneNumber) {
//        }

    }